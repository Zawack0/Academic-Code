% Tutorial 3.2 - Homework 3
%Code written by Connor Zawacki
%About: Code simulates an AELIF and ayalyzes different measures of
%variability in spike timings / ISIs.


%For more in depth discussion of solutions and answers to question, see
%attached pdf

%% Question 1a i and ii
%params
EL = -70e-3;
Vth = -50e-3;
Vres = -80e-3;
dth = 2e-3;
GL = 10e-9;
Cm = 100e-12;
a = 2e-9;
b=0e-9; %for question 1a b=0, 1b b= 1e-9, 1c b=0
tSRA = 150e-3;
dt = 0.01e-3;
sig = 20e-12; % for question 1c sig=20e-12, for all other questions sig=50e-12
sigma_I = sig/(sqrt(dt));

tmax=100;
tvec=(0:dt:tmax);
Vmvec=zeros(size(tvec));
Vmvec(1)=EL;
ISRAvec=zeros(size(tvec));
Iappvec=randn(size(tvec))*sigma_I;
spikevec=zeros(size(tvec));


for i = 2:numel(tvec)
    ISRAvec(i)= ISRAvec(i-1) + dt*(1/tSRA)*(a*(Vmvec(i-1)-EL)-ISRAvec(i-1));
    Vmvec(i) = Vmvec(i-1) + dt*(1/Cm)*(GL*(EL-Vmvec(i-1)+dth*exp((Vmvec(i-1)-Vth)/dth))-ISRAvec(i-1)+Iappvec(i-1)+0.2e-9); %additional applied current for question 1c (0, 0.1 and 0.2 nA)
    if Vmvec(i)>=Vth
        Vmvec(i)=Vres;
        ISRAvec(i)=ISRAvec(i) + b;
        spikevec(i)=1;
    end
end
%figure(3)
%plot(tvec(500000:600000),Vmvec(500000:600000)) %just here because I wanted to see it

spike_times=find(spikevec); %an array of indicies of the spike times
ISIvec=zeros(size(spike_times));
for i = 2:numel(spike_times)
    ISIvec(i)=(spike_times(i)-spike_times(i-1))*dt;
end
figure(1)
histogram(ISIvec,25)
title("Interspike Interval Histogram")
xlabel("ISI")
ylabel("Occurrences")

%CV = std(ISIvec)/mean(ISIvec) 

%% 1a iii + iv
% loops through window sizes ranging from 10 ms to 1 s, including the 100ms
% window asked for in question iii
window_vec=0.01:0.01:1 ;%i tried making so window vec only contained values that went into 100 evenly, but there were too few for my graph to come out well
Fano_vec=zeros(size(window_vec));

for j = 1:numel(window_vec)
    window=window_vec(j);
    spikes_in_window = zeros(1,ceil(tmax/window));
    for i = 1:numel(spike_times)
        clever = floor(spike_times(i)*dt/window)+1; %I think this should give the index of spikes in window that neeeds to be incremented
        spikes_in_window(clever)=spikes_in_window(clever)+1;
    end
    %if j==10 %as opposed to commenting out large sections of code for 1c in order to answer iii but not iv, this statement can give me the relavant data about the 100ms time windows
        %var(spikes_in_window)
        %mean(spikes_in_window)
    %end
    Fano_vec(j)= var(spikes_in_window)/mean(spikes_in_window);
end
figure(2)
plot(window_vec,Fano_vec)
xlabel("Window Size (s)")
ylabel("Fano Factor")
title("FF According to Window Size")
%% question 1b+1c
%code used is the same as above, with a few substitured values, mentioned
%both above and below

%question 1b b=1e-9 instead of 0

%question 1c b=0 again and sig = 20e-12, 
%also for question 1c static input of 0, 0.1, and 0.2 nA tested

