/**********************************************************
 * Assignment: Twos CP2 - Twos
 *
 * Author: Sun-Jung Yum
 *
 * Description: This class creates the GameState and GameView object and
 * runs the program.
 *
 * Academic Integrity: I pledge that this program represents my own work. I
 * received help from no one in designing and debugging my program.
 **********************************************************/

package twosCP2;

import twosCP2.GameState;
import twosCP2.GameView;

public class Twos
{
	public static void main(String[] args)
	{
		GameState grid = new GameState();
		GameView game = new GameView(grid);

		grid.newGame();
		game.display();
	}

}