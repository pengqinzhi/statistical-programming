x=linspace(0,4*pi,100);
[x,y]=meshgrid(x);
z=sin(x);
axes('view',[-37.5,30]);
hs=surface(x,y,z,'FaceColor','w','EdgeColor','flat');
grid on;

set(get(gca,'XLabel'),'String','X-axis');%…Ë÷√x÷·Àµ√˜
set(get(gca,'YLabel'),'String','Y-axis'); 
set(get(gca,'ZLabel'),'String','Z-axis');
title('mesh-surf');

pause;
set(hs,'FaceColor','interp','EdgeColor','interp');

