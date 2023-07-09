x=linspace(0.2*pi,60);    %包含 x1 和 x2 之间的 100 个等间距点的行向量
y=sin(x);
hf=figure('Color',[0,1,0], 'Position',[1,1,450,250], 'Name', 'Luck', 'NumberTitle','off', ...
'MenuBar','none', 'KeyPressFcn','plot(x,y);axis([0,2*pi,-1,1]);');