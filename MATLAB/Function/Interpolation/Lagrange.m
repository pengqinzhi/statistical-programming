function yi=Lagrange(x,y,xi)   
n=length(x); ny=length(y);
if n~=ny
    error('%输入的插值点与其函数值个数应相同?!');
end
m=length(xi); p=zeros(n,m);   
for k=1:n         
    t=ones(n,m);   
    for j=1:n       
        if j~=k
            if abs(x(k)-x(j))<eps
                error('%输入的插值节点必须互异!');
            end
            t(j,:)=(xi-x(j))/(x(k)-x(j)); 
        end
    end
    p(k,:)=prod(t);   %连乘
end
yi=y*p;


         