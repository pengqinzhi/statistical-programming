function sumsize = d_file_sumsize(path,dirname)
a = strcat(path,'\',dirname);   %构成制定目录
d = dir(a);
D = struct2cell(d);
sumsize = 0;  %初始化
for i = 1:size(D,2)
    sumsize = D{4,i} + sumsize;
end