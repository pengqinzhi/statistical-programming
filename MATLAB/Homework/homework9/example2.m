x = linspace(0,2*pi,30);
y = sin(x);
h0 = plot(x,y,'r')     %���߶���ľ��
h1 = gcf               %ͼ�δ��ھ��
h2 = gca                %��������
h3 = findobj(gca, 'Marker', 'none')  %�����������ߵľ��