p=polyfit(x,y,22);
x1=[1:80]
y1=polyval(p,x);

plot(x,y,'*',x1,y1)
