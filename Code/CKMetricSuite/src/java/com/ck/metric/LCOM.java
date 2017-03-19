package com.ck.metric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import com.ck.CKMetricValues;
import com.ck.CKReport;

public class LCOM extends ASTVisitor implements Metric {

	ArrayList<TreeSet<String>> noOfMethods = new ArrayList<TreeSet<String>>();
	Set<String> declaredFields;
	
	public LCOM() {
		this.declaredFields = new HashSet<String>();
	}
	
	public boolean visit(FieldDeclaration node) {
		
		for(Object o : node.fragments()) {
			VariableDeclarationFragment vdf = (VariableDeclarationFragment) o;
			declaredFields.add(vdf.getName().toString());
		}
		return super.visit(node);
	}
	
	public boolean visit(SimpleName node) {
		String name = node.getFullyQualifiedName();
		if(declaredFields.contains(name)) {
			acessed(name);
		}
		
		return super.visit(node);
	}

	private void acessed(String name) {
		if(!noOfMethods.isEmpty()){
			noOfMethods.get(noOfMethods.size() - 1).add(name);
		}
	}
	
	public boolean visit(MethodDeclaration node) {
		noOfMethods.add(new TreeSet<String>());
		
		return super.visit(node);
	}
	
	@Override
	public void processing(CompilationUnit cu, CKMetricValues number, CKReport report) {
		cu.accept(this);
	}

	@Override
	public void setCalculatedValues(CKMetricValues result) {
		
		/*
		 * LCOM = |P| - |Q| if |P| - |Q| > 0
		 * where
		 * P = set of all empty set intersections
		 * Q = set of all nonempty set intersections
		 */
		
		int lcom = 0;
		for (int i = 0; i < noOfMethods.size(); i++)
		    for (int j = i + 1; j < noOfMethods.size(); j++) {
		    	
				TreeSet<?> intersection = (TreeSet<?>)noOfMethods.get(i).clone();
				intersection.retainAll(noOfMethods.get(j));
				if (intersection.size() == 0) lcom++;
				else lcom--;
		    }
		result.setLCOMValue(lcom > 0 ? lcom : 0);
	}

}
