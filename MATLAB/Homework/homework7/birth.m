function p = birth(n)
k = 5000;      %ʵ�����
account = 0;   %��ʼ��ʵ��ɹ�����
for i = 1:k    %��ʼ�ظ�ʵ��k��
    flag = test(n);
    if flag == 1
        account = account + 1;
    end
end
p = account/k;      %�������
fprintf("%d���������˼�����������ͬ�ĸ���Ϊ%d%",n,p*100)

function flag = test(n)
flag = 0;
A = randi(365,1,n);
for i = 1:365
B = ismember(A,i);      %����A���ڵ�i���������
C = (B~=0);
num = sum(C(:));
if num > 1            %������������ϵ���������ͬ��������ѭ��
    flag = 1;
    break;
end
end


