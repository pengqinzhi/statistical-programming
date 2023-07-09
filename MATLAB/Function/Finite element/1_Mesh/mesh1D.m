function mesh = mesh1D(node,elem,Dirichlet,Neumann)

% Produce the mesh by the information of node,elem and Boundary
%  
% INPUT: node: the information of nodes 
%        elem: the information of element
%        Dirichlet: the node of Dirichlet boundary
%        Neumann: the node of Neumann boundary
%  OUTPUT: mesh: the mesh of 1D 

tic;
fprintf('----------Generate the mesh data---------------------\n')

%--------------------------------------------------------------------------
% Generate mesh data structure
%--------------------------------------------------------------------------
mesh = struct('node',node,'elem',elem,'Dirichlet',Dirichlet,...
              'Neumann',Neumann);          
toc