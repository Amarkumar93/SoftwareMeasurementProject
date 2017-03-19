package com.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.ck.CKMetricValues;
import com.ck.CKReport;

public class NOM extends ASTVisitor implements Metric {

	private int methodCount;

	@Override
	public boolean visit(MethodDeclaration node) {
		methodCount++;
		return false;
	}

	@Override
	public void processing(CompilationUnit cu, CKMetricValues number, CKReport report) {
		cu.accept(this);
	}

	@Override
	public void setCalculatedValues(CKMetricValues result) {
		result.setNOMValue(methodCount);
	}
}
