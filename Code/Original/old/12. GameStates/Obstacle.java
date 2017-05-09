import java.awt.image.*;
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
    public BufferedImage getImg(){
        return img;
    }
    
    public void setImg(BufferedImage image){
        img = image;
        height = img.getHeight();
        width = img.getWidth();
    }
    
 
    
}
