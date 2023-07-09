function euler1(fun,y0,a,b,h)
n = (b - a)/h;
X = a:h:b;
Y = zeros(1,n+1);
X(1) = a; Y(1) = y0;
for i =  1:n
    x = X(i); y = Y(i);
    Y(i+1) = Y(i) + h * eval(fun);
end
[X',Y']
