public class Model2 {
    public static void main(String[] args) {
        double p = 0.90;
        double T_MAX = 0.0025;
        Graph2 graph = new Graph2(p,T_MAX,1000);
        System.out.println(graph.monteCarloChannel(10000,20));
        /*for (Channel c : graph.getChannels()) {
            for (Node node : c.getPair()) {
                System.out.print(node.getId()+" ");
            }
            System.out.println(c.a);
        }*/
    }

    public static int[][] matrix(){
        int[][] N = new int[10][10];
        N[0][7] = 5;
        N[5][7] = 6;
        N[0][2] = 10;
        N[2][6] = 15;

        return N;
    }
}
