function A = stiff_matrix1D(Base1,Base2,coord,mesh,coeff,D_1,D_2)

%Assemble the Matrix for the finite element method
%INPUT: Base1, Base2, the two basis functions for the left and right sides
%of the variation form
% OUTPUT: A, the matrix

%fprintf('Assemble the matrix!\n');
%tic;

%--------get the element matrix size---------------------------------------
m=size(Base1.b([0]),2);%number of basis
n=size(Base2.b([0]),2);
Nr_elem = size(mesh.elem,1);
Nr_int=size(coord,1);
%--------compute the base value at the integral points---------------------

%--------------------------------------------------------------------------
% Compute the area of each element
%--------------------------------------------------------------------------
area = abs(mesh.node(mesh.elem(:,1))-mesh.node(mesh.elem(:,2)))/2;
%---------Compute the inverse transform matrix-----------------------------
if D_1+D_2 > 0
    inv_transf = 1./area;
else
    inv_transf = ones(Nr_elem,1);
end
%----------compute the freedom degree vector-------------------------------
[Free_deg1,Dim1]=DOF_Mangement1D(mesh,Base1);
[Free_deg2,Dim2]=DOF_Mangement1D(mesh,Base2);
%-------------set the matrix-----------------------------------------------
A = sparse(Dim1,Dim2);
%---------select the basis function for the derivatives D_1,and D_2--------
switch D_1
    case 0,
        base1 = Base1.b;
    case 1,
        base1 = Base1.gb;
    otherwise
        printf('This type of derivative has not been defined!\n');
end

switch D_2
    case 0,
        base2 = Base2.b;
    case 1,
        base2 = Base2.gb;
    otherwise
        printf('This type of derivative has not been defined!\n');
end
    
%-------------produce the stiffness matrix---------------------------------
for kk=1:Nr_int
    %----------Compute the coefficient matrix at the integral point--------    
    coeff_matrix=coeff((1-coord(kk,1))/2*mesh.node(mesh.elem(:,1))+...
                       (1+coord(kk,1))/2*mesh.node(mesh.elem(:,2)));            
    B_1 = base1(coord(kk,1));
    B_2 = base2(coord(kk,1));
    
    for i = 1: m        
        for j = 1: n               
        A       = A + sparse(Free_deg1(:,i),Free_deg2(:,j),...
                     B_1(1,i)*inv_transf.^D_1.*coeff_matrix.*inv_transf.^D_2*B_2(j).*area*coord(kk,2),...
                        Dim1,Dim2);        
        end        
    end     
end
%toc