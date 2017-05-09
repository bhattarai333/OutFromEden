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
    double x = 0;
    double y = 0;
    double nextX = 0;
    double nextY = 0;
    double velX = 0;
    double velY = 0;
    double height = 0;
    double width = 0;
    ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    boolean onGround = false;
    boolean jump = false;
    boolean leftFacing = false;
    boolean snake = false;
    boolean skipX = false;
    int tempI = 0;
    int tempJ = 0;
    Rectangle rect = null;
    
    Rectangle upRect = null;
    Rectangle downRect = null;
    Rectangle leftRect = null;
    Rectangle rightRect = null;
    
    Rectangle nextUpRect = null;
    Rectangle nextDownRect = null;
    Rectangle nextLeftRect = null;
    Rectangle nextRightRect = null;
    
    Rectangle nextRect = null;
    BufferedImage img = null;
    Tile standingOnTile = new Tile();
    Tile standingInFrontOfTile = null;
    final double GRAVITY = 1;
    double friction = 1;
    
    public BufferedImage getImg(){
        return img;
    }
    
    public void setImg(BufferedImage image){
        img = image;
        height = img.getHeight()-1;
        width = img.getWidth();
        rect = new Rectangle((int)x,(int)y,(int)width,(int)height);
        upRect = new Rectangle((int)x+3,(int)y,(int)width-6,2);
        downRect = new Rectangle((int)x+3,((int)y+(int)height)-2,(int)width-6,2);
        leftRect = new Rectangle((int)x,(int)y+3,2,(int)height-6);
        rightRect = new Rectangle((int)x+(int)width -2,(int)y+3, 2,(int)height-6);
        
    }
    
    public void update(){
        velY += GRAVITY;
        if(onGround == true && x + width >= standingOnTile.x 
        && x <= standingOnTile.x+32){
            velY -= GRAVITY;
        }else{
            onGround = false;
            standingOnTile = null;
        }
        
        if(velX > 0){
            velX -= friction;
        }else if(velX<0){
            velX += friction;
        }
        
        if(onGround == true && jump == true){
            //y -=1;
            velY = -10;
            jump = false;
            onGround = false;
            standingOnTile = null;
        }
        
        nextY = y + velY;
        nextX = x + velX;
        nextRect = new Rectangle((int)nextX, (int)nextY, (int)width, (int)height);
        nextUpRect = new Rectangle((int)nextX,(int)nextY,(int)width,2);
        nextDownRect = new 
        Rectangle((int)nextX,((int)nextY+(int)height)-2,(int)width,2);
        nextLeftRect = new Rectangle((int)nextX,(int)nextY,2,(int)height);
        nextRightRect = new 
        Rectangle((int)nextX+(int)width -2,(int)nextY, 2,(int)height);
    }
    
    
}
