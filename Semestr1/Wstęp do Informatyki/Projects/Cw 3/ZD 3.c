#include <stdio.h>

int main(){
    int x;
    printf("Testing element ");
    scanf("%d",&x);
    int n;
    printf("Ilosc elementow ");
    scanf("%d",&n);
    int i=0;
    int test=-1;
    printf("Tablica: ");
    while (i<n){
        int n;
        scanf("%d",&n);
        if (x==n){
            test=i+1;
            break;
        }
        i++;
    }
    printf("%d",test);
    return 0;
}
