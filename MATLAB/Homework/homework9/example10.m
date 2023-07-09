t=-2*pi:0.1:2*pi;
y=sin(2*t);
h1=plot(t,y);
hc=uicontextmenu;
hc1=uimenu(hc,'Label','Grid on/off'); 
hc2=uimenu(hc,'Label','Box on/off'); 
hc11=uimenu(hc1,'Label','Grid on','Callback','grid on'); 
hc12=uimenu(hc1,'Label','Grid off','Callback','grid off'); 
hc21=uimenu(hc2,'Label','Box on','Callback','box on'); 
hc22=uimenu(hc2,'Label','Box off','Callback','box off');
set(h1,'UIContextMenu',hc)