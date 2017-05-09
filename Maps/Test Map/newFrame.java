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
import java.util.ArrayList;
/**
 * Main Frame of Out From Eden
 * 
 * @author Josh
 * @version 11/5/2015
 */
public class newFrame implements KeyListener
{
    JFrame window = new JFrame("Out From Eden");//main window
    int i = 0;
    Canvas myCanvas = new Canvas();//new object of myCanvas class
    ArrayList list1 = new ArrayList<BufferedImage>();
    getResources get = new getResources();
    public void createFrame(){
        list1.add(get.getImg("16 PieRat"));
        list1.add(get.getImg("32 PieRat"));
        list1.add(get.getImg("64 PieRat"));
        list1.add(get.getImg("128 PieRat"));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 704 + 16, 512 + 39);//size is 704x512, insets
        window.setFocusable(true);
        window.setFocusTraversalKeysEnabled(false);
        window.setVisible(true);
        window.setIconImages(list1);
        window.add(myCanvas);
        window.addKeyListener(this);//let the window watch for keyboard inputs
        myCanvas.gameStart(window);//call the gameStart method of myCanvas
                                   //passing this window as a parameter
    }
    
    public void keyPressed(KeyEvent e){
        //when a key is pushed down
        myCanvas.keyPressed(e);//call the same method in myCanvas
    }
    
    public void keyReleased(KeyEvent e){
        //when a key is released
        myCanvas.keyReleased(e);
    }
    
    public void keyTyped(KeyEvent e){
        //bruh idk
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
        Timer timer = new Timer (17,this);//I'm pretty sure this is 60 times
                                          //per second, not sure though why 17
        timer.start();
    }
    
    public void actionPerformed(ActionEvent e){
        //every time the timer completes another cycle do this
        
        updateGame();
        render();
    }
    
    public void updateGame(){
       gsm.updateGame();//calls update method of the gamestate manager
    }
    
    public void render(){
        repaint();//refresh the image with the new image
    }
    
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        gsm.draw(g);//calls draw method of the gamestate manager
    }
    
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();//^
    }
    
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        gsm.keyPressed(code);//^
    }
    
    public void keyReleased(KeyEvent e) {
       int code = e.getKeyCode();
       gsm.keyReleased(code);//^
    }
}
