angle = [-180 -165 -150 -135 -120 -105 -90 -75 -60 -45 -30 -15 0 15 30 45 60 75 90 105 120 135 150 165 180];

 

CFX = [0.043011 0.055914 0.11613 0.24086 0.42581 0.62366 0.75699 0.7914 0.64946 0.4172 0.20215 0.08172 0.060215 0.13763 0.33978 0.61935 0.84301 0.96344 0.94194 0.84301 0.65806 0.44731 0.19355 0.055914 0.043011];

 

CFY = [0.14383 0.083721 0.015027 -0.092308 -0.13524 -0.096601 0.01932 0.18247 0.25975 0.20823 0.045081 -0.10089 -0.19964 -0.74919 -0.80072 -0.71055 -0.5517 -0.28122 -0.015027 0.22111 0.38426 0.46583 0.44866 0.3585 0.14812];

CMZ = [0.030108 0.056344 0.07957 0.083441 0.073118 0.054624 0.027527 -0.018495 -0.068387 -0.10495 -0.11183 -0.083871 -0.045161 0.037849 0.076989 0.04172 0.025376 0.0086022 0 -0.008172 -0.014624 -0.020645 -0.055484 -0.027097 0.030108];

 

data = [angle', CFX', CFY', CMZ'];                           % 将数据组集到data

[m, n] = size(data);            

data_cell = mat2cell(data, ones(m,1), ones(n,1));    % 将data切割成m*n的cell矩阵

title = {'angle', 'CFX', 'CFY', 'CMZ'};                          % 添加变量名称

result = [title; data_cell];                                            % 将变量名称和数值组集到result
s =  xlswrite('wind.xls', result);                                      % 将result写入到wind.xls文件中

 save('/Users/pengqinzhi/Desktop','result');