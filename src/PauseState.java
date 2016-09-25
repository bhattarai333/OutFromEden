import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.*;
/**
 * Write a description of class PauseState here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PauseState extends GameState
{
    int code;
    GameStateManager gsm;
    getResources get = new getResources();
    public void init(GameStateManager gms){
        gsm=gms;
    }

    public void keyPressed(int k){
        code = k;
        if (code == KeyEvent.VK_P||code == KeyEvent.VK_ESCAPE){
            gsm.enterState(state);
        }
    }

    public void keyReleased(int k){
        code = k;
    }

    public void draw(Graphics g){
        g.drawImage(screen,0,0,null);
        g.drawString("THE GAME IS PAUSED",10,10);
    }

    public void updateGame(){

    }

}
