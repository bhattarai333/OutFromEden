import java.awt.image.*;
import java.awt.Rectangle ;
/**
 * Write a description of class Ground here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Obstacle
{
    int x = 0;
    int y = 0;
    int height = 0;
    int width = 0;
    BufferedImage img = null;
    Rectangle rect = null;
    boolean leftWall = true;
    boolean rightWall = true;
    boolean topWall = true;
    boolean bottomWall = true;
    boolean leftMoving = false;
    boolean isDoor = false;
    int toState = 0;
    public BufferedImage getImg(){
        return img;
    }
    
    public void setImg(BufferedImage image){
        img = image;
        height = img.getHeight();
        width = img.getWidth();
    }
    
 
    
}
