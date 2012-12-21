import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Manages TetrisGames and provides simple interface for saving and restoring, and then reloading the game.
 * 
 * @author Ricky
 *
 */
public class TetrisHub 
{
	/**
	 * The main class
	 * @param args
	 */
	public static void main(String[] args)
	{
		startNewGame();
	}

	/**
	 * Starts a single new TetrisGame. The window will popup and display when popup() is called.
	 */
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

	/**
	 * Saves the TetrisGame object to a file using Serialization.
	 * @param t
	 */
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

	/**
	 * Restores a TetrisGame file that was saved.
	 * @return
	 */
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
