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
Gin1 = 1e-9;         %Excitatory input conductance 1
Gin2 = 0;         %Excitatory input conductance 1
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


figure(1)

plot(tvec,Frvec_1)
hold on
plot(tvec,Frvec_2), ylabel("Unit Firing Rate")
legend("Excitatory Unit", "Inhibitory Unit")
xlabel("time")
hold off


%% question 3 
FR1 = Frvec_1(ceil(0.5/dt):numel(Frvec_1));
freq_vec = 0:0.5:100;
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
    for k = 1:numel(cosvec) %converted a few of my matrix multiplications to nested for loops because I thought that may have been somewhere where I was having an issue, but apparently not
        helper(k) = FR1(k)*cosvec(k);
    end
    Bf(i) = mean(helper);
end

for i = 1:numel(Af)
    Pf(i) = (Af(i)*Af(i)) + (Bf(i)*Bf(i));
end


figure(2)
plot(freq_vec,Pf), ylabel("Oscillating Power"), xlabel("frequency");
