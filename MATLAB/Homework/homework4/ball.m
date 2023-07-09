% % [x, y, z] = ellipsoid(0,0,0,5.9,3.25,3.25,30);
% % surf(x, y, z)
% % axis equal
% [x,y]=meshgrid(-10:0.2:10);
% 
% a=5;b=6;c=4;
% z1=sqrt(c.^2*(x.^2/(a.^2)+y.^2/(b.^2)+1));
% z2=-sqrt(c.^2*(x.^2/(a.^2)+y.^2/(b.^2)+1));
% surf(x,y,z1)
% hold on
% surf(x,y,z2)
% % [x y z]=meshgrid(-20:2:20);
% % 
% % v=x.^2/25+y.^2/36-z.^2/16+1;
% % 
% % p=isosurface(x,y,z,v,0);
% % 
% % patch(p,'edgecolor','r','facecolor','r','facealpha',0.2);
% % 
% % view(3);axis equal;grid on;
% sphere(50)
% axis equal

funx = @(u,v) tan(u)*cos(v);
funy = @(u,v) 2*tan(u)*sin(v);
funz = @(u,v) 3*sec(u);
fsurf(funx,funy,funz)
