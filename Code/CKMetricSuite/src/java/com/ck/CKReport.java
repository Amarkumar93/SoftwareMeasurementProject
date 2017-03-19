package com.ck;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CKReport {

	private Map<String, CKMetricValues> results;

	public CKReport() {
		this.results = new HashMap<String, CKMetricValues>();
	}

	public void addCkValues(CKMetricValues ck) {
		results.put(ck.getFile(), ck);
	}

	public CKMetricValues get(String name) {
		return results.get(name);
	}

	public Collection<CKMetricValues> all() {
		return results.values();
	}

	public CKMetricValues getByClassName(String name) {
		for (CKMetricValues ck : all()) {
			if (ck.getClassName().equals(name))
				return ck;
		}

		return null;
	}
}
