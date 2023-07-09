function p =  least_squar(x, y, n, w)
if nargin < 4 w = 1; end
if nargin < 3 n = 1; end
m = length(y);
X = ones(1, m);
if m < n
    error('%拟合多项式次数必须低于数据的个数!');
end
for i = 1 : n
    X = [X ;(x.^i)];
end
A = X * diag(w) * X';
b = X * (w .* y)';
p = (A \ b)';
disp('注：从左至右分别为从零次至高次项的系数');