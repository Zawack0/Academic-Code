% Tutorial 7.2 - Homework 8
%Code written by Connor Zawacki
%About: This code simulates several firing rate models with either 2 or 3
%units, carying connection strengths and thresholds. Each "trial" is
%analyzed in order to determine the type of dynamical system present
%(distinct states, choatic vs heteroclinic, etc.)

%%
tao = 10e-3;
dt = 0.1e-3;
tmax = 4;
tvec = 0:dt:tmax;
rmax = 100;
for trial=1:9
    r1vec = zeros(size(tvec));
    r2vec = zeros(size(tvec));
switch(trial)
    case{1}
        N=2;
        W = [0.6 1; -0.2 0];
        thresh1 = -5;
        thresh2 = -10;
        Iapp1 = zeros(size(tvec));
        %Iapp1(ceil(numel(Iapp1)/4):3*ceil(numel(Iapp1)/4)) = 5;
        Iapp2 = zeros(size(tvec));

        %unit 1 has mild self excitation and excites unit 2
        %unit 2 has no self connection and has mildly inhibits unit 1
        %both units are spontaneously active, but unit 2 more than 1

    case{2}
        N=2;
        W = [1.2 -0.3; -0.2 1.1];
        thresh1 = 10;
        thresh2 = 5;
        Iapp1 = zeros(size(tvec));
        Iapp2 = zeros(size(tvec));

        %unit 1 excites itself and slightly inhibits unit 2
        %unit 2 follows the same pattern, but to a lesser degree
        %unit 1 has a higher threshold for activity
    case{3}
        N=2;
        W = [2.5 2; -3 -2];
        thresh1 = -10;
        thresh2 = 0;
        Iapp1 = zeros(size(tvec));
        Iapp2 = zeros(size(tvec));

        %unit 1 excites itself a lot and excites unit 2 slightly less
        %unit 2 inhibits unit 1 a lot and inhibits itelf slightly less 
        %unit 1 is spontaneously active
        %unit 2 has no threshold for activity
    case{4}
        N=2;
        W = [0.8 -0.2; -0.4 0.6];
        thresh1 = -10;
        thresh2 = -10;
        Iapp1 = zeros(size(tvec));
        Iapp2 = zeros(size(tvec));

        %unit 1 has mild self excitation and slight inhibition of unit 2
        %unit 2 has mild self excitation(to a lesser degree) and slight inhibition (to a higher degree) of unit 2

        %both units are spontaneously active

    case{5}
        N=2;
        W = [2 1; -1.5 0];
        thresh1 = 0;
        thresh2 = 20;
        Iapp1 = zeros(size(tvec));
        Iapp2 = zeros(size(tvec));

        %unit 1 has self excitation twice as powerful as its excitation to
        %unit 2 has inhibition to unit 1 and no recurrent connections

        %unit 1 has no threshold for activity, unit 2 has a high threshold

    case{6}
        N=3;
        W = [1.5 0 1; 0 2 1; -2.5 -3 -1];
        thresh1 = -10;
        thresh2 = -5;
        thresh3 = 5;
        Iapp1 = zeros(size(tvec));
        Iapp2 = zeros(size(tvec));
        Iapp3 = zeros(size(tvec));
        r3vec = zeros(size(tvec));
        %unit 1 has self excitation and excites unit 3
        %unit 2 has self excitation and excites unit 3
        %unit 3 inhibits all units including itself

        %units 1 and 2 are spontaneously active, unit 3 has a low threshold
        %for activity

    case{7}
        N=3;
        W = [2.2 -0.5 0.9; -0.7 2 1.2; -1.6 -1.2 0];
        thresh1 = -18;
        thresh2 = -15;
        thresh3 = 0;
        r3vec = zeros(size(tvec));
        Iapp1 = zeros(size(tvec));
        Iapp2 = zeros(size(tvec));
        Iapp3 = zeros(size(tvec));

        %unit 1 self excites powerfuly, excites unit 3 mildly, and inhibits
        %unit 2 slightly

        %unit 2 inhibits 1 slightly, self excites powerfully, and excites
        %unit 3 mildly

        %unit 3 inhibits the other 2 units, and has no self connections

        %units 1 and 2 are spontaneously active, unit 3 has no threshold
        %for activity

    case{8}
        N=3;
        W = [2.05 -0.2 1.2; -0.05 2.1 0.5; -1.6 -4 0];
        thresh1 = -10;
        thresh2 = -20;
        thresh3 = 10;
        r3vec = zeros(size(tvec));
        Iapp1 = zeros(size(tvec));
        Iapp2 = zeros(size(tvec));
        Iapp3 = zeros(size(tvec));

        %unit 1 excites itself and unit 3 and slightly inhibits unit 2
        %unit 2 barely inhibits unit 1, self excites and slightly excites
        %unit 3

        %unit 3 inhibits unit 1, inhibits unit 2 a lot, and has no self
        %connection

        %units 1 and 2 are spontaneously active, unit 3 has a higher
        %threshold for activity
    case{9}
        N=3;
        W = [0.98 -0.015 -0.01; 0 0.99 -0.02; -0.02 0.005 1.01];
        thresh1 = -2;
        thresh2 = -1;
        thresh3 = -1;
        r3vec = zeros(size(tvec));
        Iapp1 = zeros(size(tvec));
        Iapp2 = zeros(size(tvec));
        Iapp3 = zeros(size(tvec));

        %unit1 self excites, and slightly inhibits other units
        %unit2 self excites and slightly inhibits unit 3
        %unit 3 self excites, slightly inhibits unit 1 and slightly
        %excites unit 2

        %all units are spontaneously active at a a low level, unit 1 more
        %than the other two

end
    %I found it most convinient to vary initial conditions and Iapp vecs
    %here so as to test several trials with a single run of the code
    %r2vec(1)=0;
    %r2vec(1)=0;
    %r3vec(1)=0;
    %Iapp1(ceil(numel(Iapp2)/2)-10000:ceil(numel(Iapp2)/2)-5000)=-50;
    %Iapp1(ceil(numel(Iapp2)/2)-10000:ceil(numel(Iapp2)/2)+5000)=-80;
    %Iapp2(ceil(numel(Iapp2)/2)-10000:ceil(numel(Iapp2)/2)+5000)=5;
    %Iapp3(ceil(numel(Iapp2)/2)-10000:ceil(numel(Iapp2)/2)+10000)=50;
    %Iapp2(ceil(numel(Iapp2)/2)+10000:ceil(numel(Iapp2)/2)+15000)=80;
    %Iapp1(ceil(numel(Iapp2)/2)-10000:ceil(numel(Iapp2)/2)-10000+floor(numel(Iapp2)/100))=-50;
    %the above are just a few of the different params I tried, obviously
    %with varyation in duration, level of applied current, inhibition vs
    %excitation, and different starting firing rates


    


    for i=2:numel(tvec)
        if N==3
            r1vec(i)= r1vec(i-1) + dt/tao*(-r1vec(i-1)-thresh1+Iapp1(i)+W(1,1)*r1vec(i-1)+W(2,1)*r2vec(i-1)+W(3,1)*r3vec(i-1));
            if r1vec(i)>rmax
                r1vec(i)=rmax;
            end
            if r1vec(i)<0
                r1vec(i)=0;
            end
            r2vec(i)= r2vec(i-1) + dt/tao*(-r2vec(i-1)-thresh2+Iapp2(i)+W(1,2)*r1vec(i-1)+W(2,2)*r2vec(i-1)+W(3,2)*r3vec(i-1));
            if r2vec(i)>rmax
                r2vec(i)=rmax;
            end
            if r2vec(i)<0
                r2vec(i)=0;
            end
            r3vec(i)= r3vec(i-1) + dt/tao*(-r3vec(i-1)-thresh3+Iapp3(i)+W(1,3)*r1vec(i-1)+W(2,3)*r2vec(i-1)+W(3,1)*r3vec(i-1));
            if r3vec(i)>rmax
                r3vec(i)=rmax;
            end
            if r3vec(i)<0
                r3vec(i)=0;
            end
        end
        if N==2
            r1vec(i)= r1vec(i-1) + dt/tao*(-r1vec(i-1)-thresh1+Iapp1(i)+W(1,1)*r1vec(i-1)+W(2,1)*r2vec(i-1));
            if r1vec(i)>rmax
                r1vec(i)=rmax;
            end
            if r1vec(i)<0
                r1vec(i)=0;
            end
            r2vec(i)= r2vec(i-1) + dt/tao*(-r2vec(i-1)-thresh2+Iapp2(i)+W(1,2)*r1vec(i-1)+W(2,2)*r2vec(i-1));
            if r2vec(i)>rmax
                r2vec(i)=rmax;
            end
            if r2vec(i)<0
                r2vec(i)=0;
            end
        end
    end

    figure(trial)
    subplot(2,1,1), plot(tvec,r1vec); ylabel("Firing Rate"), xlabel("Time")
    hold on;
    plot(tvec,r2vec);
    if N==3
        plot(tvec,r3vec)
    end
    legend("unit 1","unit 2","unit 3")%matlab will be unhappy when n=2 when you run this line, but it it will function properly anyway
    hold off;
    subplot(2,1,2), plot(tvec,Iapp1), ylabel("Applied Current"), xlabel("Time") 
    hold on;
    plot(tvec, Iapp2);
    if N==3
        plot(tvec,Iapp3)
    end
    legend("unit 1","unit 2","unit 3")
    hold off;

end
