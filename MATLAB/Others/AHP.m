%����Ȩ������
disp('������������');
b=input('b = ');
W = { };
for i = 1:b+1
disp('������ɶԱȽϾ������������м�㣬����������ײ�');
A = input('A =');
n = size(A,1);
x = ones(n, 100);
y = ones(n, 100);
m = zeros(1, 100);
m(1) = max(x(:, 1));
y(:, 1) = x(:, 1);
x(:, 2) = A * y(:, 1);
m(2) = max(x(:, 2));
y(:, 2) = x(:, 2)/m(2);
p = 0.0001;j = 2;k = abs(m(2) - m(1));
while  k > p
    j = j+1;
    x(:, j) = A * y(:, j - 1);
    m(j) = max(x(:, j));
    y(:, j) = x(:, j)/m(j);
    k = abs(m(j) - m(j - 1));
end
a = sum(y(:, j));
w = y(:, j)/a;
if i == 1
    W1=w    %�м��ľ�������
else
    disp('�˾����Ȩ������w = ');  disp(w);   %��ײ��Ȩ������
    W{i}=w;
end
t = m(j);

%һ���Լ���
CI = (t - n)/(n - 1);  
RI = [0 0 0.52 0.89 1.12 1.26 1.36 1.41 1.46 1.49 1.52 1.54 1.56 1.58 1.59];
CR = CI/RI(n);
if CR < 0.10
    disp('ͨ��һ���Լ���!');
    disp('CI = ');  disp(CI);
    disp('CR = ');  disp(CR);
else
    error('δͨ��һ���Լ��飬�����¹������!');
end

i = i+1;
end
W2=cell2mat(W);  
Total = W2 * W1  %�������Ȩ������


% Total = 0;
% for s = 1:b
%   for v = 1:b
%     Total=Total+W{1}(s)*W{v+1}(s)
%   end
% end
