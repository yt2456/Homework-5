
public class Edge 
{
	private CityNode a;
	private CityNode b;
	private double weight;
	
	public Edge(CityNode x, CityNode y, double w)
	{
		a = x;
		b = y;
		weight = w;
	}
	
	public CityNode getA()
	{
		return a;
	}
	
	public CityNode getB()
	{
		return b;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public String toPrint()
	{
		return a.getCity() + " " + b.getCity() + " " + weight;
	}
}
