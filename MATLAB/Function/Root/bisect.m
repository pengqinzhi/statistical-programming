function[x_star,it]=bisect(fun,a,b,ep)
if nargin < 4 ep = 1e-5; end
fa = feval(fun,a);fb = feval(fun,b);
if fa*fb > 0 
    error('��ʼ���䲻���и�����!');
end
k = 0;
while abs(b-a)/2 >= ep
    x = (a+b)/2;
    fx = feval(fun,x);
    if fx == 0 break; end
    if fx*fa < 0
        b = x;fb = fx;
    else
        a = x;fa = fx;
    end
    k = k+1;
    fprintf("������%d�Σ�������Ϊ%f�����Ϊ%f\n",k , x, abs(b-a)/2);
end
x_star = x
it = k

