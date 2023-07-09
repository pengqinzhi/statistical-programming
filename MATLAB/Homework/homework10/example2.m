clf reset 
set(gcf,'unit','normalized','position',[0.1,0.4,0.85,0.35]); 
set(gcf,'defaultuicontrolunits','normalized'); 
set(gcf,'menubar','none'); 
str='通过多行指令绘图的交互界面'; 
set(gcf,'name',str,'numbertitle','off'); 
h_axes=axes('position',[0.05,0.15,0.45,0.70],'visible','off');

uicontrol(gcf,'Style','text',... 
    'position',[0.52,0.87,0.26,0.1],... 
    'String','绘图指令输入框');

hedit=uicontrol(gcf, 'Style', 'edit',... % 制作可编辑文本框 
    'position',[0.52,0.05,0.26,0.8],...
    'Max',2); % 取2，使Max-Min>1,而允许多行输入

hpop=uicontrol(gcf, 'style', 'popup',...  %制作弹出菜单
    'position',[0.8,0.73,0.18,0.12],...
    'string', 'spring|summer|autumn|winter');% 设置弹出框中选项名

hlist=uicontrol(gcf, 'Style', 'list',... % 制作列表框 
    'position',[0.8,0.23,0.18,0.37],...
    'string', 'Grid on|Box on|Hidden off|Axis off',...% 设置列表框中选项名 
    'Max',2); %取2，使Max-Min>1, ，而允许多项选择

hpush=uicontrol(gcf,'Style','push',... %制作与列表框配用的按键 
    'position',[0.8,0.05,0.18,0.15], 'string', 'Apply'); 
set(hedit,'callback','calledit(hedit, hpop, hlist)'); % 编辑框输入引起回调 
set(hpop,'callback','calledit(hedit, hpop, hlist)'); % 弹出框选中引起回调 
set(hpush,'callback','calledit(hedit, hpop, hlist)'); % 按键引起的回调