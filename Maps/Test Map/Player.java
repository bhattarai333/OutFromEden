import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.image.*;
import java.awt.Rectangle;
import java.awt.Color;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    int x = 100;
    int y = 100;
    double dx;
    double dy;
    int width;
    int height;
    int nextY;
    int nextX;
    int tempI;
    int tempN;
    int scrollX;
    int scrollY;
    
    boolean jumping;
    boolean falling;
    boolean onGround;
    boolean collide;
    
    double moveSpeed;
    double maxSpeed;
    double maxFallingSpeed;
    double friction;
    double jumpStart;
    double gravity;
    
    Rectangle rect;
    Rectangle upRect;
    Rectangle downRect;
    Rectangle leftRect;
    Rectangle rightRect;
    
    Rectangle nextRect;
    Rectangle nextUpRect;
    Rectangle nextDownRect;
    Rectangle nextLeftRect;
    Rectangle nextRightRect;
    
    boolean upKey;
    boolean downKey;
    boolean leftKey;
    boolean rightKey;
    boolean spaceKey;
    
    boolean snake;
    boolean end;
    int toState;
    
    ArrayList<BufferedImage> royImages;
    BufferedImage img;
    BufferedImage snakeImg;
    int c;
    int n;
    boolean leftFacing;
    boolean scroll;
    
    TileMap tileMap;
    TileInfo tInfo;
    getResources get = new getResources();
    int[][] map;
    Rectangle[][] boundingBox;
    public void init(ArrayList<BufferedImage> arr,TileMap tm){
        royImages = arr;
        c = 0;
        tileMap = tm;
        tInfo = new TileInfo();
        map = tileMap.getMap();
        moveSpeed = .6;
        maxSpeed = 2.1;
        maxFallingSpeed = 12.0;
        friction =.3;
        jumpStart = -12.5;
        gravity = 0.64;
        boundingBox = new Rectangle[tileMap.width*tileMap.height+1][6];
         n = 0;
        for(int row = 0; row < tileMap.height; row++){
            for(int col = 0; col < tileMap.width; col++){
                tInfo.getInfo(map[row][col]);
                boundingBox[n][0] = new Rectangle(col*32,row*32,32,32);
                boundingBox[n][1] = new Rectangle(col*32,row*32,32,2);
                boundingBox[n][2] = new Rectangle(col*32,row*32+30,32,2);
                boundingBox[n][3] = new Rectangle(col*32,row*32,2,32);
                boundingBox[n][4] = new Rectangle(col*32+30,row*32,2,32);
                boundingBox[n][5] = new Rectangle(map[row][col],0,0,0);
                n++;
            }
        }
        n=0;
        falling = true;
        x = 704/2;
        y = 300;
        nextX = 704/2;
        nextY = 300;
        snakeImg = get.getImg("snake");
        end = false;
    }
    
    public void setImg(BufferedImage image){
        img = image;
        height = img.getHeight()-1;
        width = img.getWidth();
        rect = new Rectangle((int)x,(int)y,(int)width,(int)height);
        upRect = new Rectangle((int)x+3,(int)y,(int)width-6,2);
        downRect = new Rectangle((int)x+3,((int)y+(int)height)-3,(int)width-6,2);
        leftRect = new Rectangle((int)x,(int)y+3,2,(int)height-6);
        rightRect = new Rectangle((int)x+(int)width -2,(int)y+3, 2,(int)height-6);
    }
    
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(img,x,y+1,null);
        if(snake){
            g.drawImage(snakeImg,x,y-30,null);
        }
    }
    
    public void update(boolean u,boolean d, boolean l, boolean r,boolean s){
        upKey = u;
        downKey = d;
        leftKey = l;
        rightKey = r;
        spaceKey = s;
        changeImg();
        move();
        checkBounds();
        if(scroll){
            scroll();
        }else{
            x = nextX;
            y = nextY;
        }
        checkEnd();
    }
    
    public void checkEnd(){
        if(snake == true && downKey == true){
            tInfo.getInfo((int)boundingBox[tempN][5].getX());
            toState = tInfo.state;
            end = true;
        }
    }
    
    public void scroll(){
        scrollX += x - nextX;
        scrollY += y -nextY;
        nextX = x;
        nextY = y;
        
        for(int row = 0; row < tileMap.height; row++){
            for(int col = 0; col < tileMap.width; col++){
                tInfo.getInfo(map[row][col]);
                boundingBox[n][0] = new Rectangle((col*32) + scrollX,(row*32)+scrollY,32,32);
                boundingBox[n][1] = new Rectangle((col*32)+scrollX,(row*32)+scrollY,32,2);
                boundingBox[n][2] = new Rectangle((col*32)+scrollX,(row*32+30)+scrollY,32,2);
                boundingBox[n][3] = new Rectangle((col*32)+scrollX,(row*32)+scrollY,2,32);
                boundingBox[n][4] = new Rectangle((col*32+30)+scrollX,(row*32)+scrollY,2,32);
                boundingBox[n][5] = new Rectangle(map[row][col],0,0,0);
                n++;
            }
        }
        n=0;
    }
    
    public void checkBounds(){
        nextRect = new Rectangle(nextX,nextY,(int)width,(int)height);
        nextUpRect = new Rectangle(nextX+3,nextY,(int)width-6,2);
        nextDownRect = new Rectangle(nextX+3,(nextY+(int)height)-3,(int)width-6,2);
        nextLeftRect = new Rectangle(nextX,nextY+3,2,(int)height-6);
        nextRightRect = new Rectangle(nextX+(int)width -2,nextY+3, 2,(int)height-6);
        
        for(int i = 0; i < (tileMap.width) * tileMap.height; i++){
            int ID = (int)boundingBox[i][5].getX();
            tInfo.getInfo(ID);
            
            if(rect.intersects(boundingBox[i][0]) && tInfo.flag == 1){
                snake = true;
                tempN = i;
            }
            
            if(!(rect.intersects(boundingBox[tempN][0]))){
                snake = false;
            }
            
            if(nextX < x && nextLeftRect.intersects(boundingBox[i][4])&&
            !(tInfo.solid == 0)){
                //roy is moving left
                nextX = (int)(((boundingBox[i][4].getX() + 2) - x)+x);
                collide = true;
            }
            
            if(nextX > x && nextRightRect.intersects(boundingBox[i][3])&&
            !(tInfo.solid == 0)){
                //royo is moving right
                nextX = (int)((boundingBox[i][3].getX()) - (x+width)) + (x);
                collide = true;
            }
            
            if(nextY>y && nextRect.intersects(boundingBox[i][1]) && 
            onGround == false && x+width >= boundingBox[i][3].getX() + 1
            && x <= boundingBox[i][3].getX()+31
            &&!(tInfo.solid == 0)){
                //roy is falling
                nextY = (int)(boundingBox[i][1].getY() - (y + height)) + y;
                onGround = true;
                tempI = i;
            }
            
            if(nextY < y && nextRect.intersects(boundingBox[i][2]) &&
            onGround == false && x+width >= boundingBox[i][3].getX() + 1
            && x <= boundingBox[i][3].getX()+31
            &&!(tInfo.solid == 0)){
                //roy is jumping
                nextY = (int)(((boundingBox[i][0].getY() + 32) + y)-y);
            }
            
            if(onGround){
                if(x <= boundingBox[tempI][0].getX()+31 && 
                x+width >= boundingBox[tempI][0].getX() + 1){
                }else{
                    onGround = false;
                }
            }
            
            if(onGround == false){
                falling = true;
            }else{
                jumping = false;
                falling = false;
            }
            
        }
        if(collide){
            //dx=0;
            collide =false;
        }
    }
    
    public void move(){
        if(leftKey){
            dx-=moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        }
        if(rightKey){
            dx+=moveSpeed;
            if(dx>maxSpeed){
                dx = maxSpeed;
            }
        }
        if(rightKey == false && leftKey == false){
            if(dx > 0){
                dx-=friction;
                if(dx <0){
                    dx = 0;
                }
            }
            if(dx<0){
                dx+=friction;
                if(dx > 0){
                    dx = 0;
                }
            }
        }
        
        if(spaceKey && falling == false){
            jumping = true;
        }
        
        if(jumping){
            dy = jumpStart;
            onGround = false;
            falling = true;
            jumping = false;
        }
        else
        if(falling){
            dy+=gravity;
            if(dy > maxFallingSpeed){
                dy= maxFallingSpeed;
            }
        }else{
            onGround = true;
            dy = 0;
        }
        
        nextX += (int)dx;
        nextY += (int)dy;
    }
    
    public void changeImg(){
        if(leftKey){
            setImg(royImages.get(c/16));
            c++;
            if(c>63){
                c=1;
            }
            leftFacing = true;
        }
        if(rightKey){
            setImg(royImages.get(9+(c/16)));
            c++;
            if(c>63){
                c=1;
            }
            leftFacing = false;
        }
        
        if(leftKey == false && rightKey == false){
            c=0;
            
            if(leftFacing == true){
                setImg(royImages.get(8));}
                else{
                setImg(royImages.get(17));}
        }
    }
}
