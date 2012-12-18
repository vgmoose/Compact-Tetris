import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class TetrisGame extends JFrame implements KeyListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	static int width = 10;
	static int height = 20;

	boolean gameover = false;

	int interval = 200; //500

	Tetromino active;
	int nextone;
	
	int score;

	private Container c;
	//	ArrayList <Tetromino> pieces;
	int cap;

	Random tkind;

	Tetromino bgpiece;

	Timer timer;

	boolean [][] board;

	public TetrisGame()
	{
		super("Score: 0");
		c = this.getContentPane();

		tkind = new Random();
		//		tkind.setSeed(83249284);
		//		tkind.setSeed(Math.random());

		nextone = tkind.nextInt(6);
		nextPiece();
		bgpiece = new Tetromino(nextone, true);

		//		pieces = new ArrayList<Tetromino>();

		TetrisPanel tp = new TetrisPanel();
		tp.setPreferredSize(new Dimension(250, 500));

		this.addKeyListener(this);

		board = new boolean[width][height];

		c.add(tp);
		showFrame();

		timer = new Timer(interval, this);
		timer.start();

		startNewPiece();
	}

	public void showFrame(){
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;

		// Move the window
		setLocation(x, y);

		setResizable(false);
	}

	public class TetrisPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(Color.black);
			g.fillRect(0,0,getWidth(),getHeight());

			bgpiece.drawPiece(g);

			if (gameover)
			{

			}
			else
			{
				// Setup paint field
				//				for (Tetromino p : pieces)
				//					if (p!=null)
				//						p.drawPiece(g);
				if (active!=null)
				{
					active.drawShadow(g, board);
					active.drawPiece(g);
				}

				// draw splatters
				for (int x=0; x<10; x++)
					for (int y=0; y<20; y++)
						if (board[x][y])
							Tetromino.drawSquare(g,x,y);
			}

		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		int keycode = arg0.getKeyCode();

		//		System.out.println(keycode);


		if ( keycode == 32)
		{
			active.slam(board);
			actionPerformed(null);
		}

		if (keycode == 38)
			active.rotate();

		if (keycode == 40)
			actionPerformed(null);

		if (keycode == 37)
			if (active.canMoveLeft(board))
				active.moveLeft();

		if (keycode == 39)
			if (active.canMoveRight(board))
				active.moveRight();

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void startNewPiece()
	{
		if (board[4][0] == true)
			gameover = true;
		else
		{
			active = new Tetromino(nextPiece());
			bgpiece = new Tetromino(nextone, true);
			//			pieces.add(active);
		}
	}

	public int nextPiece()
	{
		int returnme = nextone;
		nextone = tkind.nextInt(6);
		return returnme;
	}

	public void checkForClear()
	{
		int points = 0;
		
		for (int x=0; x<20; x++)
		{
			int count=0;

			for (int y=0; y<10; y++)
			{
				if (board[y][x]) count++;
			}

			if (count==10)
				points+=clearRow(x);
		}
		
		if (points == 4) points = 10;
		score += points*100;
		setTitle("Score: "+score);


	}

	public int clearRow(int row)
	{
		for (int x=row; x>0; x--)
		{
			for (int y=0; y<10; y++)
				board[y][x] = board[y][x-1];
		}
		
		// clear the top row too
		for (int x=0; x<10; x++)
			board[0][x] = false;
		
		return 1;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (active != null)
		{
			if (!active.canMoveDown(board))
			{
				//				board[active.getX()][active.getY()] = true;
				active.reportHit(board);
				checkForClear();
				startNewPiece();
			}
			else
				active.moveDown();
		}
		repaint();
		timer.start();

	}
}