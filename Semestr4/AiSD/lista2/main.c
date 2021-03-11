#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>
#include <stdbool.h>

#define COMP 1
#define SWAP 2
#define CHANGE 3

typedef struct List{
    int* list;
    int count;
} List;

int testCount,swapc,comparec,changec;


void _log(int operation,int key1, int key2){
    switch (operation) {
        case COMP:
            fprintf(stderr, "Compare %d and %d\n", key1, key2);
            comparec++;
            break;
        case SWAP:
            fprintf(stderr, "Swap %d and %d\n", key1, key2);
            swapc++;
            break;
        case CHANGE:
            fprintf(stderr, "Change %d to %d\n",key1,key2);
            changec++;
            break;
    }

}
void _logcount(int operation){
    switch (operation) {
        case COMP:
            comparec++;
            break;
        case SWAP:
            swapc++;
            break;
        case CHANGE:
            changec++;
            break;
    }

}
void swap(int* a, int* b) {
    int t = *a;
    *a = *b;
    *b = t;
}

void insertSortAsc(List* l){
    int key = 0, i = 0;
    for(int j = 1; j < l->count ; j++ ){
        _log(CHANGE,key,l->list[j]);
        key = l->list[j];
        _log(CHANGE,i,j-1);
        i = j-1;
        _log(COMP,l->list[i],key);
        while( l->list[i] > key && i >= 0){
            _log(CHANGE,l->list[i+1],l->list[i]);
            l->list[i+1] = l->list[i];
            i--;
            if(i>=0) {
                _log(COMP,l->list[i], key);
            }
        }
        _log(CHANGE,l->list[i+1],key);
        l->list[i+1] = key;
    }
}
void insertSortDesc(List* l){
    int key = 0, i = 0;
    for(int j = 1; j < l->count ; j++ ){
        _log(CHANGE,key,l->list[j]);
        key = l->list[j];
        _log(CHANGE,i,j-1);
        i = j-1;
        _log(COMP,l->list[i],key);
        while( l->list[i] < key && i >= 0){
            _log(CHANGE,l->list[i+1],l->list[i]);
            l->list[i+1] = l->list[i];
            i--;
            if(i>=0) {
                _log(COMP,l->list[i], key);
            }
        }
        _log(CHANGE,l->list[i+1],key);
        l->list[i+1] = key;
    }
}

List* mergeAsc(List* list1, List* list2){
    List* l = malloc(sizeof(List*));
    l->list = malloc((list1->count+list2->count)*sizeof(int));
    l->count = list1->count+list2->count;

    int i = 0;
    int i1 = 0;
    int i2 = 0;
    int c1 = list1->count;
    int c2 = list2->count;

    while(i1 < c1 && i2 < c2){
        _log(COMP,list1->list[i1],list2->list[i2]);
        if(list1->list[i1] < list2->list[i2]){
            _log(CHANGE,l->list[i],list1->list[i1]);
            l->list[i] = list1->list[i1];
            i1++;
        }
        else{
            _log(CHANGE,l->list[i],list1->list[i2]);
            l->list[i] = list2->list[i2];
            i2++;
        }
        i++;
    }
    _log(COMP,i1,c1);
    while(i1 < c1){
        _log(CHANGE,l->list[i],list1->list[i1]);
        l->list[i] = list1->list[i1];
        i1++;
        i++;
        _log(COMP,i1,c1);
    }
    _log(COMP,i2,c2);
    while(i2 < c2){
        _log(CHANGE,l->list[i],list1->list[i2]);
        l->list[i] = list2->list[i2];
        i2++;
        i++;
        _log(COMP,i2,c2);
    }

    return l;
}
List* mergeDesc(List* list1, List* list2){
    List* l = malloc(sizeof(List*));
    l->list = malloc((list1->count+list2->count)*sizeof(int));
    l->count = list1->count+list2->count;

    int i = 0;
    int i1 = 0;
    int i2 = 0;
    int c1 = list1->count;
    int c2 = list2->count;

    while(i1 < c1 && i2 < c2){
        _log(COMP,list1->list[i1],list2->list[i2]);
        if(list1->list[i1] > list2->list[i2]){
            _log(CHANGE,l->list[i],list1->list[i1]);
            l->list[i] = list1->list[i1];
            i1++;
        }
        else{
            _log(CHANGE,l->list[i],list1->list[i2]);
            l->list[i] = list2->list[i2];
            i2++;
        }
        i++;
    }
    _log(COMP,i1,c1);
    while(i1 < c1){
        _log(CHANGE,l->list[i],list1->list[i1]);
        l->list[i] = list1->list[i1];
        i1++;
        i++;
        _log(COMP,i1,c1);
    }
    _log(COMP,i2,c2);
    while(i2 < c2){
        _log(CHANGE,l->list[i],list1->list[i2]);
        l->list[i] = list2->list[i2];
        i2++;
        i++;
        _log(COMP,i2,c2);
    }

    return l;
}

List* mergeSortAsc(List* l){
    if(l->count == 1){
        return l;
    }

    int count1 = (int)(floor(l->count/2.0));
    int count2 = (int)(ceil(l->count/2.0));
    List* l_left = malloc(sizeof(List*));
    List* l_right = malloc(sizeof(List*));
    l_left->list = l->list;
    l_left->count = count1;
    l_right->list = l->list + count1;
    l_right->count = count2;


    return mergeAsc(mergeSortAsc(l_left), mergeSortAsc(l_right));
}
List* mergeSortDesc(List* l){
    if(l->count == 1){
        return l;
    }

    int count1 = (int)(floor(l->count/2.0));
    int count2 = (int)(ceil(l->count/2.0));
    List* l_left = malloc(sizeof(List*));
    List* l_right = malloc(sizeof(List*));
    l_left->list = l->list;
    l_left->count = count1;
    l_right->list = l->list + count1;
    l_right->count = count2;

    return mergeDesc(mergeSortDesc(l_left), mergeSortDesc(l_right));
}

int partitionAsc(List* l,int a,int b){
        _log(CHANGE,0,l->list[a]);
        int pivot = l->list[a];
        _log(CHANGE,0,a);
        int i = a;

        for (int j = a+1; j <= b; j++) {
            _log(COMP,l->list[j],pivot);
            if (l->list[j] <= pivot){
                i++;
                _log(COMP,l->list[j],l->list[i]);
                if(i!=j) {
                    _log(SWAP,l->list[i],l->list[j]);
                    swap(&(l->list[i]), &(l->list[j]));
                }
            }
        }
        _log(SWAP,l->list[i],l->list[a]);
        swap(&(l->list[i]), &(l->list[a]));
        return i;
}
int partitionDesc(List* l,int a,int b){
    _log(CHANGE,0,l->list[a]);
    int pivot = l->list[a];
    _log(CHANGE,0,a);
    int i = a;

    for (int j = a+1; j <= b; j++) {
        _log(COMP,l->list[j],pivot);
        if (l->list[j] >= pivot){
            i++;
            _log(COMP,l->list[j],l->list[i]);
            if(i!=j) {
                _log(SWAP,l->list[i],l->list[j]);
                swap(&(l->list[i]), &(l->list[j]));
            }
        }
    }
    _log(SWAP,l->list[i],l->list[a]);
    swap(&(l->list[i]), &(l->list[a]));
    return i;
}

void quickSortAsc(List* l,int a,int b){
    if(a<b){
        int k = partitionAsc(l,a,b);
        quickSortAsc(l,a,k-1);
        quickSortAsc(l,k+1,b);
    }
}
void quickSortDesc(List* l,int a,int b){
    if(a<b){
        int k = partitionDesc(l,a,b);
        quickSortDesc(l,a,k-1);
        quickSortDesc(l,k+1,b);
    }
}

int dppartitionAsc(List* l,int low,int high,int* lp){
    _logcount(COMP);
    if (l->list[low] > l->list[high]) {
        _logcount(SWAP);
        swap(&(l->list[low]), &(l->list[high]));
    }
    int small = 0, big = 0;
    int j = low + 1;
    int g = high - 1, k = low + 1, p = l->list[low], q = l->list[high];
    _logcount(COMP);
    while (k <= g) {
        _logcount(COMP);
        if (small < big) {
            _logcount(COMP);
            if (l->list[k] < p) {
                _logcount(SWAP);
                swap(&(l->list[k]), &(l->list[j]));
                small++;
                j++;
            } else if (l->list[k] >= q) {
                _logcount(COMP);
                _logcount(COMP);
                while (l->list[g] > q && k < g){
                    big++;
                    g--;
                    _logcount(COMP);
                }
                _logcount(SWAP);
                swap(&(l->list[k]), &(l->list[g]));
                g--;
                _logcount(COMP);
                if (l->list[k] < p) {
                    _logcount(SWAP);
                    swap(&(l->list[k]), &(l->list[j]));
                    small++;
                    j++;
                }
            }
            k++;
        }
        else{
            _logcount(COMP);
            if (l->list[k] >= q) {
                _logcount(COMP);
                while (l->list[g] > q && k < g) {
                    big++;
                    g--;
                    _logcount(COMP);
                }
                _logcount(SWAP);
                swap(&(l->list[k]), &(l->list[g]));
                g--;
                _logcount(COMP);
                if (l->list[k] < p) {
                    _logcount(SWAP);
                    swap(&(l->list[k]), &(l->list[j]));
                    small++;
                    j++;
                }
            }
            else if (l->list[k] < p) {
                _logcount(COMP);
                _logcount(SWAP);
                swap(&(l->list[k]), &(l->list[j]));
                small++;
                j++;
            }
            k++;
        }
        _logcount(COMP);
    }
    j--;
    g++;
    _logcount(SWAP);
    swap(&(l->list[low]), &(l->list[j]));
    _logcount(SWAP);
    swap(&(l->list)[high], &(l->list)[g]);

    *lp = j;

    return g;
}
void dualPivotQSAsc(List* l,int low,int high){
    if (low < high) {

        int lp, rp;
        rp = dppartitionAsc(l, low, high, &lp);
        dualPivotQSAsc(l, low, lp - 1);
        dualPivotQSAsc(l, lp + 1, rp - 1);
        dualPivotQSAsc(l, rp + 1, high);
    }
}

int dppartitionDesc(List* l,int low,int high,int* lp){
    _log(COMP,l->list[low],l->list[high]);
    if (l->list[low] < l->list[high]) {
        _log(SWAP,l->list[low],l->list[high]);
        swap(&(l->list[low]), &(l->list[high]));
    }
    int small = 0, big = 0;
    int j = low + 1;
    int g = high - 1, k = low + 1, p = l->list[low], q = l->list[high];
    _log(COMP,k,g);
    while (k <= g) {
        _log(COMP,small,big);
        if (small < big) {
            _log(COMP,l->list[k],p);
            if (l->list[k] > p) {
                _log(SWAP,l->list[k],l->list[j]);
                swap(&(l->list[k]), &(l->list[j]));
                small++;
                j++;
            } else if (l->list[k] <= q) {
                _log(COMP,l->list[k],q);
                _log(COMP,l->list[g],q);
                while (l->list[g] < q && k < g){
                    big++;
                    g--;
                    _log(COMP,l->list[g],q);
                }
                _log(SWAP,l->list[k],l->list[g]);
                swap(&(l->list[k]), &(l->list[g]));
                g--;
                _log(COMP,l->list[k],p);
                if (l->list[k] > p) {
                    _log(SWAP,l->list[k],l->list[j]);
                    swap(&(l->list[k]), &(l->list[j]));
                    small++;
                    j++;
                }
            }
            k++;
        }
        else{
            _log(COMP,l->list[k],q);
            if (l->list[k] <= q) {
                _log(COMP,l->list[g],q);
                while (l->list[g] < q && k < g) {
                    big++;
                    g--;
                    _log(COMP,l->list[g],q);
                }
                _log(SWAP,l->list[k],l->list[g]);
                swap(&(l->list[k]), &(l->list[g]));
                g--;
                _log(COMP,l->list[k],p);
                if (l->list[k] > p) {
                    _log(SWAP,l->list[k],l->list[j]);
                    swap(&(l->list[k]), &(l->list[j]));
                    small++;
                    j++;
                }
            }
            else if (l->list[k] > p) {
                _log(COMP,l->list[k],p);
                _log(SWAP,l->list[k],l->list[j]);
                swap(&(l->list[k]), &(l->list[j]));
                small++;
                j++;
            }
            k++;
        }
        _log(COMP,k,g);
    }
    j--;
    g++;
    _log(SWAP,l->list[low],l->list[j]);
    swap(&(l->list[low]), &(l->list[j]));
    _log(SWAP,l->list[high],l->list[g]);
    swap(&(l->list)[high], &(l->list)[g]);

    *lp = j;

    return g;
}
void dualPivotQSDesc(List* l,int low,int high){
    if (low < high) {

        int lp, rp;
        rp = dppartitionDesc(l, low, high, &lp);
        dualPivotQSDesc(l, low, lp - 1);
        dualPivotQSDesc(l, lp + 1, rp - 1);
        dualPivotQSDesc(l, rp + 1, high);
    }
}

void parseArguments(int argc, char** argv, char* isFlag, char* flag, char* type, char* comp,int* k,FILE** fp){
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
                } else if (strcmp(flag, "comp") == 0) {
                    argv++;
                    argc--;
                    memcpy(comp, *argv, strlen(*argv));
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
void printList(List* l){
    for (int i = 0; i < l->count; i++) {
        printf("%d ",(l->list)[i]);
    }
    printf("\n");
}
void sort(List* l,char* type, char* comp,FILE *fp,int k){
    clock_t start;
    if(k == 0) {
        if (strcmp(type, "insert") == 0 || strcmp(type, "merge") == 0 || strcmp(type, "quick") == 0 ||
            strcmp(type, "dpquick") == 0) {
            printf("Count of element: ");
            scanf("%d", &(l->count));
            l->list = malloc(l->count * sizeof(int));
            for (int i = 0; i < l->count; i++) {
                scanf("%d", &((l->list)[i]));
            }
            testCount = l->count;

            start = clock();

            if (strcmp(type, "insert") == 0) {
                if (strcmp(comp, ">=") == 0) {
                    insertSortDesc(l);
                } else if (strcmp(comp, "<=") == 0) {
                    insertSortAsc(l);
                }
            } else if (strcmp(type, "merge") == 0) {
                List *sorted_l;
                if (strcmp(comp, ">=") == 0) {
                    sorted_l = mergeSortDesc(l);
                } else if (strcmp(comp, "<=") == 0) {
                    sorted_l = mergeSortAsc(l);
                }
                l->list = sorted_l->list;
                l->count = sorted_l->count;
            } else if (strcmp(type, "quick") == 0) {
                if (strcmp(comp, ">=") == 0) {
                    quickSortDesc(l, 0, l->count - 1);
                } else if (strcmp(comp, "<=") == 0) {
                    quickSortAsc(l, 0, l->count - 1);
                }
            } else {
                if (strcmp(comp, "<=") == 0) {
                    dualPivotQSAsc(l, 0, l->count - 1);
                } else if (strcmp(comp, ">=") == 0) {
                    dualPivotQSDesc(l, 0, l->count - 1);
                }
            }
            fprintf(stderr, "Czas wykonywania: %.1f ms\n", (double) (clock() - start));
        }
    }else{
        int k1;
        for(int n = 100; n <= 10000; n+=100){
            printf("%d\n",n);
            k1=k;
            while(k1 > 0){
                swapc = 0,changec = 0,comparec = 0;
                int tab[n];
                for (int i = 0; i < n; i++) {
                    tab[i] = rand()%1000;
                }
                List* list = malloc(sizeof(List*));
                list->list = tab;
                list->count = n;
                start = clock();

                if (strcmp(type, "insert") == 0) {
                    if (strcmp(comp, ">=") == 0) {
                        insertSortDesc(list);
                    } else if (strcmp(comp, "<=") == 0) {
                        insertSortAsc(list);
                    }
                } else if (strcmp(type, "merge") == 0) {
                    List *sorted_l;
                    if (strcmp(comp, ">=") == 0) {
                        sorted_l = mergeSortDesc(list);
                    } else if (strcmp(comp, "<=") == 0) {
                        sorted_l = mergeSortAsc(list);
                    }
                    list->list = sorted_l->list;
                    list->count = sorted_l->count;
                } else if (strcmp(type, "quick") == 0) {
                    if (strcmp(comp, ">=") == 0) {
                        quickSortDesc(list, 0, list->count - 1);
                    } else if (strcmp(comp, "<=") == 0) {
                        quickSortAsc(list, 0, list->count - 1);
                    }
                } else if(strcmp(type, "dpquick") == 0){
                    if (strcmp(comp, "<=") == 0) {
                        dualPivotQSAsc(list, 0, list->count - 1);
                    } else if (strcmp(comp, ">=") == 0) {
                        dualPivotQSDesc(list, 0, list->count - 1);
                    }
                }

                fprintf(fp,"n=%d,swapc=%d,comparec=%d,operations=%d time=%.1f\n",n,swapc,comparec,swapc+comparec+changec,(double) (clock() - start));
                k1--;
            }
        }
    }
}
bool checkSort(List* l,char* comp){
    if(testCount != l->count){
        return false;
    }

    if(strcmp(comp,"<=")== 0)
        for (int i = 0; i < l->count-1; ++i) {
            if(l->list[i] > l->list[i+1]){
                return false;
            }
        }
    else if(strcmp(comp,">=")== 0){
        for (int i = 0; i < l->count-1; ++i) {
            if(l->list[i] < l->list[i+1]){
                return false;
            }
        }
    }
    else{
        return false;
    }
    return true;
}

int main(int argc,char** argv){
    srand(time(NULL));
    char* isFlag = malloc(sizeof(char*));
    char* flag = malloc(sizeof(char*));
    char* type = malloc(sizeof(char*));
    char* comp = malloc(sizeof(char*));
    List* list = malloc(sizeof(List*));
    FILE* fp;
    int k = 0;
    testCount = 0;
    swapc = 0;
    changec = 0;
    comparec = 0;
    strcpy(comp,"<=");
    strcpy(type,"quick");

    parseArguments(argc,argv,isFlag,flag,type,comp,&k,&fp);
    sort(list,type,comp,fp,k);
    printList(list);
    printf("Sorted: %d, swapc = %d, comparec = %d, changec = %d\n",(int)checkSort(list,comp),swapc,comparec,changec);
    return 0;
}