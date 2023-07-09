function Base = Lagrange_Basis(K)

%-Define the basis function of the finite element space 
%-Here we define the Lagrange type of basis functions 
%INPUT: K, the indicator for the different type of basis functions
% OUTPUT: the basis function which include the basis and the corresponding 
% gradient functions, also include the local interpolation nodes and the
% degree of freedom indicator
%
%
fprintf('Define the basis functions!\n');
switch K
    case 0,
        % piecewise constant basis function
        Base.b = @base_LG_0;
        Base.gb = @grad_base_LG_0;
         %--------Degree freedom description----------
        Base.DOF = [0 1];
        %-Nodal functional definition------------------
        Base.nodal = [0];
    case 1,
        % piecewise constant basis function
        Base.b = @base_LG_1;
        Base.gb = @grad_base_LG_1;
         %--------Degree freedom description----------
        Base.DOF = [1 0];
        %-Nodal functional definition------------------
        Base.nodal = [-1; 1];
    case 2,
        % piecewise constant basis function
        Base.b = @base_LG_2;
        Base.gb = @grad_base_LG_2;
         %--------Degree freedom description----------
        Base.DOF = [1 1];
        %-Nodal functional definition------------------
        Base.nodal = [-1; 1; 0];
    case 3,
        % piecewise constant basis function
        Base.b = @base_LG_3;
        Base.gb = @grad_base_LG_3;
         %--------Degree freedom description----------
        Base.DOF = [1 2];
        %-Nodal functional definition------------------
        Base.nodal = [-1; 1; -sqrt(3)/3;sqrt(3)/3];
    case 4,
        % piecewise constant basis function
        Base.b = @base_LG_4;
        Base.gb = @grad_base_LG_4;
         %--------Degree freedom description----------
        Base.DOF = [1 3];
        %-Nodal functional definition------------------
        Base.nodal = [ -1;1; -0.5; 0; 0.5];
    case 5,
        % piecewise constant basis function
        Base.b = @base_LG_5;
        Base.gb = @grad_base_LG_5;
         %--------Degree freedom description----------
        Base.DOF = [1 4];
        %-Nodal functional definition------------------
        Base.nodal = [-1; 1; -0.6; -0.2; 0.2; 0.6];
    otherwise,
        fprintf('This type of fintie element is not implemented!\n');
        Base = [];
end

%==========================================================================
%    The definition of the basis functions
%==========================================================================

%========P_0 basis function================================================
%----------basis function--------------------------------------------------
function v=base_LG_0(x)
v = 1;
%-------gradient function--------------------------------------------------
function v = grad_base_LG_0(x)
v = 0;

%========P_1 basis function================================================
%----------basis function--------------------------------------------------
function v=base_LG_1(x)
v = [(1-x)/2 (1+x)/2];
%-------gradient function--------------------------------------------------
function v = grad_base_LG_1(x)
v = [-1/2 1/2];

%========P_2 basis function================================================
%----------basis function--------------------------------------------------
function v=base_LG_2(x)
v = [x*(x-1)/2 x*(1+x)/2 (1+x)*(1-x)];
%-------gradient function--------------------------------------------------
function v = grad_base_LG_2(x)
v = [x-1/2 x+1/2 -2*x];

%========P_3 basis function================================================
%----------basis function--------------------------------------------------
function v=base_LG_3(x)
v = [-1/4+1/4*x+3/4*x^2-3/4*x^3,-1/4-1/4*x+3/4*x^2+3/4*x^3, ...
    3/4-3/4*x*3^(1/2)-3/4*x^2+3/4*x^3*3^(1/2), ...
    3/4+3/4*x*3^(1/2)-3/4*x^2-3/4*x^3*3^(1/2)];
%-------gradient function--------------------------------------------------
function v = grad_base_LG_3(x)
v = [1/4+3/2*x-9/4*x^2, -1/4+3/2*x+9/4*x^2, ...
    -3/4*3^(1/2)-3/2*x+9/4*x^2*3^(1/2),  3/4*3^(1/2)-3/2*x-9/4*x^2*3^(1/2)];

%========P_4 basis function================================================
%----------basis function--------------------------------------------------
function v=base_LG_4(x)
v = [  1/6*x-1/6*x^2-2/3*x^3+2/3*x^4, -1/6*x-1/6*x^2+2/3*x^3+2/3*x^4,...
    -4/3*x+8/3*x^2+4/3*x^3-8/3*x^4,1-5*x^2+4*x^4,  4/3*x+8/3*x^2-4/3*x^3-8/3*x^4];
%-------gradient function--------------------------------------------------
function v = grad_base_LG_4(x)
v = [1/6-1/3*x-2*x^2+8/3*x^3,-1/6-1/3*x+2*x^2+8/3*x^3,...
    -4/3+16/3*x+4*x^2-32/3*x^3, -10*x+16*x^3,  4/3+16/3*x-4*x^2-32/3*x^3];

%========P_4 basis function================================================
%----------basis function--------------------------------------------------
function v=base_LG_5(x)
v = [ 3/256-3/256*x-125/384*x^2+125/384*x^3+625/768*x^4-625/768*x^5,...
      3/256+3/256*x-125/384*x^2-125/384*x^3+625/768*x^4+625/768*x^5,...
     -25/256+125/768*x+325/128*x^2-1625/384*x^3-625/256*x^4+3125/768*x^5,...
      75/128-375/128*x-425/192*x^2+2125/192*x^3+625/384*x^4-3125/384*x^5,...
      75/128+375/128*x-425/192*x^2-2125/192*x^3+625/384*x^4+3125/384*x^5,...
     -25/256-125/768*x+325/128*x^2+1625/384*x^3-625/256*x^4-3125/768*x^5];

%-------gradient function--------------------------------------------------
function v = grad_base_LG_5(x)
v = [ -3/256-125/192*x+125/128*x^2+625/192*x^3-3125/768*x^4,...
       3/256-125/192*x-125/128*x^2+625/192*x^3+3125/768*x^4,...
       125/768+325/64*x-1625/128*x^2-625/64*x^3+15625/768*x^4,...
      -375/128-425/96*x+2125/64*x^2+625/96*x^3-15625/384*x^4,...
       375/128-425/96*x-2125/64*x^2+625/96*x^3+15625/384*x^4,...
      -125/768+325/64*x+1625/128*x^2-625/64*x^3-15625/768*x^4];

