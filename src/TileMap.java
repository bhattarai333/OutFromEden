import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
/**
 * Write a description of class TileMap here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TileMap
{
    int x = 0;
    int y = 0;

    static int[][] map = null;
    static int width = 0;
    static int height = 0;

    public int[][] getMap(){
        return map;
    }

    public  void TileMap(String s)
    {
        try{
            //System.out.println(new File(".").getAbsolutePath());
            InputStream in = getClass().getResourceAsStream("/Resources/Maps/"+s+".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String delimeter = "=";
            String line = br.readLine();
            line = br.readLine();
            String[] tokens = line.split(delimeter);
            width = Integer.parseInt(tokens[1]);
            line = br.readLine();
            tokens = line.split(delimeter);
            height = Integer.parseInt(tokens[1]);
            do{
                line = br.readLine();
            }while(!(line.equalsIgnoreCase("data=")));

            map = new int[height][width];
            delimeter = ",";
            for(int row = 0; row < height; row++){
                line = br.readLine();
                tokens = line.split(delimeter);
                for(int col = 0; col < width; col++){
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
