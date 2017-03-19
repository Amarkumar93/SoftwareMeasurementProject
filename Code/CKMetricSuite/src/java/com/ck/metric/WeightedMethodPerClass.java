package com.ck.metric;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;

import com.ck.CKMetricValues;
import com.ck.CKReport;

public class WeightedMethodPerClass extends ASTVisitor implements Metric {

	protected int CC = 0;
	
    public boolean visit(MethodDeclaration node) {
    	
    	increaseCC();
    	return super.visit(node);
    }
    
    @Override
    public boolean visit(ForStatement node) {
    	increaseCC();
    	
    	return super.visit(node);
    }

    @Override
    public boolean visit(EnhancedForStatement node) {
    	increaseCC();
    	return super.visit(node);
    }
    
    @Override
    public boolean visit(ConditionalExpression node) {
    	increaseCC();
    	return super.visit(node);
    }
    
    @Override
    public boolean visit(DoStatement node) {
    	increaseCC();
    	return super.visit(node);
    }
    
    @Override
    public boolean visit(WhileStatement node) {
    	increaseCC();
    	return super.visit(node);
    }
    
    @Override
    public boolean visit(SwitchCase node) {
    	if(!node.isDefault())
    		increaseCC();
    	return super.visit(node);
    }
    
    @Override
    public boolean visit(Initializer node) {
    	increaseCC();
    	return super.visit(node);
    }


    @Override
    public boolean visit(CatchClause node) {
    	increaseCC();
    	return super.visit(node);
    }
    
    public boolean visit(IfStatement node) {
    	
		String expr = node.getExpression().toString().replace("&&", "&").replace("||", "|");
		int ands = StringUtils.countMatches(expr, "&");
		int ors = StringUtils.countMatches(expr, "|");
		
		increaseCC(ands + ors);
    	increaseCC();
    	
    	return super.visit(node);
    }
    
    private void increaseCC() {
    	increaseCC(1);
    }

    protected void increaseCC(int qtd) {
    	CC++;
    }

	@Override
	public void processing(CompilationUnit cu, CKMetricValues number, CKReport report) {
		cu.accept(this);
		
	}

	@Override
	public void setCalculatedValues(CKMetricValues result) {
		result.setWMCValue(CC);
	}
    

}
