import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.image.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.lang.Math;
public class newFrame implements KeyListener
{
    JFrame window = new JFrame("Frame");
    int i = 0;
    Canvas myCanvas = new Canvas();
    
    public void createFrame(){
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 700, 500);
        window.setFocusable(true);
        window.setFocusTraversalKeysEnabled(false);
        window.setVisible(true);
        window.add(myCanvas);
        window.addKeyListener(this);
        myCanvas.gameStart(window);
    }
    
    public void keyPressed(KeyEvent e){
        myCanvas.keyPressed(e);
    }
    
    public void keyReleased(KeyEvent e){
        myCanvas.keyReleased(e);
    }
    
    public void keyTyped(KeyEvent e){
        myCanvas.keyTyped(e);
    }
    
    
}

class Canvas extends JPanel implements ActionListener{
    GameStateManager gsm;
    JFrame window;
    public void gameStart(JFrame f){
        window = f;
        gsm = new GameStateManager();
        gsm.createArr();
        gsm.init();
        Timer timer = new Timer (17,this);
        timer.start();
    }
    
    public void actionPerformed(ActionEvent e){
        updateGame();
        render();
    }
    
    public void updateGame(){
       gsm.updateGame();
    }
    
    public void render(){
        repaint();
    }
    
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        gsm.draw(g);
    }
    
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();
    }
    
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        gsm.keyPressed(code);
    }
    
    public void keyReleased(KeyEvent e) {
       int code = e.getKeyCode();
       gsm.keyReleased(code);
    }
}
