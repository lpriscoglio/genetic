import java.io.IOException;
import java.net.URL;
import java.util.Scanner;


public class InstanceGen {

	private int flows[] [];
	private int distances[] [];
	private int instCount = 0;
	
    public InstanceGen(String url) {
    	
    	try {
        		URL myUrl = new URL(url);
				Scanner s = new Scanner(myUrl.openStream());
				instCount = retrieveCount(s);
		    	flows = getMatrix(instCount,s);
		    	distances = getMatrix(instCount,s);
				s.close();
    		}
    		catch(IOException ex) {
    			flows = null;
    			distances = null;
    			ex.printStackTrace(); // for now, simply output it.
    		}

    }
    
	
	public int getInstanceCount()
	{
		return this.instCount;
	}	
	
	public int [][] getFlows()
	{
		return this.getFlows();
	}

	public int [][] getDistances()
	{
		return this.getDistances();
	}
	
	public int retrieveCount(Scanner s)
	{
		Scanner in;
		if (s.hasNextLine()) 
		{
			in = new Scanner(s.nextLine());
			instCount = in.nextInt();
			in.close();
		}
		return instCount;
	}
	
	public int[][] getMatrix(int instCount, Scanner s)
	{
		Scanner in;
		int [][] res = new int [instCount][instCount];
		String temp;
		
		for(int i=0; i<instCount; i++)
		{
			if(s.hasNextLine())
			{
				while( ((temp = s.nextLine()) == null) || temp.trim().length() == 0)
				{
					continue;
				}
				in = new Scanner(temp);
				for(int j=0; j<instCount; j++)
				{
					res[i][j] = in.nextInt();
				}
				in.close();
			}
		}
		
		return res;
	}
	
	public String printFlows()
	{
		String result = "\n";
    	for(int i=0; i<instCount; i++)
		{
    		for(int j=0; j<instCount; j++)
			{
    			result+=flows[i][j]+" , "; 
			}
    		result += "\n";
		}
    	return result;
	}	
	
	public String printDistances()
	{
		String result = "\n";
    	for(int i=0; i<instCount; i++)
		{
    		for(int j=0; j<instCount; j++)
			{
    			result+=distances[i][j]+ " , "; 
			}
    		result += "\n";
		}
    	return result;
	}
		
	
}
