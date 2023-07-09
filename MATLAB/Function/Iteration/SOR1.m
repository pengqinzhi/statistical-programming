function [x, k] = SOR1(A, b, w, ep, it_max)
if nargin < 5 it_max = 10000; end
if nargin < 4 ep = 1e-5; end
if nargin < 3 w = 1; end
d = diag(A); L = -tril(A,-1); U = -triu(A,1);
if min(abs(d))<1e-10
    error('%�Խ���Ԫ��Ϊ0������ʧ��%');
end
%�����ʼ����
n = length(b);
x=zeros(n,1);
for i=1:2:n
    x(i)=0.5;
end
%����
D = spdiags(d,0,n,n);


    B = (D - w * L) \ ((1 - w) * D + w * U); f = (D - w * L) \ (w * b);
    k = 1; 

    while k < it_max
        y = B*x+f;
        fprintf('������%d�����������Ϊ%f\n',k, norm(y - A\b,2));
        if norm(y - A\b,2) < ep
            break;
        end
        x = y;
        k = k+1;
    end
