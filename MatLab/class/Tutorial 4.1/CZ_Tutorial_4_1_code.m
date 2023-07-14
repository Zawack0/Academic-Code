% Tutorial 4.1 - Homework 4
%Code "written by" Connor Zawacki (see note)
%About: This code simulates the Hodgkin Huxley model's response to varied
%levels of applied current


%NOTE: Large portions of code were adapted from the online code "HH_new_zap.m"
%For more in depth discussion of solutions and answers to question, see
%attached pdf

%% question 1a

%params
G_NaMax = 12e-6;  % max sodium conductance
G_KMax = 3.6e-6;  % max delayed rectifier conductance
EL=-60e-3;        % leak reversal potential
E_Na = 0.045;     % reversal for sodium channels
E_K = -0.082;     % reversal for potassium channels
G_L = 30e-9;      % leak conductance
Cm = 100e-12;     % Membrane capacitance

dt= 0.02e-6;
tmax = 0.35;
tvec = 0:dt:tmax;          % time vector
Vmvec = zeros(size(tvec)); % Membrane potential vector
Vmvec(1)=-65e-3;           % varies, EL for 1b+c, -65e-3 for 1d+
n=zeros(size(tvec));       % n: potassium activation gating variable
%n(1) = 0.35;               %varies, 0 for all but 1d+e, where it is 0.35
m=zeros(size(tvec));       % m: sodium activation gating variable
%m(1) = 0.05;               %varies, 0 for all but 1d+e, where it is 0.05
h=zeros(size(tvec));       % h: sodim inactivation gating variable
%h(1) = 0.5;                %varies, 0 for all but 1d+e, where it is 0.5
baseline = 0.7e-9;         % varies, 0 for 1b+c, 0.6e-9 for 1d, 0.65e-9 for 1e, 0.7e-9 for 1f
Iappvec = ones(size(tvec))*baseline;% applied charge
Itot=zeros(size(tvec));    % record the total current
I_Na=zeros(size(tvec));    % record sodium curret
I_K=zeros(size(tvec));     % record potassium current
I_L=zeros(size(tvec));     % record leak current

%for 1b
%Iappvec(100e-3/dt:2*100e-3/dt)=0.22e-9;

%for 1c and beyond
delay = 100e-3;                    % delay between pulses varies for each question
pulse_amp = 1e-9;                 %charge of the pulse varies for each question
index = floor(100e-3/dt);          %starting position for pulses in tvec
pulse_size=5e-3;                  %how long the pulses last
maxpulse = 1;                    %number of pulses to be included (varies)
for i = 1:maxpulse
    Iappvec(index:(index+floor(pulse_size/dt)))=pulse_amp;
    index = index + floor((pulse_size/dt) +(delay/dt));
end

for i = 2:numel(tvec)      % simulation of Hodgkin-Huxley model adapted from HH_new_zap.m
            Vm = Vmvec(i-1);          % membrane potential for calculations
        % Sodium and potassium gating variables are defined by the
        % voltage-dependent transition rates between states, labeled alpha and
        % beta.
        % First, sodium activation rate
        if ( Vm == -0.045 )     % to avoid dividing zero by zero
            alpha_m = 1e3;      % value calculated analytically
        else
            alpha_m = (1e5*(-Vm-0.045))/(exp(100*(-Vm-0.045))-1);
        end
        beta_m = 4000*exp((-Vm-0.070)/0.018);   % Sodium deactivation rate
        alpha_h = 70*exp(50*(-Vm-0.070));       % Sodium inactivation rate
        beta_h = 1000/(1+exp(100*(-Vm-0.040))); % Sodium deinactivation rate
        if ( Vm == -0.060)      % to avoid dividing by zero
            alpha_n = 100;      % value calculated analytically
        else;                   % potassium activation rate
            alpha_n = (1e4*(-Vm-0.060))/(exp(100*(-Vm-0.060))-1);
        end
        beta_n = 125*exp((-Vm-0.070)/0.08);     % potassium deactivation rate
        % From the alpha and beta for each gating variable we find the steady
        % state values (_inf) and the time constants (tau_) for each m,h and n.
        tau_m = 1/(alpha_m+beta_m);
        m_inf = alpha_m/(alpha_m+beta_m);
        tau_h = 1/(alpha_h+beta_h);
        h_inf = alpha_h/(alpha_h+beta_h);
        tau_n = 1/(alpha_n+beta_n);
        n_inf = alpha_n/(alpha_n+beta_n);
        % Update gating variables using the Forward Euler method
        m(i) = m(i-1) + (m_inf-m(i-1))*dt/tau_m;    % Update m
        h(i) = h(i-1) + (h_inf-h(i-1))*dt/tau_h;    % Update h        
        n(i) = n(i-1) + (n_inf-n(i-1))*dt/tau_n;    % Update n
        % Calculate currents from gating variables
        I_Na(i) = G_NaMax*m(i)*m(i)*m(i)*h(i)*(E_Na-Vmvec(i-1)); % total sodium current        
        I_K(i) = G_KMax*n(i)*n(i)*n(i)*n(i)*(E_K-Vmvec(i-1)); % total potassium current        
        I_L(i) = G_L*(EL-Vmvec(i-1));    % Leak current is straightforward        
        Itot(i) = I_L(i)+I_Na(i)+I_K(i)+Iappvec(i); % total current is sum of leak + active channels + applied current
        % Update membrane potential
        Vmvec(i) = Vmvec(i-1) + Itot(i)*dt/Cm;        % Update the membrane potential, V.
end
%plot(tvec,Vmvec), ylabel("Vm"), xlabel("Time")
%with 0 input current, model stabalizes at -70.2e-3

%for 1b:
%figure(1)
%subplot(2,1,1), plot(tvec,Iappvec), ylabel("Iapp"), axis([0.05 0.25 0 3e-10])
%subplot(2,1,2), plot(tvec,Vmvec), ylabel("Vm"), xlabel("Time"), axis([0.05 0.25 -0.1 -0.05])

%for 1c:
figure(1)
subplot(2,1,1), plot(tvec,Iappvec), ylabel("Iapp"),
subplot(2,1,2), plot(tvec,Vmvec), ylabel("Vm"), xlabel("Time")

%% question 1b
% to avoid copy and pasting the entire simulation above, I will write here
% the code to produce the desired Iappvec, and any other code required for
% the question. It will also be included above, commented out in the final
% version

% Iappvec(100e-3/dt:2*100e-3/dt)=0.22e-9;
%figure(1)
%subplot(2,1,1), plot(tvec,Iappvec)
%subplot(2,1,2), plot(tvec,Vmvec)

%% question 1c
% as above (see 1b), except with some exceptions. questions 1d, 1e, and 1f
% require slightly different values of the same code below. 

%Iappvec=zeros(size(tvec));
%delay = 5e-3;             % use 5e-3, 10e-3, and 20e-3
%pulse_amp = 0.22e-9;      %charge of the pulse
%index = 1;                %starting position for pulses in tvec
%pulse_size=10e-3;         %how long the pulses last
%maxpulse = 10;            %number of pulses to be included
%for i = 1:maxpulse
%    Iappvec(index:(index+floor(pulse_size/dt)))=pulse_amp;
%    index = index + floor((pulse_size/dt) +(delay/dt));
%end



