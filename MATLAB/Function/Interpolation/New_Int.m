function yi=New_Int(x,y,xi)
n=length(x);ny=length(y);
if n~=ny
    error('%输入的插值点与对应函数值个数应当相同!');
end
%构建均差表Y
Y=zeros(n);   
Y(:,1)=y';             %确定均差表第一列为一系列函数值y
global k
for k=1:n-1            %n个节点最高构建n-1阶均差，k作表列
    for i=1:n-k        %从左至右构建均差表每一列元素依次减少1项，i作表行
        if abs(x(i+k)-x(i))<eps
            error('%输入的插值节点必须互异!')
        end
        Y(i,k+1)=(Y(i+1,k)-Y(i,k))/(x(i+k)-x(i));   %均差表为反上三角型
    end
end
%计算Newton插值公式 N（xi）
m=length(xi); yi=zeros(1,m);    %构建所求函数值yi的空矩阵
%方法一
for i=1:n
    z=ones(1,m);         %构建w(x)=(x-x0)(x-x1)...(x-xn)的空矩阵
    for k=1:i-1
        z=z.*(xi-x(k));
    end
    yi=yi+Y(1,i)*z;
end
%方法二：用两矩阵相乘，再求和
% z=ones(m,k);b=ones(m,k);
% for i=1:n
%     if i~=n
%     z(:,i+1)=z(:,i).*(xi-x(i))';     %各项的w(x)
%     end
%     b(:,i)=Y(1,i).*z(:,i);           %依次各项的w(x)*对应的均差
%    end
% yi=sum(b,2);                         %每一行求和值即为所求插值公式得到的估值
disp('均差表如下：');
disp(Y);