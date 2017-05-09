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
import java.util.ArrayList;
/**
 * Write a description of class getResources here.
 * 
 * @author Josh Bhattarai and Friends 
 * @version Jank Free
 */
public class getResources
{
    public BufferedImage getImg(String path){
        BufferedImage img = null;
        try{
            img = ImageIO.read(this.getClass().getResource("/Resources/Sprites/" + path + ".png"));//gets image from file path
        } catch (IOException e) {
            e.printStackTrace();
       }
       return img;
    }

    public AudioStream getSound(String path){
        InputStream in;
        AudioStream as = null;
        try{
               in = this.getClass().getResourceAsStream("/Resources/Audio/"+ path +".wav");//gets .wav from file path
               as = new AudioStream (in);
           }catch(Exception e){
               e.printStackTrace();
           }
        return as;
    }
    
    public ArrayList<BufferedImage> cutSheet(String path, int w, int h, int cols, int rows){
        BufferedImage spriteSheet = getImg(path);
        int sheetHeight = spriteSheet.getHeight();
        int sheetWidth = spriteSheet.getWidth();
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        int width = w+1;
        int height = h+1;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                images.add ( spriteSheet.getSubimage(
                j * width + 1,
                i * height + 1,
                width - 1,
                height - 1
                ));
            }
        }
        return images;
    }
}
