n=10;
myfun1(n)

function myfun1(n)
h=1/n;
x=0:h:1;
A=zeros(n,n);
b=zeros(n,1);
for i=2:n          %生成刚性矩阵
 A(i-1,i-1)=-(x(i-1)^3+3*x(i-1)^2*x(i)-3*x(i-1)*x(i)^2-2*x(i)^3+3*x(i)^2*x(i+1)-3*x(i)*x(i+1)^2+x(i+1)^3-3*x(i-1)+3*x(i+1))/(3*h^2);
   A(i-1,i-1)=A(1,1);
end
A(n,n)=-1/h + 1/h^2*(x(n+1)^3/3-x(n)^3/3-x(n)*x(n+1)^2+x(n)^2*x(n+1));
A(n,n)=A(1,1)/2;
for i=2:n          
    A(i-1,i)=1/h - 1/h^2*(x(i+1)^3/6-x(i)^3/6+x(i)^2*x(i+1)/2-x(i)*x(i+1)^2/2);
    A(i,i-1)= A(i-1,i);
end
for i=2:n          %生成右端常数项
    b(i-1)=1/(2*h*pi^2)*(-x(i-1)*cos(2*pi*x(i))*pi+2*cos(2*pi*x(i))*pi*x(i)-x(i+1)*cos(2*pi*x(i))*pi+sin(pi*x(i-1))*cos(pi*x(i-1))+sin(pi*x(i+1))*cos(pi*x(i+1))-sin(2*pi*x(i)));  
end
b(n)=1/(4*h*pi^2)*(-2*x(n)*cos(2*pi*x(n+1))*pi+2*cos(2*pi*x(n+1))*pi*x(n+1)+2*sin(pi*x(n))*cos(pi*x(n))-sin(2*pi*x(n+1)))-2.03*pi/(1+4*pi^2);
y=Gauss_Seidel(A,b) ;    %调用求解方程组的算法
u1=zeros(n+1,1);
u1(2:n+1)=y
plot(x,u1,'--b');hold on;
x=0:0.01:1;
y=sin(2*pi*x)/(1+4*pi^2);                     
plot(x,y,'-r');hold on;
legend('有限元','精确解');
end
