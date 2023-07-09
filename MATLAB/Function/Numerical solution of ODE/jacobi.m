function [x,n] = jacobi(A,b,x0,tol,times)
D = diag(diag(A));%��A�ĶԽǾ���
L = -tril(A,-1);  %��A�������Ǿ���
U = -triu(A,1);   %��A�������Ǿ���
G = D\(L+U);
g = D\b;
x = G*x0+g;
n = 1;%��������
while norm(x-x0)>=tol
    x0 = x;
    x = G*x0+g;
    n = n+1;
    if(n>=times)
        disp('too many iteration times!')
        return;
    end
end
end