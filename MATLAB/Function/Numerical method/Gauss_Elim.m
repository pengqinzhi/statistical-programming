function x = Gauss_Elim(A, b)
[n, m] = size(A); nb = length(b);
if n ~= m
    error('%系数矩阵必须为方阵!');
end
if m ~= nb
    error('%方程两端维数不同!')
end
for k = 1 : n-1
    %选主元
    a_max = 0;
    for i = k : n
        if abs(A(i, k)) > a_max
            a_max = abs(A(i, k));
            r = i;
        end
    end
    if a_max < 1e-15
        error('%系数矩阵奇异，无法求解!');
    end
    %两行交换
    if r > k
        for j = k : n
            z       = A(k, j);
            A(k, j) = A(r, j);
            A(r, j) = z      ;
        end
        z = b(k); b(k) = b(r); b(r) = z;
    end
    %消元
    for i = k+1 : n
        t = A(i, k) / A(k, k);
        for j = k+1 : n
            A(i, j) = A(i, j) - t * A(k, j);
        end
        b(i) = b(i) - t * b(k);
    end
end
%回代
if abs(A(n, n)) < 1e-15
    error('%系数矩阵奇异，无法求解!');
end
x = zeros(size(b));
R = zeros(size(x));
for k = n : -1 : 1
    for j= k+1 : n
        b(k) = b(k) - A(k, j) * x(j);
    end
    x(k) = b(k) / A(k, k) ;
    R(k) = A(k, k) * x(k) - b(k);
end
R1=norm(R,1);
R2=norm(R,2);
R3=norm(R,-inf);
disp('残量Ax-b的1-范数 = ');disp(R1);
disp('残量Ax-b的2-范数 = ');disp(R2);
disp('残量Ax-b的无穷-范数 = ');disp(R3);
        