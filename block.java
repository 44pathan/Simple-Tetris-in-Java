package mino;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
public class block extends Rectangle {
    public int x,y;
    public static final int SIZE=30;//30 x 30 block
    public Color c;

    public block(Color c){
        this.c=c;
    }
    public void draw(Graphics2D g2){
        int margin =2;
        g2.setColor(c);
        g2.fillRect(x+margin,y+margin,SIZE-(margin*2),SIZE-(margin*2));
    }
}
