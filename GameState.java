/**********************************************************
 * Assignment: Twos CP2 - GameState
 *
 * Author: Sun-Jung Yum
 *
 * Description: This class represents the game state, which includes the
 * status, score, and the 4x4 grid of values. It does the same things as
 * Checkpoint 1.5, but it also can shift tiles, keep track of the score,
 * and detect when the game is over. 
 * 
 * Academic Integrity: I pledge that this program represents my own work. I
 * received help from no one in designing and debugging my program.
 **********************************************************/

package twosCP2;

import java.util.ArrayList;
import java.util.Arrays;

public class GameState
{
	public final static int ROWS = 4;
	public final static int COLS = 4;

	public final static int LEFT = 0;
	public final static int UP = 1;
	public final static int RIGHT = 2;
	public final static int DOWN = 3;

	private int[][] values;
	private String status;
	private int score;
	private ArrayList<IChangeListener> listeners;
	private String[] directions = { "left", "up", "right", "down" };

	public GameState()
	{
		values = new int[ROWS][COLS];
		listeners = new ArrayList<IChangeListener>();
		status = "hellloooooo";
		score = 0;
		newGame();
	}

	public GameState(int[][] theValues)
	{
		values = copyOriginal(theValues);
		listeners = new ArrayList<IChangeListener>();
		status = "New game started";
		score = 0;
		updateListeners();
	}

	public String getStatus()
	{
		return status;
	}

	public int getScore()
	{
		return score;
	}

	public void addListener(IChangeListener listener)
	{
		listeners.add(listener);
	}

	public void newGame()
	{
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLS; c++)
			{
				values[r][c] = 0;
			}
		}
		if (!boardIsFull())
		{
			assignTiles(2);
		}
		status = "New game started";
		score = 0;
		updateListeners();
	}

	public void assignTiles(int number)
	{
		for (int i = 0; i < number; i++)
		{
			int row = (int) (Math.random() * ROWS);
			int col = (int) (Math.random() * COLS);
			while (values[row][col] != 0)
			{
				row = (int) (Math.random() * ROWS);
				col = (int) (Math.random() * COLS);
			}
			int num = (int) (Math.random() * 10);
			if (num < 9)
			{
				values[row][col] = 2;
			}
			else
			{
				values[row][col] = 4;
			}
		}
	}

	public void shift(int direction)
	{
		int[][] original = copyOriginal(values);
		ArrayList<Integer> line = new ArrayList<Integer>();
		if (direction == UP || direction == DOWN)
		{
			for (int c = 0; c < COLS; c++)
			{
				for (int r = 0; r < ROWS; r++)
				{
					line.add(values[r][c]);
				}
				shiftOneLine(direction, c, line);
				replaceOneLine(direction, c, line);
				line.clear();
			}
		}
		else
		{
			for (int r = 0; r < ROWS; r++)
			{
				for (int c = 0; c < COLS; c++)
				{
					line.add(values[r][c]);
				}
				shiftOneLine(direction, r, line);
				replaceOneLine(direction, r, line);
				line.clear();
			}
		}
		if (!arraysIdentical(original))
		{
			assignTiles(1);
		}

		if (!legalMovesLeft())
		{
			status = "Game over!";
		}
		else
		{
			status = "Shifted tiles " + directions[direction];
		}

		updateListeners();
	}

	private boolean legalMovesLeft()
	{
		if (!boardIsFull())
		{
			return true;
		}
		if (verticalMovesLeft() > 0)
		{
			return true;
		}
		if (horizontalMovesLeft() > 0)
		{
			return true;
		}
		return false;
	}

	private int horizontalMovesLeft()
	{
		int count = 0;
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 1; c < COLS; c++)
			{
				if (values[r][c] == values[r][c - 1])
				{
					count++;
				}
			}
		}
		return count;
	}

	private int verticalMovesLeft()
	{
		int count = 0;
		for (int c = 0; c < COLS; c++)
		{
			for (int r = 1; r < ROWS; r++)
			{
				if (values[r][c] == values[r - 1][c])
				{
					count++;
				}
			}
		}
		return count;
	}

	private boolean arraysIdentical(int[][] original)
	{
		if (original.length != values.length)
		{
			return false;
		}
		if (original[0].length != values[0].length)
		{
			return false;
		}
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLS; c++)
			{
				if (values[r][c] != original[r][c])
				{
					return false;
				}
			}
		}
		return true;
	}

	private int[][] copyOriginal(int[][] initial)
	{
		int[][] original = new int[initial.length][initial[0].length];
		for (int r = 0; r < ROWS; r++)
		{
			original[r] = Arrays.copyOf(initial[r], initial[r].length);
		}
		return original;
	}

	private void replaceOneLine(int direction, int constant, ArrayList<Integer> line)
	{
		for (int i = 0; i < line.size(); i++)
		{
			if (direction == UP || direction == DOWN)
			{
				values[i][constant] = line.get(i);
			}
			else
			{
				values[constant][i] = line.get(i);
			}
		}
	}

	private void shiftOneLine(int direction, int c, ArrayList<Integer> line)
	{
		removeZeros(line);
		if (line.size() > 1)
		{
			if (direction == DOWN || direction == RIGHT)
			{
				int end = line.size() - 1;
				for (int i = end; i > 0; i--)
				{
					if (line.get(i).compareTo(line.get(i - 1)) == 0)
					{
						line.set(i - 1, line.remove(i) * 2);
						score += line.get(i - 1);
						i--;
					}
				}
			}
			else
			{
				for (int i = 0; i < line.size() - 1; i++)
				{
					if (line.get(i).compareTo(line.get(i + 1)) == 0)
					{
						line.set(i, line.remove(i + 1) * 2);
						score += line.get(i);
					}
				}

			}
		}
		while (line.size() < 4)
		{
			if (direction == DOWN || direction == RIGHT)
			{
				line.add(0, 0);
			}
			else
			{
				line.add(0);
			}
		}
	}

	private void removeZeros(ArrayList<Integer> column)
	{
		int i = 0;
		while (i < column.size())
		{
			if (column.get(i) == 0)
			{
				column.remove(i);
			}
			else
			{
				i++;
			}
		}
	}

	private boolean boardIsFull()
	{
		int count = 0;
		for (int r = 0; r < ROWS; r++)
		{
			for (int c = 0; c < COLS; c++)
			{
				if (values[r][c] == 0)
				{
					count++;
				}
			}
		}
		return count == 0;
	}

	public int getValue(int r, int c)
	{
		return values[r][c];
	}

	private void updateListeners()
	{
		for (IChangeListener n : listeners)
		{
			n.redraw();
		}
	}
}