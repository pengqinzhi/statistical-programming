A = textread('orgindata.txt','%n');
xmin = min(A);
xmax = max(A);
a = find(A==xmin);  %�ҵ�A�����ֵ��Сֵλ��
b = find(A==xmax);

%�ļ���ʽ���
fid = fopen('comparedata.txt','w+'); 
fprintf(fid,' ���ֵ�� %d %d �� %d\n',b(1),b(2),xmax); 
fprintf(fid,' ��Сֵ�� %d %d �� %d\n',a(1),a(2),xmin);  
fclose(fid);

