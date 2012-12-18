import java.awt.Color;
import java.awt.Graphics;


public class Tetromino 
{
	static int size = 25;
	
	int x = 100;
	int y = 0;
	Color c;
	
	Tetromino()
	{
		c = Color.cyan;
	}
	
	public void drawPiece(Graphics g)
	{
		g.setColor(c);
		g.fillRect(x, y, size, size);
	}
}
