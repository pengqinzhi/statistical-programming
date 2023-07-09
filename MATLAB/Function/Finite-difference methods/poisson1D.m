function poisson1D(a,b,n)
h = (b-a)/n;
x = a:h:b;    %���нڵ�
A(1:n+1,1:n+1) = 0;      %����߽�����
A(1,1) =  1.0;      
A(n+1,n+1) =  1.0;
R(1,1) = 0.0;     
R(n+1,1) = 0.0;

for i = 2:n   %�ٳ�i=1��i=n�ı߽��
   R(i,1) = sin(x(i)) * h^2;    %������ʽ�Ҷ���
   A(i,i) = 2;     %д�ɾ����ʽ���Խ���Ԫ��Ϊ2
   A(i,i-1) = -1;  %�Խ����������߶���-1
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