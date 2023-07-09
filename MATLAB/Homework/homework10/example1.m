clf reset
set(gcf,'menubar','none') 
set(gcf,'unit','normalized','position',[0.2,0.2,0.64,0.32]); 
set(gcf,'defaultuicontrolunits','normalized')%设置缺省控件单位属性值

h_axes=axes('position',[0.05,0.2,0.6,0.6]); 
t=0:pi/50:2*pi; y=sin(t); plot(t,y);

set(h_axes,'xlim',[0,2*pi]); 
set(gcf,'defaultuicontrolhorizontal','left'); 
htitle = title('正弦曲线');

set(gcf,'defaultuicontrolfontsize',12); %设置用户缺省控件字体属性值 

hpanel = uipanel('FontSize',12, 'Position',[0.67,0.55,0.25,0.25]); % 创建用户控件区

uicontrol('style', 'text', 'string','正斜体图名:',...
    'position',[0.68,0.77,0.18,0.1],'horizontal','left'); %创建静态文本框

hr1=uicontrol(gcf, 'style', 'radio',... %创建单选钮
    'string','正体',... %按键功能的文字标'正体' 
    'position',[0.7,0.69,0.15,0.08]); %按键位置
set(hr1,'value',get(hr1,'Max')); %因图名缺省使用正体，所以小圆圈应被点黑 
set(hr1,'callback',[... %
'set(hr1,''value'',get(hr1,''max'')),',... %选中小圆圈点黑 
'set(hr2,''value'',get(hr2,''min'')),',... % 将“互斥”选项点白 
'set(htitle,''fontangle'',''normal''),',...%使图名字正体显示
]);

hr2=uicontrol(gcf,'style','radio',... %创建单选钮
'string','斜体',... %按键功能的文字标识'斜体' 
'position',[0.7,0.58,0.15,0.08],... %按键位置
'callback',[...
'set(hr1,''value'',get(hr1,''min'')),',... %
'set(hr2,''value'',get(hr2,''max'')),',... %
'set(htitle,''fontangle'',''italic'')',... %使图名字体斜体显示
]); %

ht=uicontrol(gcf,'style','toggle',... % 制作双位按键
'string',' Grid',...
'position',[0.67,0.40,0.15,0.12],...
'callback','grid');