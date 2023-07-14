% Tutorial 8.2 - Homework 9
%Code written by Connor Zawacki
%About: simulates batch method standard STDP by providing a LIF model
%neuron with random input from 50 presynaptic spike trains, training the
%system over the course of 200 trials. Results are then compared with
%similar simulations with changing phase offsets and correlated vs
%uncorrelated stimuli

%Question 1
rmax = 60; % mas firing rate
phi_a = 0; %phase offset
phi_b = pi;%alt phase offset
v = 20;    %frequency of oscillation
dt = 0.1e-3;
tmax = 0.5;

rates = zeros(50,tmax/dt);
tvec = dt:dt:tmax;
for i = 1:50
    if i <=25
        rates(i,:)= rmax/2*(1+sin(2*pi*tvec+phi_a));
    else
        rates(i,:)=rmax/2*(1+sin(2*pi*tvec+phi_b));
    end
end
spikes = rand(50,tmax/dt)<rates*dt;
init_str = (500e-12 +25e-12.*randn(1,50));
Gsyn = zeros(size(rates));
tao_syn=2e-3;
for i = 1:50
    for j = 2:numel(tvec)
        if spikes(i,j)==1
            Gsyn(i,j)=Gsyn(i,j)+init_str(i);
        else
            Gsyn(i,j)=Gsyn(i,j-1) + dt*(-Gsyn(i,j-1)/tao_syn);
        end
    end
end

%Question 2
