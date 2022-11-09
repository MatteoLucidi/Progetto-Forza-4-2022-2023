import java.io.IOException;

public class BasicLib
{
    public static int randomRange(int min, int max)
    {
        return (int) ((Math.random() * (max - min)) + min);
    }
    
    public static int readKey() {
        int key;
        
        try
        {
            key = System.in.read();
            return key;
        }
        catch(IOException e){}
        
        return -1;
    }
    
    public static void cls() {
       System.out.print("\u000C");
    }
}
