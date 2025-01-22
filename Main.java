package main;
import javax.swing.JFrame;
public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame("TETRIS");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        //adding game panel to the window
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gp.launchGame();
    }
}
