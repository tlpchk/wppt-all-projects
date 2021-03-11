package com.company;

/**Klasa drzewa binarnego
 *
 * @param <Typ> typ elementów znajdujących się w drzewie binarnym
 */
public class Tree<Typ extends Comparable<Typ>> {

    /**Korzeń drzewa binarnego
     */
    private TreeElem<Typ> root;

    /**Tworzenie drzewa binarnego
     */
    public Tree() { root = null; }

    /**Wstawienie elementu w drzewo binarne
     *
     * @param elem Element, który chcemy dodać
     */
    public void insert(Typ elem) {
        TreeElem<Typ> z = new TreeElem<>(elem);

        TreeElem<Typ> y = null;
        TreeElem<Typ> x = root;

        while (x != null){
            y=x;
            if(elem.compareTo(x.elem)<0){
                x=x.left;
            }
            else {
                x=x.right;
            }
        }
        z.parent=y;
        if(y==null){
            root = z; // drzewo było puste
        }
        else if(elem.compareTo(y.elem)<0){
            y.left=z;
        }
        else{
            y.right = z;
        }

    }

    /**Sprawdza czy istnieje węzeł z daną wartością
     *
     * @param elem wartość węzła, istnienie którego sprawdzamy
     * @return Inforcja o istnieniu elementu w drzewie
     */
    public boolean isElement(Typ elem) {
        if(search(elem)==null){ return false;}
        else {return true;}
    }

    /**Metoda wyszukiwania węzłą z daną wartością
     *
     * @param elem Wartość węzła, który szukamy
     * @return Węzeł, który ma szukaną wartość
     */
    public TreeElem<Typ> search(Typ elem){ return searc(elem,this.root);}


    private TreeElem<Typ> searc(Typ elem, TreeElem<Typ> w) {
        if(w!=null) {
            if (elem.equals(w.elem)) {
                return w;
            }
            if (elem.compareTo(w.elem) < 0) {
                return searc(elem, w.left);
            } else {
                return searc(elem, w.right);
            }
        }
        else{
            return null;
        }
    }

    /**Zamienia jedno poddrzewo na inne
     * @param u korzeń poddrzewa, które chcemy zamienić
     * @param v korzeń poddrzewa na które chvemy zamienić
    * */
    private void transplate(TreeElem<Typ> u , TreeElem<Typ> v){
        if (u.parent==null){
            root=v;
        }
        else if(u==u.parent.left){
            u.parent.left=v;
        }
        else{
            u.parent.right = v;
        }
        if(v!=null){
            v.parent=u.parent;
        }
    }

    private TreeElem<Typ> minimum (TreeElem<Typ> x){
        while (x.left!=null){
            x=x.left;
        }
        return x;
    }

    /**Usuwa węzeł, ktory ma daną wartość
     *
     * @param elem Wartość elementu, który próbujemy usunąć
     */
    public void delete(Typ elem){
        if(search(elem)==null){
            return;}
        del(search(elem));
    }

    private void del(TreeElem<Typ> z) {
        if(z.left==null) transplate(z,z.right);
        else if(z.right==null) transplate(z,z.left);
        else{
            TreeElem<Typ> y = minimum(z.right);
            if(y.parent!=z){
                transplate(y,y.right);
                y.right=z.right;
                y.right.parent=y;
            }
            transplate(z,y);
            y.left=z.left;
            y.left.parent=y;
        }
    }

    /**Wyświetla w konsoli potoczny stan drzewa binarnego
     *
     * @return Dzewo binarne w postaći liniowej
     */
    public String toString() { return toS(root); }

    private String toS(TreeElem<Typ> w) {
        if( w!=null )
            return "("+w.elem+" l:"+toS(w.left)+" p:"+toS(w.right)+")";
        return "__";
    }
}
