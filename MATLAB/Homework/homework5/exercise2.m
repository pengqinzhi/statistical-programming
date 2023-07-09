clc;
clear;
x=input('请输入一个整数：\n');
flag = isprimenumber(x);
if flag == 1
    fprintf('%d是素数\n',x);
else
   fprintf('%d不是素数\n',x);
end