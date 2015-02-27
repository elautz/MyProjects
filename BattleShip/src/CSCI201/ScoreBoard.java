//Emma Lautz: 5040437826
//Assignment2
package CSCI201;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//sets up the GUI for the final score board
public class ScoreBoard extends JFrame{

	private static final long serialVersionUID = 1L;
	//contains all the JLabels for the score board
	private JLabel [] scores = new JLabel [11];
	//contains all the high scoring players' information
	private HighScore [] players;
	
	public ScoreBoard(HighScore [] args, String player)
	{
		super("ScoreBoard");
		players = args;
		setSize(300, 600);
		setLocation(100, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		scores[0] = new JLabel(("Hello "+player+"! Here is the Highscore List:"));
		scores[0].setPreferredSize(new Dimension(250,40));
		
		//sets each label with the high score information from the array
		for (int i = 1; i<11; i++)
		{
			if (players[i-1].getScore() != 0)
			{
				HighScore p = players[i - 1];
				scores[i] = new JLabel((Integer.toString(i)+". "+p.getPlayer() + " - "+p.getScore()));
			}
			else
			{
				scores[i] = new JLabel((Integer.toString(i) + "."));
			}
			scores[i].setPreferredSize(new Dimension(150,40));
		}
	
		//adding the labels to a panel
		JPanel panel = new JPanel();
		for (int j = 0; j<11; j++)
		{
			panel.add(scores[j]);
		}
		add(panel, BorderLayout.CENTER);
	}
	
	//determines whether to show the user the GUI
	public void showUser(Boolean b)
	{
		setVisible(b);
	}
}
