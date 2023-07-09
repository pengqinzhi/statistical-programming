format short
disp('例子2.16');
fun = @(x)(x^3-x-1);a = 1; b = 2;
[x_star,it]=bisect(fun,a,b);

disp('例子2.18');
phi = @(x)((x+1)^(1/3));x = 1.5; 
[x_star, it] = iterate(phi, x);

disp('例子2.20');
fun = @(x)(x^3-x-1);x0 = 1.5;
[x_star, it] = Newton(fun , x0);

disp('补充题');
fun = @(x)(x*exp(x)-1);x0 = 0.5; ep= 1e-10;
[x_star, it] = Newton(fun , x0, ep);