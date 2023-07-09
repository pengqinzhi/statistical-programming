function flag = isprimenumber(x)
flag = 1;
for i = 2 : floor(x/2)   %从2到输入数的一般做循环
    if mod(x,i) == 0     %能整除说明不是素数
        flag = 0;
        break;
    end
end
