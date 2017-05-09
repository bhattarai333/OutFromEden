import java.util.Scanner;
/**
 * Write a description of class divideTester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class divideTester
{
    public static void main(String[] args){
        String s=null;
        Scanner scan = new Scanner(System.in);
        int i;
        int j=22;
        do{
            System.out.println("Enter i");
            i = Integer.parseInt(scan.next());
            //System.out.println("Enter j");
            //j = Integer.parseInt(scan.next());
            System.out.println("/ " + i/j);
            System.out.println("% " + i%(j+1));
            //System.out.println("Again?");
            //s = scan.next();
        }while(!(s == "x"));
    }
}
