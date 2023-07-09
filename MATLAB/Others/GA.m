clc;
clear all;
close all;
global Bitlength%定义3个全局变量
global boundsbegin
global boundsend
bounds=[-2 2];
boundsbegin=bounds(:,1);
boundsend=bounds(:,2);
precision=0.0001;%运算精确度
Bitlength=ceil(log2((boundsend-boundsbegin)'./precision));%染色体长度
popsize=50;%初始种群大小
Generationmax=12;%最大进化代数
pcrossover=0.90;%交叉概率
pmutation=0.09;%变异概率
population=round(rand(popsize,Bitlength));%随机生产初始化种群
[Fitvalue,cumsump]=fitness(population); %调用子函数1算个体适应度和选择累积概率
Generation=1;
while Generation<Generationmax+1%总共进化12代
    for a=1:2:popsize%每一次生成2个个体,经过25次循环生产一个新种群，完成一代进化
        seln=selection(population,cumsump);%调用子函数2选择操作，返回选中的2个个体的序号
        scro=crossover(population,seln,pcrossover);%调用子函数3交叉操作，返回2条染色体
        scnew(a,:)=scro(1,:);%存储交叉操作返回的染色体
        scnew(a+1,:)=scro(2,:);
        smnew(a,:)=mutation(scnew(a,:),pmutation);%调用子函数4对交叉操作返回的染色体变异
        smnew(a+1,:)=mutation(scnew(a+1,:),pmutation);    
    end
    population=smnew;%更新种群
    [Fitvalue,cumsump]=fitness(population);%调用子函数1计算新种群的适应度
    [fmax,nmax]=max(Fitvalue);%记录最佳适应度和其染色序号
    fmean=mean(Fitvalue);
    ymax(Generation)=fmax;%记录当代最佳适应度
    ymean(Generation)=fmean;%记录当代平均适应度
    x=transform2to10(population(nmax,:));%调用子函数6转化为10进制数
    y=boundsbegin+x*(boundsend-boundsbegin)/(power(boundsend,Bitlength)-1);%将x整合到[-2,2]区间中
    xmax(Generation)=y;%保存整合后最佳染色体10进制的值
    Generation=Generation+1;%依次循环进化12代，得到最佳种群个体
end
Generation=Generation-1;
Bestpopulation=y;%最佳种群个体，即x的值
Targetmax=targetfun(y);%调用子函数7要求的函数的最大值

%二进制转换至十进制
function x=transform2to10(Population)
Bitlength=size(Population,2);
x=Population(Bitlength);
for a=1:Bitlength-1
    x=x+Population((Bitlength-a))*power(2,a);
end
end


%适应度函数
function[Fitvalue,cumsump]=fitness(population)
BitLength=size(population,2);
global boundsbegin
global boundsend
popsize=size(population,1);
for a=1:popsize
     x=transform2to10( population(a,:));
     xx=boundsbegin+x*(boundsend-boundsbegin)/(power(2,BitLength)-1);
     Fitvalue(a)=targetfun(xx);
end
Fitvalue=Fitvalue'+230;
fsum=sum(Fitvalue);
%选择概率
Pperpopulation=Fitvalue/fsum;
%累计概率
cumsump(1)=Pperpopulation(1);
for a=2:popsize
     cumsump(a)=cumsump(a-1)+Pperpopulation(a);
end
cumsump=cumsump';
end

%基因突变
function smnew=mutation(scnew,pmutation)
BitLength=size(snew,2);
smnew=scnew;
paa=IfCroaIfMut(pmutation);%调用子函数5，判断是否进行变异
if paa==1
    v=round(rand*(BitLength-1))+1;%在[1，Bitlength]中选择一个变异位
    smnew(v)=abs(scnew(v)-1);%将smnew中第V个位置变异 
end
end

%选择是否进行
function pcc=IfCroaIfMut(mutORcro)
judge(1:100)=0;
L=round(100*mutORcro);
judge(1:L)=1;
n=round(rand*99)+1;
pcc=judge(n);
end

%交叉互换
function scro=crossover(population,seln,pcrossover)
BitLength=size(population,2);
pcc=IfCroaIfMut(pcrossover);
if pcc==1
     chb=round(rand*(BitLength-2))+1;
     scro(1,:)=[population(seln(1),1:chb) population(seln(2),chb+1:BitLength)]; 
     scro(2,:)=[population(seln(2),1:chb) population(seln(1),chb+1:BitLength)]; 
else
     scro(1,:)=[population(seln(1),:)];
     scro(1,:)=[population(seln(1),:)];
end
end




%选择两个个体
function seln=selection(population,cumsump)
for a=1:2
     r=rand;
     prand=cumsump-r;
     while prand<0
          b=b+1;
     end
     seln(1)=j;
end   
end



function y=targetfun(x)
%适应度函数，即原函数
y=200*exp(-0.05*x).*sin(x);
end