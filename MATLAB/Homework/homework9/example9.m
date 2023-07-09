%建立图形演示系统菜单
figure('Color','w', 'Name','图形演示系统','NumberTitle','off','MenuBar','none');  %定义Plot菜单项 
hplot=uimenu(gcf,'Label','&Plot'); 
uimenu(hplot,'Label','Sine Wave','Callback',['t=-pi:pi/20:pi;','plot(t,sin(t));',...
'set(hgon,''Enable'',''on'');','set(hgoff,''Enable'',''on'');',...
'set(hbon,''Enable'',''on'');','set(hboff,''Enable'',''on'');']);
uimenu(hplot,'Label','Cosine Wave','Callback',['t=- pi:pi/20:pi;','plot(t,cos(t));',...
'set(hgon,''Enable'',''on'');','set(hgoff,''Enable'',''on'');',... 
'set(hbon,''Enable'',''on'');','set(hboff,''Enable'',''on'');']);

%定义Option菜单项
hoption=uimenu(gcf,'Label','&Option'); 
hgon=uimenu(hoption,'Label','&Grid on','Callback','grid on','Enable','off'); 
hgoff=uimenu(hoption,'Label','&Grid off','Callback','grid off','Enable','off'); 
hbon=uimenu(hoption,'Label','&Box on','separator','on','Callback','box on','Enable','off');
hboff=uimenu(hoption,'Label','&Box off','Callback','box off','Enable','off'); 
hfigcor=uimenu(hoption,'Label','&Figure Color','Separator','on');
uimenu(hfigcor,'Label','&Red','Accelerator','r','Callback','set(gcf,''Color'',''r'');'); 
uimenu(hfigcor,'Label','&Blue','Accelerator','b','Callback','set(gcf,''Color'',''b'');'); 
uimenu(hfigcor,'Label','&Yellow','Callback','set(gcf,''Color'',''y'');'); 
uimenu(hfigcor,'Label','&White','Callback','set(gcf,''Color'',''w'');');

%定义Quit菜单项 
uimenu(gcf,'Label','&Quit','Callback','close(gcf)');