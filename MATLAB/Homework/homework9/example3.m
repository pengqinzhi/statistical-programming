x=0:pi/50:2*pi;
y=sin(x);
z=cos(x);
plot(x,y,'r',x,z,'g');

H1=get(gca,'Children');     %��ȡ�����߾������H1
for k=1:size(H1)
    if get(H1(k),'Color')==[0 1 0] %��0,1,0����ʾ��ɫ
        H1g=H1(k);      %��ȡ��ɫ�������
    end
end
pause;
set(H1g,'LineStyle',':','Marker','p'); %����ɫ�����������á�