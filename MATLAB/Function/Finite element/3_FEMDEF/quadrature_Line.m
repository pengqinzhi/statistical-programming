function coord = quadrature_Line(K)

%----Get the quadrature information of degree K on [-1, 1]
%---OUTPUT: Coord, the first column is the coordination of quadrature
%points, the second column is the corresponding weights
%

switch K,
    case 1,
        coord = [ 0, 2];
    case 2,
        coord = [-sqrt(3)/3, 1;
                  sqrt(3)/3, 1];
    case 3,
        coord = [-sqrt(15)/5, 5/9;
                  0,          8/9;
                  sqrt(15)/5, 5/9];
    case 4,
        coord = [-sqrt((3+2*sqrt(6/5))/7), (18-sqrt(30))/36;
                 -sqrt((3-2*sqrt(6/5))/7), (18+sqrt(30))/36;
                  sqrt((3-2*sqrt(6/5))/7), (18+sqrt(30))/36;
                  sqrt((3+2*sqrt(6/5))/7), (18-sqrt(30))/36];
    case 5,
        coord = [-1/3*sqrt(5+2*sqrt(10/7)), (322-13*sqrt(70))/900;
                 -1/3*sqrt(5-2*sqrt(10/7)), (322+13*sqrt(70))/900;
                  0,                        128/225; 
                  1/3*sqrt(5-2*sqrt(10/7)), (322+13*sqrt(70))/900;
                  1/3*sqrt(5+2*sqrt(10/7)), (322-13*sqrt(70))/900];
    case 6,
        coord = [ -0.932469514203152   0.171324492379170;
                  -0.661209386466264   0.360761573048139;
                  -0.238619186083197   0.467913934572691;
                   0.238619186083197   0.467913934572691;
                   0.661209386466264   0.360761573048139;
                   0.932469514203152   0.171324492379170];
    case 7,
        coord = [ -0.949107912342758   0.129484966168870;
                  -0.741531185599394   0.279705391489277;
                  -0.405845151377397   0.381830050505119;
                   0                   0.417959183673469;
                   0.405845151377397   0.381830050505119;
                   0.741531185599394   0.279705391489277;
                   0.949107912342758   0.129484966168870];
    case 8,
        coord = [ -0.960289856497536   0.101228536290377;
                  -0.796666477413627   0.222381034453374;
                  -0.525532409916329   0.313706645877887;
                  -0.183434642495650   0.362683783378362;
                   0.183434642495650   0.362683783378362;
                   0.525532409916329   0.313706645877887;
                   0.796666477413627   0.222381034453374;
                   0.960289856497536   0.101228536290377];
    otherwise,
        fprintf('this quadrature scheme is not implemented now!\n');
        coord=[];
end