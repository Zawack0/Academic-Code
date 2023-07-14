% Tutorial 7.1 + more - Final
%Code written by Connor Zawacki
%About: Code producing first just a single inhibition stabilized circuit
%coupling an ecitatory and inhibitory unit together and analyzing its
%response to differing inputs and time steps. Then, two such circuits are
%coupled together and activity of the resulting network is analyzed.
%Finally, an alaysis on the number of stable states given 'n' number of
%inhibition stabilized circuits coupled together is carried out

%Known Bugs: None

%% Part A: Single Inhibition Stabilized Circuit

rmax = 100;    %max frate
theta_e = -5;  %threshold of activity for e. cells
theta_i = 0;   %threshold of activity for i. cells
alpha_e = 0.05;%gain of e. cells
alpha_i = 1;   %gain of i. cells
WEI = 2.5;     %connection strength from e. to i. cells
WEE = 2;       %connection strength from e. cell to self
WII = -2;      %connection strength from i. cell to self
WIE = -2.5;    %connection strength from i. to e cells
%for just 1 or 2 circuits we don't really need a connectivity matrix

tmax = 5;
dt = 0.1e-3;  
tvec = 0:dt:tmax;
i_stimvec = zeros(size(tvec));
i_stimvec(ceil(numel(tvec)/3):2*ceil(numel(tvec)/3))=10;


Ie_base = 0; %is 0 in question 1, and 25 in question 2. baseline current for e. cell
Ii_base = 0; %is 0 in question 1, and 15 in question 2. baseline current for i. cell
tao_e = 2e-3;%is 5ms in question 1 and 2, and 2 ms in question 3 and 4. time constant for e cells
tao_i = 10e-3;%is 5ms in question 1 and 2, and 10 ms in question 3 and 4. time constant for i cells

Iapp_e = ones(size(tvec))*Ie_base;
Iapp_i = ones(size(tvec))*(Ii_base)+ i_stimvec ;

frvec_e = zeros(size(tvec));
frvec_i = zeros(size(tvec));
Ivec_e = zeros(size(tvec));
Ivec_i = zeros(size(tvec));

for t=2:numel(tvec)
    Ivec_e(t)= WEE*frvec_e(t-1) + WIE*frvec_i(t-1) + Iapp_e(t);
    Ivec_i(t)= WEI*frvec_e(t-1) + WII*frvec_i(t-1) + Iapp_i(t);

    frvec_e(t) = frvec_e(t-1) + (dt/tao_e)*(-frvec_e(t-1) + alpha_e*(Ivec_e(t)-theta_e)^2 *sign(Ivec_e(t)-theta_e));
    frvec_i(t) = frvec_i(t-1) + (dt/tao_i)*(-frvec_i(t-1) + alpha_i*(Ivec_i(t)-theta_i));

    if frvec_i(t)>rmax
        frvec_i(t)=rmax;
    end
    if frvec_i(t)<0
        frvec_i(t)=0;
    end
    if frvec_e(t)>rmax
        frvec_e(t)=rmax;
    end
    if frvec_e(t)<0
        frvec_e(t)=0;
    end
end
figure(1)
subplot(2,1,1), plot(tvec,frvec_e), hold on, plot(tvec, frvec_i), legend("Excit.","Inhib."), ylabel("Firing Rate"), hold off
subplot(2,1,2), plot(tvec,Ivec_e), hold on, plot(tvec,Ivec_i),legend("Excit.","Inhib."), ylabel("Total input"), xlabel("Time"), hold off

%% part B Two Inhibition Stabilized Circuits

WEIX = 1.75;
tao_e = 5e-3;
tao_i = 5e-3;

i_stimvec1 = zeros(size(tvec));
i_stimvec1(1/dt:ceil(1/dt+(100e-3/dt)))=10;
%i_stimvec1(ceil(0.5/dt):ceil(0.5/dt+(1e-3/dt)))=2;
%i_stimvec1(ceil(2.5/dt-1000):ceil(2.5/dt+(1e-3/dt-1000)))=2;
i_stimvec2 = zeros(size(tvec));
i_stimvec2(2/dt:ceil(2/dt+(100e-3/dt)))=10;

frvec_e1 = zeros(size(tvec));
frvec_i1 = zeros(size(tvec));
Ivec_e1 = zeros(size(tvec));
Ivec_i1 = zeros(size(tvec));

frvec_e2 = zeros(size(tvec));
frvec_i2 = zeros(size(tvec));
Ivec_e2 = zeros(size(tvec));
Ivec_i2 = zeros(size(tvec));

Ii_base=20;
Ie_base=25;

Iapp_e = ones(size(tvec))*Ie_base;
Iapp_i1 = ones(size(tvec))*(Ii_base) + i_stimvec1;
Iapp_i2 = ones(size(tvec))*(Ii_base) + i_stimvec2;

for t=2:numel(tvec)
    Ivec_e1(t)= WEE*frvec_e1(t-1) + WIE*frvec_i1(t-1) + Iapp_e(t);
    Ivec_i1(t)= WEI*frvec_e1(t-1) + WII*frvec_i1(t-1) + Iapp_i1(t) + WEIX*frvec_e2(t-1);

    frvec_e1(t) = frvec_e1(t-1) + (dt/tao_e)*(-frvec_e1(t-1) + alpha_e*(Ivec_e1(t)-theta_e)^2 *sign(Ivec_e1(t-1)-theta_e));
    frvec_i1(t) = frvec_i1(t-1) + (dt/tao_i)*(-frvec_i1(t-1) + alpha_i*(Ivec_i1(t)-theta_i));

    if frvec_i1(t)>rmax
        frvec_i1(t)=rmax;
    end
    if frvec_i1(t)<0
        frvec_i1(t)=0;
    end
    if frvec_e1(t)>rmax
        frvec_e1(t)=rmax;
    end
    if frvec_e1(t)<0
        frvec_e1(t)=0;
    end

    Ivec_e2(t)= WEE*frvec_e2(t-1) + WIE*frvec_i2(t-1) + Iapp_e(t);
    Ivec_i2(t)= WEI*frvec_e2(t-1) + WII*frvec_i2(t-1) + Iapp_i2(t) + WEIX*frvec_e1(t-1);

    frvec_e2(t) = frvec_e2(t-1) + (dt/tao_e)*(-frvec_e2(t-1) + alpha_e*(Ivec_e2(t)-theta_e)^2 *sign(Ivec_e2(t-1)-theta_e));
    frvec_i2(t) = frvec_i2(t-1) + (dt/tao_i)*(-frvec_i2(t-1) + alpha_i*(Ivec_i2(t)-theta_i));

    if frvec_i2(t)>rmax
        frvec_i2(t)=rmax;
    end
    if frvec_i2(t)<0
        frvec_i2(t)=0;
    end
    if frvec_e2(t)>rmax
        frvec_e2(t)=rmax;
    end
    if frvec_e2(t)<0
        frvec_e2(t)=0;
    end
end
figure(2)
subplot(3,2,1), plot(tvec,frvec_e1), hold on, plot(tvec, frvec_i1), legend("Excit.","Inhib."), ylabel("Firing Rate 1"), hold off
subplot(3,2,3), plot(tvec,Ivec_e1), hold on, plot(tvec,Ivec_i1),legend("Excit.","Inhib."), ylabel("Total input 1"), hold off
subplot(3,2,2), plot(tvec,frvec_e2), hold on, plot(tvec, frvec_i2), legend("Excit.","Inhib."), ylabel("Firing Rate 2"), hold off
subplot(3,2,4), plot(tvec,Ivec_e2), hold on, plot(tvec,Ivec_i2),legend("Excit.","Inhib."), ylabel("Total input 2"), hold off
subplot(3,2,5), plot(tvec, Iapp_i1,'r'), xlabel("Time (s)"), ylabel("Iapp1");
subplot(3,2,6), plot(tvec,Iapp_i2,'r'), xlabel("Time (s)"), ylabel("Iapp2");

%% # of steady states given N inhib. stab. network=?

N=3; %value for how many coupled networks. N= 3, 4, and 5 were mainly used

WEIX = 2;
tao_e = 5e-3;
tao_i = 5e-3;

i_stimmat = zeros(N,numel(tvec));
%below are only a few of the different tested inputs used, many variations
%of different stimuli and base currents were used
i_stimmat(1,ceil(1/dt):ceil(1.11/dt))= 25;
%i_stimmat(2,ceil(0.5/dt):ceil(1/dt))= -10;
%i_stimmat(3,ceil(0.25/dt):ceil(1/dt))= 100;
frmat_e = zeros(N,numel(tvec));
Imat_e = frmat_e;
frmat_i = zeros(N,numel(tvec));
Imat_i = frmat_i;


%Base current values had to vary with 'N' for signifiant activity
Ii_base=20;
Ie_base=25;

Iapp_e = ones(size(tvec))*Ie_base;
Iapp_mat = ones(size(i_stimmat))*(Ii_base) + i_stimmat;

for t = 2:numel(tvec)
    for c = 1:N
        Imat_i(c,t) = WEI*frmat_e(c,t-1) + WII*frmat_i(c,t-1) + Iapp_mat(c,t) + WEIX*(sum(frmat_e(:,t-1)) - frmat_e(c,t-1));
        Imat_e(c,t) = WEE*frmat_e(c,t-1) + WIE*frmat_i(c, t-1) + Iapp_e(t);

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
figure(3)
for i=1:N
    subplot(ceil(N/2),2,i), plot(tvec,frmat_e(i,:)),  hold on, plot(tvec,frmat_i(i,:)),  legend("excit.","inhib."), ylabel("Fr Unit#"+i), xlabel("Time(s)"), hold off;
end
figure(4)
for i=1:N
    subplot(ceil(N/2),2,i), plot(tvec,Imat_e(i,:)),  hold on, plot(tvec,Imat_i(i,:)),  legend("excit.","inhib."), ylabel("Total input Unit#"+i), xlabel("Time(s)"), hold off;
end
figure(5)
for i=1:N
    subplot(ceil(N/2),2,i), plot(tvec,Iapp_mat(i,:)), ylabel("Total Iapp Unit#"+i), xlabel("Time(s)"), hold off;
end

