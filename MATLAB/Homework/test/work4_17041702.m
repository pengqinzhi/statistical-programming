num = input('С�����˵���Ŀ������2<=n<=20��num = \n');
answer = input('С����ѡ�Ĵ� \n');
right = input('��ȷ�� \n');
for i = 1: num
    if answer(i) == right(i)
        flag{i} = '0';
    else
        flag{i} = '1';
    end
end
s = '';
for i = 1:num   %��������߼��ַ�
    s = [s,flag{i}];
end
day = bin2dec(s);   %������ת��Ϊʮ����
if day == 0
    fprintf('right now!');
else
    day
end