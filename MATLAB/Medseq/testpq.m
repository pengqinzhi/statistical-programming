
Q = [2];
for i = 3:1000
    if isprime(i) == 1;
    Q = [Q,i];
    
    end
end

p = 1000;
Ldata = [];
Tdata = [];
Theory = [];
for i = 1:10
     
    for j = 1: length(Q)
        if (j > p) || (j == p) break; end
    q = Q(j);
    tic;
    [C,length2,A] = MedSeq2(p,q);
    t(j) = toc;
    L(j) = length2;
    end
    Theory(i) = floor(log2(p)) + 2;
    p = p + 1000;
    
    Ldata(i) = mean(L);
    Tdata(i) = mean(t);
end
% P = [1000:1000:100000]
% Ldata = Ldata;
% Tdata = Tdata 
% data = [P', Ldata', Tdata'];                           % 将数据组集到data
% [m, n] = size(data);            
% data_cell = mat2cell(data, ones(m,1), ones(n,1));  
% title = {'p','平均长度','平均用时'};
% result = [title; data_cell];                                            % 将变量名称和数值组集到result
% xlswrite('testdata.csv', result);          
% save('/Users/pengqinzhi/Desktop','result');
%     