function [C,length2,A] = MedSeq2(p,q)
C=[0,p];
A = MedSeq0(p,q);
for i = 1:length(A)
    C = [C,A{i}(1)];
end
C = sort(C);
length2 = length(C);

function A = MedSeq0(p,q)

global x
global y
global z
global l
global w
u = p; v = q; w = gcd(p,q);
u = u / w; v = v / w;
A = {};
if mod(u,2) == 0
    if v == u/2
        A = {[1,0,2]};
    else
        if v < u/2
            A = [MedSeq0(u/2, v), {[u/2,0,u]}];
        else
            
            A = {[u/2,0,u]};
            B = MedSeq0(u/2, v-u/2);
            l = u/2;
            B = cellfun(@fun4,B,'UniformOutput',false);
            A = [A, B];
        end
    end
else
    if mod(v,2) == 0
        k = 0; r = v;
        while mod(r,2) ~= 1
            r = r / 2;
            k = k + 1;
        end
        
        for i = 1:k
            a = (1 - 1/2^i) * v;
            b = 2*a - v;
            A = [A,{[a,b,v]}];
            
        end
        
        if v == u - r
            A = [A,{[v,v-r,u]}];
            
        else
            if v < u - r
                
                A = [A, {[(v-r+u)/2,v-r,u]}];
                B = MedSeq0((u+r-v)/2, r);
                x=v-r;
                B = cellfun(@fun1,B,'UniformOutput',false);
                
                A = [A, B];
            else
                
                A = [A, {[(v-r+u)/2,v-r,u]}];
                B = MedSeq0((u+r-v)/2, (v+r-u)/2);
                y=(v+u-r)/2;
                B = cellfun(@fun2,B,'UniformOutput',false);
                A = [A, B];
            end
        end
        
    else
        
        B = MedSeq0(u, u-v);
        z = u;
        A = cellfun(@fun3,B,'UniformOutput',false);
        
        
    end
end

w = gcd(p,q);
A = cellfun(@fun5,A,'UniformOutput',false);
return



function B=fun1(A)
global x
B= A + x;
return

function B=fun2(A)
global y
B= A + y;
return

function B=fun3(A)
global z
B= z - A;
return

function B=fun4(A)
global l
B= A + l;
return

function B=fun5(A)
global w
B= A .* w;
return



