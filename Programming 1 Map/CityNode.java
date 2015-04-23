
public class CityNode
{
	private String city;
	private int xCoord;
	private int yCoord;
	private int cityNum;
	
	public CityNode(String c, int x, int y, int cn)
	{
		city = c;
		xCoord = x;
		yCoord = y;
		cityNum = cn;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public int getX()
	{
		return xCoord;
	}
	
	public int getY()
	{
		return yCoord;
	}
	
	public int getCityNum()
	{
		return cityNum;
	}
	
	public String toString()
	{
		return city + " " + xCoord + " " + yCoord;
	}
}
