disp('例4.14');
A = [4 3 0 ; 3 4 -1;0 -1 4];
b = [24 30 -24]';
[x,k]=Gauss_Seidel(A, b)
disp('10秒后执行下一程序');
pause(10);

disp('例4.15');
w = 1.25;
[x, k] = SOR(A, b, w)

disp('10秒后执行下一程序');
disp('例4.16');
pause(10);
n = 8;p = 3;q = 1/2;
A = sparse_matrix(n, p, q)
disp('10秒后执行下一程序');
pause(10);

disp('补充题');
%构建矩阵
n = 100;p = 2;q = 0;
A = sparse_matrix(n, p, q)
b = zeros(n,1); b(1) = 1; b(n) = 1;

disp('5秒后执行下一程序');
disp('Gauss_Seidel法');
pause(5);
[x,k]=Gauss_Seidel(A, b)

disp('10秒后执行下一程序');
disp('SOR法'); 
pause(10);
w = [1.25, 1.5, 1.75, 1.95];
K = 10000;
%取不同w值
for i = 1 : 4
[x, k] = SOR1(A, b, w(i))
    fprintf("取w=%.2f，结果如上：\n",w(i));
%找最少迭代步数及最优的w值
if k < K K = k; W = w(i); end 
disp('10秒后执行下一程序');
if i < 4 pause(10); end
end

fprintf('比较迭代的步数，得到取值效果最好为w=%.2f\n',W);