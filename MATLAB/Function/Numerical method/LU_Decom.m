function [L, U] = LU_Decom(A)
[n, m] = size(A);
if n ~= m 
    error('%��������Ƿ���!');
end
L = eye(n); U = zeros(n);
for k = 1 : n
    %��U����
    for j = k : n
        z = 0;
        for q = 1 : k-1
            z = z + L(k, q) * U(q, j);
        end
        U(k, j) = A(k, j) - z;
    end
    if abs(U(k, k)) < 1e-15
        error('%������ֽ�����!')
    end
    %��L����
    for i = k+1 : n
        z = 0;
        for q = 1 : k+1
            z = z + L(i, q) * U(q, k);
        end
        L(i, k) = (A(i, k) - z) / U(k, k);
    end
end
