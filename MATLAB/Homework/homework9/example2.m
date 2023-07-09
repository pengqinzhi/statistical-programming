x = linspace(0,2*pi,30);
y = sin(x);
h0 = plot(x,y,'r')     %曲线对象的句柄
h1 = gcf               %图形窗口句柄
h2 = gca                %坐标轴句柄
h3 = findobj(gca, 'Marker', 'none')  %坐标轴上曲线的句柄