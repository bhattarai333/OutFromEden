import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.image.*;
import java.awt.Color;
import java.io.FileReader;
import java.io.File;
/**
 * Template to copy and paste new states from
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StateFour extends GameState
{
    final int SCREEN_WIDTH = 704;
    final int SCREEN_HEIGHT = 512;
    final int TILE_WIDTH = 32;
    final int TILE_HEIGHT = 32;
    
    int code = 0;
    boolean upKey = false;
    boolean downKey = false;
    boolean leftKey = false;
    boolean rightKey = false;
    boolean spaceKey = false;
    boolean debug = false;
    boolean scroll = false;
    
    Player roy1 = new Player();
    Character roy = new Character();
    getResources get = new getResources();
    TileMap tm = new TileMap();
    int[][] map = null;
    int mapWidth = 0;
    int mapHeight = 0;
    TileInfo tile = new TileInfo();
    ArrayList<BufferedImage> royImages = null;
    GameStateManager gsm;
    public void init(GameStateManager gms){
        gsm = gms;
        tm.TileMap("map");
        mapWidth = tm.width;
        mapHeight = tm.height;
        map = tm.map;
        royImages = get.cutSheet("roySheet",25,65,9,2);
        tile.init();
        roy1.init(royImages,tm);
        scroll = true;
    }
    
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        for(int row = 0;row<mapHeight;row++){
            for(int col = 0;col<mapWidth;col++){
                int rc = map[row][col];
                g.drawImage(tile.getImg(rc),(32*col)+roy1.scrollX,(32*row)+roy1.scrollY,null);
            }
        }
        roy1.draw(g);
        
        if(debug){
            g.setColor(Color.pink);
            g2.draw(roy1.upRect);
            g.setColor(Color.white);
            g2.draw(roy1.downRect);
            g.setColor(Color.red);
            g2.draw(roy1.leftRect);
            g.setColor(Color.yellow);
            g2.draw(roy1.rightRect);
        }
    }
    
    public void keyPressed(int k){
        code = k;
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W){upKey = true;}
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){downKey = true;}
        if (code == KeyEvent.VK_LEFT ||code==KeyEvent.VK_A){leftKey = true;rightKey = false;roy.leftFacing = true;}
        if (code == KeyEvent.VK_RIGHT||code==KeyEvent.VK_D){rightKey = true;leftKey = false;roy.leftFacing = false;}
        if (code == KeyEvent.VK_SPACE){spaceKey = true;}
        if(code == KeyEvent.VK_B){
            if(debug == false){
                debug = true;
            }else if(debug == true){
                debug = false;
            }
        }
        if(code == KeyEvent.VK_C){
            if(scroll == false){
                scroll = true;
            }else if(scroll == true){
                scroll = false;
            }
            
        }
    }
    
    public void keyReleased(int k){
        code = k;
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W){upKey = false;}
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){downKey = false;}
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A){leftKey = false;}
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D){rightKey = false;}
        if (code == KeyEvent.VK_SPACE){spaceKey = false;}
    }
    
    public void updateGame(){
        roy1.scroll = scroll;
        roy1.update(upKey,downKey,leftKey,rightKey,spaceKey);
        
        if(roy1.end){
            gsm.setState(roy1.toState);
        }
    }
    
    
    
    
}
