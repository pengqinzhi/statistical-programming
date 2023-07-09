function euler_improved(fun,y0,a,b,h)
n = (b - a)/h;
X = a:h:b;
Y = zeros(1,n+1);
X(1) = a; Y(1) = y0;
for i =  1:n
    x = X(i); y = Y(i);
    y1 = Y(i) + h * eval(fun);   %算出前一步的f
    y = y1;
    y2 = Y(i) + h * eval(fun);   %代入前一步的结果，算出后一步的f
    Y(i+1) = (y1 + y2) /2;       %迭代公式
end
[X',Y']          %输出xy对应值的列表
size(X)
size(Y)
v = exp(-5*X);
plot(X,v,'r',X,Y);    %画图比较，红线代表精确解，蓝色代表近似解
error = norm(v-Y,inf)  %误差