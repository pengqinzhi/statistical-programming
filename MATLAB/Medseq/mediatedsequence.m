function A = mediatedsequence(p,q)
A = [];
A(1) = 0; A(p+1) = p; A(q+1) = q; %序列A的初始状态
qi = q;

%%通过输入的p,q迭代出满足条件的序列A
while (qi > 0) && (qi < p)          
    if (qi == p/2)
        break;
        
    elseif (qi < p/2)
        while qi < p
            qi = 2 * qi;
            if (qi == p)
                flag = 1; break;
            elseif (qi > p)
                qi = qi / 2; break;
            else
                A(qi+1) = qi;
            end
            flag = verify(A,qi);
            if flag == 1
                break;
            end
        end  
        
    else 
        while qi > 0
            interval = p - qi;
            qi = qi - interval;
            if (qi == 0)
                flag = 1; break;
            elseif (qi < 0)
                qi = qi + interval; break;
            else
                A(qi+1) = qi;
            end
            flag = verify(A,qi);
            if flag == 1
                break;
            end
        end
    end
    if flag == 1
        break;
    end
end



%%删去集合A中多余的元素
for i = 1 : p-1  
     if A(i+1) == 0
            continue;
        end
    r = A(i+1);
    A(i+1) = 0;
    for j = 1 : p-1
        if (j == i) || (A(j+1) == 0)
            continue;
        end
        flag = verify(A,A(j+1));
        if flag == 0        %如果一个元素被删除后，使得序列A中其他某一元素无法满足集合A的性质时，纠正；反之，则删除
            A(i+1) = r;
        end
    end
end

A = [0,find(A(2:p+1))];       %去掉多余的0，得到最终的序列A
        


%%验证某一个元素是否满足集合A的性质，即是否能成为序列A中其他两个不同数的平均值
function flag = verify(A,q)
flag = 0;
p = A(end);
for i = 1 : q
    if A(i) == 0 && i ~= 1
        continue;
    end
    for j = q+2 : p+1
        if A(j) == 0
            continue;
        end
        if A(q+1) == (A(i) + A(j)) / 2
            flag = 1;
            break;
        end
    end
    if flag == 1
        break;
    end
end
