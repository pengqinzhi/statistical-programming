%输入不同p,q，测试序列A的优劣性
num=0;non=0;N=0;
for  i = 2:100
    for j = 1: i-1
        A = MedSeq(i,j);
        N = N + 1;
        flag = ISTRUE(C)
        if true == 1
            num = num + 1;    %比Lemma3.4更小的序列
        else 
            non = non + 1;

        end
    end
end
N
num
non
 