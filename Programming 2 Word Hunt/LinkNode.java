
public class LinkNode {

	private Object data;
	private LinkNode next;

	public LinkNode(Object d, LinkNode n)
	{
		data = d;
		next = n;
	}
	
	public WordNode getWordNode()
	{
		return (WordNode) data;
	}
	
	public LinkNode getNext()
	{
		return next;
	}
	
	public void setNext(LinkNode n)
	{
		next = n;
	}

	public String getWord()
	{
		return (String) data;
	}
}
