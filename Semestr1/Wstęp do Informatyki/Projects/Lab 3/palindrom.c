#include <stdio.h>
#include <string.h>
#include <stdbool.h>

bool palindrom(char n1[]);

int main(){
    char n[1000];
    scanf("%s",&n);
    printf("%s",palindrom(n));
}


char palindrom(char n1[]){
    char n2[1000];
    int j,i,c;

    for(i=0,j=(strlen(n1))-1;(c=getchar())!=EOF;i++,j--)
        n2[j]=n2[i];
    return n2;
}
