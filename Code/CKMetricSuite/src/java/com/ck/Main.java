package com.ck;


import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		String path = args[0];
		String csvPath = args[1];
		
		CKReport report = new CK().calculate(path);
		
		PrintStream ps = new PrintStream(csvPath);
		ps.println("ClassName,Type,CBO,WMC,DIT,NOC,RFC,LCOM,NOM,LOC");
		for(CKMetricValues result : report.all()) {
			if(result.isError()) continue;
			
			ps.println(
				result.getClassName() + "," +
				result.getType() + "," +
				result.getCBOValue() + "," +
				result.getWMCValue() + "," +
				result.getDITValue() + "," +
				result.getNOCValue() + "," +
				result.getRFCValue() + "," +
				result.getLCOMValue() + "," +
				result.getNOMValue() + "," +
				result.getLOCValue()
			);
		}

		System.out.println("CSV file Created");
		ps.close();
		
	}
}
