package com.company;

/**Klasa elementu drzewa binarnego
 * @param <Typ> typ elementu drzewa binarnego
 */
class TreeElem<Typ extends Comparable<Typ>> {

    /**Wartość węzła dzewa binarnego
     */
    Typ elem;

    /**Wskaźnik na korzeń lewego poddrzewa
     */
    TreeElem<Typ> left;

    /**Wskaźnik na korzeń prawego poddrzewa
     */
    TreeElem<Typ> right;

    /**Wskaźnik na węzeł - rodzica
     */
    TreeElem<Typ> parent;


    /**Tworzy węzeł z danymi o nim
     *
     * @param elem Wartość węzłą
     */
    TreeElem(Typ elem)
    {
        this.elem = elem;
        left = null;
        right = null;
        parent = null;
    }

    /**Zwraca wartość węzłą
     *
     * @return wartość węzłą
     */
    public String toString() { return elem.toString(); }
}