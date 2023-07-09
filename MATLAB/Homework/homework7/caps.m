function y = caps(x)
y=lower(x);                     %所有字母小写
b=find(y==' ');                 %找到句子中所有空格的位置
y(1)=upper(y(1));               %先将句子第一个词的首字母大写
y(b+1)=upper(y(b+1));           %再将之后单词的首字母大写