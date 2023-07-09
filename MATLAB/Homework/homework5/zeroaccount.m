function num = zeroaccount(x)
y = num2str(x);               %数字转字符
num=length(strfind(y,'0'));    %查找该字符中0个数,计入num中
