A = char(importdata('bugs.txt'));
%��1��
error_num = account(A,'error');   %error���ֵĴ���
%��2��
i = strfind(A,'error');          %error���ֵ�λ��
A(i) = upper(A(i));              %error������ĸ��д
error_num = length(strfind(A,'error'));      
%��3��
[a_num,a_frequent] = account(A,'a');              %a���ֵĴ�����Ƶ�ȣ���ͬ��
[b_num,b_frequent] = account(A,'b');
[t_num,t_frequent] = account(A,'t');              
%��4��
the_num = account(A,'the');    
and_num = account(A,'and');      

%д��txt�ļ�
fid = fopen('result2.txt','w+'); 
fprintf(fid,' ��1��"error"���ִ����� %d\n',error_num); 
fprintf(fid,' ��2���޸�"error��%s\n',A);  
fprintf(fid,' ��3)"a"���ִ�����Ƶ�ȣ� %d  %.3f\n',a_num,a_frequent); 
fprintf(fid,'     "b"���ִ�����Ƶ�ȣ� %d  %.3f\n',b_num,b_frequent); 
fprintf(fid,'     "t"���ִ�����Ƶ�ȣ� %d  %.3f\n',t_num,t_frequent); 
fprintf(fid,'  (4)"the"���ִ����� %d\n',the_num);
fprintf(fid,'     "and"���ִ����� %d\n',and_num); 
fclose(fid);


%��д���������������Ƶ��
function [num,frequent] = account(A,x)
num = length(strfind(A,x));       %ָ���ַ����ֵĴ���
s = size(A,2);                      %�ַ����ܸ���
frequent = num/s;                  %ָ���ַ���Ƶ��
end
