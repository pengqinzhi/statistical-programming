
disp('例6.25');
x= [1 4 9 16]; y = [1 : 4];
pp = spline(x, y)
pp.coefs
disp('注：从左至右依次为三次、二次、一次、零次的系数');
disp('空格以继续');
pause

disp('例6.26');
pp = csape(x, y, 'complete', [1/2 1/8])
pp.coefs
disp('注：从左至右依次为三次、二次、一次、零次的系数');
pause

disp('数值实验6(3)');
f = inline('1./(1+x.^2)','x'); a = -5; b = 5;
xi = -5: 0.01 :5; ypoint = f(xi); 
for n=2 : 2 : 10
    X = [ a : (b-a)/n : b ]; Y = f(X); 
    yi = interp1(X, Y, xi);     %作分段线性插值
    plot(xi,ypoint,'r-', xi,yi,'b-','LineWidth',2);  %画出插值函数以及标出插值点
    hold on;
    plot(X,Y,'bo','LineWidth',2,'markersize',12);    %画出原函数
    hold off;
    axis([-5,5,-1,2]);
    tit = ['n=',int2str(n)];
    title(tit,'FontSize',20); legend('f(x)','L(x)');
    pause
end
disp('观察可知，分段线性插值是收敛的');

disp('数值实验6(4)');
f = inline('1./(1+x.^2)','x'); a = -5; b = 5;
xi = -5: 0.01 :5; ypoint = f(xi); 
for n=2 : 2 : 10
    X = [ a : (b-a)/n : b ]; Y = f(X); 
    yi = interp1(X, Y, xi, 'pchip');     %作分段Hermite插值
    plot(xi,ypoint,'r-', xi,yi,'b-','LineWidth',2);  %画出插值函数以及标出插值点
    hold on;
    plot(X,Y,'bo','LineWidth',2,'markersize',12);    %画出原函数
    hold off;
    axis([-5,5,-1,2]);
    tit = ['n=',int2str(n)];
    title(tit,'FontSize',20); legend('f(x)','H(x)');
    pause
end
disp('观察可知，分段Hermite插值是收敛的');

disp('例7.16');
x = [1 3 5:10]; y = [10 5 2 1 1 2 3 4];
p = least_squar(x, y, 2)
p = least_squar2(x, y, 2)

disp('补充题如图所示');
f = @(x) 1./(1+x.^2); % 函数表达式
df = @(x) -(2*x)./(x.^2 + 1).^2; %一阶导数, 用于Hermite插值 
a=-5; b=5; %插值区间
n = 10; % 等分区间数
h = (b-a)/n;
xi = a : h : b; % 插值节点
fi = f(xi); % 插值节点上的函数值
dfi = df(xi); % 一阶导数值
x = a : (b-a)/500 : b; % 需要插值的点 

% 定义线性插值函数
L1 = @(x,x0,x1,f0,f1) f0*(x-x1)/(x0-x1) + f1*(x-x0)/(x1-x0);     
% 定义两点三次Hermite插值函数
H3 = @(x,x0,x1,f0,f1,df0,df1) (f0*(1+2*(x-x0)/(x1-x0))+df0*(x-x0))*((x-x1)/(x0-x1))^2 + (f1*(1+2*(x-x1)/(x0-x1))+df1*(x-x1))*((x-x0)/(x1-x0))^2; 

N = length(x);
y1 = zeros(1,N); 
y2 = zeros(1,N); 
for j = 1 : N
    for k=1 : n+1 % 寻找 x(j) 所在的小区间 [x_k,x_{k+1}]
        
        if xi(k) >= x(j)
            break; % 找到第一个不小于 x(j) 的插值节点
        end
    end
    if k>1 k=k-1; end
    y1(j) = L1(x(j),xi(k),xi(k+1),fi(k),fi(k+1));
    y2(j) = H3(x(j),xi(k),xi(k+1),fi(k),fi(k+1),dfi(k),dfi(k+1));
end
% 作图

plot(x,f(x),'r-', 'LineWidth',2); % f(x) 图像
hold on;
plot(x,y1,'b:', 'LineWidth',2,'markersize',5); % 分段线性插值图像 
plot(x,y2,'g--', 'LineWidth',2,'markersize',10); % 分段三次Hermite插值图像 
plot(xi,fi,'ok','markersize',10,'LineWidth',1.5); % 绘制插值节点
hold off
titstr=['n=',int2str(n)]; title(titstr,'fontsize',14); 
axis([-5,5,-1,2]);
legend('f(x)','L1(x)','H3(x)');

