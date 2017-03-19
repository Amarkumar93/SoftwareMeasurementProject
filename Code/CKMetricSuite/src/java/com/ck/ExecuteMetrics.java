package com.ck;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;

import com.ck.metric.ClassInfo;
import com.ck.metric.Metric;

public class ExecuteMetrics extends FileASTRequestor {

	private CKReport report;
	private Callable<List<Metric>> metrics;
		
	public ExecuteMetrics(Callable<List<Metric>> metrics) {
		this.metrics = metrics;
		this.report = new CKReport();
	}


	@Override
	public void acceptAST(String sourceFilePath, CompilationUnit cu) {
		
		CKMetricValues result = null;
		
		try {
			ClassInfo info = new ClassInfo();
			cu.accept(info);
			if(info.getClassName()==null) return;
		
			result = new CKMetricValues(sourceFilePath, info.getClassName(), info.getType());
			
			int loc = CalculateLOC(new FileInputStream(sourceFilePath));
			result.setLOCValue(loc);
			
			for(Metric visitor : metrics.call()) {
				visitor.processing(cu, result, report);
				visitor.setCalculatedValues(result);
			}
			report.addCkValues(result);
		} catch(Exception e) {
			if(result!=null) result.error();
		}
	}
	
	public CKReport getReport() {
		return report;
	}
	
	public int CalculateLOC (InputStream sourceCode)
	{
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(sourceCode));
			int lines = 0;
			
			String line = null;
			do {
				line = reader.readLine();
				if(line!=null && !emptyLine(line)) lines++;
			}
			while (line != null);
			reader.close();

			return lines;
		} catch (IOException e) {
			return 0;
		}
	}
	
	private boolean emptyLine(String line) {
		String result = line.replace("\t", "").replace(" ", "").trim();
		return result.isEmpty();
	}
	
}
