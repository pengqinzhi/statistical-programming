function error=Euler_explicit(a, b, n, y0)
h = (b-a)/n;
x = a;
y1 = y0;
u(1,1) = y1;
t(1,1) = x;

for iter = 1:n
    x = x + h;
    y2 = y1 + h*(-5*y1);
    y1 = y2;
    u(1, iter+1) = y1;
    t(1, iter+1) = x;
end
size(t)
size(u)
v=exp(-5*t);
plot(t,v,'r',t,u);
error=norm(v-u,inf);
end
