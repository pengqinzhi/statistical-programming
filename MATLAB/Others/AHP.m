%计算权重向量
disp('请输入矩阵个数');
b=input('b = ');
W = { };
for i = 1:b+1
disp('请输入成对比较矩阵，优先输入中间层，依次输入最底层');
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
    W1=w    %中间层的决策向量
else
    disp('此矩阵的权重向量w = ');  disp(w);   %最底层的权重向量
    W{i}=w;
end
t = m(j);

%一致性检验
CI = (t - n)/(n - 1);  
RI = [0 0 0.52 0.89 1.12 1.26 1.36 1.41 1.46 1.49 1.52 1.54 1.56 1.58 1.59];
CR = CI/RI(n);
if CR < 0.10
    disp('通过一致性检验!');
    disp('CI = ');  disp(CI);
    disp('CR = ');  disp(CR);
else
    error('未通过一致性检验，请重新构造矩阵!');
end

i = i+1;
end
W2=cell2mat(W);  
Total = W2 * W1  %计算组合权重向量


% Total = 0;
% for s = 1:b
%   for v = 1:b
%     Total=Total+W{1}(s)*W{v+1}(s)
%   end
% end
