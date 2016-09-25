import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.image.*;
import javax.swing.JFrame;
/**
 * Write a description of class GameStateManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameStateManager
{
    int currentState = 1;
    JFrame window;
    ArrayList<GameState> states = new ArrayList <GameState>();
    PauseState pause = new PauseState();
    StateOne one = new StateOne();
    StateTwo two = new StateTwo();
    StateThree three = new StateThree();
    GameObject go = new GameObject();

    GameStateManager(JFrame f){
        window = f;
    }

    public void createArr(){
        //adds the levels into the array of states
        go.royHealth = 50;
        states.add(pause);
        states.add(one);
        states.add(two);
        states.add(three);
    }

    public void init(){
        //calls the init method of the current state, starts at 1
        states.get(currentState).init(this);
    }

    public void updateGame(){
        //^
        states.get(currentState).updateGame();
    }

    public void draw(Graphics g){
        //^
        states.get(currentState).draw(g);
    }

    public void keyPressed(int k){
        //^
        states.get(currentState).keyPressed(k);
    }

    public void keyReleased(int k){
        //^
        states.get(currentState).keyReleased(k);
    }

    public void setState(int state){
        //^
        if(state == 0){
            states.get(0).state = currentState;
            currentState = state;
            init();
        }else{
            try{Thread.sleep(500);}catch(Exception e){}
            currentState = state;
            init();
        }
    }

    public void enterState(int state){
        //try{Thread.sleep(500);}catch(Exception e){}
        currentState = state;
    }

    public void end(Player roy1){
        roy1.spaceKey = false;
        roy1.upKey = false;
        roy1.downKey = false;
        roy1.leftKey = false;
        roy1.rightKey = false;
        roy1.dy=0;
        roy1.dx=0;
        roy1.gravity = 0;
        roy1.end = false;
        roy1.dead = false;
        setState(roy1.toState);
        roy1.traveled = false;
        go.royHealth = roy1.health;
        roy1.toState = 0;
    }

    public void pause(int state,BufferedImage img){
        states.get(0).state = currentState;
        states.get(0).window = window;
        states.get(0).screen = img;
        currentState = state;

        init();
    }

}
