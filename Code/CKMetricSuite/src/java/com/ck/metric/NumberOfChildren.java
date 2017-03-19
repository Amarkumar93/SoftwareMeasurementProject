package com.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.ck.CKMetricValues;
import com.ck.CKReport;

public class NumberOfChildren extends ASTVisitor implements Metric {

	private CKReport report;

	@Override
	public boolean visit(TypeDeclaration node) {
		ITypeBinding currentClass = node.resolveBinding();
		ITypeBinding parent = currentClass.getSuperclass();
		if(parent!=null) 
		{	
			CKMetricValues parentCk = report.getByClassName(parent.getBinaryName());
			if(parentCk!=null) parentCk.incrementNOC();
		}

		return false;
	}

	@Override
	public void processing(CompilationUnit cu, CKMetricValues number, CKReport report) {
		this.report = report;
		cu.accept(this);
	}

	@Override
	public void setCalculatedValues(CKMetricValues result) {
	}
}
