#include <stdio.h>
#define LIMIT 1000

int dzielniki(int n);

int main(){
    int n;
    int doskonale[LIMIT];
    int i=0;

    printf("Liczbe idealnie: ");

    for (n=1; n<LIMIT;n++) {
            if (n==dzielniki(n)) {
                doskonale[i]=n;

                printf("%d ",doskonale[i]);
                i++;
                }
    }

    printf("\nZaprzyjaznione pare liczb: ");

    int m;
    for(m=LIMIT;m>0;m--){
            int s;
            for(s=0;s<m;s++){
                if (dzielniki(m)==s & dzielniki(s)==m){
                    printf("%d %d;",m, s);
                }
            }
    }
    return 0;
}

int dzielniki(int i){
    int sumx=0;
    int x;
        for (x=1; x<i; x++){
                if (i%x==0){
                    sumx+=x;
                }
        }
    return sumx;
}
