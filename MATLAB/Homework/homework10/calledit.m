function calledit(hedit, hpop, hlist)
ct=get(hedit, 'string'); % ���������ַ�������
vpop=get(hpop, 'value'); % ���ѡ���λ�ñ�ʶ
vlist=get(hlist, 'value'); % ���ѡ��λ������
if ~isempty(ct) % �ɱ༭������ǿ�ʱ
    for i=1:size(ct,1)
        eval(ct(i,:)) %���дӱ༭�ı��������ָ��
    end
    popstr ={'spring', 'summer', 'autumn', 'winter'}; % ������ɫͼ����
    liststr ={'grid on', 'box on', 'hidden off', 'axis off'};%�б��ѡ������
    invstr ={'grid off', 'box off', 'hidden on', 'axis on'};% �б�����ָ��
    colormap(popstr{vpop}) %���õ�������ѡɫͼ
    vv=zeros(1,4); vv(vlist)=1;
    for k=1:4 % ���б�ѡ��Ӱ��ͼ��
        if vv(k)
            eval(liststr{k});
        else
            eval(invstr{k});
        end
    end
end