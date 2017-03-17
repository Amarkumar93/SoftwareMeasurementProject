import java.io.*;
import java.util.*;

class CyclomaticComplexity {
	
	public static List<String> jAVAfiles = new ArrayList<String>();
	
	public static void main(String ss[])
	{
		File dir = new File("C:\\Users\\Amar\\Desktop\\Study\\SOEN 6611 Measurement Piccirili\\Project\\jEdit version\\jedit5.3.0source\\jEdit\\org\\gjt\\sp\\jedit\\");
		GetFilesIteratively(dir);
		System.out.println("Size is " + jAVAfiles.size() + "\n");
//		for(String f : jAVAfiles)
//		{
//			System.out.println(f);			
//		}
		int totalComplexity = 0;
		for (String file : jAVAfiles)
		{
			CyclomaticComplexity cc = new CyclomaticComplexity();
			totalComplexity += cc.Count(file);
		}
		System.out.print("Total Complexity: " + totalComplexity);
	}
	
	public static void GetFilesIteratively(File rootFile)
	{
		for(File file : rootFile.listFiles())
		{
			if (file.isDirectory())
			{
				GetFilesIteratively(file);
			}
			else
			{
				String reqExtension = "java";
				String fileName = file.getName();
				String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				if (reqExtension.equals(extension))
				{
					jAVAfiles.add(file.getAbsolutePath());
				}
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
