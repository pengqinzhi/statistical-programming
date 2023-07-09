function yi = Hermite(x, y, ydot, xi)
if isempty(ydot) == 1 
    ydot = gradient(y, x);
end
n = length(x); n1 = length(y); n2 = length(ydot);
if n ~= n1 |n ~= n2 |n1 ~= n1 
    error('%����Ĳ�ֵ�ڵ㡢����ֵ�Լ�����ֵ������ͬ!')
end
m = length(xi); p =zeros(n, m);
q = zeros(1, n); yi = zeros(1, m);
for k = 1 : n
    t = ones(n, m); z = zeros(1, n);
    for j = 1 : n
        if j ~= k
            if abs(x(k) - x(j)) < eps
                error('%��ֵ�ڵ���뻥��!')
            end
            t(j, :) = (xi - x(j)) / (x(k) - x(j));
            z(j) = 1 / (x(k) - x(j));
        end
    end
    p(k, :) = prod(t); q(k) = sum(z);
    yi = yi + y(k) * (1 - 2 * (xi - x(k)) * q(k)) .* p(k, :).^2 + ydot(k) * (xi - x(k)) .* p(k, :).^2;
end

 
