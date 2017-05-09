import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.Font;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
/**
 * Write a description of class getResources here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class getResources{
  
    public BufferedImage getImg(String path){
        BufferedImage img = null;
        try {                
            img = ImageIO.read(this.getClass().getResource("/Sprites/" + path + ".png"));
       } catch (IOException e) {
            e.printStackTrace();
       }
       return img;
    }
    
    public AudioStream getSound(String path){
        InputStream in;
        AudioStream as = null;
        try{
               in = this.getClass().getResourceAsStream("/Audio/"+ path +".wav");
               as = new AudioStream (in);
           }catch(Exception e){
               e.printStackTrace();
           }
        return as;
    }
}
