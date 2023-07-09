A = char(importdata('bugs.txt'));
%（1）
error_num = account(A,'error');   %error出现的次数
%（2）
i = strfind(A,'error');          %error出现的位置
A(i) = upper(A(i));              %error的首字母大写
error_num = length(strfind(A,'error'));      
%（3）
[a_num,a_frequent] = account(A,'a');              %a出现的次数和频度，下同理
[b_num,b_frequent] = account(A,'b');
[t_num,t_frequent] = account(A,'t');              
%（4）
the_num = account(A,'the');    
and_num = account(A,'and');      

%写入txt文件
fid = fopen('result2.txt','w+'); 
fprintf(fid,' （1）"error"出现次数： %d\n',error_num); 
fprintf(fid,' （2）修改"error后：%s\n',A);  
fprintf(fid,' （3)"a"出现次数及频度： %d  %.3f\n',a_num,a_frequent); 
fprintf(fid,'     "b"出现次数及频度： %d  %.3f\n',b_num,b_frequent); 
fprintf(fid,'     "t"出现次数及频度： %d  %.3f\n',t_num,t_frequent); 
fprintf(fid,'  (4)"the"出现次数： %d\n',the_num);
fprintf(fid,'     "and"出现次数： %d\n',and_num); 
fclose(fid);


%编写函数：计算次数与频度
function [num,frequent] = account(A,x)
num = length(strfind(A,x));       %指定字符出现的次数
s = size(A,2);                      %字符串总个数
frequent = num/s;                  %指定字符的频度
end
