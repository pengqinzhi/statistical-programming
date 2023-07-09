clc;
clear;
fprintf('分数序列2/1,3/2,5/3,8/5,13/8, 21/13...前n项求和:\n');
n = [50 500 1000 1500];
for i = 1:4
    y = fsum(n(i));
    
fprintf('前%d项之和为%f\n', n(i), y);
end

function y = fsum(n)
a = 2; b0 = 1;  %初始化
Y = [];     %生成序列向量
for i = 1 : n   %循环生成各项数
    b = b0;
    
        
    Y(i) = a/ b;
    b0 = a;
    
        
    a = (a + b);
    
end
y = sum(Y);
end

    
    

