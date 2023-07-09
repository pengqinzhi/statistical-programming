function y = fast_Fourier(x)
n = length(x); y = zeros(1, n);
c0 = 1; s0 = 0; 
c= cos(2 * pi / n); s = sin(2 * pi / n);
w = zeros(1, n); w(1) = 1;
for k : n
    if k <= n/2
        c1 = c*c0 -s*s0; s1 = s*c0 + c*s0;
        w(k) = c1 -1i*s1; c0 = c1; s0 = s1;