%验证集合是否符合要求
function flag = ISTRUE(A)
n = length(A);
for i = 2 : n-1
    flag = verify(A,A(i));
    if flag == 0
        break;
    end
    
end
end


%验证某一个元素是否满足集合A的性质，即是否能成为序列A中其他两个不同数的平均值
function flag = verify(A,q)
    flag = 0;
    n = length(A);
    for i = 1 : n
        if A(i) == q  continue; end
        for j = 1 : n
            if (j == i) || (A(i) == q)  continue; end
            if q == (A(i) + A(j)) / 2
                flag = 1;
                break;
            end
        end
        if flag == 1
            break;
        end
    end

end

        

