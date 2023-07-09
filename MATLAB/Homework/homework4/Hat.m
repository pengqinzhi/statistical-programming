subplot(2,2,1);
[x,y] = meshgrid(-8:0.5:8);
r = sqrt(x.^2+y.^2)+eps;
z = sin(r)./r;
surf(z)

subplot(2,2,2);
[row,col] = find((x.^2+y.^2)>70);
for n = 1:length(row)
z(row(n),col(n)) = NaN;
end
surf(x,y,z);

subplot(2,2,3);
[x,y] = meshgrid(-12:0.5:12);
r = sqrt(x.^2+y.^2)+eps;
z = sin(r)./r;
surf(z)

subplot(2,2,4);
 [row,col] = find((x.^2+y.^2)>130);
for n = 1:length(row)
z(row(n),col(n)) = NaN;
end
surf(x,y,z);

