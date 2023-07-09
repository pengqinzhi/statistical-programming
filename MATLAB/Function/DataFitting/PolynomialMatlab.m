x=[0.00 1.00 2.00 3.00 4.00 5.00 6.00 7.00 8.00 9.00];
y=[4.7*1e-5 4.5*1e-5 4.3*1e-5 4.0*1e-5 3.6*1e-5 3.2*1e-5 2.9*1e-5 2.5*1e-5 2.2*1e-5 1.9*1e-5];
p=polyfit(x,y,1);  
x1=linspace(min(x),max(x));  
y1=polyval(p,x1);  
plot(x,y,'*',x1,y1);  
xlabel('¿Î‘≠µ„æ‡¿ÎX/cm');
ylabel('Bm≤‚/T');
title('Bm≤‚-X«˙œﬂ')
