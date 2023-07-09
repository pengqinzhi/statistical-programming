function [A,RHS] = Poission_BoundaryTreatment(mesh,Base,A,RHS,u_D,g_N)

%------------Treat boundary condition for the poission equation by the
%finite element method
% INPUT: mesh, the mesh
%        Base, the basis function
%        A, RHS, the matrix and the right hand side 
%        u_D and g_N the Dirichlet and Neumann boundary condtions
%  OUTPUT: A, RHS, return the matrix and right hand side vector after the
%  boundary treatment
%
%
% fprintf('Treat the boundary condition!\n');
% tic;
%---Treat the Dirichlet boundary condition---------------------------------
if Base.DOF(1) >0 
    %---------Treat the Dirichlet boundary condition ----------------------
    if size(mesh.Dirichlet,2)>0 
        A(mesh.Dirichlet,:) = 0;
        A(mesh.Dirichlet,mesh.Dirichlet) = eye(size(mesh.Dirichlet,1));
        RHS(mesh.Dirichlet,1) = u_D(mesh.node(mesh.Dirichlet));
    end
end
%----Treat the Neumann boundary condition----------------------------------
if nargin == 6
    if size(mesh.Neumann,2)>0
        RHS(mesh.Neumann,:) = RHS(mesh.Neumann,:)+g_N(mesh.node(mesh.Neumann));
    end
end
%toc