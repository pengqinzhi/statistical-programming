x = randi(100,1); % 由计算机随机产生一个[1,100]的整数 编程示例
n=7;% 有7次机会
flag = 1;
fprintf('欢迎参加猜数游戏!你共有 %d 次机会。\n', n); fprintf('请猜一个 1 到 100 之间的一个整数\n');
for k = 1 : n
     guess=input('Enter your guess: ');
    if guess < x
        disp('Lower');
    elseif  guess>x
        disp('higher');
    else
        disp('Congratulation, You won!');
        flag = 0; break;
    end
fprintf('你还有 %d 次机会!\n',n-k); end
if flag==1
    disp('Sorry, You lost!')
end