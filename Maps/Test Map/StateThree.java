import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.image.*;
import java.awt.Color;
/**
 * Template to copy and paste new states from
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StateThree extends GameState
{
    final int SCREEN_WIDTH = 704;
    final int SCREEN_HEIGHT = 512;
    final int TILE_WIDTH = 32;
    final int TILE_HEIGHT = 32;
    int tileW = (int)SCREEN_WIDTH / TILE_WIDTH;
    int tileH = (int)SCREEN_HEIGHT / TILE_HEIGHT;
    int w = 0;
    int h = 0;
    getResources get = new getResources();
    boolean scroll = true;
    
    int[][]layout = new int[][]{//the level map
    //0  0  0  0  0  0  0  0  0  1  1  1  1  1  1  1  1  1  1  2  2  2
    //1  2  3  4  5  6  7  8  9  0  1  2  3  4  5  6  7  8  9  0  1  2     
    { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },//1
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },//2
    { 2, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2 },//3
    { 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2 },//4
    { 2, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 2 },//5
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },//6
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },//7
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },//8
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },//9
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },//10
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },//11
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2 },//12
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2 },//13
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 2 },//14
    { 2, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 1, 1, 1, 0, 0, 4, 0, 3, 2 },//15
    { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },//16
    };
    double scrollX = 0;
    double scrollY = 0;
    int tempX = 0;
    int tempY = 0;
    int i = 0;
    Tile[][] map = null;
    GameStateManager gsm = null;
    Character roy = new Character();
    boolean upKey = false;
    boolean downKey = false;
    boolean leftKey = false;//keyboard stuff
    boolean rightKey = false;
    boolean spaceKey = false;
    boolean debug = false;
    int code = 0;
    ArrayList<BufferedImage> royImages = new ArrayList<BufferedImage>();
    BufferedImage snakeImg = null;
    public void init(GameStateManager gms){
        h = layout.length;
        gsm = gms;
        w = layout[0].length;
        map = new Tile[h][w];
        for(int i = 0; i < h; i++){
           for(int j = 0; j < w; j++){
               Tile tile = new Tile();
               tile.init(layout[i][j]);//creates a 2d array of tiles
               map[i][j] = tile;
            }
        }
        for(int i = 0; i < h; i++){
           for(int j = 0; j < w; j++){
               tempX = j * 32;
               tempY = i * 32;
               map[i][j].x = tempX;
               map[i][j].y = tempY;
                                        //assigns the x and y values and the bounding boxes of tiles
               map[i][j].rect = new 
               Rectangle((int)map[i][j].x, (int)map[i][j].y,32,32);
               map[i][j].leftRect = new
               Rectangle((int)map[i][j].x, (int)map[i][j].y, 2, 32);
               map[i][j].rightRect = new
               Rectangle((int)map[i][j].x + 30, (int)map[i][j].y, 2, 32);
               map[i][j].upRect = new
               Rectangle((int)map[i][j].x, (int)map[i][j].y, 32, 2);
               map[i][j].downRect = new
               Rectangle((int)map[i][j].x, (int)map[i][j].y + 30, 32, 2);
            }
        }
        royImages = get.cutSheet("roySheet",25,65,9,2);//assign all the roy images from sprite sheet
        roy.setImg(get.getImg("player"));
        roy.x = 704/2;
        roy.y = 300;
        snakeImg = get.getImg("snake");
    }
    
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                g.drawImage(map[i][j].img,(int)map[i][j].x,(int)map[i][j].y,null);//draw the tiles
             }
         }
         if(roy.snake == true){
             g.drawImage(snakeImg, (int)roy.x,(int)roy.y-30,null);//draw the exlamation mark
            }
        g.drawImage(roy.img,(int)roy.x,(int)roy.y+1,null);//draw roy
        if(debug == true){
            g.setColor(Color.red);
            g.drawRect((int)map[14][14].x, (int)map[14][14].y, 2, 32);
            g.setColor(Color.yellow);
            g.drawRect((int)map[14][14].x + 30, (int)map[14][14].y, 2, 32);
            g.setColor(Color.white);
            g.drawRect((int)map[14][14].x, (int)map[14][14].y, 32, 2);
            g.setColor(Color.blue);
            g.drawRect((int)map[14][14].x, (int)map[14][14].y + 30, 32, 2);
                                                                                //draw the bounding boxes
                                                                                //if debug is true
            g.setColor(Color.green);
            g2.draw(roy.upRect);
            g.setColor(Color.orange);
            g2.draw(roy.downRect);
            g.setColor(Color.white);
            g2.draw(roy.leftRect);
            g.setColor(Color.pink);
            g2.draw(roy.rightRect);
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
        roy.update();
        checkBounds();
        changeImg();
        checkEnd();
        scroll();
    }
    
    public void scroll(){
        if(scroll){   
            scrollX = roy.x - roy.nextX;
            scrollY = roy.y - roy.nextY;
            for(int i = 0; i < h; i++){
                for(int j = 0; j < w; j++){
                    map[i][j].x += scrollX;
                    map[i][j].y += scrollY;
               
                    map[i][j].rect = new 
                    Rectangle((int)map[i][j].x, (int)map[i][j].y,32,32);
                    map[i][j].leftRect = new
                    Rectangle((int)map[i][j].x, (int)map[i][j].y, 2, 32);
                    map[i][j].rightRect = new
                    Rectangle((int)map[i][j].x + 30, (int)map[i][j].y, 2, 32);
                    map[i][j].upRect = new
                    Rectangle((int)map[i][j].x, (int)map[i][j].y, 32, 2);
                    map[i][j].downRect = new
                    Rectangle((int)map[i][j].x, (int)map[i][j].y + 30, 32, 2);
                }
            }
        }else{
            roy.x = roy.nextX;
            roy.y = roy.nextY;
        }
    }   
    public void checkEnd(){
        if(roy.snake == true && downKey == true){
            gsm.setState(map[roy.tempI][roy.tempJ].state);
        }
    }
   
    public void changeImg(){
        if(leftKey == true){
            roy.setImg(royImages.get(i/16));
            i++;
            if(i>63){
                i=1;
            }
        }
        if(rightKey == true){
            roy.setImg(royImages.get(9+(i/16)));
            i++;
            if(i>63){
                i=1;
            }
        }
        
        if(leftKey == false && rightKey == false){
            i=0;
            
            if(roy.leftFacing == true){
                roy.setImg(royImages.get(8));}
                else{
                roy.setImg(royImages.get(17));}
        }
    }
    
    public void checkBounds(){
        if(upKey == true){}
        if(downKey == true){}
        if(leftKey == true){roy.velX = -3;}
        if(rightKey == true){roy.velX = 3;}
        
        if(spaceKey == true && roy.onGround == true){roy.jump = true;}
        
        for(int i = 0; i < h; i++){
           for(int j = 0; j < w; j++){
                
                if(roy.rect.intersects(map[i][j].rect) && map[i][j].flag == 1){
                    roy.snake = true;
                    roy.tempI = i;
                    roy.tempJ = j;
                }
                
                if(!(roy.rect.intersects(map[roy.tempI][roy.tempJ].rect))){
                    roy.snake = false;
                }
                
                if(roy.nextY > roy.y && roy.nextX > roy.x){
                    //roy is falling and moving right
                    if(roy.nextDownRect.intersects(map[i][j].upRect) &&
                    !(map[i][j].solid == 0) && roy.onGround == false 
                    && roy.x + roy.width >= map[i][j].x + 1
                    && roy.x <= map[i][j].x+31 && 
                    roy.nextRightRect.intersects(map[i][j].leftRect) && 
                    !(map[i][j].solid == 0) && roy.skipX == false)
                    {
                        System.out.println("priyank");
                        roy.nextY = ((map[i][j].y - (roy.y + roy.height)) + roy.y);//-1;
                        roy.velY = 0;
                        roy.onGround = true;
                        roy.standingOnTile = map[i][j];
                        //roy.skipX = true;
                    }
                }
                
                if(roy.nextY > roy.y){
                    //roy is falling
                    
                    if(roy.nextRect.intersects(map[i][j].upRect) &&
                    !(map[i][j].solid == 0) && roy.onGround == false 
                    && roy.x + roy.width >= map[i][j].x + 1
                    && roy.x <= map[i][j].x+31){
                        roy.nextY = ((map[i][j].y - (roy.y + roy.height)) + roy.y);//-1;
                        roy.velY = 0;
                        roy.onGround = true;
                        roy.standingOnTile = map[i][j];
                    }
                }
                
                if(roy.nextY < roy.y){
                    //roy is going up
                    if(roy.nextRect.intersects(map[i][j].downRect) &&
                    !(map[i][j].solid == 0) && roy.onGround == false
                    &&roy.x + roy.width >= map[i][j].x + 1
                    && roy.x <= map[i][j].x+31){
                        roy.nextY = (((map[i][j].y + 32) + roy.y)-roy.y);//+1;
                        roy.velY = 0;
                    }
                }
                
                if(roy.nextX > roy.x){
                   //roy is moving right
                   if(roy.nextRightRect.intersects(map[i][j].leftRect) && 
                   !(map[i][j].solid == 0) && roy.skipX == false){
                       roy.nextX = ((map[i][j].x - (roy.x + roy.width)) + roy.x);//-1;
                       roy.velX = 0;
                    }
                }
                
                if(roy.nextX < roy.x){
                    //roy is moving left
                    if(roy.nextLeftRect.intersects(map[i][j].rightRect) &&
                    !(map[i][j].solid == 0) && roy.skipX == false){
                        roy.nextX = (((map[i][j].x + 32) + roy.x)-roy.x);//+1;
                        roy.velX = 0;
                    }
                }
                
                
            }
        }
        roy.skipX = false;
    }
    
    
}
