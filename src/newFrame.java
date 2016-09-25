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
    ArrayList<BufferedImage> list1 = new ArrayList<BufferedImage>();
    getResources get = new getResources();
    public void createFrame(){
        list1.add(get.getImg("16 PieRat"));
        list1.add(get.getImg("32 PieRat"));
        list1.add(get.getImg("64 PieRat"));
        list1.add(get.getImg("128 PieRat"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setBounds(0,0,screenSize.width, screenSize.height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 640 + 4, 480 + 39);//size is 704x512, insets are 16x39
        window.setFocusable(true);
        window.setFocusTraversalKeysEnabled(false);
        window.setVisible(true);//not invisible
        window.setIconImages(list1);//set the images like the chrome icon n stuff
        window.add(myCanvas);//adds the class down below
        window.addKeyListener(this);//let the window watch for keyboard inputs
        window.toFront();
        window.setResizable(false);
        //window.createBufferStrategy(2);
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

class Canvas extends JPanel implements Runnable{
    GameStateManager gsm;
    JFrame window;
    final int FPS = 60;
    final long OPTIMAL_TIME = 16666667;//1/60th of a second in nanoseconds
    int skippedFPS = 0;
    boolean gameRunning = true;
    boolean pauseKey = false;
    boolean fullScreen;
    Dimension d;
    Dimension nd;
    long entCounter;
    getResources get = new getResources();
    BufferedImage img;
    Val value = new Val();
    int SCREEN_HEIGHT;
    int SCREEN_WIDTH;
    Component c=null;
    public void gameStart(JFrame f){
        window = f;
        c = f.getContentPane();
        gsm = new GameStateManager(window);
        gsm.createArr();
        gsm.init();
        d=new Dimension(640+4,480+39);
        SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        gameRunning = true;
        new Thread(this).start();
    }

    public void run(){
        //every time the timer completes another cycle do this

        while(gameRunning){
            if(pauseKey == false){
                long startTime = System.nanoTime();//sets startTime to the current system time
                //in nano seconds
                updateGame();
                render();
                long elapsedTime = System.nanoTime() - startTime;
                long remainingTime = OPTIMAL_TIME - elapsedTime;
                if(remainingTime < 0){
                    updateGame();//update game logic without drawing to screen
                    skippedFPS++;
                }else{
                    long skipTime = remainingTime / 1000000;//convert remaining time to microseconds
                    int nanoSkipTime = (int)(remainingTime % 1000000);//figure out how many nanoseconds
                    //are remaining after conversion
                    try{Thread.sleep(skipTime,nanoSkipTime);}catch(Exception e){e.printStackTrace();}
                    //sleep for the time leftover
                }
                String name = "Out From Eden " + skippedFPS;
                window.setTitle(name);
            }else{
                try{Thread.sleep(17);}catch(Exception e){e.printStackTrace();}
                //sleep for 1/60th of a second so game isn't constantly rerendering
            }
        }
    }

    public void updateGame(){
        gsm.updateGame();//calls update method of the gamestate manager
    }

    public void render(){
        repaint();//refresh the image with the new image
    }

    protected void paintComponent(Graphics gMain){
        Graphics2D g2 = (Graphics2D)gMain;
        BufferedImage mainImg = new BufferedImage(c.getWidth(),c.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics g = mainImg.getGraphics();
        super.paintComponent(gMain);

        if(fullScreen){
            double/*int*/ screenVal1 = /*(int)*/ value.firstVal;
            double/*int*/ screenVal2 = /*(int)*/ value.secondVal;
            g2.scale(screenVal1,screenVal2);
        }
        gsm.draw(g);//calls draw method of the gamestate manager
        gMain.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
        gMain.drawImage(mainImg,0,0,null);
    }

    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();//^
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == 115&&fullScreen==false&&System.nanoTime()-entCounter > 6000){
            fullScreen = true;
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            //window.setUndecorated(true);
            window.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
            window.setExtendedState( window.getExtendedState()|JFrame.MAXIMIZED_BOTH );
            window.pack();
            SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            value = getScaledDimension(d,Toolkit.getDefaultToolkit().getScreenSize());
            entCounter = System.nanoTime();
        }
        if(code == 115&&fullScreen&&System.nanoTime()-entCounter > 6000){
            fullScreen = false;
            //window.setUndecorated(false);
            window.setPreferredSize(d);
            window.setBounds(30,30,700+16,500+39);
            window.pack();
            SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
            entCounter = System.nanoTime();
        }
        gsm.keyPressed(code);//calls keyPressed method of gamestatemanager
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        gsm.keyReleased(code);//^
    }

    public static Val getScaledDimension(Dimension imgSize, Dimension boundary) {
        double origWidth = imgSize.getWidth()-4;
        double origHeight = imgSize.getHeight()-39;
        double bigWidth = boundary.getWidth();
        double bigHeight = boundary.getHeight();
        double newWidth=0;
        double newHeight=0;
        double scale;
        if(origWidth < bigWidth){
            scale = bigWidth/origWidth;
            newWidth = bigWidth;
            newHeight = origHeight*scale;
        }
        Val val = new Val();
        newWidth = bigWidth/origWidth;
        newHeight = bigHeight/origHeight;
        if(newWidth < newHeight){
            newHeight = newWidth;
        }else{
            newWidth = newHeight;
        }
        val.setVals(newWidth,newHeight);
        return val;
    }
}
