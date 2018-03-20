#include<stdio.h>

void solve(int num,int z1,int z2,int z3);

void main()
{
 int n;
 printf("请输入盘子数量:");
 scanf("%d",&n);
 solve(n,1,2,3);
}

void solve(int num,int z1,int z2,int z3)
{
 if(num>0){
 solve(num-1,z1,z3,z2);
 printf("把%d上的盘子移到%d上\n",z1,z2);
 solve(num-1,z3,z2,z1);
 }
}