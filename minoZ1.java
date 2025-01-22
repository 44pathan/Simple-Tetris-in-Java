package mino;

import java.awt.*;

public class minoZ1 extends mino{
    public minoZ1(){
        create(Color.red);
    }

    public void setXY(int x, int y) {
        //      o
        //   o  o
        //   o
        b[0].x=x;
        b[0].y=y;
        b[1].x=b[0].x;
        b[1].y=b[0].y - block.SIZE;
        b[2].x=b[0].x - block.SIZE;
        b[2].y=b[0].y;
        b[3].x=b[0].x - block.SIZE;
        b[3].y=b[0].y + block.SIZE;
    }
    public void getdirection1() {
        //      o
        //   o  o
        //   o
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - block.SIZE;
        tempB[2].x = b[0].x - block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x - block.SIZE;
        tempB[3].y = b[0].y + block.SIZE;
        //updates the direction in super class
        updateXY(1);
    }
    public void getdirection2() {
        //    o o
        //      o  o
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y - block.SIZE;
        tempB[3].x = b[0].x - block.SIZE;
        tempB[3].y = b[0].y - block.SIZE;
        //updates the direction in super class
        updateXY(2);
    }
    public void getdirection3() {
        getdirection1();
    }
    public void getdirection4() {
        getdirection2();
    }
}
