public class WierszTrojkataPascala {
    int[][]  troikat;
    WierszTrojkataPascala(int n) {
        troikat = new int[n + 1][];

        for (int v = 0; v < n+1; v++) {
            troikat[v] = new int[v+1];
            troikat[v][0] = 1;
            for (int i = 1; i <= v; i++) {
                int i_copy = i;
                while (i > 0) {
                    troikat[v][i] = troikat[v][i] + troikat[v][i - 1];
                    i--;
                }
                i = i_copy;
            }
        }
    }
}