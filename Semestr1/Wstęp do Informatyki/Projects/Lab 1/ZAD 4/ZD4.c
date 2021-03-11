#include <stdio.h>

int main()
{
    int n;
    int i=1;
    int k;
    int r;

    scanf("%d", &n);

    while(n>0)
    {
        k=n-1;
        while (k>0)
        {
            printf(" ");
            k=k-1;
        }

        r=i;
        while(r>0)
        {
            printf("*");
            r=r-1;
        }

        printf("\n");
        n=n-1;
        i=i+2;
    }

    return 0;
}
