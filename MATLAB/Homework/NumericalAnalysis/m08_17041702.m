disp('例6.19');
x = [1 4 9]; y = [1 2 3];xi = [5 6];
yi=Lagrange(x,y,xi)

disp('例6.20');
yi=New_Int(x,y,xi)

disp('例6.21');
ydot = [1/2 1/4 1/6];
yi = Hermite(x, y, ydot, xi)

disp('例6.22');
xi = [2 5];
yi = interp1(x,y,xi,'linear')

disp('例6.23');
x = 0 : 10 ;y = sin(x);
xi = 0 : 0.2 : 10 ;yi = interp1(x,y,xi);
plot(x,y,'ko',xi,yi,'k-',xi,sin(xi),'r--','LineWidth',2);
xlabel('x'); ylabel('y');
legend('插值点','插值函数','原函数');
pause;

disp('补充题');
f = inline('1./(1+x.^2)','x'); a = -5; b = 5;
xi = -5: 0.01 :5; ypoint = f(xi);
for n=2 : 2 : 10
    X = [ a : (b-a)/n : b ]; Y = f(X);
    yi=Lagrange(X,Y,xi);     %作Lagrange插值
    plot(xi,ypoint,'r-', xi,yi,'b-','LineWidth',2);  %画出插值函数以及标出插值点
    hold on;
    plot(X,Y,'bo','LineWidth',2,'markersize',12);    %画出原函数
    hold off;
    axis([-5,5,-1,2]);
    tit = ['n=',int2str(n)];
    title(tit,'FontSize',20); legend('f(x)','L_n(x)');
    pause
end
disp('Runge现象的分析：');
disp('高次插值由于计算量大,可能会产生严重的误差积累,使得插值效果不佳');
disp('随着插值节点个数的增加,两个插值节点之间插值函数并不一定能够收敛至原函数。n越大，两端逼近效果越不好。');
disp('当n趋于无穷时，插值收敛区间为[-3.63,3.63]');
