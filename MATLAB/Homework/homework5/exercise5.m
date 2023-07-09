clc;
clear;
num=0; 
MAX = 1;
while  isinf(MAX) == 0
     MAX=MAX*2;
     num = num + 1;
end
MAX = 2^(num-1)


