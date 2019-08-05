/**********************************************************
 * Assignment: Twos CP2 - GameView
 *
 * Author: Sun-Jung Yum
 *
 * Description: This class represents the game view, which has all the GUI
 * code. It does the same things as CP1.5.
 * 
 * Academic Integrity: I pledge that this program represents my own work. I
 * received help from no one in designing and debugging my program.
 **********************************************************/

package twosCP2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class GameView extends JFrame implements IChangeListener
{
	private JLabel status;
	private JLabel score;
	private GameState grid;
	private JLabel[] tiles;
	private ActionHandler action;
	private Color[] colors = {new Color(215, 207, 186), new Color(255, 253, 246), new Color(247, 237, 212), new Color(252, 186, 71),
			new Color(253, 160, 66), new Color(249, 129, 50), new Color(253, 107, 34), new Color(253, 230, 83), new Color(70, 70, 70)};

	public GameView(GameState g)
	{
		status = new JLabel("hellooo");
		score = new JLabel("hellooo");
		grid = g;
		grid.addListener(this);
		action = new ActionHandler(grid);

		setTitle("Sun-Jung's Twos Game");
		setSize(300, 300);
		setLayout(new BorderLayout());

		add(buildBottomPanel(), BorderLayout.SOUTH);
		add(buildCenterPanel(action), BorderLayout.CENTER);
		add(buildTopPanel(action), BorderLayout.NORTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void display()
	{
		setVisible(true);
	}

	@Override
	public void redraw()
	{
		status.setText(grid.getStatus());
		score.setText("Score: " + grid.getScore() + " ");

		int counter = 0;
		for (int r = 0; r < grid.ROWS; r++)
		{
			for (int c = 0; c < grid.COLS; c++)
			{
				if (grid.getValue(r, c) == 0)
				{
					tiles[counter].setText("");
					tiles[counter].setOpaque(false);
				}
				else
				{
					tiles[counter].setText(grid.getValue(r, c) + "");
					if (grid.getValue(r, c) == 2)
					{
						tiles[counter].setBackground(colors[1]);
					}
					else if (grid.getValue(r, c) == 4)
					{
						tiles[counter].setBackground(colors[2]);
					}
					else if (grid.getValue(r, c) == 8)
					{
						tiles[counter].setBackground(colors[3]);
					}
					else if (grid.getValue(r, c) == 16)
					{
						tiles[counter].setBackground(colors[4]);
					}
					else if (grid.getValue(r, c) == 32)
					{
						tiles[counter].setBackground(colors[5]);
					}
					else if (grid.getValue(r, c) == 64)
					{
						tiles[counter].setBackground(colors[6]);
					}
					else if (grid.getValue(r, c) >= 128 && grid.getValue(r, c) <= 2048)
					{
						tiles[counter].setBackground(colors[7]);
					}
					else
					{
						tiles[counter].setBackground(colors[8]);
					}
					tiles[counter].setOpaque(true);
				}
				counter++;
			}
		}
	}

	private JPanel buildBottomPanel()
	{
		JPanel bottom = new JPanel();
		bottom.setBackground(colors[0]);

		status = new JLabel(grid.getStatus());
		bottom.add(status);

		return bottom;
	}

	private JPanel buildTopPanel(ActionHandler handler)
	{
		JPanel top = new JPanel(new BorderLayout());
		top.setBackground(colors[0]);
		
		JButton button = new JButton("New Game");
		top.add(button, BorderLayout.WEST);
		button.addActionListener(handler);

		score = new JLabel("Score: " + grid.getScore() + " ");
		top.add(score, BorderLayout.EAST);

		return top;
	}

	private JPanel buildCenterPanel(ActionHandler handler)
	{
		JPanel center = new JPanel(new GridLayout(grid.ROWS, grid.COLS, 5, 5));
		center.setBackground(colors[0]);
		tiles = new JLabel[grid.ROWS * grid.COLS];

		int counter = 0;
		for (int r = 0; r < grid.ROWS; r++)
		{
			for (int c = 0; c < grid.COLS; c++)
			{
				if (grid.getValue(r, c) == 0)
				{
					tiles[counter] = new JLabel("", SwingConstants.CENTER);
					tiles[counter].setOpaque(false);
				}
				else
				{
					tiles[counter] = new JLabel(grid.getValue(r, c) + "", SwingConstants.CENTER);
					if (grid.getValue(r, c) == 2)
					{
						tiles[counter].setBackground(colors[1]);
					}
					else if (grid.getValue(r, c) == 4)
					{
						tiles[counter].setBackground(colors[2]);
					}
					else if (grid.getValue(r, c) == 8)
					{
						tiles[counter].setBackground(colors[3]);
					}
					else if (grid.getValue(r, c) == 16)
					{
						tiles[counter].setBackground(colors[4]);
					}
					else if (grid.getValue(r, c) == 32)
					{
						tiles[counter].setBackground(colors[5]);
					}
					else if (grid.getValue(r, c) == 64)
					{
						tiles[counter].setBackground(colors[6]);
					}
					else if (grid.getValue(r, c) >= 128 && grid.getValue(r, c) <= 2048)
					{
						tiles[counter].setBackground(colors[7]);
					}
					else
					{
						tiles[counter].setBackground(colors[8]);
						tiles[counter].setForeground(Color.WHITE);
					}
					tiles[counter].setOpaque(true);
				}
				center.add(tiles[counter]);
				counter++;
			}
		}

		bindArrows(handler, center);
		return center;
	}

	private void bindArrows(ActionHandler handler, JPanel panel)
	{
		String[] commands = { "left arrow", "up arrow", "right arrow", "down arrow" };
		for (int i = 0; i < 4; i++)
		{
			int copy = i;
			KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT + i, 0);
			Action action = new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					handler.handleArrowPress(GameState.LEFT + copy);
				}
			};
			panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, commands[i]);
			panel.getActionMap().put(commands[i], action);
		}
	}
}