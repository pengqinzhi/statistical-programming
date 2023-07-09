function U_h = Interpolation(mesh,Base,ExactU)

%---Do the finite element interpolation 



[Free_deg,Dim]=DOF_Mangement1D(mesh,Base);
Nr_colu = size(ExactU(0),2);
m = size(Base.nodal,1);
U_h = zeros(Dim,Nr_colu);
for kk = 1:m
    U_h(Free_deg(:,kk),:) = ExactU((1-Base.nodal(kk,1))/2*mesh.node(mesh.elem(:,1))+...
                     (1+Base.nodal(kk,1))/2*mesh.node(mesh.elem(:,2)));
end