x=[0.00 1.00 2.00 3.00 4.00 5.00 6.00 7.00 8.00 9.00];
y=[4.7*1e-5 4.5*1e-5 4.3*1e-5 4.0*1e-5 3.6*1e-5 3.2*1e-5 2.9*1e-5 2.5*1e-5 2.2*1e-5 1.9*1e-5];
X=[ones(length(y),1),x'];
[b,bint,r,rint,stats]=regress(y',X,0.05)
rcoplot(r,rint);
xlabel('数据');
ylabel('残差');
title('残差分析');
z=b(1)+b(2)*x;
plot(x,y,'+',x,z,'r');
xlabel('离原点距离X');
ylabel('Bm测');
title('Bm测-X曲线')
