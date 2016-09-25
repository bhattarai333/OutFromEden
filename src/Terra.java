import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.image.*;
/**
 * Write a description of class NPC here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Terra extends MapObject
{
    TileMap tileMap = new TileMap();
    TileInfo tInfo = new TileInfo();
    getResources get = new getResources();
    public void  hit(int dmg){

    }
    Terra(Rectangle[][] R,
          TileMap tm,int xc,int yc,int st){

        init(R,tm,xc,yc,st);
        flag = true;
    }
    public void init(Rectangle[][] R,
                     TileMap tm,int xc,int yc, int st){
        images = get.cutSheet("terraSheet",9,2);
        img = images.get(0);

        height = img.getHeight()-1;
        width = img.getWidth();

        relativeX = xc;
        relativeY = yc;
        nextX = xc;
        nextY = yc;

        tileMap = tm;
        tInfo = new TileInfo();
        boundingBox = R;
        map = tm.getMap();

        falling = true;
        moveSpeed = .6;
        maxSpeed = 1.5;
        maxFallingSpeed = 12.0;
        friction =.3;
        jumpStart = -12.5;
        gravity = 0.64;

        dialogue = "";

        rect = new Rectangle((int)x,(int)y,(int)width,(int)height);
        nextRect = rect;
        upRect = new Rectangle((int)x+3,(int)y,(int)width-6,2);
        downRect = new Rectangle((int)x+3,((int)y+(int)height)-3,(int)width-6,2);
        leftRect = new Rectangle((int)x,(int)y+3,2,(int)height-6);
        rightRect = new Rectangle((int)x+(int)width -2,(int)y+3, 2,(int)height-6);
    }

    public void setImg(BufferedImage image){
        img = image;
    }

    public void draw(Graphics g){
        g.drawImage(img,x,y+1,null);
    }

    public void update(){
        playerStuff();

        x = relativeX + scrollX;
        y = relativeY + scrollY;

        if(spaceKey){
            System.out.println(falling);
        }
        changeImg();
        move();
        checkBounds();
        if(speak == false){
            ai();
        }

        x = nextX;
        y = nextY;

        relativeX = x -scrollX;
        relativeY = y - scrollY;
    }

    public void ai(){
        //do shit

    }

    public void checkBounds(){
        nextRect = new Rectangle(nextX,nextY,(int)width,(int)height);
        nextUpRect = new Rectangle(nextX+3,nextY,(int)width-6,2);
        nextDownRect = new Rectangle(nextX+3,(nextY+(int)height)-3,(int)width-6,2);
        nextLeftRect = new Rectangle(nextX,nextY+3,2,(int)height-6);
        nextRightRect = new Rectangle(nextX+(int)width -2,nextY+3, 2,(int)height-6);

        for(int i = 0; i < tileMap.width * tileMap.height; i++){
            int ID = (int)boundingBox[i][5].getX();
            tInfo.getInfo(ID);

            if(nextX < x && nextLeftRect.intersects(boundingBox[i][4]) &&
                    tInfo.solid == 1){
                //moving left
                nextX = (int)(((boundingBox[i][4].getX() + 2) - x)+x);
            }

            if(nextX > x && nextRightRect.intersects(boundingBox[i][3])&&
                    tInfo.solid == 1){
                //moving right
                nextX = (int)((boundingBox[i][3].getX()) - (x+width)) + (x);
            }

            if(nextY>y && nextRect.intersects(boundingBox[i][1]) &&
                    onGround == false && x+width >= boundingBox[i][3].getX() + 1
                    && x <= boundingBox[i][3].getX()+31
                    && (tInfo.solid == 1)){
                //falling onto solid block

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
                //jumping
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
                if(tInfo.solid == 2){
                    nextY +=1;
                    onGround = false;
                    downKey = false;
                }
            }

            if(onGround == false){
                falling = true;
            }else{
                jumping = false;
                falling = false;
            }
        }
    }

    public void move(){
        if(leftKey){
            maxSpeed = 1.5;
            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        }else if(rightKey){
            dx += moveSpeed;
            maxSpeed = 2.5;
            if(dx > maxSpeed){
                dx = maxSpeed;
            }
        }else{
            if(dx < 0){
                dx += friction;
                if(dx > 0){
                    dx=0;
                }
            }
            if(dx > 0){
                dx -= friction;
                if(dx<0){
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
        }else if(falling){
            dy += gravity;
            if(dy>maxFallingSpeed){
                dy = maxFallingSpeed;
            }
        }else{
            onGround = true;
        }

        if(onGround){
            dy = 0;
        }

        nextX = (int)(x+dx);
        nextY = (int)(y+dy);
    }

    public void changeImg(){
        if(leftKey){
            setImg(images.get(c/16));
            c++;
            if(c>63){
                c=1;
            }
            leftFacing = true;
        }
        if(rightKey){
            setImg(images.get(9+(c/16)));
            c++;
            if(c>63){
                c=1;
            }
            leftFacing = false;
        }

        if(leftKey == false && rightKey == false){
            c=0;

            if(leftFacing == true){
                setImg(images.get(8));}
            else{
                setImg(images.get(17));}
        }
    }

    public void playerStuff(){
        scrollX = p1.scrollX;
        scrollY = p1.scrollY;
        if(p1.rect.intersects(nextRect)){
            p1.snake = true;
        }else{
            speak = false;
        }
        if(p1.rect.intersects(nextRect)&&p1.downKey){
            speak = true;
        }
        boundingBox = p1.boundingBox;
        targetX = p1.x;
        targetY = p1.y;
    }
}
