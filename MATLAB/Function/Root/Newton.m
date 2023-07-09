function [x_star, it] = Newton(fun , x0, ep, it_max)
if nargin < 4 it_max = 100; end
if nargin < 3 ep = 1e-5; end
k = 1;
syms x
df = diff(fun, x);
while k <= it_max 
    f = feval(fun, x0);
    dotf = subs(df, x, x0);
    if abs(dotf) < 1e-10 
        error('���ʧ�ܣ������ĵ���Ϊ0��');
    end
    x1 = x0 - f/dotf;
    fprintf("������%d�Σ�������Ϊ%.12f������ֵΪ%.12f,���Ϊ%.12f\n",k , x1, f, abs(x1 - x0));
    if abs(f) < ep 
        break
    end
    x0 = x1;
    k = k + 1;
end
x_star =  vpa(x1,5)
it = k
