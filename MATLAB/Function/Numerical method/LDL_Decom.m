%�Ľ�ƽ�����ֽ�
function [L, D] = LDL_Decom(A)
n = length(A);
L = eye(n);
d = zeros(1, n);
T = zeros(n);
for k = 1 : n
    %�ж�
    d(k) = A(k ,k);
    for j = 1 : k-1
        d(k) = d(k) - L(k, j) * T(k, j);
    end
    if abs(d(k))< 1e-15
        error('%�����޷��ֽ�!');
    end
    %����L����
    for i = k+1 : n
        T(i, k) = A(i, k);
        for j = 1 : k-1
            T(i, k) = T(i, k) - T(i, j) * L(k, j);
        end
        L(i, k) = T(i, k) / d(k);
    end
end
D = diag(d);
