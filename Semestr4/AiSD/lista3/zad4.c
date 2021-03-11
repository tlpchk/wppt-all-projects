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
    strcpy(type,"quickh");

    parseArguments(argc,argv,isFlag,flag,type,&k,&fp);
    sortRandom(type,fp,k,100,1000,10);
    return 0;
}