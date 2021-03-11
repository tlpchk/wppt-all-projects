#include <iostream>
#include <math.h>
#include <string.h>
#include <strstream>
using namespace std;

typedef double Typ;

class TreeElem
{
    public:
    Typ element;
    TreeElem* parent=NULL;
    TreeElem* left=NULL;
    TreeElem* right=NULL;

    TreeElem(Typ element){
        this->element=element;
    }
};

class Tree{
    public:
    TreeElem* root;

    //funkcja zwraca wskaznik elementu o najmniejszej wartosci (najbardziej na lewo)
    TreeElem* minimum(TreeElem* start)
    {
        if(start->left != NULL)
        {
            return minimum(start->left);
        }
        else
        {
            return start;
        }
    }
    //funkcja zwraca wezel o podanej wartosci, badz NULL, gdy taki wezel nie istnieje
    TreeElem* searchElem(TreeElem* start, Typ element)
    {
        if (start->element == element)
        {
            return start;
        }
        else if (element < start->element && start->left != NULL)
        {
            return searchElem(start->left, element);
        }
        else if (element > start->element && start->right != NULL)
        {
            return searchElem(start->right, element);
        }
        return NULL;
    }

    void add(Typ n, TreeElem* start)
    {
        if (root == NULL)
        {
            root = new TreeElem(n);
        }
        else if(n < start->element)
        {
            if(start->left != NULL)
            {
            add(n,start->left);
            }

            else
            {
                TreeElem* nowy = new TreeElem(n);
                nowy->parent = start;
                start->left=nowy;
            }
        }
        else
        {
            if(start->right!=NULL)
            {
                add(n,start->right);
            }

            else
            {
                TreeElem* nowy = new TreeElem(n);
                nowy->parent = start;
                start->right=nowy;
            }
        }
    }

//usun wezel start
    void deleteElem(TreeElem* start)
    {

    if(start->left==NULL && start->right==NULL)
    {
        if(start->parent==NULL)
        {
            root=NULL;
        }

        else if(start->parent->left==start)
        {
            start->parent->left=NULL;
        }
        else
        {
            start->parent->right=NULL;
        }
        delete start;
    }

    else if(start->left==NULL || start->right==NULL)
    {
        if(start->left==NULL)
        {
            if(start->parent==NULL)
            {
                root=start->right;
            }

            else if(start->parent->left==start)
            {
                start->parent->left=start->right;
            }
            else
            {
                start->parent->right=start->right;
            }
        }
        else
        {
            //jezeli wezel jest korzeniem
            if(start->parent==NULL)
            {
                root=start->left;
            }
            //jezeli wezel jest po lewej stronie rodzica
            else if(start->parent->left==start)
            {
                //przyczep z lewej strony rodzica wezel bedacy po lewej stronie usuwanego wezla
                start->parent->left=start->left;
            }
            else
            {
                //przyczep z prawej strony rodzica wezel bedacy po prawej stronie usuwanego wezla
                start->parent->right=start->left;
            }
        }
            delete start;
        }
    else
    {
        //wstaw w miejsce usuwanego elementu - najmniejsza wartosc z prawego poddrzewa
        TreeElem *temp;
        temp=minimum(start->right);
        start->element = temp->element;
        deleteElem(temp);
    }
    }

    //przejdz drzewo w kolejnosci zaczynajac od wezla start
    void in_order_tree_walk(TreeElem* start)
    {
        if(start->left != NULL){
            in_order_tree_walk(start->left);
        }

        cout<<start->element<<endl; //wypisz wartosc

        if(start->right != NULL) //jezeli ma dzieci po prawej stronie wywolaj rekurencyjnie
        {
            in_order_tree_walk(start->right);
        }
    }

   void draw(TreeElem* w)
    {
        if( w!=NULL ){
            cout<<"("<<w->element<<" l:";
            draw(w->left);
            cout<<" p:";
            draw(w->right);
            cout<<")";
        }
        else{
            cout<<"__";
        }
    }

    void isElement(Typ element,TreeElem* start){
        if(searchElem(start,element)!=NULL){ cout<<element; cout<<" jest w drzewie"<<endl;}
        else{ cout<<element; cout<<" nie jest w drzewie"<<endl;}
    }

};


//przklad uzycia drzewa BST
int main(int argc, char *argv[])
{
    Tree* d = new Tree();
    d->add(8,d->root);
    d->add(10,d->root);
    d->add(14,d->root);
    d->add(13,d->root);
    d->add(3,d->root);
    d->add(6,d->root);
    d->add(5,d->root);
    d->add(4,d->root);
    d->add(1,d->root);

    d->draw(d->root);
    cout<<endl;

    d->deleteElem(d->searchElem(d->root,3));

    d->draw(d->root);
    cout<<endl;

    d->deleteElem(d->searchElem(d->root,8));

    d->draw(d->root);
    cout<<endl;

    d->isElement(3,d->root);
    d->isElement(6,d->root);


    return 0;
}
