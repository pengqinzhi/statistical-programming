function c = multi(a,b)
a = int16(a)            %将a接收为int16型
b = single(b)           %将b接收为single型
c = single(a)*b;        %将a转换为single型，二者相乘
