function poisson1D(a,b,n)
h = (b-a)/n;
x = a:h:b;    %所有节点
A(1:n+1,1:n+1) = 0;      %处理边界条件
A(1,1) =  1.0;      
A(n+1,n+1) =  1.0;
R(1,1) = 0.0;     
R(n+1,1) = 0.0;

for i = 2:n   %刨除i=1和i=n的边界点
   R(i,1) = sin(x(i)) * h^2;    %迭代公式右端项
   A(i,i) = 2;     %写成矩阵格式，对角线元素为2
   A(i,i-1) = -1;  %对角线左右两边都是-1
   A(i,i+1) = -1;
end
%size(A);
%size(R);
u = A\R;
%u=gauss(A,R);
%x0=zeros(size(A,1),1);
%[u,n]=jacobi(A,R,x0,1.0e-6,1000000);
%[u,n]=guaseidel(A,R,x0,1.0e-6,1000000);
%n
%[u, n] = CG(A,R,x0,1.0e-12,100000);
%u
plot(x,u);

end