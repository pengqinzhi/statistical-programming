funx = @(u,v) (6+2*cos(u))*cos(v);
funy = @(u,v) (6+2*cos(u))*sin(v);
funz = @(u,v) 2*sin(u);
fsurf(funx,funy,funz,[0,2*pi,0,2*pi]);
axis equal;
hold on
funx = @(u,v) 2*cos(u)*cos(v);
funy = @(u,v) 2*cos(u)*sin(v);
funz = @(u,v) 2*sin(u);
fsurf(funx,funy,funz,[0,2*pi,0,2*pi]);
axis equal;
