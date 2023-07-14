%Tutorial 4.3 - Homework 5
%Code written by Connor Zawacki
%About: This code simulates the dynamics of a Pinky-Rinzel model neuron for
%a variety of connection strengths between compartments and applied current

%For more in depth discussion of solutions and answers to question, see
%attached pdf

%% question 2 (question 1 is already complete)

fig_Vm_vec = -0.085:(0.05-(-0.085))/100:0.050;
fig_Ca_vec = 0:2e-3/100:2e-3;

%PR_dend_gating and PR_soma_gating written by Paul Miller
[dend_alpha_mca, dend_beta_mca, dend_alpha_kca, dend_beta_kca, dend_alpha_kahp, dend_beta_kahp]= PR_dend_gating(fig_Vm_vec, fig_Ca_vec); %there are more clever ways of doing this, but this should work fine.


figure(1)
subplot(3,2,1), plot(fig_Vm_vec, dend_alpha_mca), xlabel("Vm"), ylabel("Ca activation");
subplot(3,2,2), plot(fig_Vm_vec, dend_beta_mca), xlabel("Vm"), ylabel("Ca deactivation");
subplot(3,2,3), plot(fig_Vm_vec, dend_alpha_kca), xlabel("Vm"), ylabel ("K activation");
subplot(3,2,4), plot(fig_Vm_vec, dend_beta_kca), xlabel("Vm"), ylabel("K deactivation");
subplot(3,2,5), plot(fig_Ca_vec, dend_alpha_kahp), xlabel("Ca Concentration"), ylabel("post activation"); %post hyperpolarization
subplot(3,2,6), plot(fig_Ca_vec, dend_beta_kahp), xlabel("Ca Concentration"), ylabel("post deactivation");%post hyperpolarization

%PR_dend_gating and PR_soma_gating written by Paul Miller
[alpha_m, beta_m, alpha_h, beta_h , alpha_n, beta_n] = PR_soma_gating(fig_Vm_vec);
figure(2)
subplot(3,2,1), plot(fig_Vm_vec, alpha_m), xlabel("Vm"), ylabel("Na activation");
subplot(3,2,2), plot(fig_Vm_vec, beta_m), xlabel("Vm"), ylabel("Na deactivation");
subplot(3,2,3), plot(fig_Vm_vec, alpha_h), xlabel("Vm"), ylabel ("Na inactivation");
subplot(3,2,4), plot(fig_Vm_vec, beta_h), xlabel("Vm"), ylabel("Na deinactivation");
subplot(3,2,5), plot(fig_Ca_vec, alpha_n), xlabel("Vm"), ylabel("K activation"); 
subplot(3,2,6), plot(fig_Ca_vec, beta_n), xlabel("Vm"), ylabel("K deactivation");

%% question 3
tmax = 2;
dt = 2e-6;
tvec = 0:dt:tmax;
As = 1/3;                 %fractional area of Soma
Ad = 2/3;                 %fractional area of Dendrite
sGL = As*(5e-9);          %somatic leak conductance
dGL = Ad*(5e-9);          %denritic leak conductance
G_Na_Max = As*(3e-6);     %Maximum sodium conducatnce
G_K_Max = As*(2e-6);      %Max delayed rectifier conductance
G_Ca_Max= Ad*(2e-6);      %Max calcium conductance
G_KCa_Max = Ad*(2.5e-6);  %Max Ca dependent K conductance
G_KAHP_Max = Ad*(40e-9);  %Max after hyperpolarization conductance
G_Link = 50e-9;            %Link Conductance, varies for question 5 to 0, 10, and 100ns.
E_Na = 60e-3;             %Sodium reversal
E_Ca = 80e-3;             %Calcium reversal
E_K = -75e-3;             %Potassium reversal
EL = -60e-3;              %Leak reversal potential
Csoma = As*(100e-12);     %Somatic capacitance
Cdend = Ad*(100e-12);     %Dendritic capacitance
tCa = 50e-3;              %Calcium decay time constant
k = (5e6/Ad);             %conversion from charge to concentration
Iappd = 200e-12;                %applied charge varies in question 6 (50, 100, 200)e-12
Iapps = 0e-12;                %applied charge varies in question 6 (50, 100, 200)e-12
DVmvec = zeros(size(tvec));
DVmvec(1) = EL;
SVmvec = DVmvec;
Cavec = zeros(size(tvec)); %calcium concentration vector
Cavec(1) = 1e-6;
m = zeros(size(tvec));
h = zeros(size(tvec));
n = zeros(size(tvec));
m(1) = 0;
h(1) = 0.5;
n(1) = 0.4;
 
mca = zeros(size(tvec));
mkca = zeros(size(tvec));
mkca(1) = 0.2;
mkahp = zeros(size(tvec));
mkahp(1) = 0.2;


for i = 2:numel(tvec)
    %dmdt = am(1-m)-Bm*m
    %gating variables follow the above format
    [alpha_m, beta_m, alpha_h, beta_h , alpha_n, beta_n] = PR_soma_gating(SVmvec(i-1));
    m(i) = m(i-1) + (alpha_m*(1-m(i-1)) - beta_m*m(i-1))*dt;
    h(i) = h(i-1) + (alpha_h*(1-h(i-1)) - beta_h*h(i-1))*dt;
    n(i) = n(i-1) + (alpha_n*(1-n(i-1)) - beta_n*n(i-1))*dt;


    [dend_alpha_mca, dend_beta_mca, dend_alpha_kca, dend_beta_kca, dend_alpha_kahp, dend_beta_kahp]= PR_dend_gating(DVmvec(i-1), Cavec(i-1));
    mca(i) = mca(i-1) + (dend_alpha_mca*(1-mca(i-1)) - dend_beta_mca*mca(i-1))*dt;
    mkca(i) = mkca(i-1) + (dend_alpha_kca*(1-mkca(i-1)) - dend_beta_kca*mkca(i-1))*dt;
    mkahp(i) = mkahp(i-1) + (dend_alpha_kahp*(1-mkahp(i-1)) - dend_beta_kahp*mkahp(i-1))*dt;

    Cavec(i) = Cavec(i-1) + dt*(-(Cavec(i-1)/tCa) + k*(G_Ca_Max*mca(i)^2*(E_Ca-DVmvec(i-1))));

    SVmvec(i) = SVmvec(i-1) + (dt/Csoma)*( ...
        (sGL*(EL-SVmvec(i-1))) + ...                       % leak current
        (G_Na_Max*m(i)*m(i)*h(i)*(E_Na-SVmvec(i-1))) + ... % sodium current
        (G_K_Max*n(i)^2*(E_K-SVmvec(i-1))) + ...           % potassium current
        (G_Link*(DVmvec(i-1)-SVmvec(i-1))) +...            % link current
        Iapps);

    X = min(4000*Cavec(i),1);

    DVmvec(i) = DVmvec(i-1) + (dt/Cdend)*( ...
        (dGL*(EL-DVmvec(i-1))) + ...                       % leak current
        (G_Ca_Max*mca(i)^2*(E_Ca-DVmvec(i-1))) + ...       % Calcium current
        (G_KCa_Max*mkca(i)*X*(E_K-DVmvec(i-1))) + ...      % Ca dependent potassium current
        (G_KAHP_Max*mkahp(i)*(E_K-DVmvec(i-1))) - ...      % Ca dependent potassium current
        (G_Link*(DVmvec(i-1)-SVmvec(i-1))) + ...             % link current
        Iappd);
end
figure(3);
plot(tvec,DVmvec), ylabel("Dendrite Vm"), xlabel("Time");


figure(4);
plot(tvec,SVmvec), ylabel("Soma Vm"), xlabel("Time"); %a zoomed in view of one of
%the bursts
%% question 4

figure(5)
findpeaks(SVmvec), ylabel("Soma Vm"), xlabel("Time");
figure(6)
findpeaks(SVmvec(400000:600000)), ylabel("Soma Vm"), xlabel("Time") %another zoomed in view

%% question 5 + 6 are answered in the pdf



