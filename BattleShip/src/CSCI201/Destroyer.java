//Emma Lautz: 5040437826
//Assignment2
package CSCI201;
//Destroyer ship object that contains its coordinates and which have been hit
public class Destroyer {
	private Coordinates C1;
	private Coordinates C2;
	Boolean C1Hit = false;
	Boolean C2Hit = false;
	
	//constructor
	public Destroyer(Coordinates y, Coordinates x)
	{
		C1 = x;
		C2 = y;
	}
	
	//default constructor
	public Destroyer()
	{
		C1 = null;
		C2 = null;
	}
	
	//hits the appropriate coordinate of the ship, returns 1 to indicate success
	//returns -1 if it was a miss
	public int sink(int i, int j)
	{
		
		if (i == C1.getX() && j == C1.getY())
		{
			C1Hit = true;
			return 1;
		}
		else if (i == C2.getX() && j == C2.getY())
		{
			C2Hit = true;
			return 1;
		}
		else
		{
			return -1;
		}
			
	}
	
	//returns whether the ship has been sunk
	public Boolean isSunk()
	{
		if (C1Hit && C2Hit)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Boolean contains(int i, int j)
	{
		return (((C1.getX() == i) && (C1.getY() == j)) || ((C2.getX() == i) && (C2.getY() == j)));
	}
	public Coordinates getX()
	{
		return C1;
	}
	
	public Coordinates getY()
	{
		return C2;
	}

}
