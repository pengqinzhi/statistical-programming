lengthgood1=0;lengthgood2=0;N=0;timegood1=0;timegood2=0;
for  i = 100: 102
    for j = 91
        tic;
        [A,length1] = MedSeq1(i,j);
        t1=toc;
        
        tic;
        [C,length2,A] = MedSeq2(i,j);
        t2=toc;
        N = N + 1;
        if length1 < length2
            lengthgood1 = lengthgood1 + 1;    %比Lemma3.4更小的序列
        else
            lengthgood2 = lengthgood2 + 1;
        end
        if t1 < t2
            timegood1 = timegood1 + 1;    %比Lemma3.4更小的序列
        else
            timegood2 = timegood2 + 1;
        end
    end
end
N
lengthgood1
lengthgood2
timegood1
timegood2