function [zout,zz, h,inMF,outMF] = fuzzytempctrl(x, y, plt);
%----------------------------------------------------------
%
% inputs description
% -----------------------------
% x speed in ms
% y displacement in mm
% plt flag to turn plotting on (1) or off (0).
% should be on only when input x is a vector.
%
% outputs
% -----------------------------
% yout defuzzified output (scalar or vector)
% yy output MF data over 0:maxyy/0:maxyy (for plotting)
% h P-vector of rule weights given x
% inMF input MF data over 0:x for plotting
% outMF output MF data over zz for plotting
% h rule weight vector (only used when x is a scalar)
%
% There are 5 fabrics that are used for analysis with the testing:
% no fabric (nofab)
% rib 2 plies (rib2p)
% rib 4 plies (rib4p)
% interlock 2 plies (inter2p)
% interlock 4 plies (inter4p)
%
% this m-file models sum-product inference using
% center-average defuzzification. The rule base is:
%
% R1: if X is low and Y is nofab then Z is low
% R2: if X is low and Y is rib2p then Z is mid
% R3: if X is low and Y is inter2p then Z is mid
% R4: if X is low and Y is rib4p then Z is high
% R5: if X is low and Y is inter4p then Z is highmid
% R6: if X is mid and Y is nofab then Z is low
% R7: if X is mid and Y is rib2p then Z is mid
% R8: if X is mid and Y is inter2p then Z is mid
% R9: if X is mid and Y is rib4p then Z is high
% R10: if X is mid and Y is inter4p then Z is highmid
% R11: if X is high and Y is nofab then Z is low
% R12: if X is high and Y is rib2p then Z is mid
% R13: if X is high and Y is inter2p then Z is mid
% R14: if X is high and Y is rib4p then Z is high
% R15: if X is high and Y is inter4p then Z is highmid
%
% the temp input MFs are triangle functions.
% the out MFs are triangles.
%
% this function can be driven with single input values x, or
% a vector of input values. the driver program is courseProject.m
%
% Intelligent Algorithms Course Project
% Breanna Burd
% EN.525.770.82FA24
%----------------------------------------------------------
%----------------------------------------------
% define triangle anonymous functions
%----------------------------------------------
% center triangle
 ctriang=@(x,P) max( min( (x-P(1))/(P(2)-P(1)),(P(3)-x)/(P(3)-P(2)) ),0 );
% open-left triangle
 ltriang=@(x,P) max( min( 1,(P(3)-x)/(P(3)-P(2)) ),0 );
% open-right triangle
 rtriang=@(x,P) max( min( (x-P(1))/(P(2)-P(1)),1 ),0 );
%-------------------------------------------
% initialization section
%-------------------------------------------
% define number of inputs N, liguistic values LV and rules P
 N=1; LV=3; P=LV^N;
 %-----------------------------------------
% define input MF triangle parameters
% (start,center,stop)
 inMFparams = [0, 0, 30;
 0, 20, 70;
 40, 70, 80];
%----------------------------------------------
% define output MF triangle centers. this is all that
% is required since CA defuzzification is being used.
% The start and stop parameters are then defined
% in terms of the centers.
% c = outMFcenters;
% outMFparams = [c(1)-c(1)/8, c(1), c(1)+c(1)/8;
% c(2)-c(2)/8, c(2), c(2)+c(2)/8;
% c(3)-c(3)/8, c(3), c(3)+c(3)/8];
 c = outMFcenters;
 outMFparams = [c(1)-c(1)/8, c(1), c(1)+c(1)/8;
 c(2)-c(2)/8, c(2), c(2)+c(2)/8;
c(3)-c(3)/8, c(3), c(3)+c(3)/8];
%----------------------------------------------
% initialize the evaluation parameters
 inMF = zeros(LV,length(x));
 maxy = outMFparams(1,3)*1.2;
 samplerate=400;
 yy = 0:maxy/samplerate:maxy;
 outMF = zeros(LV,length(yy));
%----------------------------------------------
% initialize the rule weight variable h
 h = zeros(1,P);
%----------------------------------------------
% initialize the crisp output vector
 defuzzout=zeros(1,length(x));
%-------------------------------------------
% construct the input and output MFs by
% evaluating the MFs over the relevant
% universes (X or Y). this is done for
% plotting purposes.
%-------------------------------------------
 for ii=1:length(x),
 inMF(1,ii)=ltriang(x(ii),inMFparams(1,:));
 inMF(2,ii)=ctriang(x(ii),inMFparams(2,:));
 inMF(3,ii)=rtriang(x(ii),inMFparams(3,:));
 end
 for ii=1:length(yy),
 outMF(1,ii)=ctriang(yy(ii),outMFparams(1,:));
 outMF(2,ii)=ctriang(yy(ii),outMFparams(2,:));
 outMF(3,ii)=ctriang(yy(ii),outMFparams(3,:));
 end
%-------------------------------------------
% inference and defuzzification
%-------------------------------------------
%----------------------------------------------
% operate over the x universe
 for ix=1:length(x),
%----------------------------------------------
% compute rule weights (h) for given input x. this
% is trivial given that there is only one
% linguistic variable in each rule. therefore,
% rule i weight is simply the relevant input MF
% evaluated at x.
 for j1=1:P,
 h(1,j1)=inMF(j1,ix);
 end
%----------------------------------------------
%----------------------------------------------
% compute crisp output
 defuzzout(ix)=0;
 for irule=1:P,
 xx = outMFparams(irule,2)*h(1,irule);
 defuzzout(ix) = defuzzout(ix)+ xx;
 end
 defuzzout(ix) = defuzzout(ix) / sum(h);
 end %for ix
 yout = defuzzout;
%-------------------------------------------
% plot all MFs and results
%-------------------------------------------
 if (plt),
 figure; subplot(2,1,1); plot(x,inMF(1:3,:));
 title('X MFs'); xlabel('Universe X');
 legend('cold','cool','warm'); grid
 subplot(2,1,2); plot(yy,outMF);
 title('Y MFs'); xlabel('Universe Y');
 legend('fast','medium','slow'); grid
 figure; plot(x,defuzzout);
 title('Crisp Output of Fuzzy System')
 grid; xlabel('Universe X'); ylabel('Universe Y');
 end %plt
%
end