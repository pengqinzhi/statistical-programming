data=[7.37 187.39 7.37 187.39
      15.10 195.10 0.07 180.07
      17.06 197.05 358.12 178.13
      17.35 197.35 357.41 177.42
      17.40 197.40 357.38 177.38
      22.47 202.47 352.30 172.30
      26.43 206.45 348.32 168.32
      27.52 207.52 347.23 167.23
      27.57 207.57 347.19 167.20];
integer=floor(data);
minute=data-integer;
decimals=minute/60*100;
X=decimals+integer;
u=find(X(:,3)>180);
X(u,3)=X(u,3)-360;
a1=abs(X(:,1)-X(:,3))/2;
a2=abs(X(:,2)-X(:,4))/2;
a=(a1+a2)/2;
A=[a1,a2,a];
integer2=floor(A);
minute2=A-integer2;
decimals2=minute2/100*60;
Y=round(decimals2+integer2,2);  %衍射角φ矩阵
d=[1*wave./sind(a(2:5)'),2*wave./sind(a(6:9)')];%求d
format long g ;   %关闭科学计数法
dd=round(d);    %四舍五入取整
D=mean(dd(:));   %求d的平均值

%作拟合直线
wave=[435.88,546.1,577.0,579.0];

p1=polyfit(wave,a(2:5)',1);  
x1=linspace(min(wave),max(wave));  
y1=polyval(p1,x1);  

p2=polyfit(wave,a(6:9)',1);   
y2=polyval(p2,x1); 

plot(wave,a(2:5)','+',x1,y1,wave,a(6:9)','+',x1,y2); 
k1=p1(1)*1.0e+09*pi/180;k2=p2(1)*1.0e+09*pi/180;  %拟合直线斜率:化作弧度制
axis([400,600,5,25]);
xlabel('波长λ（nm）');
ylabel('衍射角φ（度）');
title('色散曲线')
legend(k=1,k=2)