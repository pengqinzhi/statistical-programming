%����txt�ļ�
fid = fopen('bacterial.txt','w+');
fprintf(fid,'��������������:\nʱ��          A           B\n');
fprintf(fid,'====        ====        ====\n');
fclose(fid);

%���ݳ�ʼ���������������
X = ones(8,3);
t = 0; a = 1; b = 1;
X(1,1) = 0;
X(1,2) = 1;
X(1,3) = 1;

%����ѭ������ÿ�����ݽ��д�����
for i = 1:8
    t = t + 3;
    a = a * 2^3;       %ABϸ���ֱ����3��Сʱ�ڵķ�ֳ����
    b = b * 2^2;
    X(i+1,1) = t;
    X(i+1,2) = a;
    X(i+1,3) = b;
end

%���ݼ���д�봴���õ�txt�ļ�������������
dlmwrite('bacterial.txt',X,'precision','%-09.1f','delimiter','\t','-append')


