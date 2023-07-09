clf reset
set(gcf,'menubar','none') 
set(gcf,'unit','normalized','position',[0.2,0.2,0.64,0.32]); 
set(gcf,'defaultuicontrolunits','normalized')%����ȱʡ�ؼ���λ����ֵ

h_axes=axes('position',[0.05,0.2,0.6,0.6]); 
t=0:pi/50:2*pi; y=sin(t); plot(t,y);

set(h_axes,'xlim',[0,2*pi]); 
set(gcf,'defaultuicontrolhorizontal','left'); 
htitle = title('��������');

set(gcf,'defaultuicontrolfontsize',12); %�����û�ȱʡ�ؼ���������ֵ 

hpanel = uipanel('FontSize',12, 'Position',[0.67,0.55,0.25,0.25]); % �����û��ؼ���

uicontrol('style', 'text', 'string','��б��ͼ��:',...
    'position',[0.68,0.77,0.18,0.1],'horizontal','left'); %������̬�ı���

hr1=uicontrol(gcf, 'style', 'radio',... %������ѡť
    'string','����',... %�������ܵ����ֱ�'����' 
    'position',[0.7,0.69,0.15,0.08]); %����λ��
set(hr1,'value',get(hr1,'Max')); %��ͼ��ȱʡʹ�����壬����СԲȦӦ����� 
set(hr1,'callback',[... %
'set(hr1,''value'',get(hr1,''max'')),',... %ѡ��СԲȦ��� 
'set(hr2,''value'',get(hr2,''min'')),',... % �������⡱ѡ���� 
'set(htitle,''fontangle'',''normal''),',...%ʹͼ����������ʾ
]);

hr2=uicontrol(gcf,'style','radio',... %������ѡť
'string','б��',... %�������ܵ����ֱ�ʶ'б��' 
'position',[0.7,0.58,0.15,0.08],... %����λ��
'callback',[...
'set(hr1,''value'',get(hr1,''min'')),',... %
'set(hr2,''value'',get(hr2,''max'')),',... %
'set(htitle,''fontangle'',''italic'')',... %ʹͼ������б����ʾ
]); %

ht=uicontrol(gcf,'style','toggle',... % ����˫λ����
'string',' Grid',...
'position',[0.67,0.40,0.15,0.12],...
'callback','grid');