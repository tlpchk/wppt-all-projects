import java.awt.*;

public class Prostokat extends Figura {
    private int x1,x2;
    private int y1,y2;
    private int width;
    private int height;

    public Prostokat(int x1, int y1, int x2, int y2){
        //x1,y1 - lewy górny
        //x2,y2 - prawy dolny
        this.x1 = Math.min(x1,x2);
        this.x2 = Math.max(x1,x2);
        this.y1 = Math.min(y1,y2);
        this.y2 = Math.max(y1,y2);
        this.width = this.x2-this.x1;
        this.height = this.y2-this.y1;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public void draw(Graphics2D g){
        //ustawienie kolorow
        this.setColor(g);
        //rysowanie kola
        g.fillRect(x1,y1,width,height);
    }


    public void changeSize(double s){

        int dx=(int)(s*width/(Math.sqrt(width*width+height*height)));
        int dy=(int)(s*height/Math.sqrt(width*width+height*height));

        width = x2-x1;
        height = y2-y1;
        if(width+s>5 && height+s>5) {
            x1 -= dx;
            y1 -= dy;
            x2 += dx;
            y2 += dy;
        }

        width = x2-x1;
        height = y2-y1;
    }

    public void move(int dx, int dy){
        this.x1+=dx;
        this.y1+=dy;
        this.x2+=dx;
        this.y2+=dy;
    }

    public boolean inside(int x,int y){
        boolean check = (x>=x1 && x<=x2) && (y>=y1 && y<=y2);
        this.setSelect(check);
        return(check);
    }
}

