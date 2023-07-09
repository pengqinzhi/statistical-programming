function [Err_0,Err_1]=poisson_Eigen(N)

% solve the poisson equation $-\partial_x(\alpha\partial_xu)=f$
% INPUT: the number of elements of the 1D uniform mesh
%--------------------------------------------------------------------------

%==========================================================================
%  SOLVE THE PROBLEM
%==========================================================================
%-----Define the mesh in [0, 1]--------------------------------------------
mesh=Initmesh1D(N,0,1,[1;N+1],[]);
%-----Define the quadrature scheme-----------------------------------------
coord = quadrature_Line(4);
%------Select a type of basis funtion--------------------------------------
Base = Lagrange_Basis(2);
%------Assemble the stiffmatrix--------------------------------------------
A = stiff_matrix1D(Base,Base,coord,mesh,@coeff,1,1);
M = stiff_matrix1D(Base,Base,coord,mesh,@coeff,0,0);
    
%---------Treat the boundary condition-------------------------------------
[A,M] = Poission_BoundaryTreatment(mesh,Base,A,M,@u_D);
%----Solve the eigenvalue system-----------------------------------------------
[xv,lm,iresult]=sptarn(A,M,0,100);
lm
%-----Do the error estimate for the finite element solution----------------
%Err_0 = Error_Estimate(mesh,Base,coord,mesh.solu,@ExactU,0);
%Err_1 = Error_Estimate(mesh,Base,coord,mesh.solu,@GradExactU,1);
Err_0=1;
Err_1 =1;
%------Output the solution-------------------------------------------------
%showFunct1D(mesh)


%==========================================================================
%== Problem definition ===========
%==========================================================================
%-------the coefficient function-------------------------------------------
function v=coeff(x)
epsilon = 1e-10;
v=epsilon*ones(size(x,1),1);
%-------the coefficient function-------------------------------------------
function v=coeff_Convection(x)
v = ones(size(x,1),1);
%-------the coefficient function-------------------------------------------
function v=coeff_Reaction(x)
v = ones(size(x,1),1);
%--------Exact funtion ----------------------------------------------------
function v = ExactU(x)
%------ Exampel 1--------------------------------------
%v = sin(pi*x);
%------ Example 2------------------------------------
v = x.*(1-x);

function v = GradExactU(x)
%------ Exampel 1--------------------------------------
%v = pi*cos(pi*x);
%------ Exampel 2--------------------------------------
v = 1-2*x;
%--------the right hand side function--------------------------------------
function v=RHS(x)
%------u=x(1-x)------------------------------------------------------------
%v = 1-2*x;
%-------u=sin(pi*x)--------------------------------------------------------
%v = pi*pi*sin(pi*x);
%------ Exampel 2--------------------------------------
%v = 2;
%----------Example 3--------
v = ones(size(x,1),1);
%-----------the Dirichlet condition----------------------------------------
function v=u_D(x)
v = zeros(size(x,1),1);
%------------the Neumann condition-----------------------------------------
function v=u_N(x)
v = zeros(size(x,1),1);