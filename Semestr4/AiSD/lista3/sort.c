#include "sort.h"

void _log(int operation,int key1, int key2,List* l){
    switch (operation) {
        case COMP:
            if (l->writeInSTDERR){
                fprintf(stderr, "C %d and %d\n", key1, key2);
            }
            l->comparec++;
            break;
        case SWAP:
            if (l->writeInSTDERR) {
                fprintf(stderr, "S %d and %d\n", key1, key2);
            }
            l->swapc++;
            break;
    }

}
void swap(int a, int b,List* list) {
    if(a == b){
        return;
    }
    int t = list->arr[a];
    list->arr[a] = list->arr[b];
    list->arr[b] = t;

    _log(SWAP,list->arr[a],list->arr[b],list);
}
void printList(List* list){
    for (int i = 0; i < list->count; i++) {
        printf("%d ",(list->arr)[i]);
    }
    printf("\n");
}
/*Metody do losowania*/
int randomPivot(int low, int high){
    int i = randombytes_uniform(high-low+1)+low;
    //printf("pivot:%d\n",i);
    return i;
}
int randomValue(int a){
    return randombytes_uniform(a);
}
/*Komparator, który zwraca wartość porównania zgodnie z zadanym porządku*/
bool compare(int a,int b, List* list){
    _log(COMP,a,b,list);

    if(list->naturalOrder){
        return a < b;
    }
    else{
        return a > b;
    }
}

void insertSortArray(int* arr,int n){
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
void insertSort(List* l){
    int key = 0, i = 0;
    for(int j = 1; j < l->count ; j++ ) {
        key = l->arr[j];
        i = j - 1;
        while (compare(key, l->arr[i], l) && i >= 0) {
            if (l->arr[i + 1] != l->arr[i]) {
                /*Swap w tym przypadku rozumiemy jako kopijowanie key1 do key2*/
                _log(SWAP, l->arr[i+1], l->arr[i], l);
                l->arr[i + 1] = l->arr[i];
            }
            i--;
        }
        if(key != l->arr[i+1]) {
            _log(SWAP, l->arr[i+1], key, l);
            l->arr[i + 1] = key;
        }
    }
}

void merge(List* list, int l, int m, int r) {
    int i, j, k;
    int n1 = m - l + 1;
    int n2 =  r - m;

    int L[n1], R[n2];

    /*Kopiowanie danych do lewej i prawej podtablicy*/
    for (i = 0; i < n1; i++)
        L[i] = list->arr[l + i];
    for (j = 0; j < n2; j++)
        R[j] = list->arr[m + 1+ j];

    i = 0;
    j = 0;
    k = l;
    while (i < n1 && j < n2)
    {
        if (compare(L[i], R[j],list))
        {
            /*Pseudoswap : przepis klucza*/
            if(list->arr[k] != L[i]) {
                _log(SWAP, list->arr[k], L[i], list);
                list->arr[k] = L[i];
            }
            i++;
        }
        else
        {
            if(list->arr[k] != R[j]) {
                _log(SWAP, list->arr[k], R[j], list);
                list->arr[k] = R[j];
            }
            j++;
        }
        k++;
    }

    /* Po kończeniu jednej z list przepisujemy pozostałe klucze z drugiej */
    while (i < n1)
    {
        if(list->arr[k] != L[i]) {
            _log(SWAP, list->arr[k], L[i], list);
            list->arr[k] = L[i];
        }
        i++;
        k++;
    }

    while (j < n2)
    {
        if(list->arr[k] != R[j]) {
            _log(SWAP, list->arr[k], R[j], list);
            list->arr[k] = R[j];
        }
        j++;
        k++;
    }
}
void mergeSort(List* list, int l, int r) {
    if (l < r)
    {
        int m = l+(r-l)/2;
        mergeSort(list, l, m);
        mergeSort(list, m+1, r);
        merge(list, l, m, r);
    }
}

int partition(List* l,int a,int b){
        int x = randomPivot(a,b);

        swap(a,x,l);

        int pivot = l->arr[a];
        int i = a;

        for (int j = a+1; j <= b; j++) {
            if (compare(l->arr[j], pivot,l)){
                i++;
                swap(i, j, l);
            }
        }
        swap(i,a,l);

        return i;
}
void quickSort(List* l,int a,int b){
    if(a<b){
        int k = partition(l,a,b);
        quickSort(l,a,k-1);
        quickSort(l,k+1,b);
    }
}

int dppartition(List* l,int low,int high,int* lp){
    int x = randomPivot(low,high);
    int y = randomPivot(low,high);
    while(x==y){
        x = randomPivot(low,high);
    }

    if (compare(l->arr[x], l->arr[y],l)) {
        swap(low, x,l);
        swap(y, high,l);
    }
    else{
        swap(low, y, l);
        swap(x, high, l);
    }

    if(!compare(l->arr[low],l->arr[high],l)){
        swap(low, high, l);
    }

    int t = 0;
    int j = low + 1;
    int g = high - 1, k = low + 1, p = l->arr[low], q = l->arr[high];
    while (k <= g) {
        if (t >= 0) {
            if (compare(l->arr[k], p, l)){
                swap(k, j, l);
                t--;
                j++;
            } else if (compare(q,l->arr[k],l)||q==l->arr[k]) {
                while (compare(q,l->arr[g],l) && k < g){
                    t++;
                    g--;
                }
                swap(k, g, l);
                g--;
                if (compare(l->arr[k], p, l)) {
                    swap(k, j, l);
                    t--;
                    j++;
                }
            }
            k++;
        }
        else{
            if (compare(q,l->arr[k],l)||q==l->arr[k]) {
                while (compare(q,l->arr[g],l) && k < g){
                    t++;
                    g--;
                }
                swap(k, g, l);
                g--;
                if (compare(l->arr[k], p, l)) {
                    swap(k, j, l);
                    t--;
                    j++;
                }
            }
            else if (compare(l->arr[k], p, l)){
                swap(k, j, l);
                t--;
                j++;
            }
            k++;
        }
    }
    j--;
    g++;
    swap(low, j, l);
    swap(high, g, l);

    *lp = j;

    return g;
}
void dualPivotQS(List* l,int low,int high){
    if (low < high) {

        int lp, rp;
        rp = dppartition(l, low, high, &lp);
        dualPivotQS(l, low, lp - 1);
        dualPivotQS(l, lp + 1, rp - 1);
        dualPivotQS(l, rp + 1, high);
    }
}

void parseArguments(int argc, char** argv, char* isFlag, char* flag, char* type,int* k,FILE** fp){
    while(argc > 1){
        argv++;
        memcpy(isFlag,*argv,2);
        isFlag[2] = 0;
        memcpy(flag, &(argv[0][2]), strlen(*argv)-2);

        if(strcmp(isFlag,"--") == 0) {
            if (strcmp(flag, "type") == 0 || strcmp(flag, "comp") == 0 || strcmp(flag, "stat") == 0) {
                if (strcmp(flag, "type") == 0) {
                    argv++;
                    argc--;
                    memcpy(type, *argv, strlen(*argv));
                } else if (strcmp(flag, "stat") == 0) {
                    argv++;
                    argc--;
                    *fp = fopen(*argv, "w+");
                    argv++;
                    argc--;
                    *k = atoi(*argv);
                }
            }
        }
        argc--;
    }
}
void setComp(List* list, char* comp){
    if(strcmp("<=",comp) == 0) {
        list->naturalOrder = true;
    }
    else if(strcmp(">=",comp) == 0){
        list->naturalOrder = false;
    }
}

int maxElement(List* list) {
    int mx = list->arr[0];
    for (int i = 1; i < list->count; i++)
        if (list->arr[i] > mx)
            mx = list->arr[i];
    return mx;
}
void radixSort(List *list) {
    int m = maxElement(list);

    for (int exp = 1; m/exp > 0; exp *= 10)
        countSort(list, exp);
}
void countSort(List* list, int exp) {
    int n = list->count;
    int output[n];
    int i, count[10] = {0};

    for (i = 0; i < n ; i++) {
        incrementOperation(list);
        count[(list->arr[i] / exp) % 10]++;
    }

    for (i = 1; i < 10; i++) {
        incrementOperation(list);
        count[i] += count[i - 1];
    }

    for (i = n - 1; i >= 0; i--) {
        incrementOperation(list);
        output[count[ (list->arr[i]/exp)%10 ] - 1] = list->arr[i];
        incrementOperation(list);
        count[ (list->arr[i]/exp)%10 ]--;
    }

    for (i = 0; i < n; i++) {
        list->arr[i] = output[i];
    }
}
void incrementOperation(List* list){
    list->swapc++;
}

void sortRandom(char* type,FILE *fp,int k,int start, int end, int inc) {
    clock_t timeSort;
    int k1;
    int n;
    for (n = 100; n <= 1000; n += 10) {
        printf("%d\n",n);
        k1 = 5;
        int avgComp = 0;
        int avgSwap = 0;
        double avgTime = 0;
        while (k1 > 0) {
            int tab[n];
            for (int i = 0; i < n; i++) {
                tab[i] = randomValue(1000);
            }
            List *list = malloc(sizeof(List*));
            list->swapc = 0;
            list->comparec = 0;
            list->arr = tab;
            list->count = n;
            list->writeInSTDERR = false;
            list->naturalOrder = true;

            timeSort = clock();

            if (strcmp(type, "quickh") == 0) {
                quickHybrid(list, 0, list->count - 1);
            } else if (strcmp(type, "dpquickh") == 0) {
                quickHybrid(list, 0, list->count - 1);
            }

            timeSort = clock() - timeSort;

            avgComp += list->comparec;
            avgSwap += list->swapc;
            avgTime += (double) (clock() - timeSort);

            k1--;
        }
        avgComp /= k;
        avgSwap /= k;
        avgTime /= k;

        fprintf(fp,"%d %d %d %.2f\n",n,avgComp,avgSwap,avgTime);
    }
}
void sortRandomRS(int range,char* type, FILE *fp,int k,int start, int end) {
    clock_t timeSort;
    int k1;
    int i = 1;
    struct sysinfo si1;
    struct sysinfo si2;
    const double megabyte = 1024 * 1024;

    for (int n = start; n <= end; i++) {
        if(i%2 == 0){
            n *= 5;
        }else{
            n *= 2;
        }

        printf("%d\n", n);
        k1 = k;
        int avgComp = 0;
        int avgSwap = 0;
        double avgTime = 0;
        double avgMem = 0;

        while (k1 > 0) {
            int tab[n];
            for (int i = 0; i < n; i++) {
                tab[i] = randomValue(range);
            }
            List *list = malloc(sizeof(List *));
            list->swapc = 0, list->comparec = 0;
            list->arr = tab;
            list->count = n;
            list->writeInSTDERR = false;
            list->naturalOrder = true;


            timeSort = clock();
            sysinfo(&si1);

            if (strcmp(type, "rs") == 0){
                radixSort(list);
            } else if (strcmp(type, "quick") == 0) {
                quickSort(list, 0, list->count - 1);
            } else if (strcmp(type, "dpquick") == 0) {
                dualPivotQS(list, 0, list->count - 1);
            }

            sysinfo (&si2);
            timeSort = clock() - timeSort;

            avgComp += list->comparec;
            avgSwap += list->swapc;
            avgTime += (double) (clock() - timeSort);
            avgMem += ((si1.totalram - si1.freeram) - (si2.totalram - si2.freeram)) / megabyte;
            k1--;
        }
        avgComp /= k;
        avgSwap /= k;
        avgTime /= k;
        avgMem /= k;
        fprintf(fp,"%d %d %.2f %f\n",n,avgComp+avgSwap,avgTime,avgMem);
    }
}
void sort(List* l,char* type) {
    clock_t timeSort;
    if (strcmp(type, "insert") == 0 || strcmp(type, "merge") == 0 || strcmp(type, "quick") == 0 ||
        strcmp(type, "dpquick") == 0 || strcmp(type, "rs") == 0) {

        scanf("%d", &(l->count));
        l->arr = malloc(l->count * sizeof(int));
        for (int i = 0; i < l->count; i++) {
            scanf("%d", &((l->arr)[i]));
        }

        timeSort = clock();
        if (strcmp(type, "insert") == 0) {

            insertSort(l);
        } else if (strcmp(type, "merge") == 0) {

            mergeSort(l, 0, l->count - 1);
        } else if (strcmp(type, "quick") == 0) {
            quickSort(l, 0, l->count - 1);
        } else if (strcmp(type, "dpquick") == 0){
            dualPivotQS(l, 0, l->count - 1);
        } else if (strcmp(type, "rs") == 0){
            radixSort(l);
        }
        timeSort = clock() - timeSort;
        fprintf(stderr, "Czas wykonywania: %.1f ms\n", (double) timeSort);
    }
}

int partitionX(List* l,int a,int b,int x){
    swap(a,x,l);

    int pivot = l->arr[a];
    int i = a;
    for (int j = a+1; j <= b; j++) {
        if (compare(l->arr[j], pivot,l)){
            i++;
            swap(i, j, l);
        }
    }
    swap(i,a,l);

    return i;
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
            insertSortArray(P,n-i);

            M[k] = P[(n-i-1)/2];
            break;
        }
        for (j = 0; j < c; j++) {
            P[j] = arr[j+i];
        }
        insertSortArray(P,c);

        M[k] = P[c/2];
        k++;
    }

    return medianOfMedian(M,0,m-1,c);
}
void quickHybrid(List* list,int a,int b){
    if(a<b){
        int x = medianOfMedian(list->arr,a,b,5);

        for (int j = 0; j <= list->count; j++) {
            if(list->arr[j] == x){
                x = j;
                break;
            }
        }

        int k = partitionX(list,a,b,x);
        quickHybrid(list,a,k-1);
        quickHybrid(list,k+1,b);
    }
}

void dpquickHybrid(List* list,int a,int b){
    if(a<b){
        int x = medianOfMedian(list->arr,a,b,5);

        for (int j = 0; j <= list->count; j++) {
            if(list->arr[j] == x){
                x = j;
                break;
            }
        }

        int k = partitionX(list,a,b,x);
        quickHybrid(list,a,k-1);
        quickHybrid(list,k+1,b);
    }
}
