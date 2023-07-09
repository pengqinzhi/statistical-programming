clc;
clear all;
close all;
global Bitlength%����3��ȫ�ֱ���
global boundsbegin
global boundsend
bounds=[-2 2];
boundsbegin=bounds(:,1);
boundsend=bounds(:,2);
precision=0.0001;%���㾫ȷ��
Bitlength=ceil(log2((boundsend-boundsbegin)'./precision));%Ⱦɫ�峤��
popsize=50;%��ʼ��Ⱥ��С
Generationmax=12;%����������
pcrossover=0.90;%�������
pmutation=0.09;%�������
population=round(rand(popsize,Bitlength));%���������ʼ����Ⱥ
[Fitvalue,cumsump]=fitness(population); %�����Ӻ���1�������Ӧ�Ⱥ�ѡ���ۻ�����
Generation=1;
while Generation<Generationmax+1%�ܹ�����12��
    for a=1:2:popsize%ÿһ������2������,����25��ѭ������һ������Ⱥ�����һ������
        seln=selection(population,cumsump);%�����Ӻ���2ѡ�����������ѡ�е�2����������
        scro=crossover(population,seln,pcrossover);%�����Ӻ���3�������������2��Ⱦɫ��
        scnew(a,:)=scro(1,:);%�洢����������ص�Ⱦɫ��
        scnew(a+1,:)=scro(2,:);
        smnew(a,:)=mutation(scnew(a,:),pmutation);%�����Ӻ���4�Խ���������ص�Ⱦɫ�����
        smnew(a+1,:)=mutation(scnew(a+1,:),pmutation);    
    end
    population=smnew;%������Ⱥ
    [Fitvalue,cumsump]=fitness(population);%�����Ӻ���1��������Ⱥ����Ӧ��
    [fmax,nmax]=max(Fitvalue);%��¼�����Ӧ�Ⱥ���Ⱦɫ���
    fmean=mean(Fitvalue);
    ymax(Generation)=fmax;%��¼���������Ӧ��
    ymean(Generation)=fmean;%��¼����ƽ����Ӧ��
    x=transform2to10(population(nmax,:));%�����Ӻ���6ת��Ϊ10������
    y=boundsbegin+x*(boundsend-boundsbegin)/(power(boundsend,Bitlength)-1);%��x���ϵ�[-2,2]������
    xmax(Generation)=y;%�������Ϻ����Ⱦɫ��10���Ƶ�ֵ
    Generation=Generation+1;%����ѭ������12�����õ������Ⱥ����
end
Generation=Generation-1;
Bestpopulation=y;%�����Ⱥ���壬��x��ֵ
Targetmax=targetfun(y);%�����Ӻ���7Ҫ��ĺ��������ֵ

%������ת����ʮ����
function x=transform2to10(Population)
Bitlength=size(Population,2);
x=Population(Bitlength);
for a=1:Bitlength-1
    x=x+Population((Bitlength-a))*power(2,a);
end
end


%��Ӧ�Ⱥ���
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
%ѡ�����
Pperpopulation=Fitvalue/fsum;
%�ۼƸ���
cumsump(1)=Pperpopulation(1);
for a=2:popsize
     cumsump(a)=cumsump(a-1)+Pperpopulation(a);
end
cumsump=cumsump';
end

%����ͻ��
function smnew=mutation(scnew,pmutation)
BitLength=size(snew,2);
smnew=scnew;
paa=IfCroaIfMut(pmutation);%�����Ӻ���5���ж��Ƿ���б���
if paa==1
    v=round(rand*(BitLength-1))+1;%��[1��Bitlength]��ѡ��һ������λ
    smnew(v)=abs(scnew(v)-1);%��smnew�е�V��λ�ñ��� 
end
end

%ѡ���Ƿ����
function pcc=IfCroaIfMut(mutORcro)
judge(1:100)=0;
L=round(100*mutORcro);
judge(1:L)=1;
n=round(rand*99)+1;
pcc=judge(n);
end

%���滥��
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




%ѡ����������
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
%��Ӧ�Ⱥ�������ԭ����
y=200*exp(-0.05*x).*sin(x);
end