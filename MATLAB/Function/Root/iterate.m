function [x_star, it] = iterate(phi, x, ep, it_max)
if nargin < 4 it_max=100; end
if nargin < 3 ep = 1e-5; end
k = 1;
while k <= it_max
    x1 = feval(phi,x);
    fprintf("迭代第%d次，迭代解为%f，误差为%f\n",k , x1, abs(x1-x));
    if abs(x1-x) < ep 
        break; 
    end
    x = x1;
    k = k + 1;
end
x_star = x
it = k

