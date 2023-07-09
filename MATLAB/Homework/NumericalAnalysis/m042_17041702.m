function y = m042_17041702(a,b,c)
if (nargin == 1)
    disp('error!');
    return;
elseif (nargin == 2)
    y =  factorial(a) +  factorial(b);
else 
    y =  factorial(a) +  factorial(b) + factorial(c);
end

