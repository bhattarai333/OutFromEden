import java.awt.image.*;
import java.util.ArrayList;
/**
 * Write a description of class TileInfo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TileInfo
{
    getResources get = new getResources();
    BufferedImage img;
    ArrayList<BufferedImage> tiles;
    int solid = 0;
    int flag;
    int state=0;
    public void init(){
        tiles = get.cutSheet("tiles",32,32,8,8);
    }
    
    public BufferedImage getImg(int in){
        int i = 0;
        if(in==0){
            i = 0;
        }else{
          i = in-1   ;
        }
        img = tiles.get(i);
        return img;
    }
    
    public void getInfo(int ID){
        //26 27 37
        if(ID==26||ID==27||ID==35){
            solid=0;
        }else{
            solid=1;
        }
        
        if(ID == 27 || ID == 35){
            flag = 1;
        }else{
            flag = 0;
        }
        
        if(ID == 27){
            state = 2;
        }else if(ID == 35){
            state = 3;
        }
        
    }
}
