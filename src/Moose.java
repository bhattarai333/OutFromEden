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
public class Moose extends MapObject
{
    TileMap tileMap = new TileMap();
    TileInfo tInfo = new TileInfo();
    getResources get = new getResources();

    Moose(Rectangle[][] R,
          TileMap tm,int xc,int yc, int st){

        init(R,tm,xc,yc, st);

    }
    public void init(Rectangle[][] R,
                     TileMap tm,int xc,int yc,int st){
        images = get.cutSheet("mooseSheet",9,2);
        img = images.get(0);
        enemy = true;

        height = img.getHeight()-1;
        width = img.getWidth();

        relativeX = xc;
        relativeY = yc;
        nextX = xc;
        nextY = yc;

        health = 10;

        tileMap = tm;
        tInfo = new TileInfo();
        boundingBox = R;
        map = tm.getMap();

        ID = 0;

        vulnerable = false;
        falling = true;
        moveSpeed = .6;
        maxSpeed = 1.5;
        maxFallingSpeed = 12.0;
        friction =.3;
        jumpStart = -12.5;
        gravity = 0.64;

        dmg = 10;

        rect = new Rectangle((int)x,(int)y,(int)width,(int)height);
        upRect = new Rectangle((int)x+3,(int)y+3,(int)width-6,2);
        downRect = new Rectangle((int)x+3,((int)y+(int)height)-3,(int)width-6,2);
        leftRect = new Rectangle((int)x,(int)y+6,2,(int)height-9);
        rightRect = new Rectangle((int)x+(int)width -2,(int)y+6, 2,(int)height-9);
    }

    public void setImg(BufferedImage image){
        img = image;
    }

    public void draw(Graphics g){
        if(dead == false){
            g.drawImage(img,x,y+1,null);
        }
    }

    public void  hit(int dmg){
        if(vulnerable){
            health -= dmg;
            vulnerable = false;
            if(health <1){
                dead = true;
            }
        }
    }

    public void update(){
        playerStuff();

        x = relativeX + scrollX;
        y = relativeY + scrollY;

        if(spaceKey){
            System.out.println(falling);
        }
        if(vulnerable == false){
            e++;
            if(e>60){
                vulnerable = true;
                e = 0;
            }
        }
        changeImg();
        move();
        checkBounds();
        ai();

        x = nextX;
        y = nextY;

        relativeX = x -scrollX;
        relativeY = y - scrollY;
    }

    public void ai(){
        deltaX = x-targetX;
        deltaY = y-targetY;


        distanceToTarget = (int)Math.sqrt(  ((Math.abs(deltaX))*(Math.abs(deltaX))) +
                ((Math.abs(deltaY))*(Math.abs(deltaY)))     );
        if(distanceToTarget < 250){
            aggro = true;
        }else{
            aggro = false;
        }

        if(aggro){
            if(targetX > x){
                //if player is to moose right
                rightKey = true;
                leftKey = false;
            }else if(targetX<x){
                leftKey = true;
                rightKey = false;
            }
            counter++;
            if(counting == false){
                urdCounter = counter;
                urdX = x;
                counting = true;
            }

            if(counting == true && counter - urdCounter > 80){
                if(Math.abs(urdX-x) < 7){
                    if(tInfo.getSolid((int)boundingBox[tempI][5].getX())==2){
                        if(onGround){
                            downKey = true;
                        }else{
                            //downKey = false;
                        }
                    }else{
                        jumping = true;
                        falling = true;
                    }
                    counter = 0;
                    urdCounter = 0;
                    counting = false;
                    urdX = x;
                }else{
                    counter = 0;
                    urdCounter = 0;
                    counting = false;
                    urdX = x;
                }
            }

            if(targetY < y && counter - urdCounter > 60&&counting){
                jumping = true;
                falling = true;
                counter = 0;
                counting = false;
                urdX = x;
            }else if (targetY  > y +3&& counter - urdCounter > 60&&counting&&onGround){
                downKey = true;
                counter = 0;
                counting = false;
                urdX = x;
            }else{
                downKey = false;
            }

        }else{
            counter = 0;
            rightKey = false;
            leftKey = false;
        }
    }

    public void checkBounds(){
        nextRect = new Rectangle(nextX,nextY,(int)rect.getWidth(),(int)rect.getHeight());
        nextUpRect = new Rectangle(nextX+3,nextY+3,(int)upRect.getWidth(),(int)upRect.getHeight());
        nextDownRect = new Rectangle(nextX+3,(nextY+(int)height)-3,(int)downRect.getWidth(),(int)downRect.getHeight());
        nextLeftRect = new Rectangle(nextX,nextY+6,(int)leftRect.getWidth(),(int)leftRect.getHeight());
        nextRightRect = new Rectangle(nextX+(int)width -2,nextY+6, (int)rightRect.getWidth(),(int)rightRect.getHeight());

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
        boundingBox = p1.boundingBox;
        targetX = p1.x;
        targetY = p1.y;
    }
}
