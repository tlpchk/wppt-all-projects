#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct Element{
  int val;
  struct Element* next;
} Element;

void addElement(Element** l,int v,int index);
int getElement(Element* start,int index);
int exists(Element* start,int val);
void printList(Element** l);
void addRandomElements(Element** list,int count);
void timeToSameElement(Element** l,int index);
void timeToRandomElement(Element** l);
void merge(Element** lista1,Element** lista2);

int size(Element** l){
  int count = 0;
  Element* temp = *l;
  while(temp){
    count++;
    temp = temp->next;
  }
  return count;
}

void addElement(Element** l,int v,int index){
    Element *new = malloc(sizeof(Element*));
    new->val = v;
    new->next = NULL;

    if(l[0] == NULL){
        l[0] = new;
    }
    else if(index > 0){
      Element* temp = l[0];

      while(index > 1 && temp->next){
        index--;
        temp = temp->next;
      }

      new->next = temp->next;
      temp->next = new;

    }else if(index == 0){
      new->next = l[0];
      l[0] = new;
    }
}

void printList(Element** l){
  Element *temp = *l;

  while(temp){
    printf("%d ", temp->val);
    temp = temp->next;
  }
  printf("\n\n");
}

int getElement(Element* start,int index){
  Element *temp = start;
  while(temp && index > 0){
    temp = temp->next;
    index--;
  }
  if(start == NULL || index!=0 || temp == NULL){
    return -1;
  }
  if(index == 0){
    return temp->val;
  }
}

int exists(Element* start,int val){
  Element *temp = start;
  while (temp) {
    if(temp->val == val){
      return 1;
    }
    temp=temp->next;
  }
  return 0;
}

void addRandomElements(Element** list,int count){
  int r;
  while (count>0) {
    r = rand();
    addElement(list,r%1000,0);
    count--;
  }
}

void timeToSameElement(Element** l,int index){
  clock_t start = clock();
  for (int i = 0; i < 1000; i++) {
      getElement(*l,index);
  }
  printf( "Czas wykonywania: %.2f ms\n", ((double)(clock() - start))/1000);
}

void timeToRandomElement(Element** l){
  clock_t start = clock();
  int index;
  for (int i = 0; i < 1000; i++) {
      index=rand()%1000;
      getElement(*l,index);
  }
  printf( "Czas wykonywania: %.2f ms\n", ((double)(clock() - start))/1000);
}

/*Sklejam dwie listy, zapisuję w pierwszą*/
void merge(Element** lista1,Element** lista2){
  Element* temp = *lista2;
  Element* copyLista2 = malloc(sizeof(Element*));
  int i = 1;
  while(temp){
    addElement(&copyLista2,temp->val,i);
    temp = temp->next;
    i++;
  }
  temp = *lista1;
  while(temp->next){
    temp = temp->next;
  }
  temp->next = copyLista2;

}

int main(){
  srand(time(NULL));
  Element** list = malloc(sizeof(Element**));
  Element** list2 = malloc(sizeof(Element**));

  int command,element,index,end;
  end=0;
  addRandomElements(list,1000);

  /*addElement(list,0,0);
  addElement(list,1,0);
  addElement(list,2,0);
  addElement(list,3,0);
  addElement(list2,12,0);
  addElement(list2,11,0);
  addElement(list2,10,0);
  addElement(list2,9,0);

  merge(list2,list);
  printList(list2);
*/

  printf("1: Dodać element\n");
  printf("2: Sprawdzić czy istnieje \n");
  printf("3: Dostęp do elementu\n");
  printf("4: Wyświetlić listę\n");
  printf("5: Zmierzyć czas do tego samego\n");
  printf("6: Zmierzyć średni czas do losowego elementu\n");
  printf("9: Koniec\n------------\n");

  while(!end){
    printf("Podaj komędę: ");
    scanf("%d", &command);
    switch (command) {
      case 1:
        printf("Podaj element: ");
        scanf("%d", &element);
        printf("Podaj index: ");
        scanf("%d", &index);

        addElement(list,element,index);
        printf("Element %d wstawiono\n\n", element);
        break;
      case 2:
          printf("Podaj wartość: ");
          scanf("%d", &element);
          printf("%d\n\n",exists(*list,element));
        break;
      case 3:
          printf("Podaj index: ");
          scanf("%d",&index);
          printf("%d\n\n",getElement(*list,index));
        break;
      case 4:
          printList(list);
        break;
      case 5:
          printf("Podaj index: ");
          scanf("%d",&index);
          timeToSameElement(list,index);
          break;
      case 6:
          timeToRandomElement(list);
          break;
      case 9:
          end = 1;
          break;
      default:
        printf("Zła komenda\n");
    }
    command = 0;
  }

  return 0;
}
