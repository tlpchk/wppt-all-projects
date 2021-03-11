#include <stdio.h>

int prime(int x);

int main(){
    int n;
    scanf("%d",&n);
    int i;

    for(i=2;n>0;i++){
        if (prime(i)==1){
            printf("\n%d",i);
            n--;
        }
    }

}

int prime(int x){
    int i;
    int test=1;
    for(i=2;i<x;i++){
            if (x%i==0){
                test=0;
                break;
            }
    }
    return test;
}
