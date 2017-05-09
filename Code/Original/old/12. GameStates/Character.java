import java.awt.image.*;
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
    boolean onGround = false;
    boolean jump = false;
    BufferedImage img = null;
    public BufferedImage getImg(){
        return img;
    }
    
    public void setImg(BufferedImage image){
        img = image;
        height = img.getHeight();
        width = img.getWidth();
    }
    
  
}
