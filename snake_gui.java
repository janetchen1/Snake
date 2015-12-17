// libraries
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class SnakeGui extends JFrame implements KeyListener, ActionListener
{
	// set up displays
	Snake game = new Snake();
	Timer t;
	JPanel boxy = new JPanel();
	JPanel top = new JPanel();
	JPanel bottom = new JPanel();
	JPanel[] grid = new JPanel[625];
	private JButton reset = new JButton("New Game");
	private JTextField score = new JTextField("Score: 0");
	private JTextField highscore = new JTextField("High Score: 0");

	public SnakeGui(){
		super();
		boxy.setLayout(new BoxLayout(boxy,BoxLayout.Y_AXIS));
		setSize(500,600);
		boxy.setSize(500,600);
		top.setSize(500,500);
		bottom.setSize(500,100);

		
		t = new Timer(200, this);
		
		top.setLayout(new GridLayout(25,25));
		bottom.setLayout(new FlowLayout());
		
		score.setEditable(false);
		highscore.setEditable(false);
		
		for (int i = 0; i < 625; i++){
			grid[i] = new JPanel();
			top.add(grid[i]);
			grid[i].setBackground(Color.white);
			grid[i].addKeyListener(this);
			grid[i].setFocusable(true);
			grid[i].requestFocus();
		}
		
		bottom.add(reset);
		bottom.add(score);
		bottom.add(highscore);
		
		boxy.add(top);
		boxy.add(bottom);
		
		add(boxy);
		
		reset.addActionListener(this);
		reset.setEnabled(false);
		
		// paint north wall black
		for (int i = 0; i < 25; i++){
			grid[i].setBackground(Color.black);
		}
		
		// paint south wall black
		for (int i = 600; i < 625; i++){
			grid[i].setBackground(Color.black);
		}
		
		// paint west wall black
		for (int i = 0; i < 25; i++){
			grid[25*i].setBackground(Color.black);
		}
		
		// paint east wall black
		for (int i = 0; i < 25; i++){
			grid[25*i + 24].setBackground(Color.black);
		}
		
		t.start();
		game.setUpGame();
		repaint();
	}		

  // move snake when arrow keys pressed
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			if (game.dir == 1 || game.dir == 3){
			game.dir = 2;
			}
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			if (game.dir == 1 || game.dir == 3){
			game.dir = 4;
			}
		if (e.getKeyCode() == KeyEvent.VK_UP)
			if (game.dir == 2 || game.dir == 4){
			game.dir = 1;
			}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			if (game.dir == 2 || game.dir == 4){
			game.dir = 3;
			}
	}

	// update board if game not over, else reset game
	public void actionPerformed(ActionEvent evt){
		if (!game.isGameOver()){
			updateBoard();
		}	
		
		
		if (evt.getSource() == reset){
			game.resetGame();
			updateBoard();
			reset.setEnabled(false);
			highscore.setText("High Score: " + String.valueOf(game.highscore));
			for (int i = 0; i < 625; i++){
				grid[i].setFocusable(true);
				grid[i].requestFocus();
			}
		}
	}
	
	public void updateBoard(){
			for (int i = 0; i < game.snklength; i++){
				grid[game.snake.get(i)].setBackground(Color.black);
			}
			for (int i = 0; i < 625; i++){
				if (i % 25 != 0 && i % 25 != 24){
					if (i >= 25 && i < 600){
						if (!game.snake.contains(i)){
							grid[i].setBackground(Color.white);
						}
					}
				}
			}
			
				
			game.move();
			game.pickApple();
			grid[game.apple].setBackground(Color.red);
			repaint();
			
			score.setText("Score: " + String.valueOf(game.score));
			
			
			if (game.isGameOver() == true){
				reset.setEnabled(true);
			}
		
	}

	public static void main(String[]args){
		SnakeGui play = new SnakeGui();
		play.setVisible(true);
		
		play.updateBoard();
	}


	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
