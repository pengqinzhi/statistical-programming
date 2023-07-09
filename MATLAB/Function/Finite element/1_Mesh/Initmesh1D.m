function mesh = Initmesh1D(N,left,right,Dirichlet,Neumann)

% Init mesh with the number of elements is N, the domain in 1D [left,right]
% The produced mesh is uniform 
% INPUT: N: the number of elements
%        left: the left of the domain
%        right: the right of the domain
%        Dirichlet: the node of Dirichlet boundary
%        Neumann: the node of Neumann boundary
%  OUTPUT: mesh: the mesh of 1D 

tic;
fprintf('----------Generate the mesh data---------------------\n')

node = [left:(right-left)/N:right]';
elem = [[1:N]',[2:N+1]'];
%--------------------------------------------------------------------------
% Generate mesh data structure
%--------------------------------------------------------------------------
mesh = struct('node',node,'elem',elem,'Dirichlet',Dirichlet,...
              'Neumann',Neumann);
          
toc