package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import mino.minoSQR;
import mino.minoZ2;
import mino.minoZ1;
import mino.minoL1;
import mino.minoL2;
import mino.minoBar;
import mino.minoT;
import mino.mino;
import mino.block;

public class playManager {

    /*
     *  draws play area
     * manages pieces
     * handles game play
     * */
    // main play area frame

    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    //mino
    mino currentmino;
    final int MINO_START_X;
    final int MINO_START_Y;
    public static int dropinterval = 60;// drops in every 1 sec
    mino nextmino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<block> staticblocks = new ArrayList<>();
    //EFFECTS
    boolean effectcounteron;
    int effectcounter;
    ArrayList<Integer> effectY = new ArrayList<>();
    //GAME OVER
    boolean gameover;
    //score
    int level = 1;
    int lines;
    int score;


    public playManager() {
        //main play area frame

        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;
        //starting mino

        MINO_START_X = left_x+(WIDTH/2)- block.SIZE;
        MINO_START_Y = top_y + block.SIZE;

        NEXTMINO_X = right_x +175;
        NEXTMINO_Y =  top_y + 500;
        currentmino= pickmino();
        currentmino.setXY(MINO_START_X,MINO_START_Y);
        nextmino = pickmino();
        nextmino.setXY(NEXTMINO_X,NEXTMINO_Y);
    }
    private mino pickmino(){
        //picks random mino
        mino mino = null;
        int i = new Random().nextInt(7);
        switch(i){
            case 0: mino = new minoL1();break;
            case 1: mino = new minoL2();break;
            case 2: mino = new minoSQR();break;
            case 3: mino = new minoBar();break;
            case 4: mino = new minoT();break;
            case 5: mino = new minoZ1();break;
            case 6: mino = new minoZ2();break;
        }
        return mino;
    }

    public void update() {
        //to check if current mino is active
        if(currentmino.active == false){
            // if the mino is inactive , puts it into the static blocks
            staticblocks.add(currentmino.b[0]);
            staticblocks.add(currentmino.b[1]);
            staticblocks.add(currentmino.b[2]);
            staticblocks.add(currentmino.b[3]);
            if(currentmino.b[0].x == MINO_START_X && currentmino.b[0].y == MINO_START_Y){
                // the mino is hiited immediately and cant move
                // so xy are same with the nextminos
                gameover=true;
            }
            currentmino.deactivating = false;
            // replacing the current with the next
            currentmino = nextmino;
            currentmino.setXY(MINO_START_X,MINO_START_Y);
            nextmino = pickmino();
            nextmino.setXY(NEXTMINO_X,NEXTMINO_Y);
            checkdelete();
        }
        else {
            currentmino.update();
        }
    }
    private void checkdelete(){
            int x = left_x;
            int y = top_y;
            int blockcount = 0;
            int linecount =0;

            // Loop through each row
            while (y < bottom_y) {
                blockcount = 0;  // Reset block count for each new line
                x = left_x;  // Start at the beginning of the row

                // Check each cell in the current row
                while (x < right_x) {
                    for (int i = 0; i < staticblocks.size(); i++) {
                        if (staticblocks.get(i).x == x && staticblocks.get(i).y == y) {
                            blockcount++;  // Increase count if there is a static block
                        }
                    }
                    x += block.SIZE;
                }

                // If the row is full (contains 12 blocks), remove all blocks in the current row
                if (blockcount == 12) {
                    effectcounteron=true;
                    effectY.add(y);
                    for (int j = staticblocks.size() - 1; j >= 0; j--) {
                        if (staticblocks.get(j).y == y) {
                            staticblocks.remove(j);
                        }
                    }
                    linecount++;
                    lines++;
                    // if line hits a certain number its the fastest drop speed
                    // 1 is the fastest
                    //every 10 lines level and drop speed increases
                    // if the dropinterval is 0 the game is no longer playable so we decrease it only by 1
                    if(lines %10 == 0 && dropinterval>1){
                        level++;
                        if(dropinterval>10){
                            // everytime you gain a level drop interval decreases by 10
                            dropinterval-=10;
                        }
                        else{
                            dropinterval-=1;
                        }
                    }
                    // Slide down all blocks above the deleted row
                    for (int i = 0; i < staticblocks.size(); i++) {
                        if (staticblocks.get(i).y < y) {
                            staticblocks.get(i).y += block.SIZE;
                        }
                    }
                }

                y += block.SIZE;  // Move to the next row
                //add score
                if(linecount>0){
                    int singlelinescore = 10 * level;
                    score +=singlelinescore*linecount;
                }
            }
        }

    public void draw(Graphics2D g2) {
        //DRAW main play area frame
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);
        // DRAW "NEXT" frame
        int x = right_x + 100;
        int y = bottom_y-200;
        g2.drawRect(x,y,200,200);
        g2.setFont(new Font("New Times Roman",Font.PLAIN,20));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT SHAPE",x+40,y+40);
        // score frame
        g2.drawRect(x,top_y,250,300);
        x+=40;
        y= top_y+90;
        g2.drawString("LEVEL: " +level,x,y);y+=70;
        g2.drawString("LINES: " +lines,x,y);y+=70;
        g2.drawString("SCORE: " +score,x,y);


        //DRAW current mino
        if(currentmino != null){
            currentmino.draw(g2);
        }
        //DRAW the next mino
        nextmino.draw(g2);
        //static blocks
        for(int i=0 ; i< staticblocks.size() ;i++){
            staticblocks.get(i).draw(g2);
        }
        if(effectcounteron){
            effectcounter++;
            g2.setColor(Color.white);
            for(int i=0; i<effectY.size(); i++){
                g2.fillRect(left_x,effectY.get(i),WIDTH,block.SIZE);
            }
            if(effectcounter==10){
                effectcounteron=false;
                effectcounter=0;
                effectY.clear();
            }
        }
        //PAUSE
        g2.setColor(Color.WHITE); // Set color to white
        if(KeyHandler.pausePressed){
            x=left_x+150;
            y=top_y+220;
            int[] xPoints = {x , x, x +100};
            int[] yPoints = {y , y + 100, y +50};
            int numberOfPoints = 3;
            g2.fillPolygon(xPoints, yPoints, numberOfPoints);
        }
        //game over
        if(gameover){
            x=left_x+ -10;
            y=top_y+280;
            g2.setFont(new Font("Times New Roman", Font.BOLD, 80)); // Increased the font size to 80
            g2.setColor( Color.yellow);
            g2.drawString("GAME OVER!",x,y);
        }
        //EFFECT
        g2.setColor(Color.red);
        g2.setFont(g2.getFont().deriveFont(90f));
    }
}
