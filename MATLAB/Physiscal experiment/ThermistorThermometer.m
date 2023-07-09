x=[25,35:5:75];
y=[0 0.415 0.594 0.741 0.920 1.065 1.207 1.320 1.405 1.50];
X=[ones(length(y),1),x'];
[b,bint,r,rint,stats]=regress(y',X,0.05);
z=b(1)+b(2)*x;
plot(x,y,'*',x,z,'r');  
xlabel('ÎÂ¶ÈT/¡æ');
ylabel('µçÑ¹/V');
title('U-TÇúÏß');

