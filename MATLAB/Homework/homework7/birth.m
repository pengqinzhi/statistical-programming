function p = birth(n)
k = 5000;      %实验次数
account = 0;   %初始化实验成功次数
for i = 1:k    %开始重复实验k次
    flag = test(n);
    if flag == 1
        account = account + 1;
    end
end
p = account/k;      %算出概率
fprintf("%d个人中两人及以上生日相同的概率为%d%",n,p*100)

function flag = test(n)
flag = 0;
A = randi(365,1,n);
for i = 1:365
B = ismember(A,i);      %查找A中在第i天出生的人
C = (B~=0);
num = sum(C(:));
if num > 1            %如果两个及以上的人生日相同，则跳出循坏
    flag = 1;
    break;
end
end


