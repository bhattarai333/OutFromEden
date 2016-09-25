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
    int tempC;
    int scrollX;
    int scrollY;
    int dmg;
    int xd;

    boolean traveled;
    boolean jumping;
    boolean falling;
    boolean onGround;
    boolean collide;
    boolean dead;
    boolean vulnerable;

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

    Rectangle hitBox;

    boolean upKey;
    boolean downKey;
    boolean leftKey;
    boolean rightKey;
    boolean spaceKey;
    boolean medAttack;
    boolean sprintKey;
    boolean attacking;
    boolean hit;

    boolean movable;
    boolean snake;
    boolean end;
    int toState;

    ArrayList<BufferedImage> royImages;
    ArrayList<BufferedImage> combatImg;
    BufferedImage img;
    BufferedImage snakeImg;
    BufferedImage snakeImg6;
    BufferedImage snakeImg5;
    BufferedImage snakeImg4;
    BufferedImage snakeImg3;
    BufferedImage snakeImg2;
    int a;
    int b;
    int c;
    int e;
    int f;
    int h=20;
    int l;
    int n;
    int o;
    boolean leftFacing;
    boolean scroll;
    int health;
    int maxHealth;
    boolean blinking;

    TileMap tileMap;
    TileInfo tInfo;
    getResources get = new getResources();
    int[][] map;
    Rectangle[][] boundingBox;
    public void init(ArrayList<BufferedImage> arr,TileMap tm,int xc,int yc){
        royImages = arr;
        combatImg = get.cutSheet("roySheet2",3,2);
        vulnerable = false;
        attacking = false;
        movable = true;
        a=0;
        c = 0;
        maxHealth = 50;
        tileMap = tm;
        tInfo = new TileInfo();
        map = tileMap.getMap();
        moveSpeed = .6;
        maxSpeed = 2.6;
        maxFallingSpeed = 12.0;
        friction =.3;
        jumpStart = -12.5;
        gravity = 0.64;
        boundingBox = new Rectangle[tileMap.width*tileMap.height+1][6];
        hitBox = new Rectangle(-1000,-1000,1,1);
        n = 0;
        for(int row = 0; row < tileMap.height; row++){
            for(int col = 0; col < tileMap.width; col++){
                tInfo.getInfo(map[row][col]);
                boundingBox[n][0] = new Rectangle(col*32,row*32,32,32);
                boundingBox[n][1] = new Rectangle(col*32,row*32,32,15);
                boundingBox[n][2] = new Rectangle(col*32,row*32+15,32,15);
                boundingBox[n][3] = new Rectangle(col*32,row*32,15,32);
                boundingBox[n][4] = new Rectangle(col*32+15,row*32,15,32);
                boundingBox[n][5] = new Rectangle(map[row][col],0,0,0);
                n++;
            }
        }

        n=0;
        falling = true;
        x = xc;
        y = yc;
        nextX = xc;
        nextY = yc;
        end = false;
        img = arr.get(0);
        height = img.getHeight()-1;
        width = img.getWidth();
        rect = new Rectangle((int)x,(int)y,(int)width,(int)height);
        upRect = new Rectangle((int)x+3,(int)y,(int)width-6,2);
        downRect = new Rectangle((int)x+3,((int)y+(int)height)-3,(int)width-6,2);
        leftRect = new Rectangle((int)x,(int)y+3,2,(int)height-6);
        rightRect = new Rectangle((int)x+(int)width -2,(int)y+3, 2,(int)height-6);
        ArrayList<BufferedImage> snakeArr;
        snakeArr = get.cutSheet("snakeImgs",3,2);
        snakeImg = snakeArr.get(5);
        snakeImg2 = snakeArr.get(4);
        snakeImg3 = snakeArr.get(3);
        snakeImg4 = snakeArr.get(2);
        snakeImg5 = snakeArr.get(1);
        snakeImg6 = snakeArr.get(0);
    }

    public void setImg(BufferedImage image){
        img = image;
    }

    public void hitHandle(){
        if(vulnerable){
            health -= dmg;
            vulnerable = false;
            if(health <1){
                dead = true;
            }
            if(xd<x){
                dx = 3;
            }else{
                dx = -3;
            }
        }
        hit = false;
    }

    public void hit(int dm, int xl){
        hit = true;
        dmg = dm;
        xd=xl;
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        if(attacking){
            if(leftFacing){
                g.drawImage(img,x-20,y+1,null);
            }else{
                g.drawImage(img,x-10,y+1,null);
            }
        }else{
            g.drawImage(img,x,y+1,null);
        }

        if(snake){
            h=0;
            if(f<20){
                f++;
            }
            if(f>0 && f <=2){
                g.drawImage(snakeImg6,x,y-30,null);
            }else if(f>2&&f<=4){
                g.drawImage(snakeImg5,x,y-30,null);
            }else if(f>4 && f<=6){
                g.drawImage(snakeImg4,x,y-30,null);
            }else if(f>6&&f<=8){
                g.drawImage(snakeImg3,x,y-30,null);
            }else if(f>8&&f<=10){
                g.drawImage(snakeImg2,x,y-30,null);
            }else{
                g.drawImage(snakeImg,x,y-30,null);
            }
        }else{
            f=0;
            if(h<20){
                h++;
            }
            if(h>0 && h <=2){
                g.drawImage(snakeImg,x,y-30,null);
            }else if(h>2&&h<=4){
                g.drawImage(snakeImg2,x,y-30,null);
            }else if(h>4 && h<=6){
                g.drawImage(snakeImg3,x,y-30,null);
            }else if(h>6&&h<=8){
                g.drawImage(snakeImg4,x,y-30,null);
            }else if(h>8&&h<=10){
                g.drawImage(snakeImg5,x,y-30,null);
            }else if(h>10&& h<=12){
                g.drawImage(snakeImg6,x,y-30,null);
            }
        }
    }

    public void update(boolean u,boolean d, boolean l, boolean r,boolean s,boolean sp,boolean ma){
        upKey = u;
        downKey = d;
        leftKey = l;
        rightKey = r;
        spaceKey = s;
        sprintKey = sp;
        medAttack = ma;

        attack();
        if(attacking){}else{
            changeImg();
        }
        if(vulnerable){
            blinking = false;
        }else{
            e++;
            if(e>60){
                e = 0;
                vulnerable = true;
            }
            blinking = true;
        }
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

    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////

    public void attack(){
        if(attacking == false && onGround){
            if(medAttack){
                dy =0;
                dx=0;
                a++;
                attacking = true;
                medAttack = false;
                movable = false;
            }
        }

        if(a>0){
            if(leftFacing){
                b=a/12;
                setImg(combatImg.get(b));
                a++;
                if(a>35){
                    a=0;
                    attacking = false;
                    movable = true;
                }

                hitBox = new Rectangle(x-20,y,30,height);
            }else{
                b=3+(a/12);
                setImg(combatImg.get(b));
                a++;
                if(a>35){
                    a=0;
                    attacking = false;
                    movable = true;
                }

                hitBox = new Rectangle(x+12,y,30,height);
            }
        }else{
            hitBox = new Rectangle(-1000,-1000,1,1);
        }
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

                boundingBox[n][1] = new Rectangle((col*32)+scrollX,(row*32)+scrollY,32,10);
                boundingBox[n][2] = new Rectangle((col*32)+scrollX,(row*32+20)+scrollY,32,12);
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

        if(hit){
            hitHandle();
        }

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
                    tInfo.solid == 1){
                //roy is moving left
                nextX = (int)(((boundingBox[i][4].getX() + 2) - x)+x);
                dx=0;
                collide = true;
            }

            if(nextX > x && nextRightRect.intersects(boundingBox[i][3])&&
                    tInfo.solid == 1){
                //royo is moving right
                nextX = (int)((boundingBox[i][3].getX()) - (x+width)) + (x);
                dx=0;
                collide = true;
            }

            if(nextY>y && nextRect.intersects(boundingBox[i][1]) &&
                    onGround == false && x+width >= boundingBox[i][3].getX() + 1
                    && x <= boundingBox[i][3].getX()+31
                    && (tInfo.solid == 1)){
                //roy is falling onto solid block

                nextY = (int)(boundingBox[i][1].getY() - (y + height)) + y;
                onGround = true;
                tempI = i;

            }

            if(nextY>y && nextRect.intersects(boundingBox[i][1]) &&
                    onGround == false && x+width >= boundingBox[i][3].getX() + 1
                    && x <= boundingBox[i][3].getX()+31 && y+height <= (int)boundingBox[i][0].getY()
                    && (tInfo.solid == 2)){
                //roy is falling onto platform

                nextY = (int)(boundingBox[i][1].getY() - (y + height)) + y;
                onGround = true;
                tempI = i;

            }

            if(nextY < y && nextRect.intersects(boundingBox[i][2]) &&
                    onGround == false && x+width >= boundingBox[i][3].getX() + 1
                    && x <= boundingBox[i][3].getX()+31
                    &&!(tInfo.solid == 0|| tInfo.solid == 2)){
                //roy is jumping
                nextY = (int)(((boundingBox[i][0].getY() + 32) + y)-y);
                dy = 0;
            }

            if(onGround){
                if(x <= boundingBox[tempI][0].getX()+31 &&
                        x+width >= boundingBox[tempI][0].getX() + 1){
                }else{
                    onGround = false;
                }
            }

            if(downKey && falling == false){
                tInfo.getInfo((int)boundingBox[tempI][5].getX());
                if(tInfo.solid == 2 && snake ==false){
                    nextY +=1;
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
        if(sprintKey == true){
            maxSpeed = 3.5;
        }else{
            maxSpeed = 2.5;
        }
        if(movable){
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
        if(movable){
            if(upKey && falling == false){
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
