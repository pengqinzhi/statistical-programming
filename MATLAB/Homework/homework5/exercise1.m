fprintf('��Ϸ��ʼ!\n')
flag=1;
y=randi(100,1);
n=7;
fprintf('��²�1��100֮���ĳһ���֣�����%d�λ���',n);
for b=1:7
    g=input('���������֣�\n');
    if g<y
        disp('Low');end
    if g>y
        disp('High');end
    if g==y
        disp('You won!');
        flag=0;
        break
    end
    fprintf('�����²²⣬��ʣ%d�λ���!\n',n-b);
end
if flag==1
    disp('You lost!\n')
    fprintf('The answer is %d\n',y);
end
