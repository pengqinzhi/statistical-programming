x=linspace(0.2*pi,60);    %���� x1 �� x2 ֮��� 100 ���ȼ����������
y=sin(x);
hf=figure('Color',[0,1,0], 'Position',[1,1,450,250], 'Name', 'Luck', 'NumberTitle','off', ...
'MenuBar','none', 'KeyPressFcn','plot(x,y);axis([0,2*pi,-1,1]);');