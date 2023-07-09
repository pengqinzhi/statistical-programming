h=waitbar(0,'pleas wait...');
for i=1:10000
    waitbar(i/10000,h)
end
close(h)