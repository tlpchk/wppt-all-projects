#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>
#include <stdbool.h>
#include <sodium.h>

#define COMP 1
#define SWAP 2

typedef struct List{
    int* arr;
    int count;
    int swapc,comparec;
    bool writeInSTDERR;
    bool naturalOrder;
} List;

void _log(int operation,int key1, int key2,List* l);
void swap(int a, int b,List* list);
bool checkSort(List* l);
int randomPivot(int low, int high);
bool compare(int a,int b, List* list);
void insertSort(List* l);
void merge(List* list, int l, int m, int r);
void mergeSort(List* list, int l, int r);
int partition(List* l,int a,int b);
void quickSort(List* l,int a,int b);
int dppartition(List* l,int low,int high,int* lp);
void dualPivotQS(List* l,int low,int high);
void parseArguments(int argc, char** argv, char* isFlag, char* flag, char* type, char* comp,int* k,FILE** fp);
void printList(List* l);
void sort(List* l,char* type, char* comp);
void sortRandom(char* type, char* comp,FILE *fp,int k,int start, int end, int inc);
