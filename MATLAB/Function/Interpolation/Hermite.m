function yi = Hermite(x, y, ydot, xi)
if isempty(ydot) == 1 
    ydot = gradient(y, x);
end
n = length(x); n1 = length(y); n2 = length(ydot);
if n ~= n1 |n ~= n2 |n1 ~= n1 
    error('%输入的插值节点、函数值以及导数值必须相同!')
end
m = length(xi); p =zeros(n, m);
q = zeros(1, n); yi = zeros(1, m);
for k = 1 : n
    t = ones(n, m); z = zeros(1, n);
    for j = 1 : n
        if j ~= k
            if abs(x(k) - x(j)) < eps
                error('%插值节点必须互异!')
            end
            t(j, :) = (xi - x(j)) / (x(k) - x(j));
            z(j) = 1 / (x(k) - x(j));
        end
    end
    p(k, :) = prod(t); q(k) = sum(z);
    yi = yi + y(k) * (1 - 2 * (xi - x(k)) * q(k)) .* p(k, :).^2 + ydot(k) * (xi - x(k)) .* p(k, :).^2;
end

 
