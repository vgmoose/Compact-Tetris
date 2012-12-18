
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
	}
}
