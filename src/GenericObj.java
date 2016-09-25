import java.awt.Graphics;
import java.awt.Rectangle;
/**
 * Write a description of class Obj here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GenericObj extends MapObject
{
    getResources get = new getResources();
    GenericObj(Rectangle[][] R,TileMap tm, int x1, int y1, int st,String path1){
        path = path1;
        init(R,tm,x1,y1,st);
    }
    public void  hit(int dmg){}
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
    public void init(Rectangle[][]r,TileMap mp,int x1,int y1, int z1){
        img = get.getImg(path);
        relativeX = x1;
        relativeY = y1;
        nextX = x1;
        nextY = y1;
        x = x1;
        y = y1;
        height = img.getHeight();
        width = img.getWidth();
        rect = new Rectangle((int)x,(int)y,(int)width,(int)height);
    }
    public void playerStuff(){
        scrollX = p1.scrollX;
        scrollY = p1.scrollY;
        boundingBox = p1.boundingBox;
    }
}
