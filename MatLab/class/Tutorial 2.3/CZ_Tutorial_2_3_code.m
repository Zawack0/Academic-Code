% Tutorial 2.3 - Homework 2
%Code written by Connor Zawacki
%About: This code simulates an extention of the leaky integrate and fire 
%model neuron through addition of a spiking mechanism and adaption currents.


%For more in depth discussion of solutions and answers to question, see
%attached pdf

%% Question 1a
%params
EL = -75e-3;   %Reversal potential
Vth = -50e-3;  %Threshold potential
Vres = -80e-3; %Reset potential
Rm = 100e6;    %Resistance
Cm = 100e-12;  %Capacitance
EK = -80e-3;   %Reversal potential of potassium channel
dGSRA = 1e-9;  %Increment of spike rate adaptation
dt=0.1e-3;     %Time-step of Vm
tSRA = 200e-3; %Time constant of decay for GSRA
spikes=0;

%vectors
tmax = 1.5; %varies for trial to trial
tvec = (0:dt:tmax); 
GSRAvec = zeros(size(tvec)); %NOTE GSRAvec(1)=0
Vmvec = zeros(size(tvec));
Iappvec = zeros(size(tvec));
Vmvec(1)=EL;
Iappvec(round(numel(Iappvec)/3):round(2*numel(Iappvec)/3)) = 550e-12;

%simulation
for i = 2:numel(tvec)
    GSRAvec(i)=GSRAvec(i-1) + (dt*(-GSRAvec(i-1)/tSRA));
    Vmvec(i)=Vmvec(i-1) + dt*1/Cm*((EL-Vmvec(i-1))/Rm+GSRAvec(i-1)*(EK-Vmvec(i-1))+Iappvec(i));
    if Vmvec(i)>=Vth
        Vmvec(i)=Vres;
        GSRAvec(i) = GSRAvec(i)+dGSRA;
    end
end
%NOTE: the subplot position code below was adapted from AELIF_code.m just
%to construct the 3 seperate vertical plot design. All data and plots are
%original

figure(1)
subplot('Position',[0.2 0.72 0.78,0.22]) %see note above
plot(tvec,Iappvec);
axis([0 tmax 0 1.25*(500e-12)]);
ylabel("Applied current")
subplot('Position',[0.2 0.41 0.78,0.22])%see note above
plot(tvec,Vmvec);
axis([0 tmax -100e-3 0]);
ylabel("Membrane Potential")
subplot('Position',[0.2 0.10 0.78,0.22]) %see note above
plot(tvec,GSRAvec);
ylabel("Adaption Conductance")
xlabel("Time(s)")

%% Question 1b
tmax = 5; %varies for trial to trial
tvec = (0:dt:tmax); 
GSRAvec = zeros(size(tvec)); %NOTE GSRAvec(1)=0
Vmvec = zeros(size(tvec));
Vmvec(1)=EL;
Iappvec = 250e-12:(550e-12 - 250e-12)/29:550e-12; 
ISIvec_1= zeros(size(Iappvec));
ISIvec_steady = ISIvec_1;


for j = 1:numel(Iappvec)
    Iapp = Iappvec(j);
    tempspike1 = 0;
    tempspike2 = 0;
    for i = 2:numel(tvec)
        GSRAvec(i)=GSRAvec(i-1) + (dt*(-GSRAvec(i-1)/tSRA));
        Vmvec(i)=Vmvec(i-1) + dt*1/Cm*((EL-Vmvec(i-1))/Rm+GSRAvec(i-1)*(EK-Vmvec(i-1))+Iapp);
        if Vmvec(i)>=Vth
            Vmvec(i)=Vres;
            GSRAvec(i) = GSRAvec(i)+dGSRA;
            if tempspike1==0
                tempspike1=tvec(i);
            elseif tempspike2==0
                tempspike2=tvec(i);
                ISIvec_1(j)=tempspike2-tempspike1;
            elseif round(tvec(i)-tempspike2, 3)==round(tempspike2-tempspike1, 3) && tvec(i)-tempspike2~=0
                ISIvec_steady(j)=tvec(i)-tempspike2;
            else
                tempspike1=tempspike2;
                tempspike2=tvec(i);
            end
        end
    end
end
firstfirerate = ISIvec_1.^-1;
steadyfirerate = ISIvec_steady.^-1;

%the below 2 lines of code are to fix the Inf value given from 0^-1
firstfirerate(1)=0;
steadyfirerate(1)=0;


figure(2)
plot(Iappvec,steadyfirerate)
hold on
plot(Iappvec, firstfirerate, 'x')
axis([2e-10 Iappvec(numel(Iappvec)) 0 150])
xlabel("Applied Current (V)")
ylabel("Spike Rate (Hz)")
legend("Steady State","Initial")

%% question 2a

dth=2e-3;
Vmax=100e-3;
GL=3e-9;
a=2e-9;
b=0.02e-9;
%most of below was copied and edited from above
tmax = 1.5; %varies for trial to trial
tvec = (0:dt:tmax); 
ISRAvec = zeros(size(tvec)); %NOTE ISRAvec(1)=0
Vmvec = zeros(size(tvec));
Iappvec = zeros(size(tvec));
Vmvec(1)=EL;
Iappvec(round(numel(Iappvec)/3):round(2*numel(Iappvec)/3)) = 550e-12;
% 100 to 410
%simulation
for i = 2:numel(tvec)
    ISRAvec(i) = ISRAvec(i-1) + dt*(tSRA^-1)*(a*(Vmvec(i-1)-EL)-ISRAvec(i-1));
    Vmvec(i)= Vmvec(i-1) + dt*(Cm^-1)*(GL*(EL-Vmvec(i-1)+ dth*exp((Vmvec(i-1)-Vth)/dth))-ISRAvec(i-1) + Iappvec(i));
    if Vmvec(i)>=Vth
        Vmvec(i)=Vres;
        ISRAvec(i)=ISRAvec(i) + b;
    end
end
%NOTE: the subplot position code below was adapted from AELIF_code.m just
%to construct the 3 seperate vertical plot design. All data and plots are
%original

figure(3)
subplot('Position',[0.2 0.72 0.78,0.22]) %see note above
plot(tvec,Iappvec);
axis([0 tmax 0 1.25*(500e-12)]);
ylabel("Applied current")
subplot('Position',[0.2 0.41 0.78,0.22])%see note above
plot(tvec,Vmvec);
axis([0 tmax -150e-3 0]);
ylabel("Membrane Potential")
subplot('Position',[0.2 0.10 0.78,0.22]) %see note above
plot(tvec,ISRAvec);
ylabel("Adaption Conductance")
xlabel("Time(s)")

%% question 2b

tmax = 5; %varies for trial to trial
tvec = (0:dt:tmax); 
GSRAvec = zeros(size(tvec)); %NOTE GSRAvec(1)=0
Vmvec = zeros(size(tvec));
Vmvec(1)=EL;
Iappvec = 100e-12:(410e-12 - 100e-12)/19:410e-12; 
ISIvec_1= zeros(size(Iappvec));
ISIvec_steady = ISIvec_1;


for j = 1:numel(Iappvec)
    Iapp = Iappvec(j);
    tempspike1 = 0;
    tempspike2 = 0;
    for i = 2:numel(tvec)
        ISRAvec(i) = ISRAvec(i-1) + dt*(tSRA^-1)*(a*(Vmvec(i-1)-EL)-ISRAvec(i-1));
        Vmvec(i)= Vmvec(i-1) + dt*(Cm^-1)*(GL*(EL-Vmvec(i-1)+ dth*exp((Vmvec(i-1)-Vth)/dth))-ISRAvec(i-1) + Iapp);
        if Vmvec(i)>=Vth
            Vmvec(i)=Vres;
            ISRAvec(i)=ISRAvec(i) + b;
            if tempspike1==0
                tempspike1=tvec(i);
            elseif tempspike2==0
                tempspike2=tvec(i);
                ISIvec_1(j)=tempspike2-tempspike1;
            elseif round(tvec(i)-tempspike2, 3)==round(tempspike2-tempspike1, 3) && tvec(i)-tempspike2~=0
                ISIvec_steady(j)=tvec(i)-tempspike2;
            else
                tempspike1=tempspike2;
                tempspike2=tvec(i);
            end
        end
    end
end
firstfirerate = ISIvec_1.^-1;

steadyfirerate = ISIvec_steady.^-1;
steadyfirerate(1:2)=0;%this is here to correct dividing by 0 when taking the inverse above


figure(4)
plot(Iappvec,steadyfirerate)
hold on
plot(Iappvec, firstfirerate, 'x')
axis([90e-12 Iappvec(numel(Iappvec)) 0 150])
xlabel("Applied Current (V)")
ylabel("Spike Rate (Hz)")
legend("Steady State","Initial")