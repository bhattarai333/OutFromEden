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
public class StateTwo extends GameState
{
    final int SCREEN_WIDTH = 704;
    final int SCREEN_HEIGHT = 512;
    final int TILE_WIDTH = 32;
    final int TILE_HEIGHT = 32;
    //these might come in handy later

    int code = 0;
    boolean upKey = false;
    boolean downKey = false;
    boolean leftKey = false;
    boolean rightKey = false;
    boolean spaceKey = false;
    boolean sprintKey = false;
    boolean medAttack = false;
    boolean debug = false;
    boolean scroll = false;

    Player roy1 = new Player();//our player
    getResources get = new getResources();//pull resources from folders
    TileMap tm = new TileMap();//the map object
    int[][] map = null;//the actual map
    int mapWidth = 0;
    int mapHeight = 0;
    TileInfo tile = new TileInfo();
    ArrayList<BufferedImage> royImages = null;
    GameStateManager gsm;
    Rectangle boundingBoxes[][];
    ArrayList<MapObject> objects = new ArrayList<MapObject>();
    public void init(GameStateManager gms){
        gsm = gms;
        tm.TileMap("map2");
        mapWidth = tm.width;
        mapHeight = tm.height;
        map = tm.map;
        royImages = get.cutSheet("roySheet",9,2);
        tile.init();
        int xc = 704/2;
        int yc = 300;
        roy1.init(royImages,tm,xc,yc);//create new player
        scroll = true;
        boundingBoxes = roy1.boundingBox;

        roy1.health = gsm.go.royHealth;
        int d =1 ;
        if(d>objects.size()){
            objects.add(new Obj());
        }
        objects.add(new Door(
                boundingBoxes,tm,280,501,1,"doorSheet"
        ));

        for(int i = 0; i < objects.size();i++){
            //update all the objects and pass variables into them
            objects.get(i).p1 = roy1;
        }
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        for(int row = 0;row<mapHeight;row++){
            for(int col = 0;col<mapWidth;col++){
                int rc = map[row][col];
                BufferedImage img = tile.getImg(rc);
                if(img == null){
                    //if tile ID is 0 there's no image so nothing to draw
                }else{
                    //draw the tiles and compensate for scrolling by adding scrollX and scrollY
                    g.drawImage(tile.getImg(rc),(32*col)+roy1.scrollX,(32*row)+roy1.scrollY,null);
                }
            }
        }

        for(int i = 0; i < objects.size();i++){
            //call draw method of each object
            objects.get(i).draw(g);
        }
        roy1.draw(g);
        if(debug){
            //draws helpful stuff
            g.setColor(Color.pink);
            g2.draw(roy1.upRect);
            //g2.draw(objects.get(0).nextUpRect);
            g.setColor(Color.white);
            g2.draw(roy1.downRect);
            //g2.draw(objects.get(0).nextDownRect);
            g.setColor(Color.red);
            g2.draw(roy1.leftRect);
            //g2.draw(objects.get(0).nextLeftRect);
            g.setColor(Color.yellow);
            g2.draw(roy1.rightRect);
            //g2.draw(objects.get(0).nextRightRect);
            g.setColor(Color.black);
            g.drawString("X: " + (roy1.x-roy1.scrollX) + " Y: " + (roy1.y-roy1.scrollY),roy1.x,roy1.y-10);

        }
    }

    public void keyPressed(int k){
        code = k;
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W){upKey = true;}
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){downKey = true;}
        if (code == KeyEvent.VK_LEFT ||code==KeyEvent.VK_A){leftKey = true;rightKey = false;}
        if (code == KeyEvent.VK_RIGHT||code==KeyEvent.VK_D){rightKey = true;leftKey = false;}
        if (code == KeyEvent.VK_SPACE){spaceKey = true;}
        if(code == KeyEvent.VK_B){
            if(debug == false){
                debug = true;
            }else if(debug == true){
                debug = false;
            }
        }
        if(code == KeyEvent.VK_L){
            if(scroll == false){
                scroll = true;
            }else if(scroll == true){
                scroll = false;
            }

        }

        if(code == KeyEvent.VK_F){
            if(sprintKey == false){
                sprintKey = true;
            }else if(sprintKey == true){
                sprintKey = false;
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
        roy1.update(upKey,downKey,leftKey,rightKey,spaceKey,sprintKey,medAttack);
        for(int i = 0; i < objects.size();i++){
            //update all the objects and pass variables into them
            objects.get(i).update();
            if(objects.get(i).dead == true){
                objects.remove(i);
            }

        }



        if(roy1.end){
            gsm.end(roy1);
        }
    }




}
