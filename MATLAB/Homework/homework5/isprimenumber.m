function flag = isprimenumber(x)
flag = 1;
for i = 2 : floor(x/2)   %��2����������һ����ѭ��
    if mod(x,i) == 0     %������˵����������
        flag = 0;
        break;
    end
end
