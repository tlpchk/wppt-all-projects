import java.util.ArrayList;

public class PriorityQueue<T> {
    private ArrayList<PriorityQueueNode> list;

    public PriorityQueue(){
        list = new ArrayList<>();
    }

    public void insert(T x,int p){
        list.add(list.size(),new PriorityQueueNode(x,Integer.MAX_VALUE));
        increaseKey(list.size()-1,p);
    }

    public boolean empty(){
        if(list.size() == 0){
            return true;
        }else {
            return false;
        }
    }

    public void top(){
        if(list.size() == 0){
            System.out.print("\n");
        }else{
            System.out.println(list.get(0).getX());
        }
    }

    public T pop(){
        if(list.size() == 0){
            throw new IllegalStateException("Heap is empty");
        }
        T min = list.get(0).getX();

        list.set(0,list.get(list.size()-1));
        list.remove(list.size()-1);
        minHeapify(0);
        return min;
    }

    public int priority(T x,int p){
        int changed = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getX() == x){
                increaseKey(i,p);
                changed ++;
            }
        }
        return changed;
    }

    public void print(){
        for (int i = 0; i < list.size(); i++) {
            System.out.print("(" + list.get(i).getX() + ","
                + list.get(i).getP() + ") "
            );
        }
        System.out.print("\n");
    }

    private void minHeapify(int i){
        if(list.size() == 0 ){
            return;
        }
        int least;

        int p = list.get(i).getP();
        int left = left(i);
        int right = right(i);
        if (left < list.size()
                &&  list.get(left).getP() < p){
            least = left;}
        else{
            least = i;
        }
        if (right < list.size()
                &&  list.get(right).getP() < list.get(least).getP()){
            least = right;
        }
        if(least != i){
            swap(i,least);
            minHeapify(least);
        }
    }

    private void increaseKey(int i, int p){
        if(p > list.get(i).getP()){
            return;
        }
        list.get(i).setP(p);
        while (i > 0 && list.get(parent(i)).getP() > p){
            swap(i,parent(i));
            i = parent(i);
        }
    }

    private void swap(int a, int b) {
        PriorityQueueNode c = list.get(a);
        list.set(a,list.get(b));
        list.set(b,c);
    }

    private int parent(int i){
        return (i-1)/2;
    }

    private int left(int i){
        //System.out.println(2*i + 1);
        return 2*i + 1;
    }

    private int right(int i){
        //System.out.println(2*i + 2);
        return 2*i + 2;
    }

    private class PriorityQueueNode {
        private T x;
        private int p;

        public PriorityQueueNode(T x, int p) {
            this.x = x;
            this.p = p;
        }

        public T getX() {
            return x;
        }

        public int getP() {
            return p;
        }

        public void setP(int p) {
            this.p = p;
        }
    }
}
