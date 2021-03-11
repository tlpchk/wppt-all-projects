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

    FILE* fp;
    int k = 0;
    strcpy(comp,"<=");
    strcpy(type,"rs");

    parseArguments(argc,argv,isFlag,flag,type,&k,&fp);
    setComp(list,comp);

    if(k == 0){
        list->swapc = 0;
        list->comparec  = 0;
        list->writeInSTDERR = false;
        sort(list,type);
        printList(list);
    }
    else{
        sortRandomRS(1000,type,fp,k,5,100000);
    }

    return 0;
}