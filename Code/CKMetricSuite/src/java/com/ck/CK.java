package com.ck;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;


import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;

import com.ck.metric.CouplingBetweenObjects;
import com.ck.metric.DepthOfInheritanceTree;
import com.ck.metric.LCOM;
import com.ck.metric.Metric;
import com.ck.metric.NumberOfChildren;

import com.ck.metric.NOM;
import com.ck.metric.RFC;
import com.ck.metric.WeightedMethodPerClass;
import com.google.common.collect.Lists;

public class CK {

	private static int partitionSize = 400;

	public List<Callable<Metric>> pluggedMetrics; 

	public CK() {	}
	
	
	public CKReport calculate(String path) {
		String[] srcDirs = getAllDirs(path);
		String[] javaFiles = getAllJavaFiles(path);
		
		ExecuteMetrics storage = new ExecuteMetrics(() -> metrics());
		
		List<List<String>> partitions = Lists.partition(Arrays.asList(javaFiles), partitionSize);

		for(List<String> partition : partitions) {
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			
			parser.setEnvironment(null, srcDirs, null, true);
			parser.createASTs(partition.toArray(new String[partition.size()]), null, new String[0], storage, null);
		}
		
		return storage.getReport();
	}
	
	private List<Metric> metrics() {
		List<Metric> all = new ArrayList<>(Arrays.asList(new DepthOfInheritanceTree(), new NumberOfChildren(), new WeightedMethodPerClass(), new CouplingBetweenObjects(), new LCOM(), new RFC(), new NOM()));
		
		return all;
	}

	public static String[] getAllDirs(String path) {
		ArrayList<String> dirs = new ArrayList<String>();
		getAllDirs(path, dirs);
		
		String[] ar = new String[dirs.size()];
		ar = dirs.toArray(ar);
		return ar;
	}
	
	private static void getAllDirs(String path, ArrayList<String> dirs) {
		
		File f = new File(path);		
		for(File inside : f.listFiles()) {
			if(inside.isDirectory()) {
				String newDir = inside.getAbsolutePath();
				dirs.add(newDir);
				getAllDirs(newDir, dirs);
			}
		}
	}

	public static String[] getAllJavaFiles(String path) {
		ArrayList<String> files = new ArrayList<String>();
		getAllJavaFiles(path, files);
		String[] ar = new String[files.size()];
		ar = files.toArray(ar);
		return ar;
	}
	
	private static void getAllJavaFiles(String path, ArrayList<String> files) {
		
		File f = new File(path);
		for(File inside : f.listFiles()) {
			if(inside.isDirectory()) {
				String newDir = inside.getAbsolutePath();
				getAllJavaFiles(newDir, files);
			} else if(inside.getAbsolutePath().toLowerCase().endsWith(".java")) {
				files.add(inside.getAbsolutePath());
			}
		}
	}

}
