fid=fopen('score.txt','rt');
A = importdata('score.txt'); 
fclose(fid);

Name = A.textdata;            %��ȡtxt�ļ��е��ı�
data = A.data;                %��ȡtxt�ļ��е�����
data(isnan(A.data)) = 0;      %�Ѿ�����Nanֵ����0
total = sum(data,2);   %�����

%����tableд��txt�ļ�
T = table(Name,total);
writetable(T,'result.txt','WriteVariableNames',false,'Delimiter',' ')  
