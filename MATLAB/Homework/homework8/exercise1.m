A =input('������һ��Ӣ�ľ��ӣ�(ע���������)\n');
[Name,No,Length,Value] = information(A);

%��д�ṹ�������txt
data = struct('Name',Name,'No',num2cell(No),'Length',num2cell(Length),'Value',num2cell(Value));    
writetable(struct2table(data), 'result1.txt','Delimiter','\t')

function [Name,No,Length,Value]=information(A)
word =split(A,{' ',',','.'}); %�ָ���ĸ
for i = 1 : length(word)
Name{i} = word(i);  
No(i) = i;  
Length(i) = length(word{i});
Value(i) = sum(word{i});    %ASCII֮��
i = i + 1;
end
end

% ���õĺ�����Ҫ�Ľ�
% function [Name,No,Length,Value]=information(A)
% i = 1;   %���
% while ~isempty(A)
% [word,A] =strtok(A,',. '); %���źͿո�ָ���ĸ
% Name{i} = word;  
% No(i) = i;  
% Length(i) = length(word);
% Value(i) = sum(word);    %ASCII֮��
% i = i + 1;
% end
% end