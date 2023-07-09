function true = compare(account,p)
    true = 0;
    theory = ((log2(p)+3/2)^2)/2;
    if account < theory
        true = 1;
    end
    return
