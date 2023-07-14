% Tutorial 2.1 - Homework 1
%Code written by CConnor Zawacki
%About: This code simulates a leaky integrate and fire model neuron
%and analyzes the resulting f-I curve.

%NOTE: all "plot" functions are currently commented out. To see their
%products please view the attached pdf

%% Question 1a
%params 
EL = -70e-3; %  Reversal Potentail
Rm = 5e6;    %  Membrane Resistance
Cm = 2e-9;   %  Membrane Capacitance
Vth = -50e-3; %  Threshold Potential
Vres = -65e-3;%  Reset Potential
dt = 0.1e-3; %  Timestep used in calculations
%dt = 0.1e-4 %  Timestep used in question 2c

%Vectors
Tvec = (0:dt:2);
Vmvec = zeros(size(Tvec));
Vmvec(1) = EL;
Io = 0; % This is just a placeholder so I can compile this section of code
Iappvec = ones(size(Tvec))*Io; % Io not yet determined

%Calculations (for loop)
for i = 2:numel(Tvec)
    Vmvec(i) = Vmvec(i-1) + (((EL - Vmvec(i-1))/Rm + Iappvec(i))/Cm)*dt;
    if Vmvec(i)>= Vth
        Vmvec(i) = Vres;
    end
end

%% Question 1b
%Q: What is the minimum applied current needed for a neuron to produce
%spikes?
%A: min applied current = conductance * (difference between threshold
%potential and leak potential)
Ith = (1/Rm) * (Vth - EL); 

%simulation of applied charge just below min
Io = (Ith*0.95);
Iappvec = ones(size(Tvec))*Io;
for i = 2:numel(Tvec)
    Vmvec(i) = Vmvec(i-1) + (((EL - Vmvec(i-1))/Rm + Iappvec(i))/Cm)*dt;
    if Vmvec(i)>= Vth
        Vmvec(i) = Vres;
    end
end


%plot(Tvec(1:2000), Vmvec(1:2000));
%xlabel("Time (s)");
%ylabel("Membrane Potential (V)")
%title("LiF model (no spikes)")
%200ms because there is no ISI for a non firing neuron
%plot(Tvec, Vmvec) % this is here in case you wanted the whole thing

%simulation of applied charge just above min
Io = (Ith*1.01);
Iappvec = ones(size(Tvec))*Io;
for i = 2:numel(Tvec)
    Vmvec(i) = Vmvec(i-1) + (((EL - Vmvec(i-1))/Rm + Iappvec(i))/Cm)*dt;
    if Vmvec(i)>= Vth
        Vmvec(i) = Vres;
    end
end

%plot(Tvec(1:2000), Vmvec(1:2000));
%xlabel("Time (s)");
%ylabel("Membrane Potential (V)")
%title("LiF model (few spikes)")
%plot(Tvec, Vmvec) % this is here in case you wanted the whole thing

%% Question 1c
%Simulate at least 10 different values of Iapp such that firing rate varies
%from 0-100 Hz

%after some experimentation, 1.45*Ith produces about 100 Hz, so to solve
%the question we make a vec storing a uniform range of values between Ith
%and Ith*1.45 and a corresponding firing rate vec of the same size

%this implementation may look strange, but it produces a 20 element vector 
% with values uniformly sclaing from Ith to Ith*1.45 for better looking
%smoother plots, replace the "19" figure below with "n" where 
% n = (desired data points)-1

Iovec = Ith:((Ith*1.45)-Ith)/19:(Ith*1.45); 
firevec = zeros(size(Iovec));

%To update each value of the above vectors we must loop their values
%through our "trial" logic, updating spike numbers in each trial
%individually 
for k=1:numel(Iovec)
    trialspikes = 0;
    Io = (Iovec(k));
    Iappvec = ones(size(Tvec))*Io;
    for i = 2:numel(Tvec)
        Vmvec(i) = Vmvec(i-1) + (((EL - Vmvec(i-1))/Rm + Iappvec(i))/Cm)*dt;
        if Vmvec(i)>= Vth
            Vmvec(i) = Vres;
            trialspikes = trialspikes+1;
        end
    end
    firevec(k) = trialspikes/2; %simulation is 2 seconds long, we want fires per second
end
%plot(Iovec,firevec, 'o');
%xlabel("Applied Current (V)");
%ylabel("Fire Rate (Hz)")
%title("LiF model f-i curve")
%hold on
%% Question 1d
%Q: Compare by plotting with different symbols on the same graph produced
%in 1c the curve you obtain from the equation below for the firng rate of
%the neuron as you vary injected current

altfirevec = zeros(size(firevec));

for i = 1:numel(Iovec)
    if Iovec(i)>Ith % this should ensure that we aren't taking ln of a neg number or 0
        ISI = (Rm*Cm)*log(Iovec(i)*Rm+EL-Vres) - (Rm*Cm)*log(Iovec(i)*Rm+EL-Vth);
        altfirevec(i)= 1/ISI ;

    end
end
%plot(Iovec, altfirevec, 'v')


%% Question 2a + 2b
%Add a noise term to the simulation of Q1 by adding to the total membrane
%potential change at each time step a noise term
%Plot firingrate curve as a function of Iapp for at least 2 values of
%sigma_I. Explain the effect of increasing sigma_I

%code adapted from question 1c. the one difference is the addition of a
%noise term. Triple nested for loop is very ugly, but it was the simplest
%solution I could think of.

Iovec = Ith:((Ith*1.45)-Ith)/19:(Ith*1.45); 
firevec = zeros(size(Iovec));
sigmavec = [0, 10, 30]*0.001; 
for j=1:numel(sigmavec)
sigma=sigmavec(j);
for k=1:numel(Iovec)
    trialspikes = 0;
    Io = (Iovec(k));
    Iappvec = ones(size(Tvec))*Io;
    for i = 2:numel(Tvec)
        Vmvec(i) = Vmvec(i-1) + (((EL - Vmvec(i-1))/Rm + Iappvec(i))/Cm)*dt + (randn(1)*sigma*sqrt(dt));
        if Vmvec(i)>= Vth
            Vmvec(i) = Vres;
            trialspikes = trialspikes+1;
        end
    end
    firevec(k) = trialspikes/2; %simulation is 2 seconds long, we want fires per second
end
plot(Iovec,firevec);
xlabel("Applied Current")
ylabel("Firing Rate");
title("LiF model f-i curve with noise")
hold on
end


%% Question 2c

%as opposed to copying and pasting code for this question as I have above,
%it is far simpler to just swap the variable manually. I have included a
%commented section of code near where dt was first initialized
%corresponding to the swap

%Q: Test that your code is correct by repeating a smiulation with your
%time-step, dt, a factor of ten smaller. Are the results very different?

%A: See pdf

