import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class TetrisGame extends JFrame implements KeyListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	
	static int width = 10;
	static int height = 20;
	
	Tetromino active;
	
	private Container c;
	ArrayList <Tetromino> pieces;
	int cap;
	
	Timer timer;
	
	int[][] board;

	public TetrisGame()
	{
		super("Tetris Game");
		c = this.getContentPane();
		
		pieces = new ArrayList<Tetromino>();
		
		TetrisPanel tp = new TetrisPanel();
		tp.setPreferredSize(new Dimension(250, 500));
		
		this.addKeyListener(this);
		
		board = new int[width][height];
		
		c.add(tp);
		showFrame();
		
		timer = new Timer(500, this);
		timer.start();
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
	}

	public class TetrisPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g)
		{
			// Setup paint field
			super.paintComponent(g);
			g.setColor(Color.black);
			g.fillRect(0,0,getWidth(),getHeight());
			
			for (Tetromino p : pieces)
				if (p!=null)
					p.drawPiece(g);
				
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		active = new Tetromino();
		pieces.add(active);
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

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (active != null)
			active.y+=Tetromino.size;
		repaint();
		timer.start();
		
	}
}