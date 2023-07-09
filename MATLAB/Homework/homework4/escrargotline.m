t=0:0.1:30;
x=2*(cos(t)+t.*sin(t));
y=2*(sin(t)-t.*cos(t));
z=0.8*(1+t.^2);
plot3(x,y,-z)
axis equal