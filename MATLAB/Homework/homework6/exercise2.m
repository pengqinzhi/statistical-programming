x = -3: .01: 3;
y = piecewise(x);

fid = fopen('y.txt','w+');
fprintf(fid,'y = %.4f \n',y);  
fclose(fid);

%��д�ֶκ���
function y = piecewise(x)    %��д�ֶκ���
y = (-x.^2-4*x-3)/2 .* ((x>=-3) & (x<=-1))+ (-x.^2+1) .* ((x>-1) & (x<=1))+ (-x.^2+4*x-3)/2 .* ((x>1) & (x<=3));
end



