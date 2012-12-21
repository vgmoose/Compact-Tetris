import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.Random;

import javax.swing.*;

/**
 * The object that manages the the falling of pieces and the entire game.
 * 
 * @author Ricky
 *
 */
public class TetrisGame extends JFrame implements KeyListener, ActionListener, Serializable
{
	private static final long serialVersionUID = 1L;

	static int width = 10;
	static int height = 20;

	boolean swappedonce = false;
	boolean gameover = false;
	int interval = 500;

	Tetromino active;
	Tetromino swapped;

	int nextone;
	int score;

	private Container c;
	//	ArrayList <Tetromino> pieces;
	int cap;

	Random tkind;
	Tetromino bgpiece;
	Timer timer;

	int [][] board;

	/**
	 * The constructor for the TetrisGame that sets the active piece and next piece into motion.
	 */
	public TetrisGame()
	{
		super("Score: 0");
		c = getContentPane();

		tkind = new Random();
		//		tkind.setSeed(83249284);
		//		tkind.setSeed(Math.random());

		nextone = tkind.nextInt(7);
		bgpiece = new Tetromino(nextone, true);

		//		pieces = new ArrayList<Tetromino>();

		TetrisPanel tp = new TetrisPanel();
		tp.setPreferredSize(new Dimension(250, 500));

		addKeyListener(this);	// I am my own key listener

		board = new int[width][height];

		c.add(tp);
		popup();

		timer = new Timer(interval, this);	// we will handle the timer
		timer.start();

		startNewPiece();
	}

	/**
	 * Displays the window onscreen once it's been consturcted.
	 */
	public void popup(){
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

		if (timer!=null)
		{
			timer.stop();
			timer = new Timer(interval, this);
			timer.start();
		}

		setResizable(false);
	}


	/**
	 * The TetrisPanel class that is actually created on top of the JFrame that is TetrisGame
	 * @author Ricky
	 *
	 */
	public class TetrisPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			g.setColor(Color.black);
			g.fillRect(0,0,getWidth(),getHeight());


			if (gameover)
			{
				g.setColor(Color.white);
				g.drawString("GAME OVER", 85, 40);
				g.drawString("Try again next time!", 60, 100);

				g.drawString("Score: "+score, 80, 180);
				g.drawString("Press ENTER to play again.", 40, 250);

			}
			else
			{

				bgpiece.drawPiece(g);

				// Setup paint field
				//				for (Tetromino p : pieces)
				//					if (p!=null)
				//						p.drawPiece(g);

				if (swapped != null)
					swapped.drawPiece(g);

				// draw splatters
				for (int x=0; x<10; x++)
					for (int y=0; y<20; y++)
						if (board[x][y] > 0)
						{
							Color c;
							switch(board[x][y]-1)
							{
							case 0:
								c = Color.cyan;
								break;
							case 1: 
								c = Color.magenta;
								break;
							case 2:
								c = Color.yellow;
								break;
							case 3:
								c = Color.orange;
								break;
							case 4:
								c = Color.blue;
								break;
							case 5:
								c = Color.green;
								break;
							case 6:
								c = Color.red;
								break;
							default:
								c = Color.white;
							}
							Tetromino.drawSquare(g,c,x,y);
						}

				if (active!=null)
				{
					active.drawShadow(g, board);
					active.drawPiece(g);
				}
			}

		}
	}

	/**
	 * Detects any key presses and handles them accordingly
	 */
	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		int keycode = arg0.getKeyCode();

		//		System.out.println(keycode);

		if (keycode == 10 && gameover)
		{
			TetrisHub.startNewGame();
			dispose();
		}

		if ((keycode == 16 || keycode == 157) && !swappedonce)
		{
			if (swapped!=null)
			{
				Tetromino temp = active;
				active = swapped;
				swapped = temp;

				active.markActive();
				swapped.shadowify();
			}
			else
			{
				swapped = active;
				swapped.shadowify();
				startNewPiece();
			}

			swappedonce = true;

		}

		if ( keycode == 32)
		{
			active.slam(board);
			actionPerformed(null);
		}

		if (keycode == 38)
			try {
				active.rotate(board);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (keycode == 40)
				actionPerformed(null);

			if (keycode == 37)
				if (active.canMoveLeft(board))
					active.moveLeft();

			if (keycode == 39)
				if (active.canMoveRight(board))
					active.moveRight();

			if (keycode == 49)
				active.y = 0;

			if (keycode == 50)
				if (timer.isRunning())
					timer.stop();
				else
					timer.start();

			if (keycode == 51)
				startNewPiece();

			if (keycode == 52)
				Tetromino.playwithshadows = !Tetromino.playwithshadows;

			if (keycode == 55)
				Tetromino.realshadow = !Tetromino.realshadow;

			if (keycode == 56 )
				Tetromino.wires = !Tetromino.wires;

			if (keycode == 57)
				Tetromino.borders = !Tetromino.borders;

			if (keycode == 83) // S
			{
				TetrisHub.save(this);
			}

			if (keycode == 82) // R
			{
				if (TetrisHub.restore())
					dispose();
			}

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

	/**
	 * Starts a new piece falling, as well as checking for the end of the game.
	 */
	public void startNewPiece()
	{
		if (board[4][0] > 0)
			gameover = true;
		else
		{
			active = new Tetromino(nextPiece());
			bgpiece = new Tetromino(nextone, true);
			//			pieces.add(active);
		}
	}

	/**
	 * Cycles the next piece as well as taking care of the next one and the previous one.
	 * @return	the next value that used to be in next one.
	 */
	public int nextPiece()
	{
		int returnme = nextone;
		nextone = tkind.nextInt(7);
		return returnme;
	}

	/**
	 * Checks any and all rows for any cleared rows, and clears them if they need to be
	 */
	public void checkForClear()
	{
		int points = 0;
		swappedonce = false;

		for (int x=0; x<20; x++)
		{
			int count=0;

			for (int y=0; y<10; y++)
			{
				if (board[y][x] > 0) count++;
			}

			if (count==10)
				points+=clearRow(x);
		}

		if (points == 4) points = 10;
		score += points*100;
		setTitle("Score: "+score);


	}

	/**
	 * Clears the entire row that is specified, and moves everything down
	 * 
	 * @param row	row to be cleared
	 * @return
	 */
	public int clearRow(int row)
	{
		for (int x=row; x>0; x--)
		{
			for (int y=0; y<10; y++)
				board[y][x] = board[y][x-1];
		}

		// clear the top row too
		for (int x=0; x<10; x++)
			board[x][0] = 0;

		if (500 - (score/500)*50 != timer.getDelay())
			timer.setDelay(500 - (score/500)*50);

		return 1;
	}

	/**
	 * Is called when the timer fires.
	 */
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
		//		timer.start();

	}
}