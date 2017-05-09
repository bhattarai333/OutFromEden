import java.awt.image.*;
import java.awt.Rectangle ; 
/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile
{
    getResources get = new getResources();
    int id = 0;
    BufferedImage img = null;
    int solid = 0;
    int flag = 0;
    int state = 0;
    double x = 0;
    double y = 0;
    double scrollValX = 0;
    double scrollValY = 0;
    Rectangle rect = null;
    Rectangle upRect = null;
    Rectangle downRect = null;
    Rectangle leftRect = null;
    Rectangle rightRect = null;
    
    public void init(int ID){
        id = ID;
        if(id == 0){
            img = get.getImg("blackTile");
            img = img.getSubimage(0,0,32,32);
            solid = 0;
        }
        if(id == 1){
            img = get.getImg("grayTile");
            img = img.getSubimage(0,0,32,32);
            solid = 1;
        }
        if(id == 2){
            img = get.getImg("brickTile");
            img = img.getSubimage(0,0,32,32);
            solid = 1;
        }
        if(id == 3){
            img = get.getImg("finishTile");
            img = img.getSubimage(0,0,32,32);
            solid = 0;
            flag = 1;
            state = 2;
        }
        if(id == 4){
            img = get.getImg("finishTile2");
            img = img.getSubimage(0,0,32,32);
            solid = 0;
            flag = 1;
            state = 3;
        }
        if(id == 5){
            img = get.getImg("finishTile3");
            img = img.getSubimage(0,0,32,32);
            solid = 0;
            flag = 1;
            state = 4;
        }
    }
    
}
