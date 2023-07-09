function x = Gauss_Elim(A, b)
[n, m] = size(A); nb = length(b);
if n ~= m
    error('%ϵ���������Ϊ����!');
end
if m ~= nb
    error('%��������ά����ͬ!')
end
for k = 1 : n-1
    %ѡ��Ԫ
    a_max = 0;
    for i = k : n
        if abs(A(i, k)) > a_max
            a_max = abs(A(i, k));
            r = i;
        end
    end
    if a_max < 1e-15
        error('%ϵ���������죬�޷����!');
    end
    %���н���
    if r > k
        for j = k : n
            z       = A(k, j);
            A(k, j) = A(r, j);
            A(r, j) = z      ;
        end
        z = b(k); b(k) = b(r); b(r) = z;
    end
    %��Ԫ
    for i = k+1 : n
        t = A(i, k) / A(k, k);
        for j = k+1 : n
            A(i, j) = A(i, j) - t * A(k, j);
        end
        b(i) = b(i) - t * b(k);
    end
end
%�ش�
if abs(A(n, n)) < 1e-15
    error('%ϵ���������죬�޷����!');
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
disp('����Ax-b��1-���� = ');disp(R1);
disp('����Ax-b��2-���� = ');disp(R2);
disp('����Ax-b������-���� = ');disp(R3);
        