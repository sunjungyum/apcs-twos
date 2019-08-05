/**********************************************************
 * Assignment: Twos CP2 - ActionHandler
 *
 * Author: Sun-Jung Yum
 *
 * Description: This class routes the users' actions to the grid and
 * implements action listener. It is able to act upon the user pressing
 * the "New Game" button and the user pressing one of the arrow keys.
 *
 * Academic Integrity: I pledge that this program represents my own work. I
 * received help from no one in designing and debugging my program.
 **********************************************************/

package twosCP2;

import java.awt.event.*;

public class ActionHandler implements ActionListener
{
	private GameState grid;

	public ActionHandler(GameState g)
	{
		this.grid = g;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equalsIgnoreCase("new game"))
			grid.newGame();
	}

	public void handleArrowPress(int direction)
	{
		grid.shift(direction);
	}
}