x = [0:0.1:10];
y = exp((-0.5)*x).*sin(2*x);
plot(x,y,'-b','linewidth',2);
hold on
y = exp(-0.5*x).*cos(2*x);
plot(x,y,':r','linewidth',3)

title('function')
xlabel('x')
ylabel('y')
legend('y1 = exp(-0.5x)sin(2x)','y2 = exp(-0.5x)cos(2x)');
grid on;