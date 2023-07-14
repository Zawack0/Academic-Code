% Tutorial 9.1 - Last Homework
%Code written by Connor Zawacki
%About: Principle component analysis on firing rate trajectories.

%Known Bugs: Occasionally when running a simulation, a
%warning will appear where "solumns of X are linearly dependent" which
%truncates one of the resulting TSQUARED vector accordingly, making some
%of the vecor sizes later in the code inconsistent. I personally
%"solved" this problem by clearing the workspace and running again until I
%got a trial that didn't have this error. Theorhetically, the rest of the
%code is still correct and this is just a minor artifact of how MATLAB does
%pca

Ncells = 50;
Aamp = 20;
Bamp = 10;
freq = 0.5;
dt = 1e-3;
tmax = 10;
tvec = 0:dt:tmax;
IA = Aamp*sin(2*pi*freq*tvec);    %for Q1
%IA = Aamp*sin(2*pi*1*tvec);      %for Q2
%IA = Aamp*tvec;                  %for Q3
IB = Bamp*cos(2*pi*freq*tvec);    %for Q1
%IB = Bamp*cos(2*pi*0.5*tvec);    %for Q2
%IB = Bamp*sin(2*pi*freq*tvec);   %for Q3
%IB(1:(4/dt)) = 0;                %for Q3
%IB(5/dt:numel(IB)) = 0;          %for Q3


Fr_matrix = zeros(numel(tvec),Ncells);
sig = 10;
Io = 50;
cells = 1:50;
WiO = randn(size(cells));
WiA = randn(size(cells));
WiB = randn(size(cells));
nit = randn(size(Fr_matrix));

for t = 1:(numel(tvec))
    Fr_matrix(t,:)=100+ WiO*Io + WiA*IA(t) + WiB*IB(t) + sig*nit(t,:);
end
Fr_matrix(Fr_matrix<0)=0; %fixes a problem with neg firing rates

[COEFF,SCORE,LATENT,TSQUARED,EXPLAINED,MU] = pca(Fr_matrix); % see known bugs

figure(1)
subplot(2,1,1), plot(WiA,COEFF(:,1),'.'), xlabel("Input Weight A"), ylabel("First Prin. Comp.")
subplot(2,1,2), plot(WiB,COEFF(:,2),'.'), xlabel("Input Weight B"), ylabel("Second Prin. Comp.")
%sign of slope means nothing

figure(2)
helper = EXPLAINED(1) + EXPLAINED(2);
plot(EXPLAINED), xlabel("Principle Component"), ylabel("% Varience Explained"), title("Sum of First 2 Prin. Comp. = "+ helper + "%");

%helper = COEFF';
new_rates = SCORE(:,1:2)*COEFF(:,1:2)';
for c = 1:length(new_rates(1,:))
    new_rates(:,c) = new_rates(:,c)+MU(c);
end

figure(3)
subplot(2,2,1), plot(tvec,Fr_matrix(:,1)), xlabel("time"), ylabel("Frate of noisy neuron 1");
subplot(2,2,2), plot(tvec,Fr_matrix(:,25)), xlabel("time"), ylabel("Frate of noisy neuron 25");
subplot(2,2,3), plot(tvec,new_rates(:,1)), xlabel("time"), ylabel("Frate of de-noised neuron 1");
subplot(2,2,4), plot(tvec,new_rates(:,25)), xlabel("time"), ylabel("Frate of de-noised neuron 25");

figure(4)
subplot(2,1,1), plot(Fr_matrix(:,1),Fr_matrix(:,25),'.'), xlabel("Frate of noisy neuron 1"), ylabel("Frate of noisy neuron 25");
subplot(2,1,2), plot(new_rates(:,1),new_rates(:,25),'.'), xlabel("Frate of de-noised neuron 1"), ylabel("Frate of de-noised neuron 25");


figure(5)
subplot(2,1,1), plot(tvec,SCORE(:,1)), ylabel("Prin. Comp. 1")
subplot(2,1,2), plot(tvec,SCORE(:,2)), xlabel("time"), ylabel("Prin. Comp. 2")