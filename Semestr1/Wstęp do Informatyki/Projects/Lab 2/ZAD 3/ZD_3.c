#include <stdio.h>

main(){
    double i=0;
    int n=1;
    int war=0;
    while(i<=10){
        i=i+(1.0/n);
        n++;
        war++;
    }
    printf("n=%d",war);
}
