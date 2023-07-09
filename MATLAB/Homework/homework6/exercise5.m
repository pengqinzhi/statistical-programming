%文件形式输出出圈人的顺序
fid = fopen('numberoff.txt','w+'); 
y = numberoff(7,20);
fprintf(fid,' 出圈顺序为： \n');  
fprintf(fid,' %d \n',y);  
fclose(fid);


%编写报数函数
function y = numberoff(m,n)
x = 1:n;
y = zeros(1,n-m+1);
t = 0;            %目前出圈的总人数
p = size(x,2);
s = fix(p/m);      %每轮出圈的人数
while s > 0
    j = 1;
    %进行一轮报数
    while j <= s
        y(t+j) = x(m*j);     %每次数到m的人出圈
        j = j + 1;
    end
    %一轮结束后，剩下的人重新排列
    x(m :m:m*s) = [];         
    if 3*s < p                  
        x = x([(m-1)*s+1:p-s, 1:(m-1)*s]);
    end
    t = t+s;
    p = size(x,2);
    s = fix(p/m);
end
end