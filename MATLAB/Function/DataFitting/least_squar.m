function p =  least_squar(x, y, n, w)
if nargin < 4 w = 1; end
if nargin < 3 n = 1; end
m = length(y);
X = ones(1, m);
if m < n
    error('%��϶���ʽ��������������ݵĸ���!');
end
for i = 1 : n
    X = [X ;(x.^i)];
end
A = X * diag(w) * X';
b = X * (w .* y)';
p = (A \ b)';
disp('ע���������ҷֱ�Ϊ��������ߴ����ϵ��');