import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.*;

public class Drawing extends JPanel
{
	 ArrayList<CityNode> city;
	 ArrayList<Edge> fEdges;
	 
	 public Drawing( ArrayList<CityNode> c, ArrayList<Edge> f)
	 {
		 city = c;
		 fEdges = f;
	 }
	 
	public void paintComponent(Graphics g )
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);
        
        Font f = new Font("SansSerif", Font.BOLD, 9);
        g.setFont(f);

        for(int x = 0; x < city.size(); x++) 
        {
            Ellipse2D.Double circle = new Ellipse2D.Double(city.get(x).getX()/3 + 5, (1500 - city.get(x).getY())/3, 7, 7);
            g2d.fill(circle);
            g.drawString(city.get(x).getCity(), (city.get(x).getX() + 40)/3, (1525 - city.get(x).getY())/3);
        }
        
        g2d.setPaint(Color.red);
        for(int y = 0; y < fEdges.size(); y++)
        {
        	g2d.drawLine(fEdges.get(y).getA().getX()/3 + 8, (1500 - fEdges.get(y).getA().getY())/3 + 3, fEdges.get(y).getB().getX()/3 + 8, (1500 - fEdges.get(y).getB().getY())/3 + 3);
        }
    }
}
