#include <stdio.h>

float main(){
    int n;
    float suma=0;
    scanf("%d",&n);
    while (n>0){
        float x;
        scanf("%f", &x);
        suma+=x/2.0;
        n--;
        }
    printf("%.1f",suma);
}
