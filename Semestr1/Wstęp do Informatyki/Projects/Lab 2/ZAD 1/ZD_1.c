#include <stdio.h>

main()
{
    printf("Write amount of ZLOTE ");
    int Z;
    scanf("%d",&Z);

    printf("Write amount of GROSZE ");
    int G;
    scanf("%d",&G);

    int banknoty[5]={10,20,50,100,200};
    int monety_z[3]={1,2,5};
    int monety_g[6]={1,2,5,10,20,50};

    while (Z!=0){
        printf("banknoty:\n");

        int i=4;
        int c=0;
        for(i;i>=0;i--){

            while((Z-banknoty[i])>=0){
                Z-=banknoty[i];
                    c+=1;
                }

            if (c!=0){
                printf("\t%d x %d zl\n",c,banknoty[i]);
                c=0;
            }
        }
        printf("monety:\n");

        i=3;
        for(i;i>=0;i--){
            while((Z-monety_z[i])>=0){
                Z-=monety_z[i];
                    c+=1;
                }

            if (c!=0){
                printf("\t%d x %d zl\n",c,monety_z[i]);
                c=0;
            }
        }
    }

    while (G!=0){
        int x=5;
        int s=0;
        for(x;x>=0;x--){
            while((G-monety_g[x])>=0){
                G-=monety_g[x];
                    s+=1;
                }

            if (s!=0){
                printf("\t%d x %d gr\n",s,monety_g[x]);
                s=0;
            }
        }
    }
}

