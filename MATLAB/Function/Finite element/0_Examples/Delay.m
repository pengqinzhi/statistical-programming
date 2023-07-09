function Delay(N,order,q_1,q_2,a,b,c,f)
%--------------------------------------------------------------------------
%-------compute the solution of the Delay equation with DG method
%  u'(t)=a(t)u'(q_1t)+b(t)u(t)+c(t)u(q_2t)+f(t)
%INPUT: N: the number of the element in the mesh on [0,1]
%       a,b,c,f: the functions defined in the delay equation
%       order: the order of the basis function
%
%-----Hehu Xie: 2010-12-22-------------

%----get the step size ----------------------------------------------------
h= 1/N;
%-----Define the quadrature scheme-----------------------------------------
coord = quadrature_Line(8);
%------Select a type of basis funtion--------------------------------------
Base = Lagrange_Basis(5);
%-----set some variables---------------------------------------------------
global Step
%-----build the mass matrix------------------------------------------------
mesh=Initmesh1D(1,0,1,[1;1+1],[]);
M = stiff_matrix1D(Base,Base,coord,mesh,@mass,0,1);
M = M + Base.b(0)'*Base.b(0);
G = Base.b(0)'.*Base.b(1);
for Step = 1:N
    %------Assemble the stiffmatrix----------------------------------------
    A = stiff_matrix1D(Base,Base,coord,mesh,@coeff,1,1);
end