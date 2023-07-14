% Tutorial 6.3 - Homework 7
%Code written by Connor Zawacki
%About: This code simulates a firing rate model of an Exitatory-Inhibitory 
%Coupled unin oscillator and PING. Also Observes the impact of varyation of
%external input and analysis using Fourier transform

%% Question 4 and 5

%the assignment requires varyation of input on each unit and then a
%thourough analysis of its impacts. Questions 1-3 are for a single trial,
%but 4 and 5 require the repetition of all previous steps for range of Gin1
%and Gin2

Question4=0:0.1e-9:10e-9;
Question5=0:0.1e-12:25e-12;
Gin1trials=zeros(1,1+numel(Question5)+numel(Question4));
Gin2trials=Gin1trials;
Gin1trials(1)=1e-9;
Gin2trials(1)=0;
Gin1trials(2:numel(Question4)+1) = Question4;
Gin1trials(numel(Question4)+2:numel(Gin2trials))=2e-9;
Gin2trials(2:numel(Question4)+1)=0;
Gin2trials(numel(Question4)+2:numel(Gin2trials))=Question5;

meanfrvec1=zeros(size(Gin2trials));
meanfrvec2=meanfrvec1;
oscil_freqvec = meanfrvec1;
oscil_ampvec1 = meanfrvec1;
oscil_ampvec2 = meanfrvec1;



for z = 1:numel(Gin1trials)
% Question 1
Vth = -50e-3;     %Threshold Potential
Vreset = -80e-3;  %Reset Potential
sig = 1e-3;       %noise in membrane potential
tao_V = 3e-3;     %Membrane time constant
tao_E = 2e-3;     %Excitatory synaptic time constant
tao_I = 5e-3;     %Inhibitory synaptic time constant
EL = -70e-3;      %Leak Reversal Potential
EI = -65e-3;      %Inhibitory Reversal Potential
EE = 0e-3;        %Excitatory Reversal Potential
GL = 50e-12;      %Leak Conductance
WEE = 25e-9;      %Self Excitatory Connection
WEI = 4e-9;       %Excitatory to Inhibitory unit connection
WIE = 800e-9;     %Inhibitory to Excitatory unit connection
Gin1 = Gin1trials(z);         %Excitatory input conductance 1
Gin2 = Gin2trials(z);         %Excitatory input conductance 1
alpha = 0.2;

tmax = 2.5;
dt = 0.1e-3;
tvec = 0:dt:tmax;
Vss1_vec = zeros(size(tvec));%instantaneious steady membrane potential in absence of spiking
Vss2_vec = zeros(size(tvec));%instantaneious steady membrane potential in absence of spiking
Givec_1 = zeros(size(tvec)); %cell 1 inhibitory conductance
Givec_2 = zeros(size(tvec)); %cell 2 is only excitatory, this vec should never change
Gevec_1 = zeros(size(tvec)); %cell 1 excitatory conductance
Gevec_2 = zeros(size(tvec)); %cell 2 excitatory conductance
FRFvec_1 = zeros(size(tvec));%firing rate function of cell 1
FRFvec_2 = zeros(size(tvec));%firing rate function of cell 2
Frvec_1 = zeros(size(tvec)); %firing rate of cell 1
Frvec_2 = zeros(size(tvec)); %firing rate of cell 2
synEvec = zeros(size(tvec)); %exitatory synaptic variable
synIvec = zeros(size(tvec)); %inhibitory synaptic variable

for i = 2:numel(tvec)
    synEvec(i) = synEvec(i-1) + dt*((-synEvec(i-1)/tao_E)+alpha*Frvec_1(i-1)*(1-synEvec(i-1)));

    synIvec(i) = synIvec(i-1) + dt*((-synIvec(i-1)/tao_I)+alpha*Frvec_2(i-1)*(1-synIvec(i-1)));

    Gevec_1(i) = WEE*synEvec(i)+Gin1;
    Givec_1(i) = WIE*synIvec(i);
    Gevec_2(i) = WEI*synEvec(i)+Gin2;
    Givec_2(i) = 0;

    Vss1_vec(i) = (GL*EL + EI*Givec_1(i)+EE*Gevec_1(i))/(GL+Givec_1(i)+Gevec_1(i));
    Vss2_vec(i) = (GL*EL + EI*Givec_2(i)+EE*Gevec_2(i))/(GL+Givec_2(i)+Gevec_2(i));

    if Vss1_vec(i)==Vth
        FRFvec_1(i)=sig/(tao_V*(Vss1_vec(i)-Vreset));
    else
        FRFvec_1(i)= (Vss1_vec(i)-Vth)/ (tao_V*(Vth-Vreset)*(1-exp ((-(Vss1_vec(i)-Vth)/sig))));
    end
    if Vss2_vec(i)==Vth
        FRFvec_2(i)=sig/(tao_V*(Vss2_vec(i)-Vreset));
    else
        FRFvec_2(i)= (Vss2_vec(i)-Vth)/ (tao_V*(Vth-Vreset)*(1-exp((-(Vss2_vec(i)-Vth)/sig))));
    end

    Frvec_1(i) = Frvec_1(i-1) + dt/tao_V*(-Frvec_1(i-1) + FRFvec_1(i));
    Frvec_2(i) = Frvec_2(i-1) + dt/tao_V*(-Frvec_2(i-1) + FRFvec_2(i));
end

if z==1 %figure is for question1
figure(1)

plot(tvec,Frvec_1)
hold on
plot(tvec,Frvec_2), ylabel("Unit Firing Rate")
legend("Excitatory Unit", "Inhibitory Unit")
xlabel("time")
hold off
end

%the following can be used to visualize the lack of oscillations provided
%sufficient input to the inhibiotry unit
%{
if z==numel(Gin2trials)-20
figure(10)
plot(tvec,Frvec_1)
hold on
plot(tvec,Frvec_2)
hold off
end
%}

% question 2


FR1 = Frvec_1(ceil(0.5/dt):numel(Frvec_1));
nearmax = max(FR1)*0.9;
nearmin = min(FR1)*1.1;
indicator = 0;
swaptimes = zeros(size(tvec));
index = 1;

for i = 1:numel(FR1)
    if FR1(i)>nearmax && indicator==0
        swaptimes(index) = i;
        index=index+1;
        indicator=1;
    end
    if FR1(i)<nearmin && indicator==1
        indicator=0;
    end
end
swaptimes=swaptimes(1:index-1);
%swaptimes=swaptimes*dt;
period=(swaptimes(numel(swaptimes))-swaptimes(1))/numel(swaptimes);
Q2freq = 1/period;

%the following block can be used to report data to the console about answers to
%the second question
%{
if z==1
    "time of first peak: 'x' "
    swaptimes(1)+0.5
    "time of last peak: 'y'"
    swaptimes(numel(swaptimes))+0.5
    "number of peaks: 'n'"
    numel(swaptimes)
    "period of oscillation = (y-x)/n"
    period
    "frequency = n/(y-x)"
    Q2freq
end
%}
if nearmax-nearmin<0.01 %non ideal solution, but tests for non oscillatory properties
    Q2freq=0; %needed some way of saying that for sufficiently small "oscillations" the frequency didn't increase unbound,
end

% question 3
FR1 = Frvec_1(ceil(0.5/dt):numel(Frvec_1));
freq_vec = 0:0.1:100;
t = tvec(ceil(0.5/dt):numel(Frvec_1));
t = t-t(1);
Af = zeros(size(freq_vec));
Bf = zeros(size(freq_vec));
Pf = zeros(size(freq_vec));
helper = zeros(size(t));
sinvec = zeros(size(t));
cosvec = zeros(size(t));

for i =1:numel(freq_vec)
    f = freq_vec(i);

    sinvec = sin(f*2*pi*t);
    cosvec = cos(f*2*pi*t);

    for k = 1:numel(sinvec)
        helper(k) = FR1(k)*sinvec(k);
    end
    Af(i)=mean(helper);
    for k = 1:numel(cosvec)
        helper(k) = FR1(k)*cosvec(k);
    end
    Bf(i) = mean(helper);
end

for i = 1:numel(Af)
    Pf(i) = (Af(i)*Af(i)) + (Bf(i)*Bf(i));
end

if z==1%for question 3
figure(2)
plot(freq_vec,Pf), ylabel("Oscillating Power"), xlabel("frequency");
end

meanfrvec1(z)= mean(Frvec_1);
meanfrvec2(z)= mean(Frvec_2);
oscil_freqvec(z)= Q2freq;
%the following method for calculating amplitude only works because the
%oscillations are "one sided" ie the max-min gives you displacement of the
%max from resting
oscil_ampvec1(z) = max(Frvec_1(ceil(0.5/dt):numel(Frvec_1)))- min(Frvec_1(ceil(0.5/dt):numel(Frvec_1)));
oscil_ampvec2(z) = max(Frvec_2(ceil(0.5/dt):numel(Frvec_2)))- min(Frvec_2(ceil(0.5/dt):numel(Frvec_2)));

end

figure(4)
subplot(3,1,1), plot(Gin1trials(2:numel(Question4)+1),oscil_freqvec(2:numel(Question4)+1)), ylabel("Oscillation Frequency");
subplot(3,1,2), plot(Gin1trials(2:numel(Question4)+1),oscil_ampvec1(2:numel(Question4)+1)), hold on, plot(Gin1trials(2:numel(Question4)+1),oscil_ampvec2(2:numel(Question4)+1)), hold off, ylabel("Oscillation Amplitude");
subplot(3,1,3), plot(Gin1trials(2:numel(Question4)+1),meanfrvec1(2:numel(Question4)+1)), hold on, plot(Gin1trials(2:numel(Question4)+1),meanfrvec2(2:numel(Question4)+1)), hold off, ylabel("Mean Firing Rate");
xlabel("Input Conductance to unit 1");

figure(5)
subplot(3,1,1), plot(Gin2trials(numel(Question4)+2:numel(Gin2trials)),oscil_freqvec(numel(Question4)+2:numel(Gin2trials))), ylabel("Oscillation Frequency"), ylabel("Oscillation Frequency");
subplot(3,1,2), plot(Gin2trials(numel(Question4)+2:numel(Gin2trials)),oscil_ampvec1(numel(Question4)+2:numel(Gin2trials))), hold on, plot(Gin2trials(numel(Question4)+2:numel(Gin2trials)),oscil_ampvec2(numel(Question4)+2:numel(Gin2trials))), hold off, ylabel("Oscillation Amplitude");
subplot(3,1,3), plot(Gin2trials(numel(Question4)+2:numel(Gin2trials)),meanfrvec1(numel(Question4)+2:numel(Gin2trials))), hold on, plot(Gin2trials(numel(Question4)+2:numel(Gin2trials)),meanfrvec2(numel(Question4)+2:numel(Gin2trials))), hold off, ylabel("Mean Firing Rate");
xlabel("Input Conductance to unit 2");

