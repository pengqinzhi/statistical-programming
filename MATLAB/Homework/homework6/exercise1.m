clear
n = 20; 
%创建Fibonacci数列数组
a=[];
a(1) = 1;a(2) = 1;
for i = 1:(n-2)
a(i+2) = a(i+1) + a(i);
end

plot(a);

fid=fopen('Fibonacci.txt','w+'); 
fprintf(fid,' %d \n',a);  
fclose(fid);
