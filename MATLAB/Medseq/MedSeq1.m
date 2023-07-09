function [A,length1] = MedSeq1(p,q)
A = [];
A(1) = 0; A(2) = p; A(3) = q; %序列A的初始状态
k = 4;    %从A中第四格开始记录数据，k表示元素在A中的次序
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
                A(k) = qi;
            end
            flag = verify(A,p,qi);
            if flag == 1
                break;
            end
            k = k + 1;
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
                A(k) = qi;
            end
            flag = verify(A,p,qi);
            if flag == 1
                break;
            end
            k = k + 1;
        end
    end
    if flag == 1
        break;
    end
end



%%删去集合A中多余的元素
n = length(A);
for i = 4 : n
    r = A(i);             %假设删除某一元素，是否对A的性质有影响
    A(i) = 0;
    for j = 4 : n
        if j == i  continue;  end
        flag = verify(A,p,A(i));
        if flag == 0      %如果一个元素被删除后，使得序列A中其他某一元素无法满足集合A的性质时，纠正；反之，则删除
            A(i) = r; break
        end
    end
end

%确定最终的序列A，并验证Lemma3.4
X = find(A);                      %找到所有非零元素
A = sort([0,A(X)]);               %去掉多余的0，并排序后，得到最终的序列A
length1 = length(A);



%验证某一个元素是否满足集合A的性质，即是否能成为序列A中其他两个不同数的平均值
function flag = verify(A,p,q)
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
    return
        



            
            

