%Coupled Inhibition Stabilized Circuits
%7/12/22
%Code written by Connor Zawacki for the Miller Lab at Brandeis University

%IS regimine with non linear firing rate equations for Excitatory units and
%E-I cross connections. 

% anywhere between 2 and 19 units can be active enough to suppress all
% other units

% also a steady state where all units fire at low rates
%% basics
N=20;          %value for how many coupled networks
on=10;
tmax = 20;
dt = 0.0001;  
tvec = 0:dt:tmax;
rmax = 100;    

frmat_e = zeros(N,numel(tvec));
Imat_e = frmat_e;
frmat_i = zeros(N,numel(tvec));
Imat_i = frmat_i;

%% parameters to vary
theta_e = 0;   %threshold of activity for e. cells
theta_i = 0;   %threshold of activity for i. cells
alpha_e = 0.1; %gain of e. cells
alpha_i = 1;   %gain of i. cells
tao_e = 10e-3; %time constant of e. cells
tao_i = 5e-3;  %time constant of i. cells

WEI = 3.5;     %connection strength from e. to i. cells 3.5
WEE = 3;       %connection strength from e. cell to self
WII = -3;      %connection strength from i. cell to self
WIE = -3;      %connection strength from i. to e cells
WEIX = 0.035;     %connection strength from e. cells to i cells from other coupled units. must be changed with N

Ii_base=-10;
Ie_base=-1;



%% applied charge
i_stimmat = zeros(N,numel(tvec));%each row is a vector for applied current to each coupled set's inhibitory unit, with # of columns = tmax/dt
Iapp_e = ones(size(i_stimmat))*Ie_base;
%random noise
for i = 1:N
    noisevec = randn(size(tvec))*(dt^(0.5))*15;
    i_stimmat(i,:)= noisevec;
    Iapp_e(i,:) = noisevec + Iapp_e(i,:);
end

%transition 1 
i_stimmat(1:on,ceil(10/dt):ceil(10.1/dt))= 10;    


%transition 2 
%


Iapp_e(1:20,ceil(0.5/dt):ceil(1/dt))= 3; 
Iapp_i = ones(size(i_stimmat))*(Ii_base) + i_stimmat;

%% simulation
for t = 2:numel(tvec)
    for c = 1:N
        Imat_i(c,t) = WEI*frmat_e(c,t-1) + WII*frmat_i(c,t-1) + Iapp_i(c,t) + WEIX*(sum(frmat_e(:,t-1)) - frmat_e(c,t-1));
        Imat_e(c,t) = WEE*frmat_e(c,t-1) + WIE*frmat_i(c, t-1) + Iapp_e(c,t);

        frmat_e(c,t) = frmat_e(c,t-1) + (dt/tao_e)*(-frmat_e(c,t-1) + alpha_e*(Imat_e(c,t)-theta_e)^2 *sign(Imat_e(c,t)-theta_e));
        frmat_i(c,t) = frmat_i(c,t-1) + (dt/tao_i)*(-frmat_i(c,t-1) + alpha_i*(Imat_i(c,t)-theta_i));

        if frmat_i(c,t)>rmax
            frmat_i(c,t)=rmax;
        end
        if frmat_i(c,t)<0
            frmat_i(c,t)=0;
        end
        if frmat_e(c,t)>rmax
            frmat_e(c,t)=rmax;
        end
        if frmat_e(c,t)<0
            frmat_e(c,t)=0;
        end
    end
end

%% preliminary figures
figure(3)
for i=1:N
    subplot(4,5,i), plot(tvec,frmat_e(i,:)),  hold on, plot(tvec,frmat_i(i,:)), ylabel("Fr Unit#"+i), xlabel("Time(s)"), hold off;
end
legend("excit.","inhib.")
figure(4)
for i=1:N
    subplot(4,5,i), plot(tvec,Imat_e(i,:)),  hold on, plot(tvec,Imat_i(i,:)),   ylabel("Total input Unit#"+i), xlabel("Time(s)"), hold off;
end
legend("excit.","inhib."),
figure(5)
for i=1:N
    subplot(4,5,i), plot(tvec,Iapp_i(i,:)), ylabel("Total Iapp Unit#"+i), xlabel("Time(s)"), hold off;
end
