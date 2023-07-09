
num=0;non=0;N=0;
for  i = 500:700
    for j = 1: i-1
        [C,length2,A] = MedSeq2(i,j);
        N = N + 1;
        flag = ISTRUE(C);
        if flag == 1
            num = num + 1;    
        else 
            non = non + 1;

        end
    end
end

 N
num
non