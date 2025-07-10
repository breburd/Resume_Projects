% Breanna Burd
% EN.525.770.82FA24
% Intelligent Algorithms Course Project

% This project was developed in order to reproduce and make additional
% results for the following research paper:
% "Adaptive Control of an Electromagnetically Actuated Presser-Foot for
% Industrial Sewing Machines"

x = 0:1:4700;
inMFXparams = [-1, 0, 2350;
               0, 2350, 4700;
               2350, 4700, 4701];
figure;
plot(x, triangle_mf(x, inMFXparams(1,:)), 'r');
hold on;
plot(x, triangle_mf(x, inMFXparams(2,:)), 'b');
plot(x, triangle_mf(x, inMFXparams(3,:)), 'g');
hold off;
title('(A) Sewing Speed');
ylim([0, 1]);
xlim([0, 4700]);
annotation('textbox', [0.2, .5, 0.1, 0.05], 'String', 'low', ...
    'HorizontalAlignment', 'center', 'EdgeColor', 'none', 'FontSize', 12);
annotation('textbox', [0.45, .5, 0.1, 0.05], 'String', 'mid', ...
    'HorizontalAlignment', 'center', 'EdgeColor', 'none', 'FontSize', 12);
annotation('textbox', [0.75, .5, 0.1, 0.05], 'String', 'high', ...
    'HorizontalAlignment', 'center', 'EdgeColor', 'none', 'FontSize', 12);

y = -0.8:0.0001:0.8;
inMFYparams = [-0.81, -0.8, -0.7;
               -0.7, -0.6, -0.5;
               -0.5, -0.35, -0.2;
               0, 0.125, 0.25;
               0.3, 0.4, 0.5];
figure;
plot(y, triangle_mf(y, inMFYparams(1,:)), 'm');
hold on;
plot(y, triangle_mf(y, inMFYparams(2,:)), 'g');
plot(y, triangle_mf(y, inMFYparams(3,:)), 'r');
plot(y, triangle_mf(y, inMFYparams(4,:)), 'k');
plot(y, triangle_mf(y, inMFYparams(5,:)), 'c');
hold off;
title('(B) Presser-foot Displacement Range Consequence');
ylim([0, 1]);
xlim([-0.8, 0.8]);
legend('nofab', 'rib2p', 'inter2p', 'rib4p', 'interp4p');

z = 0.3:0.001:0.46;
outMFParams = [0.29, 0.3, 0.34;
              0.3, 0.34, 0.38;
              0.34, 0.38, 0.42;
              0.38, 0.42, 0.46;
              0.42, 0.46, 0.47];
figure;
plot(z, triangle_mf(z, outMFParams(1,:)), 'r');
hold on;
plot(z, triangle_mf(z, outMFParams(2,:)), 'k');
plot(z, triangle_mf(z, outMFParams(3,:)), 'b');
plot(z, triangle_mf(z, outMFParams(4,:)), 'm');
plot(z, triangle_mf(z, outMFParams(5,:)), 'g');
hold off;
title('(C) Force on the Presser-foot');
ylim([0, 1]);
xlim([0.3, 0.46]);
annotation('textbox', [0.175, .8, 0.1, 0.05], 'String', 'low', ...
    'HorizontalAlignment', 'center', 'EdgeColor', 'none', 'FontSize', 12);
annotation('textbox', [0.275, .65, 0.1, 0.05], 'String', 'lowmid', ...
    'HorizontalAlignment', 'center', 'EdgeColor', 'none', 'FontSize', 12);
annotation('textbox', [0.475, .65, 0.1, 0.05], 'String', 'mid', ...
    'HorizontalAlignment', 'center', 'EdgeColor', 'none', 'FontSize', 12);
annotation('textbox', [0.67, .65, 0.1, 0.05], 'String', 'highmid', ...
    'HorizontalAlignment', 'center', 'EdgeColor', 'none', 'FontSize', 12);
annotation('textbox', [0.775, .8, 0.1, 0.05], 'String', 'high', ...
    'HorizontalAlignment', 'center', 'EdgeColor', 'none', 'FontSize', 12);

% Rules defined in the reference
% | Rule No. | IF speed | AND displace | THEN force |
% | 1 | low | nofab | low |
% | 2 | low | rib2p | mid |
% | 3 | low | inter2p | mid |
% | 4 | low | rib4p | high |
% | 5 | low | inter4p | highmid |
% | 6 | mid | nofab | low |
% | 7 | mid | rib2p | mid |
% | 8 | mid | inter2p | mid |
% | 9 | mid | rib4p | high |
% | 10 | mid | inter4p | highmid |
% | 11 | high | nofab | low |
% | 12 | high | rib2p | mid |
% | 13 | high | inter2p | mid |
% | 14 | high | rib4p | high |
% | 15 | high | inter4p | highmid |
% using the rules, I determined which centers would be used based on the
% THEN column
low = outMFParams(1,2);
lowmid = outMFParams(2,2);
mid = outMFParams(3,2);
highmid = outMFParams(4,2);
high = outMFParams(5,2);
centers = [low, mid, mid, high, highmid, low, mid, mid, high, highmid, low, mid, mid, high, highmid ];

lowParams = outMFParams(1,:);
lowmidParams = outMFParams(2,:);
midParams = outMFParams(3,:);
highmidParams = outMFParams(4,:);
highParams = outMFParams(5,:);
outRuleParams = [lowParams; midParams; midParams; highParams; highmidParams; lowParams; midParams; midParams; highParams; highmidParams; lowParams; midParams; midParams; highParams; highmidParams ];

% define number of inputs N, liguistic values LV and rules P
N=[1, 1]; LV=[3, 5]; P=LV(1)^N(1) * LV(2)^N(2);

% the research paper lays out 15 rules, which matches the expected
% number of rules determined using the formula from module 5, slide 39
disp(['Number of rules : ', num2str(P)]);

x = 0:100:5000;
y = -0.8:0.01:0.8;
[X, Y] = meshgrid(x, y);

% initialize the rule weight variable h
h = zeros(1,P);
%---------------------------0-------------------
% initialize the crisp output vector
defuzzout=zeros(size(X));

%-------------------------------------------
%-------------------------------------------------------------
% simplified product inference
%-------------------------------------------------------------
% operate over the x / y universe of values
for ix=1:size(X, 1)
    for iy=1:size(X, 2)
        % compute rule certainty weights / heights (h) for given {x,y}
        irule=0;
        for j1=1:LV(1)
            xvalue = triangle_mf(X(ix, iy), inMFXparams(j1,:));
            if xvalue == 0
                xvalue = 1e-6;
            end
            for j2=1:LV(2)
                yvalue = triangle_mf(Y(ix, iy), inMFYparams(j2,:));
                irule=irule+1;
                if yvalue == 0
                    yvalue = 1e-6;
                end
                h(1,irule) = xvalue * yvalue;
            end
        end
        % center average (ca) defuzzification section
        defuzzout(ix,iy)=0;
        for irule=1:P
            defuzzout(ix,iy) = defuzzout(ix,iy)+centers(irule)*h(1,irule);
        end
        defuzzout(ix,iy) = defuzzout(ix,iy) / sum(h);
    end
end

%-------------------------------------------
% plot the results
%-------------------------------------------
figure; mesh(X,Y,defuzzout); title('Crisp Output of Fuzzy System')
xlabel('Speed'); ylabel('Displacement');
zlabel('Force');
shading interp;  % Smooth the surface
% Set axis limits
xlim([0 5000]);  % Set x-axis limits
ylim([-1 1]);    % Set y-axis limits
zlim([0.3 0.5]);       % Set z-axis limits

%------------------------------------------------
% Output without the speed variable
%------------------------------------------------
y = -0.8:0.01:0.8;

% initialize the rule weight variable h
h = zeros(1,LV(2));
%---------------------------0-------------------
% initialize the crisp output vector
defuzzout=zeros(length(y));

%-------------------------------------------
%-------------------------------------------------------------
% simplified product inference
%-------------------------------------------------------------
% operate over the y universe of values
for iy=1:length(y)
    % compute rule certainty weights / heights (h) for given {x,y}
    irule=0;
    for j2=1:LV(2)
        yvalue = triangle_mf(y(iy), inMFYparams(j2,:));
        irule=irule+1;
        if yvalue == 0
            yvalue = 1e-6;
        end
        h(1,irule) = yvalue;
    end
    % center average (ca) defuzzification section
    defuzzout(iy)=0;
    for irule=1:LV(2)
        defuzzout(iy) = defuzzout(iy)+centers(irule)*h(1,irule);
    end
    defuzzout(iy) = defuzzout(iy) / sum(h);
end

%-------------------------------------------
% plot the results
%-------------------------------------------
figure; plot(y,defuzzout); title('Crisp Output of Fuzzy System')
xlabel('Displacement');
ylabel('Force');
% shading interp;  % Smooth the surface
% Set axis limits
xlim([-1 1]);    % Set y-axis limits
ylim([0.3 0.5]);       % Set z-axis limits

function y = triangle_mf(x, parameter)
% TRIANGLE_MF Triangle MF with two parameters.
% TRIANGLE_MF(x,[a, b, c])returns matrix y with same size
% as x; each element of y is a grade of membership.
a = parameter(1); b = parameter(2); c = parameter(3);
y = max(min(((x-a)/(b-a)), ((c-x)/(c-b))), 0);
end
