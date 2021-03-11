#include <stdio.h>

int main(){
    int n;
    int m;
    scanf("%d %d",&n,&m);
    int z=m+n;
    int c[z];
    int a[n];
    int i=0;
    while (i<n){
        scanf("%d",&a[i]);
        c[i]=a[i];
        i++;
    }

    int k=0;
    int b[m];
    while (k<m){
        scanf("%d",&b[k]);
        c[i]=b[k];
        k++;
        i++;
    }

    i=0;
    while (i<z){
        printf("%d ",c[i]);
        i++;
    }
}
