#include <stdio.h>

int main(){
    int n,y;
    printf("ELEMENT: ");
    scanf("%d",&y);
    printf("ILOSC ELEMENTOW: ");
    scanf("%d",&n);

    int s[n];
    int i=0;
    while (i<n){
        int x;
        scanf("%d",&x);
        s[i]=x;
        i++;
    }
    i=0;

    int s1[n];
    int c=0;
    while (i<n){
        if (s[i]<y){
            s1[c]=s[i];
            c++;
            i++;
            break;
        }

       if (s[i]==y){
            s1[c]=s[i];
            c++;
            i++;
            break;
       }

        if (s[i]>y){
            s1[c]=s[i];
            c++;
            i++;
            break;
        }
    }

    i=0;
    while (i<n){
        printf("%d ",s1[i]);
        i++;
    }

    return 0;
}
