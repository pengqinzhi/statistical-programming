fprintf('游戏开始!\n')
flag=1;
y=randi(100,1);
n=7;
fprintf('请猜测1到100之间的某一数字，共有%d次机会',n);
for b=1:7
    g=input('请输入数字：\n');
    if g<y
        disp('Low');end
    if g>y
        disp('High');end
    if g==y
        disp('You won!');
        flag=0;
        break
    end
    fprintf('请重新猜测，还剩%d次机会!\n',n-b);
end
if flag==1
    disp('You lost!\n')
    fprintf('The answer is %d\n',y);
end
