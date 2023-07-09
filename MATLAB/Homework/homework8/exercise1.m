A =input('请输入一个英文句子：(注意添加引号)\n');
[Name,No,Length,Value] = information(A);

%编写结构，并输出txt
data = struct('Name',Name,'No',num2cell(No),'Length',num2cell(Length),'Value',num2cell(Value));    
writetable(struct2table(data), 'result1.txt','Delimiter','\t')

function [Name,No,Length,Value]=information(A)
word =split(A,{' ',',','.'}); %分隔字母
for i = 1 : length(word)
Name{i} = word(i);  
No(i) = i;  
Length(i) = length(word{i});
Value(i) = sum(word{i});    %ASCII之和
i = i + 1;
end
end

% 不好的函数，要改进
% function [Name,No,Length,Value]=information(A)
% i = 1;   %编号
% while ~isempty(A)
% [word,A] =strtok(A,',. '); %逗号和空格分隔字母
% Name{i} = word;  
% No(i) = i;  
% Length(i) = length(word);
% Value(i) = sum(word);    %ASCII之和
% i = i + 1;
% end
% end