function num = zeroaccount(x)
y = num2str(x);               %����ת�ַ�
num=length(strfind(y,'0'));    %���Ҹ��ַ���0����,����num��
