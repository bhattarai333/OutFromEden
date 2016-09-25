import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.image.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Arrays;
/**
 * Template to copy and paste new states from
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StateOne extends GameState
{
    final int SCREEN_WIDTH = 704;
    final int SCREEN_HEIGHT = 512;
    final int TILE_WIDTH = 32;
    final int TILE_HEIGHT = 32;
    final int START_X = 20;
    final int START_Y = 431;
    final int END_X = 610;
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
    boolean display = false;


    Player roy1 = new Player();//our player
    getResources get = new getResources();//pull resources from folders
    TileMap tm = new TileMap();//the map object
    TileInfo tile = new TileInfo();
    GameStateManager gsm;

    ArrayList<BufferedImage> royImages = null;//objects
    ArrayList<MapObject> objects = new ArrayList<MapObject>();


    Rectangle boundingBoxes[][];//bounds of the map
    int[][] map = null;//the actual map
    int mapWidth = 0;
    int mapHeight = 0;

    int a;
    int b;
    int c;
    int e;//counters
    int z;
    int n;


    BufferedImage capture;

    BufferedImage layerOneImg;

    BufferedImage darrow;
    BufferedImage frame;//Dialogue stuff
    Font pixelFont= null;
    FontMetrics fm = null;
    boolean first = true;
    boolean secondLine;
    boolean moreText;
    boolean stringWrapped;
    boolean stringSplit;
    boolean lineCompleted;
    boolean lineCompleted2;
    boolean stall;
    boolean stringObtained;
    boolean textObtained;
    int interval = 0;

    String text;
    String displayText="";
    String displayText2="";
    ArrayList<String> lines = new ArrayList<String>();
    String[] chars;

    BufferedImage healthImg;
    double paraX;
    double paraY;
    public void init(GameStateManager gms){
        gsm = gms;
        tm.TileMap("map");
        mapWidth = tm.width;
        frame = get.getImg("frame");
        mapHeight = tm.height;
        map = tm.map;
        healthImg = get.getImg("healthbar");
        royImages = get.cutSheet("roySheet",9,2);
        darrow = get.getImg("darrow");
        tile.init();
        int xc = 700/2;
        int yc = 300;
        roy1.init(royImages,tm,xc,yc);//create new player
        scroll = true;
        boundingBoxes = roy1.boundingBox;
        roy1.health = gsm.go.royHealth;
        layerOneImg = get.getImg("layer1");
        for(int i = 0; i < objects.size();i++){
            //empty array
            objects.remove(i);
        }
        int d = 1;
        if(d>objects.size()){
            objects.add(new Obj());
        }
        objects.set(d-1,new Moose(
                boundingBoxes,tm,900,32,0//add moose enemy at 70 32
        ));
        a=d-1;
        d++;


        if(d>objects.size()){
            objects.add(new Obj());
        }
        objects.set(d-1,new GenericObj(
                boundingBoxes,tm,2000,1017,0,"hospital"//
        ));
        d++;

        if(d>objects.size()){
            objects.add(new Obj());
        }
        objects.set(d-1,new Door(
                boundingBoxes,tm,2125,1170,3,"doorSheet"
        ));
        d++;

        if(d>objects.size()){
            objects.add(new Obj());
        }
        objects.set(d-1,new Terra(
                boundingBoxes,tm,64,64,3
        ));
        d++;
        objects.get(d-2).dialogue = "blah blah blah blah aklsdjfas;dfj blah blah blah hi im terra this is a test blah blah blahb lahslkdfj;asdf blah.";
        if(d>objects.size()){
            objects.add(new Obj());
        }
        objects.set(d-1,new Door(
                boundingBoxes,tm,100,100,2,"doorSheet"
        ));


        for(int i = 0; i < objects.size();i++){
            //update all the objects and pass variables into them
            objects.get(i).p1 = roy1;
        }

        pixelFont = get.getFont("pixel",48);
        upKey = false;
        downKey = false;
        rightKey = false;
        leftKey = false;
        medAttack = false;
        spaceKey = false;
    }

    public void keyPressed(int k){
        code = k;
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W){upKey = true;}
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){downKey = true;}
        if (code == KeyEvent.VK_LEFT ||code==KeyEvent.VK_A){leftKey = true;rightKey = false;}
        if (code == KeyEvent.VK_RIGHT||code==KeyEvent.VK_D){rightKey = true;leftKey = false;}
        if (code == KeyEvent.VK_SPACE){spaceKey = true;}else{spaceKey = false;}
        if (code == KeyEvent.VK_X){medAttack = true;}else{medAttack=false;}
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

        if(code == KeyEvent.VK_P||code == KeyEvent.VK_ESCAPE){
            upKey = false;
            downKey = false;
            leftKey = false;
            rightKey = false;
            spaceKey = false;
            medAttack = false;

            capture = get.getScreenShot(gsm.window.getContentPane());

            gsm.pause(0,capture);
        }

        if(code == KeyEvent.VK_O){
            BufferedImage img = get.getScreenShot(gsm.window.getContentPane());
            get.saveImg(img);
        }

    }

    public void keyReleased(int k){
        code = k;
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W){upKey = false;}
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){downKey = false;}
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A){leftKey = false;}
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D){rightKey = false;}
        if (code == KeyEvent.VK_SPACE){spaceKey = false;}
        if (code == KeyEvent.VK_X){medAttack = false;}
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        if(first){
            fm = g.getFontMetrics(pixelFont);
            first = false;
        }

        for(int row = 0; row < mapHeight/4;row++){
            for(int col = 0; col < mapWidth/4;col++){
                //draw layer one of the parralax
                paraX = (128*col)+roy1.scrollX/3;
                paraY =(128*row)+roy1.scrollY/3;
                g.drawImage(layerOneImg, (int)paraX,(int)paraY,null);
            }
        }
        for(int row = 0;row<mapHeight;row++){
            for(int col = 0;col<mapWidth;col++){
                int rc = map[row][col];
                BufferedImage img = tile.getImg(rc);
                if(img == null){
                    //if tile ID is 0 there's no image so nothing to draw
                }else{
                    //draw the tiles and compensate for scrolling by adding scrollX and scrollY
                    g.drawImage(tile.getImg(rc),(32*col)+roy1.scrollX/1,(32*row)+roy1.scrollY/1,null);
                }
            }
        }



        for(int i = 0; i < objects.size();i++){
            //call draw method of each object
            objects.get(i).draw(g);
        }


        if(display){
            g.drawImage(frame,-1,390,null);
            if(moreText){
                a++;
                if(a<60){
                    g.drawImage(darrow,615,470,null);
                }
                if(a>120){
                    a=0;
                }
            }
            g.setFont(pixelFont);
            g.setColor(Color.black);
            g.drawString(displayText,START_X,START_Y);
            if(secondLine&&lineCompleted){
                g.drawString(displayText2,START_X,START_Y+fm.getHeight());
            }
        }

        g.drawImage(healthImg,10,10,null);
        double healthdouble = roy1.health;
        double maxhealthdouble = roy1.maxHealth;
        double healthWidth = (healthdouble/maxhealthdouble)*99;
        g.setColor(Color.red);
        g.fillRect(15,15,(int)healthWidth,22);

        roy1.draw(g);
        debug(g,g2);
    }


    public void debug(Graphics g,Graphics2D g2){
        if(debug){
            //draws helpful stuff
            g.setColor(Color.pink);
            g2.draw(roy1.upRect);
            g2.draw(objects.get(0).nextUpRect);
            g.setColor(Color.white);
            g2.draw(roy1.downRect);
            g2.draw(objects.get(0).nextDownRect);
            g.setColor(Color.red);
            g2.draw(roy1.leftRect);
            g2.draw(objects.get(0).nextLeftRect);
            g.setColor(Color.yellow);
            g2.draw(roy1.rightRect);
            g2.draw(objects.get(0).nextRightRect);
            g.setColor(Color.black);
            g.drawString("X: " + (roy1.x-roy1.scrollX) + " Y: " + (roy1.y-roy1.scrollY)+" Health: "+roy1.health,roy1.x,roy1.y-10);
            g2.draw(roy1.hitBox);
        }
    }

    //////////////////////////////////////////////////////////////////////////////
    //                                                                          //
    //////////////////////////////////////////////////////////////////////////////


    public void updateGame(){
        royStuff();
        objectHandling();
        textDisplay();
    }



    public void textDisplay(){
        if(textObtained ==false&&display){
            wrapTextNice();
            textObtained = true;
            n=0;
        }

        if(stringWrapped == false&&display&&lineCompleted == false&&stall == false){
            stringWrapped = true;
            if(lines.size() > 1){
                secondLine = true;
                if(lines.size() > 2){
                    moreText = true;
                }
            }
        }

        if(stringSplit == false && display&&lineCompleted == false&&stall == false){
            chars = lines.get(0).split("");
            stringSplit = true;
            n=0;
        }

        if(stringWrapped && stringSplit&&lineCompleted == false&&stall == false){
            if(n < chars.length){
                displayText = displayText + chars[n];
                n++;
            }else{
                n=0;
                stringSplit = false;
                //stringWrapped = false;
                lineCompleted = true;
            }
        }


        if(lineCompleted&&stringSplit == false&&stall == false&&secondLine){
            chars = lines.get(1).split("");
            stringSplit = true;
            n=0;
        }

        if(stringSplit && lineCompleted&&stall == false){
            if(n<chars.length){
                displayText2 = displayText2 + chars[n];
                n++;
            }else{
                n=0;
                stringSplit = false;
                stringWrapped = false;
                lines.remove(0);
                lines.remove(0);
                stall = true;
                lineCompleted2 = true;
            }
        }

        if(downKey && lineCompleted2 && moreText){
            displayText = "";
            displayText2 = "";
            stall = false;
            stringSplit = false;
            stringWrapped = false;
            secondLine = false;
            lineCompleted = false;
            moreText = false;

        }

    }


    public void objectHandling(){
        for(int i = 0; i < objects.size();i++){
            //update all the objects and pass variables into them
            if(objects.get(i).dead == false){
                objects.get(i).update();
            }
            if(objects.get(i).dead == true){
                if(objects.get(i).ID == 0 && objects.get(i).killCounted == false){
                    gsm.go.mooseKills=gsm.go.mooseKills+1;
                    objects.get(i).killCounted = true;
                }
            }

            if(objects.get(i).speak){
                //resetText();
                display = true;
                interval = i;
                if(stringObtained == false){
                    text = objects.get(i).dialogue;
                    stringObtained = true;
                }
            }


            if(roy1.hitBox.intersects(objects.get(i).nextRect)){
                objects.get(i).hit(5);
            }
            if(objects.get(i).enemy&&roy1.rect.intersects(objects.get(i).nextRect)
                    &&objects.get(i).dead == false){
                roy1.hit(objects.get(i).dmg,objects.get(i).x);
            }



        }

        if(objects.get(interval).speak == false){
            resetText();
        }

        if(roy1.dead){
            gsm.go.royHealth = 50;
            roy1.toState = gsm.currentState;
            try{Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
            gsm.end(roy1);
        }

        if(roy1.end){
            gsm.go.royHealth = roy1.health;
            gsm.end(roy1);
        }
    }

    public void royStuff(){
        roy1.scroll = scroll;
        roy1.update(upKey,downKey,leftKey,rightKey,spaceKey,sprintKey,medAttack);


    }



    public void wrapTextNice(){
        for(int i = 0; i < lines.size(); i++){
            lines.remove(i);
        }
        String[] arr = text.split(" ");
        int nIndex = 0;
        while(nIndex  < arr.length){
            String line = arr[nIndex];
            nIndex++;
            while (nIndex < arr.length && fm.stringWidth(line + " " + arr[nIndex]) < END_X){
                line = line + " " + arr[nIndex];
                nIndex++;
            }
            lines.add(line);
        }
    }

    public void resetText(){
        for(int i = 0;i<lines.size();i++){
            lines.remove(i);
        }
        display = false;
        displayText = "";
        displayText2 = "";
        stall = false;
        stringSplit = false;
        stringWrapped = false;
        secondLine = false;
        lineCompleted = false;
        moreText = false;
        textObtained = false;
        stringObtained = false;
        n = 0;
    }
}
