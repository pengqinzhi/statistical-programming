disp('��3.19');
A = [1:3;4:6;7 8 0]
b = [1 1 1]'
disp('��˹��ȥ��������£�');
x = Gauss_Elim(A, b)

disp('��3.21');
disp('LU�ֽ������£�');
[L, U] = LU_Decom(A)

disp('��3.22');
B = [4 -1 1; -1 4.25 2.75; 1 2.75 3.5]
disp('Cholesky�ֽ�ֽ������£�');
L = Chol_Factor(B)

disp('��3.23');
disp('�Ľ�ƽ�����ֽ�ֽ������£�');
[L, D] = LDL_Decom(B)

disp('������8')
%��8���һ��
D = diag(1:10);
S = orth(rand(10));
C = S * D * S';
disp('���������Գƾ���C ='); disp(C);
%��8��ڶ���
disp('Cholesky�ֽ������� ��')
L = Chol_Factor(C)

disp('�Ľ�ƽ�����ֽ������� ��')
[L, D] = LDL_Decom(C)