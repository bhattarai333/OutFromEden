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
import java.util.Arrays;
/**
 * Write a description of class StateOne here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StateOne extends GameState
{
    getResources get = new getResources();
    Character roy = new Character();
    Obstacle hut = new Obstacle();
    Obstacle ground = new Obstacle();
    Obstacle goal = new Obstacle();
    final int GRAVITY = 1;
    int code = 0;
    String currentKey = null;
    boolean leftFacing = false;
    int i = 0;
    int j = 0;
    int playerVal = 0;
    int imgCount = 0;
    ArrayList<BufferedImage> imagesL = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    GameStateManager gsm;
    boolean goalAchieve = false;
    boolean clearScreen = false;
    public void init(GameStateManager gms){
        ground.setImg(get.getImg("ground"));
        hut.setImg(get.getImg("obstacle"));
        roy.setImg(get.getImg("player"));
        goal.setImg(get.getImg("goal"));
        goal.x = 575;
        goal.y = 350;
        ground.x = 0;
        ground.y = 300;
        roy.x = 200;
        hut.y = 300-hut.height;
        gsm = gms;
        imagesL.add(get.getImg("royWalkL1"));
        imagesL.add(get.getImg("royWalkL2"));
        imagesL.add(get.getImg("royWalkL3"));
        imagesL.add(get.getImg("royWalkL4"));
        images.add(get.getImg("royWalk1"));
        images.add(get.getImg("royWalk2"));
        images.add(get.getImg("royWalk3"));
        images.add(get.getImg("royWalk4"));
    }
    
    public void updateGame(){
        royImg();
        fall();
        checkBounds();
        if(clearScreen == true){
         try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gsm.currentState = 2;   
        }
    }
    
    public void draw(Graphics g){
        g.drawImage(roy.getImg(), roy.x, roy.y, null);
        g.drawImage(ground.getImg(), ground.x, ground.y, null);
        g.drawImage(hut.getImg(), hut.x, hut.y, null);
        g.drawImage(goal.getImg(), goal.x, goal.y, null);
        if(goalAchieve == true){
            g.clearRect(0,0,700,500);
            g.drawString("Level 2 coming up",10,10);
            clearScreen = true;
        }
        
    }
    
    
    public void keyPressed(int k){
        code = k;
        if (code == KeyEvent.VK_UP){currentKey = "up";}
        if (code == KeyEvent.VK_DOWN){currentKey = "down";}
        if (code == KeyEvent.VK_LEFT){currentKey = "left";}
        if (code == KeyEvent.VK_RIGHT){currentKey = "right";}
        if (code == KeyEvent.VK_SPACE){currentKey = "space";}
    }
    
    public void keyReleased(int k){
        currentKey = "null";
    }
    
    
    public void checkBounds(){
        if(roy.x < 0){roy.x = 0;} if(roy.y < 0){roy.y = 0;}
        if(roy.x + roy.width > 700){roy.x = 700 - roy.width;}
        if(roy.y + roy.height > 500){roy.velY = 0; roy.y = 500 - roy.height;}
        
        if(
        roy.y + roy.height > ground.y &&
        roy.y + roy.height < ground.y + ground.height &&
        roy.x + roy.width > ground.x && 
        roy.x + roy.width < ground.x + ground.width && roy.jump == false){
            roy.onGround = true;
        }else{
            roy.onGround = false;
        }
        
        if(roy.x < hut.x + hut.width && roy.y + roy.height > hut.y &&
        roy.y < hut.y + hut.height){
            roy.x = hut.x + hut.width;
        }
        Rectangle rect1 
        = new Rectangle(roy.x, roy.y, roy.height, roy.width);
        
        Rectangle rect2 
        = new Rectangle(goal.x, goal.y, goal.height, goal.width);
        int w = 0;
        if(rect1.intersects(rect2)){
            goalAchieve=true;
        }
    }
    
    public void fall(){
        if(roy.onGround == false){
            roy.y += roy.velY;
            j++;
            if(j>19){
                roy.velY += GRAVITY;
                j=0;
            }
        }else{
            roy.velY = 0;
        }
    }
    
    public void royImg(){
        if(currentKey == "left"){
            leftFacing = true;
            roy.x--;
            roy.setImg(imagesL.get(i/16));
            i++;
            if(i == 64){
                i = 1;
            }
        }
        
         if(currentKey == "right"){
            leftFacing = false;
            roy.x++;
            roy.setImg(images.get(i/16));
            i++;
            if(i == 64){
                i = 1;
            }
        }
        
        if(currentKey == "null"){
            i=0;
            if(leftFacing == true){roy.setImg(get.getImg("playerL"));}
            else{roy.setImg(get.getImg("player"));}
        }
        
        if(currentKey == "space" && roy.onGround == true){
            roy.onGround = false;
            roy.velY = -3;
        }
    }
}
