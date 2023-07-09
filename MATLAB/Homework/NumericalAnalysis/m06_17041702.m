disp('例3.19');
A = [1:3;4:6;7 8 0]
b = [1 1 1]'
disp('高斯消去法结果如下：');
x = Gauss_Elim(A, b)

disp('例3.21');
disp('LU分解结果如下：');
[L, U] = LU_Decom(A)

disp('例3.22');
B = [4 -1 1; -1 4.25 2.75; 1 2.75 3.5]
disp('Cholesky分解分解结果如下：');
L = Chol_Factor(B)

disp('例3.23');
disp('改进平方根分解分解结果如下：');
[L, D] = LDL_Decom(B)

disp('附加题8')
%第8题第一问
D = diag(1:10);
S = orth(rand(10));
C = S * D * S';
disp('构造正定对称矩阵C ='); disp(C);
%第8题第二问
disp('Cholesky分解结果如下 ：')
L = Chol_Factor(C)

disp('改进平方根分解结果如下 ：')
[L, D] = LDL_Decom(C)