disp('��4.14');
A = [4 3 0 ; 3 4 -1;0 -1 4];
b = [24 30 -24]';
[x,k]=Gauss_Seidel(A, b)
disp('10���ִ����һ����');
pause(10);

disp('��4.15');
w = 1.25;
[x, k] = SOR(A, b, w)

disp('10���ִ����һ����');
disp('��4.16');
pause(10);
n = 8;p = 3;q = 1/2;
A = sparse_matrix(n, p, q)
disp('10���ִ����һ����');
pause(10);

disp('������');
%��������
n = 100;p = 2;q = 0;
A = sparse_matrix(n, p, q)
b = zeros(n,1); b(1) = 1; b(n) = 1;

disp('5���ִ����һ����');
disp('Gauss_Seidel��');
pause(5);
[x,k]=Gauss_Seidel(A, b)

disp('10���ִ����һ����');
disp('SOR��'); 
pause(10);
w = [1.25, 1.5, 1.75, 1.95];
K = 10000;
%ȡ��ͬwֵ
for i = 1 : 4
[x, k] = SOR1(A, b, w(i))
    fprintf("ȡw=%.2f��������ϣ�\n",w(i));
%�����ٵ������������ŵ�wֵ
if k < K K = k; W = w(i); end 
disp('10���ִ����һ����');
if i < 4 pause(10); end
end

fprintf('�Ƚϵ����Ĳ������õ�ȡֵЧ�����Ϊw=%.2f\n',W);