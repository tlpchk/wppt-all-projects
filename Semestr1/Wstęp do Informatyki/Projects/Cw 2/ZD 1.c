#include <stdio.h>


int line(int x);

int main(){
    int n;
    scanf("%d",&n);

    int test_n=0;
    int k=n;

    for(k;(k%10)!=0;k=k/10){
       test_n+=pow(10,(line(k)))*(k%10);
    }
    printf("%d",line(n));
    if (n==test_n/10)
        printf("Palindrom");
    else
        printf("Nie palindrom");
}



int line(int x){
    int len=1;
    int i;
    for(i=10;(x%i)!=x;i*=10){
        len++;
    }
    return len;
}
