clf reset 
set(gcf,'unit','normalized','position',[0.1,0.4,0.85,0.35]); 
set(gcf,'defaultuicontrolunits','normalized'); 
set(gcf,'menubar','none'); 
str='ͨ������ָ���ͼ�Ľ�������'; 
set(gcf,'name',str,'numbertitle','off'); 
h_axes=axes('position',[0.05,0.15,0.45,0.70],'visible','off');

uicontrol(gcf,'Style','text',... 
    'position',[0.52,0.87,0.26,0.1],... 
    'String','��ͼָ�������');

hedit=uicontrol(gcf, 'Style', 'edit',... % �����ɱ༭�ı��� 
    'position',[0.52,0.05,0.26,0.8],...
    'Max',2); % ȡ2��ʹMax-Min>1,�������������

hpop=uicontrol(gcf, 'style', 'popup',...  %���������˵�
    'position',[0.8,0.73,0.18,0.12],...
    'string', 'spring|summer|autumn|winter');% ���õ�������ѡ����

hlist=uicontrol(gcf, 'Style', 'list',... % �����б�� 
    'position',[0.8,0.23,0.18,0.37],...
    'string', 'Grid on|Box on|Hidden off|Axis off',...% �����б����ѡ���� 
    'Max',2); %ȡ2��ʹMax-Min>1, �����������ѡ��

hpush=uicontrol(gcf,'Style','push',... %�������б�����õİ��� 
    'position',[0.8,0.05,0.18,0.15], 'string', 'Apply'); 
set(hedit,'callback','calledit(hedit, hpop, hlist)'); % �༭����������ص� 
set(hpop,'callback','calledit(hedit, hpop, hlist)'); % ������ѡ������ص� 
set(hpush,'callback','calledit(hedit, hpop, hlist)'); % ��������Ļص�