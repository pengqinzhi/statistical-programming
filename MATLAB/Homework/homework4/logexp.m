%1
%x=0:.01:10; y=log(x);
% figure(1)
% subplot(2,1,1)
% plot(x,y,'k-'), grid on
% title('\ity=log_{10}(x) ')
% ylabel('y');
% xlabel('x');
% legend('y1 = log_{10}(x) )');
% x=-5:.01:5; y=exp(x);
% figure(1)
% subplot(2,1,2)
% plot(x,y,'k-'), grid on
% title('\ity=exp(x) ')
% ylabel('y') ;
% xlabel('x');
% legend('y2 = exp(x)');

%2
% x=-1.56:0.01:1.56; y1=1./cos(x);
% figure(1)
% subplot(2,1,1)
% plot(x,y1)
% title('y1 = sec(x) ')
% ylabel('y');
% xlabel('x');
% y2=1./sin(x);
% figure(1)
% subplot(2,1,2)
% plot(x,y2)
% title('y2 = csc(x) ')
% ylabel('y') ;
% xlabel('x');

%3
% x=-3:0.01:3; y1=2*x+5;
% figure(1)
% subplot(2,1,1)
% plot(x,y1)
% axis([-3 3 -5 20]);
% title('y1 = 2x+5 ')
% ylabel('y') ;
% xlabel('x');
% y2=x.^2-3*x+1;
% figure(1)
% subplot(2,1,2)
% plot(x,y2)
% title('y2 = x^2-3x+1 ')
% ylabel('y') ;
% xlabel('x');

%4
% x = [190 33 45 42 45];
% explode = [0 1 0  0 0];
% label = {'����� 190Ԫ 53.5%','���Ϸ� 33Ԫ','�绰�� 45Ԫ','�����·� 42Ԫ','���� 45Ԫ'};
% pie3(x,explode,label)
% title('��ͼ') 

%5
% X =-10:.25:10;
% Y =-10:.25:10;
% Z = (X-2).^2+(Y-1.2).^2;
% subplot(2,1,1)
% plot3(X,Y,Z)
% subplot(2,1,2)
% plot3(X,Y,Z), grid on

%6
% [X,Y] = meshgrid([-2:.25:2]);
% Z = X.^2+Y.^2+sin(X.*Y);
% subplot(2,1,1)
% surf(X,Y,Z),axis square
% subplot(2,1,2)
% contour3(X,Y,Z,30)
% surface(X,Y,Z,'EdgeColor',[.8 .8 .8],'FaceColor','none')
% view(-15, 25) 

%7
x=0:.1:4;
y=exp(x).*sin(x);
subplot(2,4,1)
plot(x,y)
title('a ���Կ̶Ȼ�ͼ');
ylabel('y');
xlabel('x');
subplot(2,4,2)
semilogy(x,y)
title('b ����/���Կ̶Ȼ�ͼ');
ylabel('y');
xlabel('x');
subplot(2,4,3)
stem(x,y);
title('c ��״ͼ');
ylabel('y');
xlabel('x');
subplot(2,4,4)
stairs(x,y)
title('d ����ͼ');
ylabel('y');
xlabel('x');
subplot(2,4,5)
bar(x,y)
title('e ����ͼ');
ylabel('y');
xlabel('x');
subplot(2,4,6)
barh(x,y)
title('f ˮƽ����ͼ');
ylabel('y');
xlabel('x');
subplot(2,4,7)
compass(x,y)
title('g ����ͼ');
ylabel('y');
xlabel('x');
