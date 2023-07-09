function euler_improved(fun,y0,a,b,h)
n = (b - a)/h;
X = a:h:b;
Y = zeros(1,n+1);
X(1) = a; Y(1) = y0;
for i =  1:n
    x = X(i); y = Y(i);
    y1 = Y(i) + h * eval(fun);   %���ǰһ����f
    y = y1;
    y2 = Y(i) + h * eval(fun);   %����ǰһ���Ľ���������һ����f
    Y(i+1) = (y1 + y2) /2;       %������ʽ
end
[X',Y']          %���xy��Ӧֵ���б�
size(X)
size(Y)
v = exp(-5*X);
plot(X,v,'r',X,Y);    %��ͼ�Ƚϣ����ߴ���ȷ�⣬��ɫ������ƽ�
error = norm(v-Y,inf)  %���