function A = mediatedsequence(p,q)
A = [];
A(1) = 0; A(p+1) = p; A(q+1) = q; %����A�ĳ�ʼ״̬
qi = q;

%%ͨ�������p,q��������������������A
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



%%ɾȥ����A�ж����Ԫ��
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
        if flag == 0        %���һ��Ԫ�ر�ɾ����ʹ������A������ĳһԪ���޷����㼯��A������ʱ����������֮����ɾ��
            A(i+1) = r;
        end
    end
end

A = [0,find(A(2:p+1))];       %ȥ�������0���õ����յ�����A
        


%%��֤ĳһ��Ԫ���Ƿ����㼯��A�����ʣ����Ƿ��ܳ�Ϊ����A������������ͬ����ƽ��ֵ
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
