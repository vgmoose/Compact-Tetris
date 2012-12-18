import java.awt.Color;
import java.awt.Graphics;


public class Tetromino 
{
	int size = 25;

	Tetromino shadow;

	int x = 100;
	int y = 0;
	Color c;

	boolean isshadow = false;

	int kind;
	boolean[][] blocks;

	Tetromino(int kind)
	{
		this.kind = kind;
		getRandomKind();
		//		c = Color.cyan;
		shadow = new Tetromino(kind, false);
		shadow.x = this.x;
	}

	public Tetromino(int kind, boolean isnextpiece) 
	{
		this.kind = kind;
		getRandomKind();
		
		c = new Color(55, 55, 55);

		if (isnextpiece)	// alternative is shadow
		{
			rotate();

			size = 35;

			x = 185;
			y = 80;

			if (kind == 5)
				x -= 35;
		}
		else
		{
			isshadow=true;
		}
	}

	public void getRandomKind()
	{
		//		kind = (int)(Math.random()*6);


		if (kind == 0)		// Line piece
		{
			boolean[][] t = 
			{{ false, false, false, false},
					{  true,  true,  true,  true},
					{ false, false, false, false},
					{ false, false, false, false}};  // pivot: 1,1
			blocks = t;
			c = Color.cyan;
		}
		else if (kind == 1)		// T piece
		{
			boolean[][] t = 
			{{ false, false, false},
					{  true,  true,  true},
					{ false,  true, false}};  // pivot: 1,1
			blocks = t;
			c = Color.magenta;
		}
		else if (kind == 2)		// square piece
		{
			boolean[][] t = 
			{{  true,  true},
					{  true,  true}};  // pivot: none
			blocks = t;
			c = Color.yellow;
		}
		else if (kind == 3) 	// L piece
		{
			boolean[][] t = 
			{{ false, false, false},
					{  true,  true,  true},
					{  true, false, false}};  // pivot: 1,1
			blocks = t;
			c = Color.orange;
		}
		else if (kind == 4) 	// reverse L piece
		{
			boolean[][] t = 
			{{ false, false, false},
					{  true,  true,  true},
					{ false, false,  true}};  // pivot: 1,1
			blocks = t;
			c = Color.blue;
		}
		else if (kind == 5)
		{
			boolean[][] t = 
			{{ false,  true,  true},
					{  true,  true, false},
					{ false, false, false}}; // pivot: 1,1
			blocks = t;
			c = Color.green;
		}
	}

	public void drawPiece(Graphics g)
	{
		g.setColor(c);

		for (int x=0; x<blocks.length; x++)
		{
			for (int y=0; y<blocks.length; y++)
				if (blocks[y][x])
					g.fillRect(x*size+this.x-size, y*size+this.y-size, size, size);
		}
		// Draw a single square
		//		g.fillRect(x, y, size, size);

	}

	public void drawShadow(Graphics g, boolean[][] board)
	{
		// Draw the shadow
		if (!isshadow)
		{
			updateShadow(board);
			shadow.drawPiece(g);
		}

	}
	
	public void updateShadow(boolean[][] board)
	{
		shadow.x = this.x;
		shadow.y = this.y;
		shadow.slam(board);
	}

	public static void drawSquare(Graphics g, int x, int y)
	{
		g.fillRect(x*25, y*25, 25, 25);

	}

	public void moveDown()
	{
		y += size;
	}

	public void moveLeft()
	{
		if (getX() != 0)
			x -= size;
	}

	public void moveRight()
	{
		if (getX() != 9)
			x += size;
	}

	public int getY()
	{
		return y/size;
	}

	public boolean canMoveRight(boolean [][] board)
	{

		for (int x=0; x<blocks.length; x++)
			for (int y=0; y<blocks.length; y++)
				if (blocks[x][y])
					if (y+getX()-1 == 9 || board[getX()+y][getY()+x-1])
						return false;
		return true;
	}

	public boolean canMoveLeft(boolean [][] board)
	{
		for (int x=0; x<blocks.length; x++)
			for (int y=0; y<blocks.length; y++)
				if (blocks[x][y])
					if (y+getX()-1 == 0 || board[getX()+y-2][getY()+x-1])
						return false;

		return true;
	}

	public boolean canMoveDown(boolean [][] board) 
	{
		for (int x=0; x<blocks.length; x++)
			for (int y=0; y<blocks.length; y++)
				if (blocks[x][y])
					if (getY()+x-1 == 19 || board[getX()+y-1][getY()+x])
						return false;

		return true;
	}

	public int getX() 
	{
		return x/size;
	}

	public void slam(boolean [][] board)
	{
		while (canMoveDown(board))
			moveDown();
	}

	public void rotate() 
	{
		boolean[][] newblocks = new boolean[blocks.length][blocks.length];

		if (kind != 2)
		{			
			newblocks[0][2] = blocks[0][0];
			newblocks[1][2] = blocks[0][1];

			newblocks[2][2] = blocks[0][2];
			newblocks[2][1] = blocks[1][2];

			newblocks[2][0] = blocks[2][2];
			newblocks[1][0] = blocks[2][1];

			newblocks[0][0] = blocks[2][0];
			newblocks[0][1] = blocks[1][0];

			newblocks[1][1] = true;

			if (kind == 0)
			{
				newblocks[1][2] = blocks[2][1];
				newblocks[1][3] = blocks[3][1];

				newblocks[2][1] = blocks[1][2];
				newblocks[3][1] = blocks[1][3];
			}

			blocks = newblocks;
			
			if (!isshadow && shadow!=null)
				shadow.rotate();
		}
	}

	public void reportHit(boolean[][] board) 
	{
		for (int x=0; x<blocks.length; x++)
			for (int y=0; y<blocks.length; y++)
				if (blocks[x][y])
					board[getX()+y-1][getY()+x-1] = true;
	}

	public int getWidth() 
	{
		return blocks.length;
	}
}
