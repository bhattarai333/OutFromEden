import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.image.*;
import java.awt.Color;
/**
 * Write a description of class NPC here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Door extends MapObject
{
    getResources get = new getResources();
    public void  hit(int dmg){

    }
    Door(Rectangle[][] R,
         TileMap tm,int xc,int yc,int st,String path1){
        path = path1;
        init(R,tm,xc,yc,st);
        flag = true;
    }
    public void init(Rectangle[][] R,
                     TileMap tm,int xc,int yc, int st){
        images = get.cutSheet(path,3,1);
        img = images.get(0);

        height = img.getHeight();
        width = img.getWidth();
        state = st;
        relativeX = xc;
        relativeY = yc;
        nextX = xc;
        nextY = yc;
        x=xc;
        y=yc;
        rect = new Rectangle((int)x,(int)y,(int)width,(int)height);
        nextRect = rect;

    }

    public void setImg(BufferedImage image){
        img = image;
    }

    public void draw(Graphics g){
        g.drawImage(img,x,y,null);
    }

    public void update(){
        playerStuff();
        rect = new Rectangle(x,y,width,height);
        nextRect = rect;
        x = relativeX + scrollX;
        y = relativeY + scrollY;



        nextX = (int)(x+dx);
        nextY = (int)(y+dy);





        x = nextX;
        y = nextY;

        relativeX = x -scrollX;
        relativeY = y - scrollY;
    }

    public void playerStuff(){
        scrollX = p1.scrollX;
        scrollY = p1.scrollY;
        boundingBox = p1.boundingBox;
        targetX = p1.x;
        targetY = p1.y;

        if(p1.rect.intersects(rect)){
            p1.snake = true;
        }
        if(p1.rect.intersects(rect)&&p1.snake && p1.downKey&&p1.traveled == false){

            p1.toState = state;
            p1.traveled = true;
            p1.end = true;
        }
    }
}
