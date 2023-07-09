function [Err_0,Err_1] = parabolic(N)
%--------------------------------------------------------------------------
% solve the poisson equation $-\partial_x(\alpha\partial_xu)=f$
% INPUT: the number of elements of the 1D uniform mesh
%--------------------------------------------------------------------------

%==========================================================================
%  SOLVE THE PROBLEM
%==========================================================================
%----Define the initial and final time ------------------------------------
global TIME_0 TIME_END TIME_STEP TIME_CURRENT
TIME_0 = 0;
TIME_END = 10;
TIME_STEP = 0.05;
TIME_CURRENT = TIME_0;
%-----Define the mesh in [0, 1]--------------------------------------------
mesh=Initmesh1D(N,0,1,[1;N+1],[]);
%-----Define the quadrature scheme-----------------------------------------
coord = quadrature_Line(3);
%------Select a type of basis funtion--------------------------------------
Base = Lagrange_Basis(1);
%-------Assemble the Mass matrix-------------------------------------------
M = stiff_matrix1D(Base,Base,coord,mesh,@coeff,0,0);
%--------Get the initial solution by Interpolation method------------------
mesh.solu = Interpolation(mesh,Base,@InitialU);
%---Start do the time loop-------------------------------------------------
TIME_CURRENT
while TIME_CURRENT < TIME_END-1e-10
    %------Update the current time-----------------------------------------
    TIME_CURRENT = TIME_CURRENT + TIME_STEP;
    TIME_CURRENT
    %------Assemble the stiffmatrix----------------------------------------
    A = stiff_matrix1D(Base,Base,coord,mesh,@coeff,1,1);
    %--------Assemble the right hand side----------------------------------
    b_right = RHS_vector1D(Base,coord,mesh,@RHS);
    %-----Oragnize the matrix and the right hand side----------------------
    A = M + TIME_STEP*A;
    b_right = M*mesh.solu + TIME_STEP*b_right;
    %---------Treat the boundary condition---------------------------------
    [A,b_right] = Poission_BoundaryTreatment(mesh,Base,A,b_right,@u_D);
    %----Solve the linear system-------------------------------------------
    mesh.solu = A\b_right;
    %------Output the solution---------------------------------------------
    hold off;
    showFunct1D(mesh)
    pause(TIME_STEP);
end
TIME_CURRENT
%-----Do the error estimate for the finite element solution------------
Err_0 = Error_Estimate(mesh,Base,coord,mesh.solu,@ExactU,0);
Err_1 = Error_Estimate(mesh,Base,coord,mesh.solu,@GradExactU,1);

%==========================================================================
%== Problem definition ===========
%==========================================================================
%-------the coefficient function-------------------------------------------
function v=coeff(x)
v=ones(size(x,1),1);
%--------Exact funtion ----------------------------------------------------
function v = ExactU(x)
global TIME_CURRENT;
v = sin(pi*(x+TIME_CURRENT));
function v = GradExactU(x)
global TIME_CURRENT;
v = pi*cos(pi*(x+TIME_CURRENT));
%-------Initial condition--------------------------------------------------
function v = InitialU(x)
v = sin(pi*x);
%--------the right hand side function--------------------------------------
function v=RHS(x)
global TIME_CURRENT;
%------u=x(1-x)------------------------------------------------------------
%v = 1-2*x;
%-------u=sin(pi*x)--------------------------------------------------------
v = pi*pi*sin(pi*(x+TIME_CURRENT))+pi*cos(pi*(x+TIME_CURRENT));
%-----------the Dirichlet condition----------------------------------------
function v=u_D(x)
global TIME_CURRENT;
v = ExactU(x);
%------------the Neumann condition-----------------------------------------
function v=u_N(x)
v = zeros(size(x,1),1);