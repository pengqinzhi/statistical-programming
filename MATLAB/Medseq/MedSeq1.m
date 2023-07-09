function [A,length1] = MedSeq1(p,q)
A = [];
A(1) = 0; A(2) = p; A(3) = q; %����A�ĳ�ʼ״̬
k = 4;    %��A�е��ĸ�ʼ��¼���ݣ�k��ʾԪ����A�еĴ���
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



%%ɾȥ����A�ж����Ԫ��
n = length(A);
for i = 4 : n
    r = A(i);             %����ɾ��ĳһԪ�أ��Ƿ��A��������Ӱ��
    A(i) = 0;
    for j = 4 : n
        if j == i  continue;  end
        flag = verify(A,p,A(i));
        if flag == 0      %���һ��Ԫ�ر�ɾ����ʹ������A������ĳһԪ���޷����㼯��A������ʱ����������֮����ɾ��
            A(i) = r; break
        end
    end
end

%ȷ�����յ�����A������֤Lemma3.4
X = find(A);                      %�ҵ����з���Ԫ��
A = sort([0,A(X)]);               %ȥ�������0��������󣬵õ����յ�����A
length1 = length(A);



%��֤ĳһ��Ԫ���Ƿ����㼯��A�����ʣ����Ƿ��ܳ�Ϊ����A������������ͬ����ƽ��ֵ
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
        



            
            

