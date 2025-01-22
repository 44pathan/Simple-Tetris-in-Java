package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public static boolean upPressed;
    public  static boolean downPressed;
    public static boolean rightPressed;
    public static boolean leftPressed;
    public static boolean pausePressed;
    @Override
    public void keyTyped(KeyEvent e){

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP){
            upPressed = true;
        }
        if(code == KeyEvent.VK_DOWN){
             downPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT){
             rightPressed = true;
        }
        if(code == KeyEvent.VK_LEFT){
             leftPressed = true;
        }
        if(code == KeyEvent.VK_SPACE){
            if(pausePressed){
                pausePressed=false;
            }
            else{
                pausePressed=true;
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}