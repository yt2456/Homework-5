import java.awt.BorderLayout;
import java.awt.Container;
import java.io.*;
import java.net.URL;
import java.util.*;

import javax.swing.*;


public class MapDist extends JFrame
{
	private BufferedReader diskInput;
	private String filename = "cityxy.txt";
	private ArrayList<CityNode> cities = new ArrayList<CityNode>(29);
	private ArrayList<CityNode> unlinked;
	private ArrayList<LinkedList<CityNode>> linkedset = new ArrayList<LinkedList<CityNode>>();
	private ArrayList<Edge> finalEdges = new ArrayList<Edge>(28);
	private double[][] distances = new double[29][29];
	Comparator<Edge> comp = new EdgeComparator();
    PriorityQueue<Edge> queue = new PriorityQueue<Edge>(10, comp);
    JFrame frame;
    
    
	
	public static void main(String args[])
	{
		MapDist map = new MapDist();
		
		map.initialize();
	}
	
	public void initialize()
	{
		try
		{
			URL text = new URL("http://www.cs.columbia.edu/~jweisz/W3137/homework_files/cityxy.txt");
			diskInput = new BufferedReader(new InputStreamReader(
					text.openStream()));
            
			
			String line;
			String[] splited;
			int currentNum = 0;
			 while ((line = diskInput.readLine()) != null)
			 {
				 splited = line.split("\\s+");
				 cities.add(new CityNode(splited[0], Integer.parseInt(splited[1]), Integer.parseInt(splited[2]), currentNum)); 
				 currentNum++;
			 }
			  
			 calculateDistances();
		}
		catch (IOException e)
		{
			System.out.println("io exception!");
		}
		
		printCities();
		Kruskals();
		//printMST();
		
		frame = new JFrame("Map");
		frame.setSize(1000, 1000);
		Container contentPane = frame.getContentPane();
		contentPane.add(new Drawing(cities, finalEdges));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void printCities()
	{
		for(int x = 0; x < cities.size(); x++)
		{
			System.out.println(cities.get(x).toString());
		}
	}
	
	public void printMST()
	{
		for(int x = 0; x < finalEdges.size(); x++)
		{
			System.out.println(finalEdges.get(x).toPrint());
		}
	}
	
	private void calculateDistances()
	{
		for(int x = 0; x < distances.length; x++)
		{
			for(int y = 0; y < distances.length; y++)
			{
				if( x == y)
				{
					distances[x][y] = 0;
				}
				else
				{
					distances[x][y] = Math.sqrt(Math.pow((cities.get(x).getX() - cities.get(y).getX()), 2) + 
						Math.pow((cities.get(x).getY() - cities.get(y).getY()), 2));   
				}
			}
		}
	}
	
	public void Kruskals()
	{
		for(int x = 0; x < (distances.length - 1); x++)
		{
			for(int y = x + 1; y < distances.length; y++)
			{		
				queue.add(new Edge(cities.get(x), cities.get(y),
						distances[x][y]));
			}
		}
		
		unlinked = new ArrayList<CityNode>(cities);
				
		int connections = 0;
		Edge temp;
		CityNode toAdd = null;
		
		while(connections != 28)
		{
			temp = queue.remove();
			
			if(connections == 0)
			{
				LinkedList<CityNode> t = new LinkedList<CityNode>();
				t.add(temp.getA());
				t.add(temp.getB());
				linkedset.add(t);
				
				unlinked.set(temp.getA().getCityNum(), null);
				unlinked.set(temp.getB().getCityNum(), null);
				finalEdges.add(temp);
				connections++;
			}
			else
			{
				if(unlinked.get(temp.getA().getCityNum()) != null && unlinked.get(temp.getB().getCityNum()) != null)
				{
					LinkedList<CityNode> t = new LinkedList<CityNode>();
					t.add(temp.getA());
					t.add(temp.getB());
					linkedset.add(t);
					
					unlinked.set(temp.getA().getCityNum(), null);
					unlinked.set(temp.getB().getCityNum(), null);
					finalEdges.add(temp);
					connections++;
				}
				else
				{
					for(int x = 0; x < linkedset.size(); x++)
					{
						ListIterator<CityNode> itr = linkedset.get(x).listIterator();
						int same = 0;
						while(itr.hasNext())
						{
							CityNode comp = itr.next();
							if(comp.getCityNum() == temp.getA().getCityNum())
							{
								same++;
								toAdd = temp.getB();
							}
							
							if(comp.getCityNum() == temp.getB().getCityNum())
							{
								same++;
								toAdd = temp.getA();
							}
						}
						
						if(same == 2)
						{
							break;
						}
						
						if(same == 1)
						{
							linkedset.get(x).add(toAdd);
							
							unlinked.set(temp.getA().getCityNum(), null);
							unlinked.set(temp.getB().getCityNum(), null);
							finalEdges.add(temp);
							connections++;
								
							break;
						}			
					}
					
					merge();
				}
			}	
		}
	}
	
	public void merge()
	{
		int merge1 = -1;
		int merge2 = -1;
		
		for(int x = 0; x < linkedset.size(); x++)
		{
			ListIterator<CityNode> itr = linkedset.get(x).listIterator();

			while(itr.hasNext())
			{
				CityNode comp1 = itr.next();
				
				for(int y = x + 1; y < linkedset.size(); y++)
				{
					ListIterator<CityNode> itr2 = linkedset.get(y).listIterator();
					
					while(itr2.hasNext())
					{
						CityNode comp2 = itr2.next();
						
						if(comp1.getCityNum() == comp2.getCityNum())
						{
							itr2.remove();
							merge1 = x;
							merge2 = y;
						}
					}
				}
			}
		}
		
		if( merge1 >= 0 && merge2 >= 0)
		{
			ListIterator<CityNode> itr3 = linkedset.get(merge2).listIterator();
		
			while(itr3.hasNext())
			{
				linkedset.get(merge1).add(itr3.next());
			}
		
			linkedset.get(merge2).clear();
		}
	}
}
