function sumsize = d_file_sumsize(path,dirname)
a = strcat(path,'\',dirname);   %�����ƶ�Ŀ¼
d = dir(a);
D = struct2cell(d);
sumsize = 0;  %��ʼ��
for i = 1:size(D,2)
    sumsize = D{4,i} + sumsize;
end