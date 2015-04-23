import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;


public class WordHunt
{
	private BufferedReader diskInput;
	private BufferedReader secondInput;
	private BufferedReader dictInput;
	Scanner input = new Scanner(System.in);
	private int nLength;
	private WordNode[] letters = new WordNode[18];
	private LinkNode[] adjList = new LinkNode[18];
	private String[] hash = new String[304961];
	private LinkNode header = new LinkNode(null, null);
	private LinkNode currentNode = header;
	private LinkNode legitWordHeader = new LinkNode(null, null);
	private LinkNode legitCNode = legitWordHeader;
	
	public static void main(String args[])
	{
		WordHunt w = new WordHunt();
		w.readIn();
		w.wHunt();
		//w.foundWords();
		w.isLegal();
		w.printLegal();
	}
	
	public void readIn()
	{
		try
		{
			URL text = new URL("http://www.cs.columbia.edu/~jweisz/W3137/homework_files/graph.txt");
			diskInput = new BufferedReader(new InputStreamReader(
					text.openStream()));
			secondInput = new BufferedReader(new InputStreamReader(
					text.openStream()));
			
			String line;
			String letter;
			String[] splited;
			int counter = 0;
			
			 while ((line = diskInput.readLine()) != null)
			 {
				 if(Character.isDigit(line.charAt(0)) == false)
				 {
					 letter = line;
					 line = diskInput.readLine();
					 splited = line.split("\\s+");
					 letters[counter] = new WordNode(letter, counter, Integer.parseInt(splited[0]), Integer.parseInt(splited[1]), Integer.parseInt(splited[2]));
					 counter++;
				 }
			 }
			 
			//printLetterNodes();
			 
			 counter = 0;
			 LinkNode current;
			 line = secondInput.readLine();
			 while(line != null)
			 {
				 
				 if(Character.isDigit(line.charAt(0)) == false)
				 {
					 line = secondInput.readLine();
					 line = secondInput.readLine();
					 
					 adjList[counter] = new LinkNode(null, null);
									 
					 while(Character.isDigit(line.charAt(0)) == true)
					 {	
						 for(int x = 0; x < letters.length; x++)
						 {
							 splited = line.split("\\s+");
							 if(letters[x].isThisNode(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]), Integer.parseInt(splited[2])) == true)
							 {
								 current = adjList[counter];
								 while(current.getNext() != null)
								 {
									 current = current.getNext();
								 }
								 
								 current.setNext(new LinkNode(letters[x],null));
								 break;
							 }
						 }	 
						 line = secondInput.readLine();
						 if(line == null)
						 {
							 break;
						 }
					 }
					 counter++;
					 continue;
				 }
				 line = secondInput.readLine();
			 }
			
			 //printAdjList();			 

			URL dicttext = new URL("http://www.cs.columbia.edu/~jweisz/W3137/homework_files/largedictionary.txt");
			dictInput = new BufferedReader(new InputStreamReader(
						dicttext.openStream()));
			
			int hashCode;
			
			while((line = dictInput.readLine()) != null)
			{
				hashCode = hash(line, hash.length);
				
				if( hash[hashCode] != null)
				{
					while(hash[hashCode] != null)
					{
						hashCode++;
					}
					
					hash[hashCode] = line;
				}
				else
				{
					hash[hashCode] = line;
				}
			}          
		}
		catch (IOException e)
		{
			System.out.println("io exception!");
		}
		
	}
	
	public int hash(String word, int tableSize)
	{
		int hashVal = 997;
		
		for( int i = 0; i < word.length(); i++)
		{
			hashVal = 63 * hashVal + word.charAt(i);
		}
		
		hashVal %= tableSize;
		
		if( hashVal < 0)
			hashVal += tableSize;
		
		return hashVal;
	}
	
	public void printLetterNodes()
	{
		for(int x = 0; x < letters.length; x++)
		{
			System.out.println(letters[x].toString());
		}
	}
	
	private void printAdjList()
	{
		LinkNode current;
		for(int x = 0; x < adjList.length; x++)
		{
			current = adjList[x];
			while(current.getNext() != null)
			{
				System.out.print(current.getNext().getWordNode().toString() + " ");
				current = current.getNext();
			}
			
			System.out.println();
		}
	}
	
	private void foundWords()
	{
		currentNode = header.getNext();
		while( currentNode != null)
		{
			System.out.println(currentNode.getWord());
			currentNode = currentNode.getNext();
		}
	}
	
	public void wHunt()
	{
		System.out.println("Please the word length: ");
		
		nLength = input.nextInt();
		String cWord;
	
		for(int x = 0; x < letters.length; x++)
		{
			cWord = letters[x].getLetter();
		
	//		currentNode.setNext(new LinkNode(cWord, null));
	//		currentNode = currentNode.getNext();
			
			makeWord(cWord, x, nLength - 1);	
		}

	}

	public String makeWord(String current, int prevNode, int lLeft)
	{
		LinkNode cNode = adjList[prevNode].getNext();
		String cWord;	
		
		if(lLeft == 0)
		{
			currentNode.setNext(new LinkNode(current, null));
			currentNode = currentNode.getNext();
			return null;
		}
		else
		{
			while( cNode!= null)
			{
				cWord = current + cNode.getWordNode().getLetter();
				//System.out.println(cWord);
				//currentNode.setNext(new LinkNode(cWord, null));
				//currentNode = currentNode.getNext();
				
				makeWord(cWord, cNode.getWordNode().getNum() ,lLeft - 1);
				
				
				cNode = cNode.getNext();
			}
		}
		
		return null;
	}
	
	public void isLegal()
	{
		currentNode = header.getNext();
		int hashVal;
		boolean alreadyF = false;;
		
		while(currentNode != null)
		{
			hashVal = hash(currentNode.getWord(), hash.length);
		
			if(hash[hashVal] != null)
			{
				while(currentNode.getWord().equals(hash[hashVal]) == false)
				{
					if( hashVal < 304960)
					{
						hashVal++;
					}
					else
					{
						if(++hashVal > 304960)
						{
							hashVal = 0;
							break;
						}
					}
					
					if(hash[hashVal] == null)
					{
						break;
					}
				}
				
				if(currentNode.getWord().equals(hash[hashVal]))
				{
					LinkNode c = legitWordHeader.getNext();
					while(c != null)
					{
						if(currentNode.getWord().equals(c.getWord()))
						{
							alreadyF = true;
						}
					   
					    c = c.getNext();
					}
					
					if(alreadyF == false)
					{
						legitCNode.setNext(new LinkNode(currentNode.getWord(), null));
						legitCNode = legitCNode.getNext();
					}
					else
					{
						alreadyF = false;
					}

				}
			}
			
			currentNode = currentNode.getNext();
		}
	}
	
	public void printLegal()
	{
		legitCNode = legitWordHeader.getNext();
		System.out.println(legitCNode.getWord());
		System.out.println("Legal Words");
		while( legitCNode != null)
		{
			System.out.println(legitCNode.getWord());
			legitCNode = legitCNode.getNext();
		}
	}
	
	
}
