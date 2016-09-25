import java.awt.Graphics;
import java.awt.image.*;
import javax.swing.JFrame;
/**
 * abstract void class GameState - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract  class GameState
{
    //abstract class for all states
    int state;
    BufferedImage screen;
    JFrame window;

    public abstract void init(GameStateManager gms);
    public abstract void updateGame();
    public abstract void draw(Graphics g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
}
