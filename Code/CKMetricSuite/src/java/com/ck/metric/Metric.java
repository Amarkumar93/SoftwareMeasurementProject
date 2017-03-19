package com.ck.metric;

import org.eclipse.jdt.core.dom.CompilationUnit;

import com.ck.CKMetricValues;
import com.ck.CKReport;

public interface Metric {

	void processing(CompilationUnit cu, CKMetricValues result, CKReport report);
	void setCalculatedValues(CKMetricValues result);
}
