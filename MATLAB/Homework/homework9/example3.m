x=0:pi/50:2*pi;
y=sin(x);
z=cos(x);
plot(x,y,'r',x,z,'g');

H1=get(gca,'Children');     %获取两曲线句柄向量H1
for k=1:size(H1)
    if get(H1(k),'Color')==[0 1 0] %【0,1,0】表示绿色
        H1g=H1(k);      %获取绿色线条句柄
    end
end
pause;
set(H1g,'LineStyle',':','Marker','p'); %对绿色线条进行设置。