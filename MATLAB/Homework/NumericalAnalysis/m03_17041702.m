%1、
fprintf('第1题\n前者计算matlab所能得到的大于0的最小有效值,即下确界，后者为matlab能得到的最大值，即上确界。前者x=0,后者x=Inf(无穷大）。\n');
%2、 猜数游戏
fprintf('第2题：猜数')
flag=1;
y=randi(100,1);
n=7;
fprintf('1-100之间的整数的猜数游戏,共有%d次机会',n);
for b=1:7
    g=input('请输入\n')
    if g<y
        disp('Lower');end
    if g>y
        disp('Higher');end
    if g==y
        disp('You won!');
        flag=0;
        break
    end
    fprintf('你还有%d次机会!\n',n-b);
end
if flag==1
    disp('You lost!')
    fprintf('The answer is %d\n',y);
end

    
%3、
format long
clear all
fprintf('第三题：计算展开式取前6项的sin(pi/2)值\n')
x=pi/2;z=0;
for a=1:6
    z=z+(x^(2*a-1)*(-1)^(a+1))/factorial(2*a-1);
    a=a+1;
end
fprintf('由展开式取前6项得到的sin(pi/2)=%.10f \n',z);
fprintf('误差|R(x)|=%.10f \n',1-z);
