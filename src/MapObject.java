import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.image.*;
/**
 * Abstract class MapObject - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class MapObject
{
    protected int x;
    protected int y;
    protected int urdX;
    protected int relativeX;
    protected int relativeY;
    protected int e;
    protected int n;
    protected int c;
    protected long counter;
    protected long urdCounter;
    protected boolean counting;
    protected double dx;
    protected double dy;
    protected int width;
    protected int height;
    protected int nextY;
    protected int nextX;
    protected int relativeNX;
    protected int relativeNY;
    protected int tempI;
    protected int scrollX;
    protected int scrollY;

    protected boolean jumping;
    protected boolean falling;
    protected boolean onGround;
    protected boolean leftFacing;
    protected boolean aggro;
    protected boolean vulnerable;
    protected boolean enemy;

    protected boolean upKey;
    protected boolean downKey;
    protected boolean leftKey;
    protected boolean rightKey;
    protected boolean spaceKey;

    protected double moveSpeed;
    protected double maxSpeed;
    protected double friction;
    protected double maxFallingSpeed;
    protected double jumpStart;
    protected double gravity;

    protected Rectangle rect;
    protected Rectangle upRect;
    protected Rectangle downRect;
    protected Rectangle leftRect;
    protected Rectangle rightRect;

    protected Rectangle nextRect=new Rectangle(0,0,0,0);
    protected Rectangle nextUpRect;
    protected Rectangle nextDownRect;
    protected Rectangle nextLeftRect;
    protected Rectangle nextRightRect;

    protected ArrayList<BufferedImage> images;
    protected BufferedImage img;

    protected int[][] map;
    protected Rectangle[][] boundingBox;

    protected int targetX;
    protected int targetY;
    protected int deltaX;
    protected int deltaY;
    protected int distanceToTarget;
    protected int dmg;

    protected boolean dead = false;
    protected int health;
    protected boolean flag;
    protected int state;
    protected boolean end;
    protected boolean speak;
    protected boolean killCounted;

    String path;
    Player p1;
    protected String dialogue;

    int ID;


    public abstract void init(Rectangle[][] R,
                              TileMap tileMap,int xc,int yc, int st);
    public abstract void update();
    public abstract void draw(Graphics g);
    public abstract void hit(int dmg);

}
