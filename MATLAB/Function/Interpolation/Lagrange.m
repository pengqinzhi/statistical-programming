function yi=Lagrange(x,y,xi)   
n=length(x); ny=length(y);
if n~=ny
    error('%����Ĳ�ֵ�����亯��ֵ����Ӧ��ͬ?!');
end
m=length(xi); p=zeros(n,m);   
for k=1:n         
    t=ones(n,m);   
    for j=1:n       
        if j~=k
            if abs(x(k)-x(j))<eps
                error('%����Ĳ�ֵ�ڵ���뻥��!');
            end
            t(j,:)=(xi-x(j))/(x(k)-x(j)); 
        end
    end
    p(k,:)=prod(t);   %����
end
yi=y*p;


         