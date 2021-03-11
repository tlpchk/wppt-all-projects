#include <stdio.h>

int main()
{
    int n ;
    scanf("%d", &n);
    int r=n;
    while (r>0)
        {
            int p=n*2;
            while (p>0)
                {
                    printf("*");
                    p=p-1;
                }
            printf("\n");
            r=r-1;
        }
        return 0;
}
