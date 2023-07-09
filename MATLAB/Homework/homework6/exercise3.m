fid=fopen('score.txt','rt');
A = importdata('score.txt'); 
fclose(fid);

Name = A.textdata;            %提取txt文件中的文本
data = A.data;                %提取txt文件中的数字
data(isnan(A.data)) = 0;      %把矩阵中Nan值换成0
total = sum(data,2);   %行求和

%利用table写入txt文件
T = table(Name,total);
writetable(T,'result.txt','WriteVariableNames',false,'Delimiter',' ')  
