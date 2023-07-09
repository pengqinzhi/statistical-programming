function [Free_deg,Dim]=DOF_Mangement1D(mesh,Base)

% DOF_Mangement1D.m produce the degree of freedom corresponding to the degree
% freedom description DOF
% INPUT: MESH, the mesh used in the finite element method for 1D
%        DOF, the DOF description, DOF(1) denote the number of the degree 
%   of freedom  at each vertex, DOF(2), denote the number of the degree of
%   freedom on each element. For example for the P_3 element, the DOF is 
%    DOF=[1 2]
%  OUTPUT: FREE_DEG, the numbering for each degree of freedom
%  Hehu Xie, 20-05-2009

%------get the information of the mesh-------------------------------------
DOF = Base.DOF;
Nr_node=size(mesh.node,1);
Nr_elem=size(mesh.elem,1);
%----------compute the freedom degree vector-------------------------------
Free_base=[kron(Nr_node*[0:DOF(1)-1],ones(1,2)),...
           kron(Nr_node*DOF(1)+Nr_elem*[0:DOF(2)-1],ones(1,1))];
Dim = Nr_node*DOF(1)+Nr_elem*DOF(2);
Free_deg = ones(Nr_elem,1)*Free_base+...
           [kron(ones(1,DOF(1)),mesh.elem(:,1:2)),...
                 kron(ones(1,DOF(2)),[1:Nr_elem]')];