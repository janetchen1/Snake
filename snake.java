// configuration
import java.util.ArrayList;

public class Snake {
	
	// array for board
	int[] board = new int[625];
	
	public int dir;
	public int snklength;
	public ArrayList<Integer> snake = new ArrayList<Integer>(snklength);
	public int apple;
	public int score = 0;
	public int highscore = 0;
	public int possible;
	public boolean valid;
	public boolean cond1;
	public boolean cond2;
	public boolean cond3;
	public int temphead;
	
  // Initializes game.
	public void setUpGame(){
	  // initialize board
		for (int i = 0; i < board.length; i++){
			board[i] = i;
		}
		
		// initialize snake in "1" direction
		dir = 1;
		
		// initialize snake length
		snklength = 2;
		
		//set apple
		valid = false;
		cond1 = false;
		cond2 = false;
		cond3 = false;

		while (valid == false){
		
		  // select random possible starting point for apple
			possible = (int) Math.floor(Math.random()*625);
			
			// check for top and bottom walls
			if (possible > 24 && possible < 600){
				cond1 = true;
			}
			
			// check for left and right walls
			if (possible % 25 != 0 && possible % 25 != 24){
				cond2 = true;
			}
			
			// if apple in valid position, place apple
			if (cond1 == true && cond2 == true){
				valid = true;
				apple = possible;
			}
		}
		
		// reset variables for next round
		valid = false;
		cond1 = false;
		cond2 = false;
		cond3 = false;
		
    // start snake head at position 342.
		snake.add(0, 342);
		snake.add(1, 367);
	}
	
  // Moves snake.
	public void move(){
		
		// get position of head	
		temphead = snake.get(0);
				
		// if facing north
		if (dir == 1){
		  // shift head one up
			snake.set(0, snake.get(0) - 25);
		}
		
		// if facing south
		if (dir == 3){
		  // shift head accordingly
			snake.set(0, snake.get(0) + 25);
		}
		
		// if facing east
		if (dir == 2){
			snake.set(0, snake.get(0) + 1);
		}
		
		// if facing west
		if (dir == 4){
			snake.set(0, snake.get(0) - 1);
		}
		
		// to move body of snake, set block to previous position of preceding block
		for (int i = snklength-1; i > 1; i--){
			snake.set(i, snake.get(i - 1));
		}
		
		// move block following head to former location of head
		snake.set(1, temphead);
		
	}

	// "Eats" apple when snake head makes contact with it.  
	public int pickApple(){
		if (snake.get(0) == apple){
			
			valid = false;

			while (valid == false){
				cond1 = false;
				cond2 = false;
				cond3 = false;
				possible = (int) Math.floor(Math.random()*625);
				if (possible > 24 && possible < 600){
					cond1 = true;
				}
				
				if (possible % 25 != 0 && possible % 25 != 24){
					cond2 = true;
				}
				
				if (!snake.contains(possible)){
					cond3 = true;
				}
				if (cond1 == true && cond2 == true){
					if (cond3 == true){
						valid = true;
						apple = possible;
					}
				}
			}
			
			valid = false;
			cond1 = false;
			cond2 = false;
			cond3 = false;
			
			score++;
			int diff = snake.get(snklength - 1) - snake.get(snklength - 2);
			snake.add(snake.get(snklength - 1) + diff);
			snklength++;
		}	
		
		return score;
	}
	
	// Checks for collisions with walls or snake body
	public boolean collide(){
		//hits itself
		if (snklength >= 4){
			int head = snake.get(0);
			for (int i = 1; i < snklength; i++){
				if (snake.get(i) == head){
					return true;
				}
			}
		}
		
		// hits north wall
		if (snake.get(0) <= 24 && snake.get(0) >= 0){
			return true;
		}
		
		// hits south wall
		if (snake.get(0) >= 600 && snake.get(0) <= 624){
			return true;
		}
		
		// hits east or west wall
		for (int i = 0; i <= 24; i++){
			if (snake.get(0) == 24 + 25*i || snake.get(0) == 25*i){
				return true;
			}
		}
		
		return false;
		
	}
	
	// Checks if game is over (has collision happened)
	public boolean isGameOver(){
		if (collide()){
			return true;
		}
		else
		return false;
	}

	// Resets game on game over.
	public void resetGame(){
		if (collide()){
			if (score > highscore){
				highscore = score;
			}
			
			score = 0;
			
			
			dir = 1;
			
			valid = false;
			cond1 = false;
			cond2 = false;
			cond3 = false;
			
			
			while (valid == false){
				possible = (int) Math.floor(Math.random()*625);
				if (possible > 24 && possible < 600){
					cond1 = true;
				}
				if (possible % 25 != 0 && possible % 25 != 24){
					cond2 = true;
				}
				
				if (!snake.contains(possible)){
					cond3 = true;
				}
				
				if (cond1 == true && cond2 == true){
					if (cond3 == true){
						valid = true;
						apple = possible;
					}
				}
			}
			

			valid = false;
			cond1 = false;
			cond2 = false;
			cond3 = false;
			
			snake.subList(2, snklength).clear();
			snklength = 2;
			snake.set(0, 342);
			snake.set(1, 367);
			
		}
	}
}
