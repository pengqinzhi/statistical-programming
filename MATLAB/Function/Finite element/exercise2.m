h = 0.1;a = 0; b = 2;   %设置条件
youxianyuan(a, b, h)

function youxianyuan(a, b, h)
n = (b - a)/h;      %节点个数
X = a:h:b;          %所有节点
A = zeros(n,n);
F = zeros(n,1);

%刚性矩阵
for i = 2:n
    A(i-1,i-1) = ((pi*pi/4)*(X(i-1)^3+3*X(i-1)^2*X(i)-3*X(i-1)*X(i)^2-2*X(i)^3+3*X(i)^2*X(i+1)-3*X(i)*X(i+1)^2+X(i+1)^3)-3*X(i-1)+3*X(i+1))/(3*h^2);
    A(i-1,i-1) = A(1,1);
end
A(n,n) = -1/h + ((pi*pi/4)*(X(n+1)^3/3-X(n)^3/3-X(n)*X(n+1)^2+X(n)^2*X(n+1)))/h^2;
A(n,n) = A(1,1)/2;
for i = 2:n
    A(i-1,i) = 1/h-((pi*pi/4)*(X(i+1)^3/6-X(i)^3/6+X(i)^2*X(i+1)/2-X(i)*X(i+1)^2/2)) / h^2;
    A(i,i-1) = A(i-1,i);
end

%计算右端项
for i=2:n
    F(i-1) = (pi*X(i-1)*cos(pi/2*X(i)) + pi*X(i+1)*cos(pi/2*X(i)) - 2*pi*X(i)*cos(pi/2*X(i)) + 4*sin(pi/2*X(i)) - 2*sin(pi/2*X(i-1) - 2*sin(pi/2*X(i+1))))/h^2;
end
F(n) = (pi*X(n-1)*cos(pi/2*X(n)) - pi*X(n)*cos(pi/2*X(n)) + 2/pi*sin(pi/2*X(n)) - 2/pi*sin(pi/2*X(n-1)) - pi/2) /h^2 ;
F
y = SOR1(A,F);    %求解方程组
u=zeros(n+1,1);
u(2:n+1)=y;
v=sin(pi/2*X);       %精确解
[u,v']  

plot(X,v,'r',X,u)
legend('精确解','数值解');
end


% SOR 迭代法求线性方程组数值解
function [x, k] = SOR1(A, b, w, ep, it_max)
if nargin < 5 it_max = 10000; end
if nargin < 4 ep = 1e-5; end
if nargin < 3 w = 1; end
d = diag(A); L = -tril(A,-1); U = -triu(A,1);
if min(abs(d))<1e-10
    error('%对角线元素为0，计算失败%');
end
%构造初始向量
n = length(b);
x=zeros(n,1);
for i=1:2:n
    x(i)=0.5;
end
%迭代
D = spdiags(d,0,n,n);
    B = (D - w * L) \ ((1 - w) * D + w * U); f = (D - w * L) \ (w * b);
    k = 1; 

    while k < it_max
        y = B*x+f;
        if norm(y - A\b,2) < ep
            break;
        end
        x = y;
        k = k+1;
    end
end