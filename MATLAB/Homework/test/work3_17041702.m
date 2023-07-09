Ki = [1.1, 1.2, 1.3, 1.4, 2.4, 2.5, 2.6, 5.2, 5.3, 5.4, 5.5, 5.6, 6.6, 6.7, 6.8]; 	% 共15个数据
K= length (Ki);  % 即：K=15
Delta_t = 0.1;

Chain = {};
j = 1;
for i = 1:K
    if i == 1  %从1开始
        Chain{j} = Ki(1);    
        continue;
        
    elseif (i == 15)
        if (Ki(15) - Ki(14) == Delta_t)
            Chain{j} = [Chain{j} Ki(15)];
        else
            Chain{j+1} = Ki(15);
        end
        
    else   %在第2个到第14个间，依次判断
        Ki(i) - Ki(i-1) == 0.100;
        if (Ki(i) - Ki(i-1) == Delta_t) && (Ki(i+1) - Ki(i) == Delta_t)
            Chain{j} = [Chain{j} Ki(i)];   %与前面构成数组
        else
            Chain{j+1} = Ki(i);     %否则，构成一个新数组
            j = j + 1;
            continue;
        end
        
    end
end

fid=fopen('work3_17041702_result.txt','w+'); 
fprintf(fid,' Chain(1).Ki= [1.1, 1.2, 1.3,1.4] \n');  
fprintf(fid,' Chain(1).Ki= [1.1, 1.2, 1.3,1.4] \n'); 
fprintf(fid,' Chain(3).Ki = [5.2, 5.3, 5.4, 5.5, 5.6] \n');  
fprintf(fid,' Chain(4).Ki = [6.6, 6.7, 6.8] \n');  

fprintf(fid,' Chain(1).idx = [1 2 3] \n');  
fprintf(fid,' Chain(2).idx = [4 5 6 7]  \n'); 
fprintf(fid,' Chain(3).idx = [8 9 10 11 12]   \n');  
fprintf(fid,' Chain(4).idx = [12 13 14 15]  \n');  

fclose(fid);