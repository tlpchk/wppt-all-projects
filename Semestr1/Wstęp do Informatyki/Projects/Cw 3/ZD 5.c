#include <stdio.h>

int test(int x);

int main(){
    int n;
    scanf("%d",&n);

    int p[n+1];
    int i=0;
    while(i<=n){
        int x;
        scanf("%s",&x);
        if (i==0) {
            p[i]=0;
        }
        else{
            p[i]=test(i);
        }
        i++;
    }
    i=0;
    for(;i<=n;i++){
        printf("%d ",p[i]);
    }
    return 0;
}

int test(int x){
    int i=2;
    int ret=1;
    for(;i*i<=x;i++){
        if (x%i==0)
            ret=0;
            break;
    }
    return ret;
}
