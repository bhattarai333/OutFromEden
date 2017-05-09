import java.awt.image.*;
import java.util.ArrayList;
import java.awt.Rectangle ; 
/**
 * Write a description of class Character here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Character
{
    int x = 0;
    int y = 0;
    int velX = 0;
    int velY = 0;
    int height = 0;
    int width = 0;
    ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    boolean onGround = false;
    boolean jump = false;
    boolean leftFacing = false;
    Rectangle rect = null;
    BufferedImage img = null;
    Obstacle standingOn = new Obstacle();
    boolean snake = false;
    Obstacle standingInFrontOf = new Obstacle();
    public BufferedImage getImg(){
        return img;
    }
    
    public void setImg(BufferedImage image){
        img = image;
        height = img.getHeight();
        width = img.getWidth();
    }
    
  
}
