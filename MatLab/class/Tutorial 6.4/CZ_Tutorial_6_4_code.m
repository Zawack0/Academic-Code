% Tutorial 6.3 - Homework 7
%Code written by Connor Zawacki
%About: This code simulates a firing rate model of an Exitatory-Inhibitory 
%Coupled unin oscillator and PING. Also Observes the impact of caryation of
%external input and analysis using Fourier transform

%% 
fignum = 1;
N=50;
dt=0.1e-3;
tmax=300e-3;
tvec = 0:dt:tmax;
Nt = numel(tvec);
theta_i = (1:N)*pi/N;
theta_j = theta_i';
theta_cue = pi/2; %this could be an area of error, not sure if this is the correct value
epsilon = 0.5;


networks_tao_E=[10e-3,10e-3,50e-3];
networks_tao_I=[10e-3,10e-3,5e-3];
networks_E_baseline = [-10,-5,2];
networks_I_baseline = [-10,-5,0.5];
networks_AE = [40,40,100];
networks_AI = [40, 40,0];


for networks=1:3
for c = [0,0.25,0.5,0.75,1]
Ecells_FR = zeros(Nt,N);
Icells_FR = zeros(Nt,N);
if networks==1
WEE = zeros(N);
WEI = zeros(N);
WIE = zeros(N);
end
if networks==2
WEE = zeros(N);
WEI = zeros(N);
WIE = -1*(1+cos(pi + 2*theta_i - 2*theta_j));
end
if networks==3
WEE = 5*(1+cos(2*theta_i - 2*theta_j));
WEI = 3*(1+cos(2*theta_i - 2*theta_j));
WIE = -4*(1+cos(2*theta_i - 2*theta_j));
end
AE = networks_AE(networks);
AI = networks_AI(networks);
tao_E = networks_tao_E(networks);
tao_I = networks_tao_I(networks);
E_baseline = networks_E_baseline(networks);
I_baseline = networks_I_baseline(networks);


S_E = AE*c*(1+epsilon*cos(2*theta_cue - 2*theta_i));
S_I = AI*c*(1+epsilon*cos(2*theta_cue - 2*theta_i));

for i=2:Nt
    WEEsum = Ecells_FR(i-1,:)*WEE;
    WIEsum = Icells_FR(i-1,:)*WIE;
    WEIsum = Ecells_FR(i-1,:)*WEI;

    Ecells_FR(i,:)= Ecells_FR(i-1,:) + dt/tao_E*(-Ecells_FR(i-1,:)+(1/N)*WEEsum + (1/N)*WIEsum + E_baseline+ S_E);
    for j =1:numel(Ecells_FR(i,:))
        if Ecells_FR(i,j)<0
            Ecells_FR(i,j)=0;
        end
    end

    Icells_FR(i,:) = Icells_FR(i-1,:) + dt/tao_I*(-Icells_FR(i-1,:) + (1/N)*WEIsum + I_baseline+S_I);
    for j =1:numel(Icells_FR(i,:))
        if Icells_FR(i,j)<0
            Icells_FR(i,j)=0;
        end
    end

end

figure(fignum)

% for question 4, divide the firerate cells vec by the helper
% for question 3, don't

Ehelp = mean(Ecells_FR, "all");
Ihelp = mean(Icells_FR, "all");
subplot(2,1,1), plot(1:N,Ecells_FR(Nt,:)/Ehelp), ylabel("E. cells Fr/mean"), title("Orientation Preference vs Fr at final timepoint")
subplot(2,1,2), plot(1:N,Icells_FR(Nt,:)/Ihelp), xlabel("Index"), ylabel("I. cells Fr/mean"), title("Network "+networks+": C="+c);
fignum=fignum+1;
%}
%{
%for question 2
helpE=Ecells_FR';
helpI=Icells_FR';
figure(fignum)
title("Network "+networks+": C="+c);
subplot(2,2,1), plot(tvec,helpE(25,:)), ylabel("E. cell Fr"), title("Network "+networks+": C="+c); %plot excitory cell fr vs time when thetai = pi/2 (when i =25, so the 25th unit)
subplot(2,2,2), plot(tvec,helpE(50,:)),ylabel("E. cell Fr"),  title("Network "+networks+": C="+c);%plot excitory cell fr vs time when thetai = pi  (when i = 50, so the 50th unit)
subplot(2,2,3), plot(tvec,helpI(25,:)),xlabel("time"), ylabel("I. cell Fr"), title("Pref. Orient. = pi/2") %plot inhibitory cell fr vs time when thetai = pi/2 (25th unit)
subplot(2,2,4), plot(tvec,helpI(50,:)), xlabel ("time"), ylabel("I.cell Fr"), title ("Pref. Orient. = pi")%plot inhibitory cell fr vs time when thetai = pi (50th unit)
fignum=fignum+1;
%}
end
end