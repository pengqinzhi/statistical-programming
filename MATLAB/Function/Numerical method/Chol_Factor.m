%Cholesky�ֽ⣺A=L*L'
function L = Chol_Factor(A)
n = length(A); L = zeros(n);
for k = 1 : n
    %�ж�������
    delta = A(k, k);
    for j= 1 :k-1
        delta = delta - L(k, j)^2;
    end
    if delta < 1e-15
        error('%�����������޷��ֽ�!');
    end
    %����L����
    L(k, k) = sqrt(delta);
    for i = k+1 : n
        L(i, k) = A(i, k);
        for j = 1 : k-1
            L(i, k) =  L(i, k) - L(i, j) * L(k, j);
        end
        L(i, k) = L(i, k) / L(k, k);
    end
end