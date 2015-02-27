//Emma Lautz: 5040437826
//Assignment2
package CSCI201;
//this class stores the information of a highscore player
public class HighScore {

	private int score;
	private String player;
	private int place;
	
	//highscore constructor that takes in the player's name, their score, and the place they've won
	public HighScore(String name, int points, int place)
	{
		score = points;
		player = name;
		this.place = place;
	}
	
	//returns the player name
	public String getPlayer()
	{
		return player;
	}
	
	//returns the players score
	public int getScore()
	{
		return score;
	}
	
	//sets the place of the player
	public void setPlace(int p)
	{
		place = p;
	}
	
	//sets the player name
	public void setPlayer(String n)
	{
		player = n;
	}
	
	//returns the place
	public int getPlace()
	{
		return place;
	}
	
	//sets the score
	public void setScore(int s)
	{
		score = s;
	}
}
