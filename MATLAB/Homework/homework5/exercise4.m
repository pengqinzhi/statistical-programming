clc;
clear;
fprintf('��������2/1,3/2,5/3,8/5,13/8, 21/13...ǰn�����:\n');
n = [50 500 1000 1500];
for i = 1:4
    y = fsum(n(i));
    
fprintf('ǰ%d��֮��Ϊ%f\n', n(i), y);
end

function y = fsum(n)
a = 2; b0 = 1;  %��ʼ��
Y = [];     %������������
for i = 1 : n   %ѭ�����ɸ�����
    b = b0;
    
        
    Y(i) = a/ b;
    b0 = a;
    
        
    a = (a + b);
    
end
y = sum(Y);
end

    
    

