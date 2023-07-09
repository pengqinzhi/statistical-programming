function Fn = m041_17041702(n)
if n == 1
    Fn = 1;
    return;
end
if n == 2
    Fn = 2;
    return;
else
    for i = 3:n
        Fn =m041_17041702(n-1) + m041_17041702(n-2);
        return;
    end
end


