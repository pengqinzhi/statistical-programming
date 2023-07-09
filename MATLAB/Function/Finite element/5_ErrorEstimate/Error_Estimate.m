function Err = Error_Estimate(mesh,Base,coord,u_h,Exactu,D)

%----Do the error estimate of the finite element solution u to the exact
%solution Exactu
% INPUT: mesh, Base, coord, are the mesh,  basis function and the
% quadrature information 
%        u_h, the finite element solution 
%        Exactu, the exact soltion
%        D: the derivatives, 0 means to do the $L^2$ error estimate, 1
%        means to do the $H^1$ error estimate
% OUPUT: Err, the returned error
%

% fprintf('Do the error estimate!\n');
% tic;
%----get the number of quadrature points-----------------------------------
Nr_int = size(coord,1);

Nr_elem=size(mesh.elem,1);

[Free_deg,Dim]=DOF_Mangement1D(mesh,Base);

%--------------------------------------------------------------------------
% Compute the area of each element
%--------------------------------------------------------------------------
area = abs(mesh.node(mesh.elem(:,1))-mesh.node(mesh.elem(:,2)))/2;
%---------Compute the inverse transform matrix-----------------------------
if D > 0
    inv_transf = 1./area;
else 
    inv_transf = ones(Nr_elem,1);
end

switch D
    case 0,
        base = Base.b;
    case 1,
        base = Base.gb;
    otherwise,
        fprintf('This type of error estimate is not implemented!\n');
end

Err=0;
m = size(base(0),2);
for kk=1:Nr_int
    B = base(coord(kk,1));
    EXACTU = Exactu((1-coord(kk,1))/2*mesh.node(mesh.elem(:,1))+...
                     (1+coord(kk,1))/2*mesh.node(mesh.elem(:,2)));
                   
    v      = zeros(Nr_elem,1);
    for i =1:m
        v = v + B(1,i)*inv_transf.^D.*u_h(Free_deg(:,i));
    end
    Err = Err + coord(kk,2)*sum((EXACTU-v).^2.*area);                   
end
Err = sqrt(Err);
% toc;