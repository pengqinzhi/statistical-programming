function [b,gb,DOF,coord]=produce_basi(K)


syms x A b v coord
v=[];
for i=0:K
    v = [v, x^i];
end

coord =[-1 1];
for i=1:K-1
    coord = [coord, -1+2*i/K];
end

%coord=[-1 1 -sqrt(3)/3 sqrt(3)/3];

m=size(coord,2);
A=sym(zeros(m));

for k=1:m
    A(k,:)=sym(subs(v,x,coord(k)));
end
A = inv(A);
b=v*A;

for k=1:m
    A(k,:)=sym(subs(b,x,coord(k)));
end
A
gb=diff(b,x,1);

DOF=[1,K-1];
coord=coord';