clc;
clear;
account=0;      %初始化
for i=1:2020 %循环，统计个数
    account = account + zeroaccount(i);
end
fprintf('1至2020中共有%d个零\n',account);