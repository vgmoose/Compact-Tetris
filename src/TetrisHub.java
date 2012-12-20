import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class TetrisHub 
{
	public static void main(String[] args)
	{
		startNewGame();
	}
	
	public static void startNewGame()
	{
		TetrisGame tg = new TetrisGame();
		tg.popup();
		
//		TetrisGame tg2 = new TetrisGame();
//		tg2.popup();
//		
//		TetrisGame tg3 = new TetrisGame();
//		tg3.popup();
	}
	
	public static void save(TetrisGame t)
	{
	     try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("tetris.sav");
	         ObjectOutputStream out =
	                            new ObjectOutputStream(fileOut);
	         out.writeObject(t);
	         out.close();
	          fileOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public static boolean restore()
	{
		TetrisGame t = null;
        try
        {
           FileInputStream fileIn =
                         new FileInputStream("tetris.sav");
           ObjectInputStream in = new ObjectInputStream(fileIn);
           t = (TetrisGame) in.readObject();
           in.close();
           fileIn.close();
           
           t.popup();
           return true;
       }catch(Exception i)
       {
//           i.printStackTrace();
           return false;
       }
	}
}
