%创建txt文件
fid = fopen('bacterial.txt','w+');
fprintf(fid,'培养基增长速率:\n时间          A           B\n');
fprintf(fid,'====        ====        ====\n');
fclose(fid);

%数据初始化，创建输出矩阵
X = ones(8,3);
t = 0; a = 1; b = 1;
X(1,1) = 0;
X(1,2) = 1;
X(1,3) = 1;

%利用循环，将每次数据结果写入矩阵
for i = 1:8
    t = t + 3;
    a = a * 2^3;       %AB细菌分别的在3个小时内的繁殖总量
    b = b * 2^2;
    X(i+1,1) = t;
    X(i+1,2) = a;
    X(i+1,3) = b;
end

%数据继续写入创建好的txt文件，并对齐数据
dlmwrite('bacterial.txt',X,'precision','%-09.1f','delimiter','\t','-append')


