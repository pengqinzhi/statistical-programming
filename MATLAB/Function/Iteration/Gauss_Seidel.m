function[x,k]=Gauss_Seidel(A, b, ep, it_max)
if nargin < 4 it_max = 100; end
if nargin < 3 ep = 1e-5; end
d = diag(A); L = -tril(A,-1); U = -triu(A,1);
if min(abs(d)) < 1e-10
    error('%�Խ���Ԫ��Ϊ0������ʧ��%');
end
n = length(b); x = zeros(n,1);
D = spdiags(d,0,n,n);
B = (D-L)\U; f = (D-L)\b;
k = 1; x0 = A\b;
while k < it_max
    y = B*x+f;
    fprintf('������%d�����������Ϊ%f\n',k, norm(y - A\b,inf));
    if norm(y-x,inf) < ep
        break;
    end
    x = y;
    k = k+1;
end