#include <stdio.h>

int main(){
    unsigned int n;
    printf("podaj liczbe calkowita bez znaku  ");
    scanf("%d",&n);
    int result=0;
    while(n>0){
        if (n%2==0){
            n/2;
        }
        else
            result+=1;
            n/=2;
    }
    printf("liczba jedynek w binarnej reprezentacji wynosi  %d",result);
}
