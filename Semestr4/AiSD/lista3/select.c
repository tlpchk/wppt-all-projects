#include "select.h"

void _logMsg(char* msg){
    fprintf(stderr, "%s\n",msg);
}
void _log(int operation, int key1, int key2, List* l){
    switch (operation) {
        case COMP:
            fprintf(stderr, "C %d and %d\n", key1, key2);
            l->compares++;
            break;
        case SWAP:
            fprintf(stderr, "S %d and %d\n", key1, key2);
            l->swaps++;
            break;
    }

}
void swap(int a, int b,List* list,bool inStderr) {
    if(list->arr[a] == list->arr[b]){
        return;
    }
    int t = list->arr[a];
    list->arr[a] = list->arr[b];
    list->arr[b] = t;

    if(inStderr) {
        _log(SWAP, list->arr[a], list->arr[b], list);
    }
}
bool compare(int a,int b, List* list){
    _log(COMP,a,b,list);
    return a < b;

}

void insertSort(int* arr,int n){
    int key = 0, i = 0;
    for(int j = 1; j < n ; j++ ) {
        key = arr[j];
        i = j - 1;
        while (key < arr[i] && i >= 0) {
            //_logMsg("Compare");
            if (arr[i + 1] != arr[i]) {
                //_logMsg("Swap");
                arr[i + 1] = arr[i];
            }
            i--;
        }
        if(key != arr[i+1]) {
            //_logMsg("Swap");
            arr[i + 1] = key;
        }
    }
}

int randomPivot(int low, int high){
    int i = randombytes_uniform(high-low+1)+low;

    char str[50];
    sprintf(str,"Index of pivot i = %d",i);
    _logMsg(str);

    return i;
}
int randomValue(int a){
    return randombytes_uniform(a);
}
void randomList(List* list){
    list-> arr = malloc(sizeof(int)*list->n);
    for(int i = 0; i < list->n; i++) {
        list->arr[i] = randomValue(list->n);
    }
}
void randomPermutation(List* list){
    list-> arr = malloc(sizeof(int)*list->n);
    int a,b,i;
    for(i = 0; i < list->n; i++){
        list->arr[i] = i+1;
    }

    for(i = 0; i < list->n; i++){
        a = randomValue(list->n);
        b = randomValue(list->n);
        swap(a,b,list,false);
    }
}

int partition(List* l,int a,int b){
    int x = randomPivot(a,b);

    swap(a,x,l,true);

    int pivot = l->arr[a];
    int i = a;
    for (int j = a+1; j <= b; j++) {
        if (compare(l->arr[j], pivot,l)){
            i++;
            swap(i, j, l,true);
        }
    }
    swap(i,a,l,true);

    char str[50];
    sprintf(str,"Index of pivot i = %d",i);
    _logMsg(str);

    return i;
}
int randomSelectAlgorithm(List* list,int p,int q,int i){
    if(p==q)
        return p;
    int r = partition(list,p,q);
    int k = r - p + 1;

    compare(i, k, list);

    if(i == k)
        return r;
    else if (i < k)
        return randomSelectAlgorithm(list, p, r - 1, i);
    else
        return randomSelectAlgorithm(list,r+1,q,i-k);
}
void randomSelect(int argc, char** argv) {
    if (argc == 1) {
        return;
    }
    char *flag = malloc(sizeof(char*));
    argv++;
    memcpy(flag, *argv, 2);
    flag[2] = 0;

    List *list = malloc(sizeof(List*));
    list->swaps = 0;
    list->compares = 0;

    if (strcmp(flag, "-r") == 0) {
        readParameters(list);
        printf(" \b");
        randomList(list);
        logList(list);
        printList(list,randomSelectAlgorithm(list,0,(list->n)-1,list->k));
    } else if (strcmp(flag, "-p") == 0) {
        readParameters(list);
        printf(" \b");
        randomPermutation(list);
        logList(list);
        printList(list,randomSelectAlgorithm(list,0,(list->n)-1,list->k));
    }
}

int medianOfMedian(int* arr,int p,int q,int c){
    if(p == q){
        return arr[p];
    }
    int n = q + 1;
    int m = (int)ceil(n/(double)c);
    int M[m];
    int P[c];
    int k = 0;
    int j = 0;
    for(int i = p; i < n; i+= c){
        if(n-i < c){
            for (j = 0; j < n-i; j++) {
                P[j] = arr[j+i];
            }
            insertSort(P,n-i);

            M[k] = P[(n-i-1)/2];
            break;
        }
        for (j = 0; j < c; j++) {
            P[j] = arr[j+i];
        }
        insertSort(P,c);

        M[k] = P[c/2];
        k++;
    }

    return medianOfMedian(M,0,m-1,c);
}
int partitionX(List* l,int a,int b,int x){
    swap(a,x,l,true);

    int pivot = l->arr[a];
    int i = a;
    for (int j = a+1; j <= b; j++) {
        if (compare(l->arr[j], pivot,l)){
            i++;
            swap(i, j, l,true);
        }
    }
    swap(i,a,l,true);

    return i;
}
int selectAlgorithm(List* list,int p,int q,int i){
    if(p==q)
        return p;

    int x = medianOfMedian(list->arr,p,q,5);

    for (int j = 0; j <= list->n; j++) {
        if(list->arr[j] == x){
            x = j;
            break;
        }
    }
    int r = partitionX(list,p,q,x);
    int k = r - p + 1;
    if(i == k)
        return r;
    else if (i < k)
        return randomSelectAlgorithm(list,p,r-1,i);
    else
        return randomSelectAlgorithm(list,r+1,q,i-k);
}
void Select(int argc, char** argv) {
    if (argc == 1) {
        return;
    }
    char *flag = malloc(sizeof(char*));
    argv++;
    memcpy(flag, *argv, 2);
    flag[2] = 0;

    List *list = malloc(sizeof(List*));
    list->swaps = 0;
    list->compares = 0;

    if (strcmp(flag, "-r") == 0) {
        readParameters(list);
        printf(" \b");
        randomList(list);
        logList(list);
        printList(list,selectAlgorithm(list,0,(list->n)-1,list->k));
    } else if (strcmp(flag, "-p") == 0) {
        readParameters(list);
        printf(" \b");
        randomPermutation(list);
        logList(list);
        printList(list,selectAlgorithm(list,0,(list->n)-1,list->k));
    }
}

void readParameters(List* list){
    scanf("%d",&(list->n));
    scanf("%d",&(list->k));
    fprintf(stderr,"%d\n",list->k);
}
void printList(List* list,int k){
    for (int i = 0; i < list->n; i++) {
        if(i == k){
            printf("[%d] ",(list->arr)[i]);
        }
        else{
            printf("%d ",(list->arr)[i]);
        }
    }
    printf("\n");
}

void logList(List* list){
    for (int i = 0; i < list->n; i++) {
        fprintf(stderr,"%d ",list->arr[i]);
    }
    fprintf(stderr,"\n");
}