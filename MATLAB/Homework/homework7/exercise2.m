A = textread('orgindata.txt','%n');
xmin = min(A);
xmax = max(A);
a = find(A==xmin);  %找到A中最大值最小值位置
b = find(A==xmax);

%文件形式输出
fid = fopen('comparedata.txt','w+'); 
fprintf(fid,' 最大值： %d %d 行 %d\n',b(1),b(2),xmax); 
fprintf(fid,' 最小值： %d %d 行 %d\n',a(1),a(2),xmin);  
fclose(fid);

