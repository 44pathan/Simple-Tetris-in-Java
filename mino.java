package mino;
import main.KeyHandler;
import main.playManager;

import java.awt.*;
import java.awt.Graphics2D;

public class mino {
    public block b[] = new block[4];
    public block tempB[] = new block[4];
    int autoDropCounter = 0;
    public int direction = 1;// 4 directions 1 to 4
    boolean leftcollision, rightcollision, bottomcollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivatecounter = 0;

    public void create(Color c){
        b[0] = new block(c);
        b[1] = new block(c);
        b[2] = new block(c);
        b[3] = new block(c);
        tempB[0]=new block(c);
        tempB[1]=new block(c);
        tempB[2]=new block(c);
        tempB[3]=new block(c);
    }
    public void setXY(int x,int y){

    }
    public void checkmovementcollision(){
        leftcollision=false;
        rightcollision=false;
        bottomcollision=false;
        checkStaticBlockCollision();
        //checking frame collision
        //for LEFT WALL
        for(int i=0;i<b.length;i++){
            if(b[i].x==playManager.left_x){
                leftcollision=true;
            }
        }
        //for RIGHT WALl
        for(int i=0;i<b.length;i++){
            if(b[i].x + block.SIZE==playManager.right_x){
                rightcollision=true;
            }
        }
        //for BOTTOM FLOOR
        for(int i=0;i<b.length;i++){
            if(b[i].y + block.SIZE==playManager.bottom_y){
                bottomcollision=true;
            }
        }
    }

    public void checkrotationalcollision(){
        leftcollision=false;
        rightcollision=false;
        bottomcollision=false;
        checkStaticBlockCollision();
        //checking frame collision
        //for LEFT WALL
        for(int i=0;i<b.length;i++){
            if(tempB[i].x<playManager.left_x){
                leftcollision=true;
            }
        }
        //for RIGHT WALl
        for(int i=0;i<b.length;i++){
            if(tempB[i].x + block.SIZE>playManager.right_x){
                rightcollision=true;
            }
        }
        //for BOTTOM FLOOR
        for(int i=0;i<b.length;i++){
            if(tempB[i].y + block.SIZE>playManager.bottom_y){
                bottomcollision=true;
            }
        }
    }
    private void checkStaticBlockCollision(){
        for(int i=0; i<playManager.staticblocks.size() ; i++){
            int targetX = playManager.staticblocks.get(i).x;
            int targetY = playManager.staticblocks.get(i).y;
            //check down
            for(int i1=0; i1< b.length;i1++){
                // this means a static block is right below and bottomcollision happens
                if(b[i1].y + block.SIZE == targetY && b[i1].x == targetX){
                    bottomcollision=true;
                }
            }
            //check left
            for(int i1=0; i1< b.length;i1++){
                // this means a static block is right below and bottomcollision happens
                if(b[i1].y - block.SIZE == targetX && b[i1].y == targetY){
                    leftcollision=true;
                }
            }
            for(int i1=0; i1< b.length;i1++){
                // this means a static block is right below and bottomcollision happens
                if(b[i1].x + block.SIZE == targetX && b[i1].y == targetY){
                    rightcollision=true;
                }
            }

        }
    }
    public void updateXY(int direction){
        checkrotationalcollision();
        if(leftcollision==false && rightcollision==false && bottomcollision==false){
            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }

    }
    public void getdirection1(){

    }
    public void getdirection2(){

    }
    public void getdirection3(){

    }
    public void getdirection4(){

    }

    private void deactivating(){
        deactivatecounter++;
        //will wait for 45 frames until deactivating
        if(deactivatecounter == 45){
            deactivatecounter=0;
            // to check if bottom is hitting or not
            checkmovementcollision();
            // if the bottom is hitting after 45 frames, deactivate this mino
            if(bottomcollision){
                active=false;
            }
        }

    }
    public void update(){
        if(deactivating){
            deactivating();
        }
        //controlling the mino
        
        if(KeyHandler.downPressed){
            //if bottom not hitted it will go down
            if(bottomcollision==false){
                b[0].y += block.SIZE;
                b[1].y += block.SIZE;
                b[2].y += block.SIZE;
                b[3].y += block.SIZE;
                //when it goes down the counter resets
                autoDropCounter=0;
            }
            KeyHandler.downPressed=false;

        }
        if(KeyHandler.upPressed){
            switch (direction){
                case 1: getdirection2();break;
                case 2: getdirection3();break;
                case 3: getdirection4();break;
                case 4: getdirection1();break;
            }
            KeyHandler.upPressed=false;
        }
        checkmovementcollision();
        if(KeyHandler.leftPressed){
            if(leftcollision==false){
                b[0].x-=block.SIZE;
                b[1].x-=block.SIZE;
                b[2].x-=block.SIZE;
                b[3].x-=block.SIZE;
            }
            KeyHandler.leftPressed=false;
        }
        if(KeyHandler.rightPressed){
            if(rightcollision==false){
                b[0].x+=block.SIZE;
                b[1].x+=block.SIZE;
                b[2].x+=block.SIZE;
                b[3].x+=block.SIZE;
            }
            KeyHandler.rightPressed=false;
        }
        if(bottomcollision){
            deactivating=true;
        }
        else{
            //increases every 1 sec
            autoDropCounter++;
            if(autoDropCounter == playManager.dropinterval){
                //mino comes down
                b[0].y += block.SIZE;
                b[1].y += block.SIZE;
                b[2].y += block.SIZE;
                b[3].y += block.SIZE;
                autoDropCounter = 0;
            }
        }

    }
    public void draw(Graphics2D g2){
        int margin =2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x+margin,b[0].y+margin,block.SIZE-(margin*2),block.SIZE-(margin*2));
        g2.fillRect(b[1].x+margin,b[1].y+margin,block.SIZE-(margin*2),block.SIZE-(margin*2));
        g2.fillRect(b[2].x+margin,b[2].y+margin,block.SIZE-(margin*2),block.SIZE-(margin*2));
        g2.fillRect(b[3].x+margin,b[3].y+margin,block.SIZE-(margin*2),block.SIZE-(margin*2));
    }

}
