function A = sparse_matrix(n, p, q)
e = ones(n, 1);
S = spdiags([-e p*e -e], -1:1, n, n);
S= S + sparse(1:n, n:-1:1, q, n, n);
m = n / 2; S(m, m+1) = -1; s(m+1, m) = -1;
A = full(S);