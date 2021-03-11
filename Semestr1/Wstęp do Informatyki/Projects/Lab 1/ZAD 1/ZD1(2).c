#include <stdio.h>
#define ROZMIAR 11

int main()
{
    char tablica[ROZMIAR]={'A','B','R','A','K','A','D','A','B','R','A'};
    int r;
    for(r=ROZMIAR;r>=0;r--){
        int i=0;
        int k=r;

        while((ROZMIAR-k)!=0){
            printf(" ");
            k+=1;
        }
        k=r;

        while (i!=k){
            printf("%c ",tablica[i]);
            i++;
        }
        i=k=0;
        printf("\n");
    }
    return 0;
}
