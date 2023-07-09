disp('��6.19');
x = [1 4 9]; y = [1 2 3];xi = [5 6];
yi=Lagrange(x,y,xi)

disp('��6.20');
yi=New_Int(x,y,xi)

disp('��6.21');
ydot = [1/2 1/4 1/6];
yi = Hermite(x, y, ydot, xi)

disp('��6.22');
xi = [2 5];
yi = interp1(x,y,xi,'linear')

disp('��6.23');
x = 0 : 10 ;y = sin(x);
xi = 0 : 0.2 : 10 ;yi = interp1(x,y,xi);
plot(x,y,'ko',xi,yi,'k-',xi,sin(xi),'r--','LineWidth',2);
xlabel('x'); ylabel('y');
legend('��ֵ��','��ֵ����','ԭ����');
pause;

disp('������');
f = inline('1./(1+x.^2)','x'); a = -5; b = 5;
xi = -5: 0.01 :5; ypoint = f(xi);
for n=2 : 2 : 10
    X = [ a : (b-a)/n : b ]; Y = f(X);
    yi=Lagrange(X,Y,xi);     %��Lagrange��ֵ
    plot(xi,ypoint,'r-', xi,yi,'b-','LineWidth',2);  %������ֵ�����Լ������ֵ��
    hold on;
    plot(X,Y,'bo','LineWidth',2,'markersize',12);    %����ԭ����
    hold off;
    axis([-5,5,-1,2]);
    tit = ['n=',int2str(n)];
    title(tit,'FontSize',20); legend('f(x)','L_n(x)');
    pause
end
disp('Runge����ķ�����');
disp('�ߴβ�ֵ���ڼ�������,���ܻ�������ص�������,ʹ�ò�ֵЧ������');
disp('���Ų�ֵ�ڵ����������,������ֵ�ڵ�֮���ֵ��������һ���ܹ�������ԭ������nԽ�����˱ƽ�Ч��Խ���á�');
disp('��n��������ʱ����ֵ��������Ϊ[-3.63,3.63]');
