#include "sort.h"

int main(int argc,char** argv){
    if (sodium_init() == -1) {
        return 1;
    }
    srand(time(NULL));
    char* isFlag = malloc(sizeof(char*));
    char* flag = malloc(sizeof(char*));
    char* type = malloc(sizeof(char*));
    char* comp = malloc(sizeof(char*));
    List* list = malloc(sizeof(List*));

    list->arr = malloc(sizeof(int*));
    list->swapc = 0;
    list->comparec  = 0;
    list->writeInSTDERR = true;

    strcpy(comp,"<=");
    strcpy(type,"quick");

    parseArguments(argc,argv,isFlag,flag,type,comp,NULL,NULL);

    /*Wybieramy porządek sortowania*/
    if(strcmp("<=",comp) == 0) {
        list->naturalOrder = true;
    }
    else if(strcmp(">=",comp) == 0){
        list->naturalOrder = false;
    }

    sort(list,type,comp);
    printList(list);

    fprintf(stderr,"is Sorted: %d, swapc = %d, comparec = %d\n",
            (int) checkSort(list), list->swapc, list->comparec);


    return 0;
}