
public class WordNode 
{
	private String letter;
	private int lNum;
	private int x;
	private int y;
	private int z;
	
	public WordNode(String l, int n, int xCoord, int yCoord, int zCoord)
	{
		letter = l;
		lNum = n;
		x = xCoord;
		y = yCoord;
		z = zCoord;
	}
	
	public String getLetter()
	{
		return letter;
	}
	
	public int getNum()
	{
		return lNum;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getZ()
	{
		return z;
	}
	
	public boolean isThisNode(int a, int b, int c)
	{
		if(a == x && b == y && z == c)
		{
			return true;
		}
		
		return false;
	}
	
	public String toString()
	{
		return letter + " " + x + " " + y + " " + z;
	}
}
