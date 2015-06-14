import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;


public class InstanceGen {

	private static int flows[] [];
	private static int distances[] [];
	private static int instCount = 0;
	
    public static void setInstanceGen(String url, boolean invert) {
    	
    	try {
        		URL myUrl = new URL(url);
				Scanner s = new Scanner(myUrl.openStream());
				instCount = retrieveCount(s);
				if(invert)
				{
			    	flows = getMatrix(instCount,s);
			    	distances = getMatrix(instCount,s);
				}
				else
				{
			    	distances = getMatrix(instCount,s);		
			    	flows = getMatrix(instCount,s);			
				}
				s.close();
    		}
    		catch(IOException ex) {
    			flows = null;
    			distances = null;
    			ex.printStackTrace(); // for now, simply output it.
    		}

    }
    
	
	public static int getInstanceCount()
	{
		return instCount;
	}	
	
	public static int [][] getFlows()
	{
		return flows;
	}

	public static int [][] getDistances()
	{
		return distances;
	}
	
	public static int retrieveCount(Scanner s)
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
	
	public static int[][] getMatrix(int instCount, Scanner s)
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
	
	public static String printFlows()
	{
		String result = "";
    	for(int i=0; i<instCount; i++)
		{
    		result+=Arrays.toString(flows[i]);
    		result += "\n";
		}
    	return result;
	}	
	
	public static String printDistances()
	{
		String result = "";
    	for(int i=0; i<instCount; i++)
		{
    		result+=Arrays.toString(distances[i]);
    		result += "\n";
		}
    	return result;
	}
		
	
}
