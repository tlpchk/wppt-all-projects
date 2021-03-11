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

    FILE* fp;
    int k = 0;
    strcpy(comp,"<=");
    strcpy(type,"quick");

    parseArguments(argc,argv,isFlag,flag,type,comp,&k,&fp);
    sortRandom(type,comp,fp,k,100,10000,100);

    return 0;
}