function [L, U] = LU_Decom(A)
[n, m] = size(A);
if n ~= m 
    error('%矩阵必须是方阵!');
end
L = eye(n); U = zeros(n);
for k = 1 : n
    %求U矩阵
    for j = k : n
        z = 0;
        for q = 1 : k-1
            z = z + L(k, q) * U(q, j);
        end
        U(k, j) = A(k, j) - z;
    end
    if abs(U(k, k)) < 1e-15
        error('%不满足分解条件!')
    end
    %求L矩阵
    for i = k+1 : n
        z = 0;
        for q = 1 : k+1
            z = z + L(i, q) * U(q, k);
        end
        L(i, k) = (A(i, k) - z) / U(k, k);
    end
end
