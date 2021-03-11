import java.awt.*;
import java.util.ArrayList;

public class Wielokat extends Figura {
    private int[] x;
    private int[] y;
    private int count;

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    Wielokat(ArrayList<Integer> arrayx, ArrayList<Integer> arrayy){
        this.x =  new int[arrayx.size()];
        this.y =  new int[arrayy.size()];
        this.count = arrayx.size();


        for (int i = 0; i <arrayx.size() ; i++) {
            this.x[i]=arrayx.get(i);
            this.y[i]=arrayy.get(i);
        }

    }

    public void draw(Graphics2D g) {
        this.setColor(g);
        g.fillPolygon(x, y, x.length);
    }


    public void changeSize(double s) {

        if(s<0)
            s=0.9f;
        else
            s=1.1f;
        double x_center1=0,y_center1=0;
        double x_center2=0,y_center2=0;
        for (int i = 0; i < count; i++) {
            x_center1+=x[i];
            y_center1+=y[i];
        }
        x_center1=x_center1/count;
        y_center1=y_center1/count;

        for (int i = 0; i < count; i++) {
            x[i]*=s;
            y[i]*=s;
        }

        for (int i = 0; i < count; i++) {
            x_center2+=x[i];
            y_center2+=y[i];
        }
        x_center2=x_center2/count;
        y_center2=y_center2/count;

        double dx = x_center1-x_center2;
        double dy = y_center1-y_center2;

        for (int i = 0; i < count; i++) {
            x[i]=(int)(x[i]+dx);
            y[i]=(int)(y[i]+dy);
        }
    }

    public void move(int dx, int dy){
        for (int i = 0; i < count; i++) {
            x[i] += dx;
            y[i] += dy;
        }
    }


    public boolean inside(int x, int y){
        return (new Polygon(this.x,this.y,this.x.length)).contains(x,y);
    }
}
