import java.awt.Graphics;
/**
 * abstract void class GameState - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract  class GameState
{
    public abstract void reEnter(GameStateManager gms, GameObject g);
    public abstract void init(GameStateManager gms,GameObject g);
    public abstract void updateGame();
    public abstract void draw(Graphics g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
}
