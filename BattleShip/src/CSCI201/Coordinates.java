//Emma Lautz: 5040437826
//Assignment2
package CSCI201;
//Coordinate class contains both an x and a y coordinate
public class Coordinates {
	private int x;
	private int y;
	
	//constructor
	public Coordinates(int i, int j)
	{
		x = i;
		y = j;
	}
	
	//returns x coordinate
	public int getX()
	{
		return x;
	}
	
	//returns y coordinate
	public int getY()
	{
		return y;
	}
	
	//set the x coordinate
	public void setX(int v)
	{
		x = v;
	}
	
	//sets the y coordinate
	public void setY(int w)
	{
		y = w;
	}
	
}
