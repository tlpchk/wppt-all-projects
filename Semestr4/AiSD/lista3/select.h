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
    int n;
    int k;
    int compares;
    int swaps;
} List;

void randomSelect(int argc, char** argv);
int randomValue(int a);
int randomPivot(int low, int high);
void _logMsg(char* msg);
void _log(int operation, int key1, int key2, List* l);
void swap(int a, int b,List* list,bool inStderr);
bool compare(int a,int b, List* list);
void readParameters(List* list);
void randomList(List* list);
void randomPermutation(List* list);
int randomSelectAlgorithm(List* list,int p,int q,int i);
int partition(List* l,int a,int b);
void printList(List* list,int k);
void Select(int argc, char** argv);
int medianOfMedian(int arr[],int p,int q,int c);
void logList(List* list);
void quickHybrid(List* l,int a,int b);
int partitionX(List* l,int a,int b,int x);