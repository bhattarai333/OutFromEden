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

    public ArrayList<BufferedImage> cutSheet(String path, int cols, int rows){
        BufferedImage spriteSheet = getImg(path);
        double sheetHeight = spriteSheet.getHeight();
        double sheetWidth = spriteSheet.getWidth();
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        double width = sheetWidth/cols;
        double height = sheetHeight/rows;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                images.add ( spriteSheet.getSubimage(
                        (int)(j * width + 1),
                        (int)(i * height + 1),
                        (int)(width - 1),
                        (int)(height - 1)
                ));
            }
        }
        return images;
    }


    public Font getFont(String filename, float fontSize) {
        Font myfont = null;
        Font myfontReal = null;
        try {
            InputStream is = new BufferedInputStream(this.getClass().getResourceAsStream("/Resources/Fonts/" + filename+".ttf"));

            myfont = Font.createFont(Font.TRUETYPE_FONT, is);
            myfontReal = myfont.deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            System.out.println(e.getMessage());
        }
        return myfontReal;
    }

    public BufferedImage getScreenShot(Component c) {
        BufferedImage img = new BufferedImage(c.getWidth(),c.getHeight(),BufferedImage.TYPE_INT_RGB);
        c.paint( img.getGraphics() );
        return img;
    }

    public void saveImg(BufferedImage img){
        String path = null;
        String path2 = null;
        path = "./Screens/screenshot0t.png";
        try{
            new File("./Screens").mkdir();

            path2 = iterate(path,0);
            // System.out.println(path2);
            File outputfile = new File(path2);
            ImageIO.write(img, "png", outputfile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public String iterate(String path,int d){
        File f = new File(path);
        String path2 = path;
        int i = d;
        if(f.exists() && !f.isDirectory()) {
            String[] tokens = path2.split("t");
            i=i+1;
            path2 = tokens[0]+"t" + i + "t"+tokens[2];
            return (iterate(path2, i));
        }else{
            return path2;
        }
    }

    int randomWithRange(int min, int max)
    {
        int range = Math.abs(max - min) + 1;
        return (int)(Math.random() * range) + (min <= max ? min : max);
    }

}
