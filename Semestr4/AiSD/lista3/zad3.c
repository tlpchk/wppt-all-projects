#include "sort.h"

int binarySearch(List* list, int a, int b , int v);

int main(int argc,char** argv){
    if (sodium_init() == -1) {
        return 1;
    }
    srand(time(NULL));

    clock_t timeSort;
    int avgComp = 0;
    double avgTime = 0;
    int k = 5;
    int k1;
    FILE *fp = fopen("binSearch", "w+");

    for (int n = 1000; n <= 100000; n += 1000) {
        printf("%d\n",n);
        avgComp = 0;
        avgTime = 0;
        k1 = k;
        while(k1 > 0) {
            int tab[n];
            for (int i = 0; i < n; i++) {
                tab[i] = randomValue(n);
            }

            List *list = malloc(sizeof(List *));
            list->arr = tab;
            list->count = n;
            list->comparec = 0;
            radixSort(list);
            list->comparec = 0;
            timeSort = clock();
            binarySearch(list, 0, n - 1, randomValue(1000));
            timeSort = clock() - timeSort;


            avgComp += list->comparec;
            avgTime += (double) (clock() - timeSort);

            k1--;
        }
        avgComp /= k;
        avgTime /= k;

        fprintf(fp,"%d %d %.2f\n",n,avgComp,avgTime);
    }

    /*int tab[10000];
    for (int i = 0; i < 10000; i++) {
        tab[i] = i+1;
    }

    List *list = malloc(sizeof(List *));
    list->arr = tab;
    list->count = 10000;
    list->comparec = 0;

    binarySearch(list,0,9999,2);
    printf("%d\n",list->comparec);*/

    return 0;
}

int binarySearch(List* list, int a, int b , int v){
    if(a > b){
        return 0;
    }else{
        int c = (b + a)/2;
        if (list->arr[c] == v){
            return 1;
        }
        else if( v < list->arr[c]){
            list->comparec++;
            binarySearch(list, a, c-1, v);
        }
        else{
            list->comparec++;
            binarySearch(list, c+1, b, v);
        }
    }
}