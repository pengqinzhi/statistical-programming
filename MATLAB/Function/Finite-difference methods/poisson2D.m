function poisson2D(a,b,m,c,d,n)
h1 = (b-a)/m;    %x轴区间m等分
h2 = (d-c)/n;    %y轴区间n等分
X = a:h1:b;      %x轴节点
Y = c:h2:d;      %y轴节点
N=(m+1)*(n+1);   %所有点的个数
%U = sin(pi.*X).*sin(pi.*Y);
%L = 4*del2(U,h1,h2);    %计算拉普拉斯算子

%迭代
A=zeros(N,N);    %初始化 
for i = 1:m+1
    for j = 1:n+1
       if (i~=1) && (i~=(m+1)) && (j~=1) && (j~=(n+1))    %i,j不在四条矩形边时
          %x = X(i); y = Y(j);
          w = (i-1)*(n+1)+j;
          up = w+1;        %上下左右四个点
          down = w-1;
          left = w-(n+1);
          right = w+(n+1);
          R(w,1) = 2*pi*pi*sin(pi*X(i))*sin(pi*Y(j));    %迭代方程的右端项
          %R(w,1) = -L *  eval(fun);    
          A(w,w) = 2/h1^2+2/h2^2;
          A(w,up) = -1/h2^2;        %四个点的系数
          A(w,down) = -1/h2^2;
          A(w,left) = -1/h1^2;
          A(w,right) = -1/h1^2;
       else
          w = (i-1)*(n+1)+j;     %处理边界条件
          A(w,w) = 1;
          R(w,1) = 0;
       end
    end
end
size(A)
size(R)
u = A\R;

for i=1:m+1
    for j=1:n+1
       v(i,j)=u((i-1)*(n+1)+j);
    end
end
surf(X,Y,v);

end


