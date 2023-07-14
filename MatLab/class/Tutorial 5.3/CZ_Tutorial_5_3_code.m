% Tutorial 5.3 - Homework 6
%Code written by Connor Zawacki
%About: This code combines two leaky integrate and fire models into a
%simple circuit that can produce bistability or oscillations depending on
%values of noise and depression parameters

%% part Ai + Aii

C = 1e-9;           % capacitance
R = 10e6;           % resistance
EL = -70e-3;        % leak/reversal potential
E_12 = -70e-3;      % reversal potential syn 1 to 2
E_21 = -70e-3;      % reversal potential syn 2 to 1
Vth = -54e-3;       % spiking threshold
VR = -80e-3;        % reset potential
G_12 = 1e-6;        % maximum conductance syn 1 to 2
G_21 = 1e-6;        % maximum conductance syn 2 to 1
tao_syn = 10e-3;    % synaptic time constant
tao_dep = 0.2;        % depression time constant, not relavant until part B
pR = 0.2;             % probability of release. 0.2 for part B

Tmax = 6;
dt = 10e-6; 
tvec = 0:dt:Tmax;
baseline = 2e-9;
Iapp1 = ones(size(tvec))*baseline;
Iapp2 = ones(size(tvec))*baseline;
Iapp1(1:(100e-3/dt)) = baseline + 3e-9;
Iapp2(ceil(numel(tvec)/2):ceil(numel(tvec)/2)+(100e-3/dt)) = baseline + 3e-9;
s1vec = zeros(size(tvec));
s2vec = zeros(size(tvec));
dep1vec = zeros(size(tvec));
dep1vec(1) = 1;
dep2vec = dep1vec;
Vm1vec = zeros(size(tvec));
Vm1vec(1) = -70e-3;
Vm2vec = Vm1vec;
Vm2vec(1)= -65e-3;
sig = 0; %amplitude of the noise
noise1 = randn(size(tvec))*sig*sqrt(dt);
noise2 = randn(size(tvec))*sig*sqrt(dt);


for i=2:numel(tvec)
    s1vec(i) = s1vec(i-1) + dt*(-(s1vec(i-1)/tao_syn));
    s2vec(i) = s2vec(i-1) + dt*(-(s2vec(i-1)/tao_syn));

    %use the following 2 lines for part A
    %dep1vec(i) = 1;
    %dep2vec(i) = 1;

    %use the following 2 lines for part B
    dep1vec(i) = dep1vec(i-1) + dt*((1-dep1vec(i-1))/tao_dep);
    dep2vec(i) = dep2vec(i-1) + dt*((1-dep2vec(i-1))/tao_dep);

    Vm1vec(i) = Vm1vec(i-1) + dt/C*(...
        ((EL - Vm1vec(i-1))/R)+ ...
        (G_21*s2vec(i-1)*(E_21-Vm1vec(i-1))) + ...
        Iapp1(i) +noise1(i));

    Vm2vec(i) = Vm2vec(i-1) + dt/C*(...
        ((EL - Vm2vec(i-1))/R)+ ...
        (G_12*s1vec(i-1)*(E_12-Vm2vec(i-1))) + ...
        Iapp2(i) +noise2(i));

    if Vm1vec(i)>Vth
        Vm1vec(i) = VR;
        s1vec(i) = s1vec(i-1)+ pR*dep1vec(i-1)*(1-s1vec(i-1));
        dep1vec(i) = dep1vec(i-1)*(1-pR);%only use this line for part B
    end
    if Vm2vec(i)>Vth
        Vm2vec(i) = VR;
        s2vec(i) = s2vec(i-1)+ pR*dep2vec(i-1)*(1-s2vec(i-1));
        dep2vec(i) = dep2vec(i-1)*(1-pR);%only use this line for part B
    end
end

figure(1)
subplot (2,1,1), plot(tvec, Vm1vec), xlabel("Time"), ylabel("Vm"), title("Neuron 1")
subplot (2,1,2), plot(tvec, Vm2vec), xlabel("Time"), ylabel("Vm"), title("Neuron 2")

%% part Aiii

%commented out when running part b problems
pR=1;
sig = 5e-11; %5e-12 for part B
noise1 = randn(size(tvec))*sig/sqrt(dt);
noise2 = randn(size(tvec))*sig/sqrt(dt);
Iapp2 = ones(size(tvec))*baseline;
Iapp1= ones(size(tvec))*baseline;

for i=2:numel(tvec)
    s1vec(i) = s1vec(i-1) + dt*(-(s1vec(i-1)/tao_syn));
    s2vec(i) = s2vec(i-1) + dt*(-(s2vec(i-1)/tao_syn));

    %use the following 2 lines for part A
    dep1vec(i) = 1;
    dep2vec(i) = 1;

    %use the following 2 lines for part B
    %dep1vec(i) = dep1vec(i-1) + dt*((1-dep1vec(i-1))/tao_dep);
    %dep2vec(i) = dep2vec(i-1) + dt*((1-dep2vec(i-1))/tao_dep);

    Vm1vec(i) = Vm1vec(i-1) + dt/C*(...
        ((EL - Vm1vec(i-1))/R)+ ...
        (G_21*s2vec(i-1)*(E_21-Vm1vec(i-1))) + ...
        Iapp1(i) +noise1(i));

    Vm2vec(i) = Vm2vec(i-1) + dt/C*(...
        ((EL - Vm2vec(i-1))/R)+ ...
        (G_12*s1vec(i-1)*(E_12-Vm2vec(i-1))) + ...
        Iapp2(i) +noise2(i));

    if Vm1vec(i)>Vth
        Vm1vec(i) = VR;
        s1vec(i) = s1vec(i-1)+ pR*dep1vec(i-1)*(1-s1vec(i-1));
        dep1vec(i) = dep1vec(i-1)*(1-pR);%only used for part B
    end
    if Vm2vec(i)>Vth
        Vm2vec(i) = VR;
        s2vec(i) = s2vec(i-1)+ pR*dep2vec(i-1)*(1-s2vec(i-1));
        dep2vec(i) = dep2vec(i-1)*(1-pR);%only used for part B
    end
end

figure(2)
subplot (2,1,1), plot(tvec, Vm1vec), xlabel("Time"), ylabel("Vm"), title("Neuron 1")
subplot (2,1,2), plot(tvec, Vm2vec), xlabel("Time"), ylabel("Vm"), title("Neuron 2")
%}
%% part Aiv
 
 sig = 5e-12; %sig = 5e-11 for part Aiv, and 5e-12 for part B
 swaps = 0;
 swaptimes=1:1001;
 iterations = 0; %a counter variable to keep track of the number of times we enter the for loop
 state = -1; %if neuron 1 is active, state = 1, if neuron 2, state = 2

while swaps<1005
noise1 = randn(size(tvec))*sig/sqrt(dt);
noise2 = randn(size(tvec))*sig/sqrt(dt);
for i=2:numel(tvec)
    s1vec(i) = s1vec(i-1) + dt*(-(s1vec(i-1)/tao_syn));
    s2vec(i) = s2vec(i-1) + dt*(-(s2vec(i-1)/tao_syn));

    %use the following 2 lines for part A
    %dep1vec(i) = 1;
    %dep2vec(i) = 1;

    %use the following 2 lines for part B
    dep1vec(i) = dep1vec(i-1) + dt*((1-dep1vec(i-1))/tao_dep);
    dep2vec(i) = dep2vec(i-1) + dt*((1-dep2vec(i-1))/tao_dep);

    Vm1vec(i) = Vm1vec(i-1) + dt/C*(...
        ((EL - Vm1vec(i-1))/R)+ ...
        (G_21*s2vec(i-1)*(E_21-Vm1vec(i-1))) + ...
        Iapp1(i) +noise1(i));

    Vm2vec(i) = Vm2vec(i-1) + dt/C*(...
        ((EL - Vm2vec(i-1))/R)+ ...
        (G_12*s1vec(i-1)*(E_12-Vm2vec(i-1))) + ...
        Iapp2(i) +noise2(i));

    if Vm1vec(i)>Vth
        Vm1vec(i) = VR;
        s1vec(i) = s1vec(i-1)+ pR*dep1vec(i-1)*(1-s1vec(i-1));
        dep1vec(i) = dep1vec(i-1)*(1-pR);%only used for part B
        if state~=1
            state=1;
            swaps = swaps+1;
            swaptimes(swaps)=i+(iterations*numel(tvec));
        end
    end
    if Vm2vec(i)>Vth
        Vm2vec(i) = VR;
        s2vec(i) = s2vec(i-1)+ pR*dep2vec(i-1)*(1-s2vec(i-1));
        dep2vec(i) = dep2vec(i-1)*(1-pR);
        if state~=2
            state=2;
            swaps = swaps+1;
            swaptimes(swaps)=i+(iterations*numel(tvec));
        end
    end
end
iterations=iterations+1;
s1vec(1)=s1vec(i);
s2vec(1)=s2vec(i);
Vm1vec(1) = Vm1vec(i);
Vm2vec(1)=Vm2vec(i);
dep1vec(1)=dep1vec(i);
dep2vec(1)=dep2vec(i); %these resets should allow us to loop through the simulation as if nothing had happended at the end of every iteration of tvec
end


swaptimes = swaptimes*dt;
durations = diff(swaptimes);
figure(3)
histogram(durations, 100), xlabel("duration"), ylabel("occurences");

%% Part B

% part B involves the same code as above with slightly different values, as
% opposed to repasting the above section, listed below and commented above
% are the necessary parameters for part v and vi

%pR=0.2;
%tao_dep=0.2

%sig = 5e-12
%}