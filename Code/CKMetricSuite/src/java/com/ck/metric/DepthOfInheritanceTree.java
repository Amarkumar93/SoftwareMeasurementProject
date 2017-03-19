package com.ck.metric;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.ck.CKMetricValues;
import com.ck.CKReport;

public class DepthOfInheritanceTree extends ASTVisitor implements Metric {

	int DIT = 0;

	@Override
	public boolean visit(TypeDeclaration node) {
		ITypeBinding binding = node.resolveBinding();
		if(binding!=null) calculate(binding);

		return super.visit(node);
	}

	private void calculate(ITypeBinding binding) {
		ITypeBinding father = binding.getSuperclass();
		if (father != null) {
			String fatherName = father.getQualifiedName();
			if (fatherName.endsWith("Object")) return;
			DIT++;

			calculate(father);
		}

	}

	@Override
	public void processing(CompilationUnit cu, CKMetricValues number, CKReport report) {
		cu.accept(this);
	}

	@Override
	public void setCalculatedValues(CKMetricValues result) {
		result.setDITValue(DIT);
	}
}
