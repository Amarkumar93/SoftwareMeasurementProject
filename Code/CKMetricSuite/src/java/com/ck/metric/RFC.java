package com.ck.metric;

import java.util.HashSet;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

import com.ck.CKMetricValues;
import com.ck.CKReport;

public class RFC extends ASTVisitor implements Metric {

	private HashSet<String> methodCalls = new HashSet<String>();

	public boolean visit(MethodInvocation node) {
		IMethodBinding binding = node.resolveMethodBinding();
		count(node.getName()  + "/" + arguments(node.arguments()), binding);
		
		return super.visit(node);
	}

	private String arguments(List<?> arguments) {
		if(arguments==null || arguments.isEmpty()) return "0";
		return "" + arguments.size();
	}

	private void count(String methodName, IMethodBinding binding) {
		if(binding!=null) {
			String method = getMethodName(binding);
			methodCalls.add(method);
		} else {
			methodCalls.add(methodName);
		}
	}
	
	public boolean visit(SuperMethodInvocation node) {
		IMethodBinding binding = node.resolveMethodBinding();
		count(node.getName()  + "/" + arguments(node.arguments()), binding);
		
		return super.visit(node);
	}

	private String getMethodName(IMethodBinding binding) {
		
		String argumentList = "";
		ITypeBinding[] args = binding.getParameterTypes();
		for(ITypeBinding arg : args) {
			argumentList += arg.getName();
		}
		String method = binding.getDeclaringClass().getQualifiedName() + "." + binding.getName() + "/" + binding.getTypeArguments().length + "[" + argumentList + "]";
		return method;
	}
	
	@Override
	public void processing(CompilationUnit cu, CKMetricValues number, CKReport report) {
		cu.accept(this);
	}

	@Override
	public void setCalculatedValues(CKMetricValues result) {
		result.setRFCValue(methodCalls.size());
		
	}
	
}
