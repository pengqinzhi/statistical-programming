function p =  least_squar2(x, y, n, w)
if nargin < 4 w = 1; end
if nargin < 3 n = 1; end
m = length(x);
X = ones(1, m);
if m < n
    error('%拟合多项式次数必须低于数据的个数!');
end
for i = 1 : n 
    X = [x.^i; X];
end
A = zeros(1, n+1); A(1, n+1) = 1;
a = zeros(1, n+1); z = zeros(1, n+1);
for i = 1 : n
    phi = A(i, :) * X;
    t = sum(w .* phi .* phi);
    b = - sum(w .*phi .* x .* phi) / t;
    a(i) = sum(w .* y .*phi) / t;
    if i == 1 c = 0; else c = - t /t1; end
    t1 = t; %上一步phi的内积
    for j = 1 : n 
        z(j) = A(i, j+1); 
    end
    z(n+1) = 0;
    if i == 1
        z = z + b * A(i, :);
    else
        z = z + b * A(i, :) + c * A(i-1, :);
    end
    A = [A; z];     %记录phi0、phi1、...
end
phi = A(n+1, :) * X;
t = sum(w .* phi .* phi);
a(n+1) = sum(w .* y .*phi) / t;
p = rot90(a * A, 2);