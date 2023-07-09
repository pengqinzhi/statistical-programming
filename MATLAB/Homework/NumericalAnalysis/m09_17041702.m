
disp('��6.25');
x= [1 4 9 16]; y = [1 : 4];
pp = spline(x, y)
pp.coefs
disp('ע��������������Ϊ���Ρ����Ρ�һ�Ρ���ε�ϵ��');
disp('�ո��Լ���');
pause

disp('��6.26');
pp = csape(x, y, 'complete', [1/2 1/8])
pp.coefs
disp('ע��������������Ϊ���Ρ����Ρ�һ�Ρ���ε�ϵ��');
pause

disp('��ֵʵ��6(3)');
f = inline('1./(1+x.^2)','x'); a = -5; b = 5;
xi = -5: 0.01 :5; ypoint = f(xi); 
for n=2 : 2 : 10
    X = [ a : (b-a)/n : b ]; Y = f(X); 
    yi = interp1(X, Y, xi);     %���ֶ����Բ�ֵ
    plot(xi,ypoint,'r-', xi,yi,'b-','LineWidth',2);  %������ֵ�����Լ������ֵ��
    hold on;
    plot(X,Y,'bo','LineWidth',2,'markersize',12);    %����ԭ����
    hold off;
    axis([-5,5,-1,2]);
    tit = ['n=',int2str(n)];
    title(tit,'FontSize',20); legend('f(x)','L(x)');
    pause
end
disp('�۲��֪���ֶ����Բ�ֵ��������');

disp('��ֵʵ��6(4)');
f = inline('1./(1+x.^2)','x'); a = -5; b = 5;
xi = -5: 0.01 :5; ypoint = f(xi); 
for n=2 : 2 : 10
    X = [ a : (b-a)/n : b ]; Y = f(X); 
    yi = interp1(X, Y, xi, 'pchip');     %���ֶ�Hermite��ֵ
    plot(xi,ypoint,'r-', xi,yi,'b-','LineWidth',2);  %������ֵ�����Լ������ֵ��
    hold on;
    plot(X,Y,'bo','LineWidth',2,'markersize',12);    %����ԭ����
    hold off;
    axis([-5,5,-1,2]);
    tit = ['n=',int2str(n)];
    title(tit,'FontSize',20); legend('f(x)','H(x)');
    pause
end
disp('�۲��֪���ֶ�Hermite��ֵ��������');

disp('��7.16');
x = [1 3 5:10]; y = [10 5 2 1 1 2 3 4];
p = least_squar(x, y, 2)
p = least_squar2(x, y, 2)

disp('��������ͼ��ʾ');
f = @(x) 1./(1+x.^2); % �������ʽ
df = @(x) -(2*x)./(x.^2 + 1).^2; %һ�׵���, ����Hermite��ֵ 
a=-5; b=5; %��ֵ����
n = 10; % �ȷ�������
h = (b-a)/n;
xi = a : h : b; % ��ֵ�ڵ�
fi = f(xi); % ��ֵ�ڵ��ϵĺ���ֵ
dfi = df(xi); % һ�׵���ֵ
x = a : (b-a)/500 : b; % ��Ҫ��ֵ�ĵ� 

% �������Բ�ֵ����
L1 = @(x,x0,x1,f0,f1) f0*(x-x1)/(x0-x1) + f1*(x-x0)/(x1-x0);     
% ������������Hermite��ֵ����
H3 = @(x,x0,x1,f0,f1,df0,df1) (f0*(1+2*(x-x0)/(x1-x0))+df0*(x-x0))*((x-x1)/(x0-x1))^2 + (f1*(1+2*(x-x1)/(x0-x1))+df1*(x-x1))*((x-x0)/(x1-x0))^2; 

N = length(x);
y1 = zeros(1,N); 
y2 = zeros(1,N); 
for j = 1 : N
    for k=1 : n+1 % Ѱ�� x(j) ���ڵ�С���� [x_k,x_{k+1}]
        
        if xi(k) >= x(j)
            break; % �ҵ���һ����С�� x(j) �Ĳ�ֵ�ڵ�
        end
    end
    if k>1 k=k-1; end
    y1(j) = L1(x(j),xi(k),xi(k+1),fi(k),fi(k+1));
    y2(j) = H3(x(j),xi(k),xi(k+1),fi(k),fi(k+1),dfi(k),dfi(k+1));
end
% ��ͼ

plot(x,f(x),'r-', 'LineWidth',2); % f(x) ͼ��
hold on;
plot(x,y1,'b:', 'LineWidth',2,'markersize',5); % �ֶ����Բ�ֵͼ�� 
plot(x,y2,'g--', 'LineWidth',2,'markersize',10); % �ֶ�����Hermite��ֵͼ�� 
plot(xi,fi,'ok','markersize',10,'LineWidth',1.5); % ���Ʋ�ֵ�ڵ�
hold off
titstr=['n=',int2str(n)]; title(titstr,'fontsize',14); 
axis([-5,5,-1,2]);
legend('f(x)','L1(x)','H3(x)');

