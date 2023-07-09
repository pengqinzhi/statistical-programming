function calledit(hedit, hpop, hlist)
ct=get(hedit, 'string'); % 获得输入的字符串函数
vpop=get(hpop, 'value'); % 获得选项的位置标识
vlist=get(hlist, 'value'); % 获得选项位置向量
if ~isempty(ct) % 可编辑框输入非空时
    for i=1:size(ct,1)
        eval(ct(i,:)) %运行从编辑文本框送入的指令
    end
    popstr ={'spring', 'summer', 'autumn', 'winter'}; % 弹出框色图矩阵
    liststr ={'grid on', 'box on', 'hidden off', 'axis off'};%列表框选项内容
    invstr ={'grid off', 'box off', 'hidden on', 'axis on'};% 列表框的逆指令
    colormap(popstr{vpop}) %采用弹出框所选色图
    vv=zeros(1,4); vv(vlist)=1;
    for k=1:4 % 按列表选项影响图形
        if vv(k)
            eval(liststr{k});
        else
            eval(invstr{k});
        end
    end
end