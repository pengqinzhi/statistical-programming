function yi=New_Int(x,y,xi)
n=length(x);ny=length(y);
if n~=ny
    error('%����Ĳ�ֵ�����Ӧ����ֵ����Ӧ����ͬ!');
end
%���������Y
Y=zeros(n);   
Y(:,1)=y';             %ȷ��������һ��Ϊһϵ�к���ֵy
global k
for k=1:n-1            %n���ڵ���߹���n-1�׾��k������
    for i=1:n-k        %�������ҹ��������ÿһ��Ԫ�����μ���1�i������
        if abs(x(i+k)-x(i))<eps
            error('%����Ĳ�ֵ�ڵ���뻥��!')
        end
        Y(i,k+1)=(Y(i+1,k)-Y(i,k))/(x(i+k)-x(i));   %�����Ϊ����������
    end
end
%����Newton��ֵ��ʽ N��xi��
m=length(xi); yi=zeros(1,m);    %����������ֵyi�Ŀվ���
%����һ
for i=1:n
    z=ones(1,m);         %����w(x)=(x-x0)(x-x1)...(x-xn)�Ŀվ���
    for k=1:i-1
        z=z.*(xi-x(k));
    end
    yi=yi+Y(1,i)*z;
end
%������������������ˣ������
% z=ones(m,k);b=ones(m,k);
% for i=1:n
%     if i~=n
%     z(:,i+1)=z(:,i).*(xi-x(i))';     %�����w(x)
%     end
%     b(:,i)=Y(1,i).*z(:,i);           %���θ����w(x)*��Ӧ�ľ���
%    end
% yi=sum(b,2);                         %ÿһ�����ֵ��Ϊ�����ֵ��ʽ�õ��Ĺ�ֵ
disp('��������£�');
disp(Y);