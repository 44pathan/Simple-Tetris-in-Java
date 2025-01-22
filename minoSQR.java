package mino;

import java.awt.*;

public class minoSQR extends mino{

    public minoSQR(){
        create(Color.yellow);
    }

    @Override
    public void setXY(int x, int y) {
        // o o
        // o o
        b[0].x=x;
        b[0].y=y;
        b[1].x=b[0].x;
        b[1].y=b[0].y + block.SIZE;
        b[2].x=b[0].x + block.SIZE;
        b[2].y=b[0].y;
        b[3].x=b[0].x+block.SIZE;
        b[3].y=b[0].y+block.SIZE;
    }
    public void getdirection1() {
    }
    public void getdirection2() {
    }
    public void getdirection3() {
    }
    public void getdirection4() {
    }
}
