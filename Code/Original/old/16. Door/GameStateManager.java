import java.awt.Graphics;
import java.util.ArrayList;
/**
 * Write a description of class GameStateManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameStateManager
{
   int currentState = 1;
   ArrayList<GameState> states = new ArrayList <GameState>();
   GameObject GO = new GameObject();
   Character roy = new Character();
   StateOne one = new StateOne();
   StateTwo two = new StateTwo();
   StateThree three = new StateThree();
    public void createArr(){
       states.add(one);
       states.add(two);
       states.add(three);
    }
    
    public void reEnter(){
        states.get(currentState -1).reEnter(this, GO);
    }
    
    public void init(){
        states.get(currentState -1).init(this, GO);
    }
   
    public void updateGame(){
       states.get(currentState -1).updateGame();
    }
    
    public void draw(Graphics g){
        states.get(currentState -1).draw(g);
    }
    
    public void keyPressed(int k){
        states.get(currentState -1).keyPressed(k);
    }
    
    public void keyReleased(int k){
        states.get(currentState -1).keyReleased(k);
    }
    
    public void setState(int state, GameObject g){
        GO = g;
        currentState = state;
        init();
    }
    
    public void enterState(int state, GameObject g){
        GO = g;
        currentState = state;
        reEnter();
    }
}
