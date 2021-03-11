#include <stdio.h>
#include <math.h>

double main(){
    double a=1000;
    double result=1;
    while (a>0){
        double power=pow(a,0.001);
        result*=power;
        a--;
    }
    printf("%f",result);
}
