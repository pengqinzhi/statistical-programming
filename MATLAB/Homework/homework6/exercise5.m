%�ļ���ʽ�����Ȧ�˵�˳��
fid = fopen('numberoff.txt','w+'); 
y = numberoff(7,20);
fprintf(fid,' ��Ȧ˳��Ϊ�� \n');  
fprintf(fid,' %d \n',y);  
fclose(fid);


%��д��������
function y = numberoff(m,n)
x = 1:n;
y = zeros(1,n-m+1);
t = 0;            %Ŀǰ��Ȧ��������
p = size(x,2);
s = fix(p/m);      %ÿ�ֳ�Ȧ������
while s > 0
    j = 1;
    %����һ�ֱ���
    while j <= s
        y(t+j) = x(m*j);     %ÿ������m���˳�Ȧ
        j = j + 1;
    end
    %һ�ֽ�����ʣ�µ�����������
    x(m :m:m*s) = [];         
    if 3*s < p                  
        x = x([(m-1)*s+1:p-s, 1:(m-1)*s]);
    end
    t = t+s;
    p = size(x,2);
    s = fix(p/m);
end
end