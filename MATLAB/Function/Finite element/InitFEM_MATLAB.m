function InitFEM_MATLAB

curPath = pwd;
subDir = [findstr('0_Example',curPath),findstr('1_Mesh',curPath),...
          findstr('2_Matrix',curPath),findstr('3_FEMDEF',curPath),...
          findstr('4_LinearSolver',curPath),findstr('5_ErrorEstimate',curPath),...
          findstr('6_BaseFunctions',curPath),findstr('7_Figure',curPath)]
% if ~isempty(subDir)
%     curPath = curPath(1:subDir-2);
% end
addpath(genpath(curPath))
