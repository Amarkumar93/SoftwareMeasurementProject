import java.io.*;
import java.util.*;

class CyclomaticComplexity {
	
	public static List<String> jAVAfiles = new ArrayList<String>();
	
	public static void main(String args[])
	{
		File dir = new File(args[0]);
		GetFilesIteratively(dir);
		
		int totalComplexity = 0;
		for (String file : jAVAfiles)
		{
			CyclomaticComplexity cc = new CyclomaticComplexity();
			totalComplexity += cc.Count(file);
		}
		
		System.out.print("Total Java Files: " + jAVAfiles.size() + "\n");
		
		System.out.print("Total Complexity: " + totalComplexity + "\n");
		
		System.out.print("Average Complexity: " + (double)((double)totalComplexity/(double)jAVAfiles.size()));
	}
	
	public static void GetFilesIteratively(File rootFile)
	{
		for(File file : rootFile.listFiles())
		{
			if (file.isDirectory())
			{
				GetFilesIteratively(file);
			}
			else if (file.getAbsolutePath().toLowerCase().endsWith(".java")) 
			{
				jAVAfiles.add(file.getAbsolutePath());
			}
		}
	}

	public int Count(String filePath) {
		int complexity = 1;
		String[] keywords = {"if","while","case","for","switch","&&","||","?","catch"};
		String words = "";
		String line = null;
		try {
				FileReader fr = new FileReader(filePath);
				BufferedReader br = new BufferedReader(fr);
				line = br.readLine();
				while (line != null)
				{
					StringTokenizer stTokenizer = new StringTokenizer(line);
					while (stTokenizer.hasMoreTokens())
					{
						words = stTokenizer.nextToken();
						for(int i=0; i<keywords.length; i++)
						{
							if (keywords[i].equals(words))
							{
								complexity++;
							}
						}
					}
					line = br.readLine();
				}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return (complexity);
	}
}
