function printyh(n)
% 打印杨辉三角形, 本函数没有输出参数 yh=1; disp(yh);
if n==1, return; end 
yh=[1,1]; disp(yh);
for k=3:n
    yh_old=yh;
    yh(1)=1; yh(k)=1;
    for l=2:k-1
        yh(l)=yh_old(l-1)+yh_old(l);
    end
    disp(yh);
end