function poisson2D(a,b,m,c,d,n)
h1 = (b-a)/m;    %x������m�ȷ�
h2 = (d-c)/n;    %y������n�ȷ�
X = a:h1:b;      %x��ڵ�
Y = c:h2:d;      %y��ڵ�
N=(m+1)*(n+1);   %���е�ĸ���
%U = sin(pi.*X).*sin(pi.*Y);
%L = 4*del2(U,h1,h2);    %����������˹����

%����
A=zeros(N,N);    %��ʼ�� 
for i = 1:m+1
    for j = 1:n+1
       if (i~=1) && (i~=(m+1)) && (j~=1) && (j~=(n+1))    %i,j�����������α�ʱ
          %x = X(i); y = Y(j);
          w = (i-1)*(n+1)+j;
          up = w+1;        %���������ĸ���
          down = w-1;
          left = w-(n+1);
          right = w+(n+1);
          R(w,1) = 2*pi*pi*sin(pi*X(i))*sin(pi*Y(j));    %�������̵��Ҷ���
          %R(w,1) = -L *  eval(fun);    
          A(w,w) = 2/h1^2+2/h2^2;
          A(w,up) = -1/h2^2;        %�ĸ����ϵ��
          A(w,down) = -1/h2^2;
          A(w,left) = -1/h1^2;
          A(w,right) = -1/h1^2;
       else
          w = (i-1)*(n+1)+j;     %����߽�����
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


