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
/**
 * The start menu for Out From Eden, has a start and stop button.
 * 
 * @author Josh Bhattarai
 * @version 8/12/2015
 */
public class startMenu
{
    public static final int HEIGHT = 516;
    public static final int WIDTH = 424;
    public static void main(String[] args){
        getResources get = new getResources();//the class that pulls images from files
        JFrame window = new JFrame("Menu");//the actual frame that the game plays on
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30,30,HEIGHT,WIDTH);
        
        Icon startButtonImg = new ImageIcon(get.getImg("Start"));//create new icon of Start.png
        Icon startButtonOverImg = new ImageIcon(get.getImg("StartOver"));//create new icon of StartOver.png
        Icon stopButtonImg = new ImageIcon(get.getImg("Stop"));//create new icon of Stop.png
        Icon stopButtonOverImg = new ImageIcon(get.getImg("StopOver"));//create new icon of StopOver.png
       
        JButton startButton = new JButton(startButtonImg);//create a new button with the icon of startButtonImg
        startButton.setRolloverIcon(startButtonOverImg);
        JButton stopButton = new JButton(stopButtonImg);
        stopButton.setRolloverIcon(stopButtonOverImg);
        
        window.add(startButton);//add startButton to window
        startButton = setButtonSize(startButton, 140, 115, 300, 50);
        window.add(stopButton);
        stopButton = setButtonSize(stopButton, 121, 133, 300, 200);
        
        
        startButton.addActionListener(new ActionListener(){
            //tells the program to watch for when the user clicks the startButton
            public void actionPerformed(ActionEvent e){
                //this happens when the startButton is clicked
                
                window.dispose();//close window
                gameStart();//call the gameStart method, seen below
            }
        });
        
        stopButton.addActionListener(new Stop());//tells the program to watch for when the stop button is clicked
                                                 //and then call the Stop() method, seen below
        
        window.getContentPane().add(new MyCanvas());//add the myCanvas class to the window
        window.setVisible(true);
    }
    
    public static JButton setButtonSize(JButton button,
    int w, int h, int x, int y){
        //this method takes in a button, and spits a button back out after giving all the correct formatting for
        //its x and y and width and height
        button.setSize(w,h);
        button.setLocation(x,y);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        return button;
    }
    
    public static void gameStart(){
        newFrame f = new newFrame();//create a new object of the newFrame class
        f.createFrame();//call createFrame in the newFrame class
    }
    
    static class Stop implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);//close the window
        }
    }
    
}
class MyCanvas extends JComponent{
    
    public void paint(Graphics g){
       getResources get = new getResources();
      
        BufferedImage titleImg = get.getImg("title");//get the background image
        g.drawImage(titleImg, 0, 0, null);//draw the background image to coordinates 0, 0
    }
    
}
