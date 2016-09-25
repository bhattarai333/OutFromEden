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
        tiles = get.cutSheet("tiles",8,8);
    }

    public BufferedImage getImg(int in){
        int i = 0;
        if(in==0){
            img = null;
            return img;
        }else{
            i = in-1   ;
        }
        img = tiles.get(i);
        return img;
    }

    public void getInfo(int ID){
        //0 is transparent 1 is solid 2 is platform
        if(ID==25||ID==26||ID==27||ID==35||ID==0){
            solid=0;
        }else if(ID == 61 || ID == 62|| ID == 63|| ID == 64){
            solid = 2;
        }
        else{
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

    public int getSolid(int ID){
        if(ID==25||ID==26||ID==27||ID==35||ID==0){
            solid=0;
        }else if(ID == 61 || ID == 62|| ID == 63|| ID == 64){
            solid = 2;
        }
        else{
            solid=1;
        }
        return solid;
    }
}
