#include <stdio.h>
#include <math.h>

float main()
{
    float a, b, c, x1, x2;
    scanf("%f" "%f" "%f", &a, &b, &c);
    float d=(b*b)-(4*a*c);
    if (a!=0)
        {
        x1=(-b+sqrt(d))/(2*a);
        x2=(-b-sqrt(d))/(2*a);
        if (d>0){
            printf("x 1 = %f , x 2 = %f\n", x1, x2);
        }

        else if (d==0){
            printf("x = %f\n", x1);
        }
        else{
            printf("x not exist\n");
        }
    }
    else
    {
        if (b==0 && c!=0){
            printf("Not exist");
        }
        else if (b==0 && c==0){
            printf("x - to jest dowolna liczba rzeczywista");
        }
        else if (b!=0 && c==0){
            printf("x=0");
        }
        else{
            x1=(-c)/b;
            printf("x=%f",x1);
        }
    }
    return 0;
}
