clc;
clear;
x=input('������һ��������\n');
flag = isprimenumber(x);
if flag == 1
    fprintf('%d������\n',x);
else
   fprintf('%d��������\n',x);
end