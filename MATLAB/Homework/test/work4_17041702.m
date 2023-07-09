num = input('小红做了的题目个数（2<=n<=20）num = \n');
answer = input('小红所选的答案 \n');
right = input('正确答案 \n');
for i = 1: num
    if answer(i) == right(i)
        flag{i} = '0';
    else
        flag{i} = '1';
    end
end
s = '';
for i = 1:num   %结合所有逻辑字符
    s = [s,flag{i}];
end
day = bin2dec(s);   %二进制转化为十进制
if day == 0
    fprintf('right now!');
else
    day
end