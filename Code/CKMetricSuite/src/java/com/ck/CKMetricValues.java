package com.ck;

import java.util.HashMap;
import java.util.Map;

public class CKMetricValues {

	private Map<String, Integer> specific;
	
	
	private String file;
	private String className;
	private String type;

	private int DIT;
	private int NOC;
	private int WMC;
	private int CBO;
	private int LCOM;
	private int RFC;
	private int NOM;
	private int LOC;
	
	private boolean error; 

	public CKMetricValues(String file, String className, String type) {
		this.file = file;
		this.className = className;
		this.type = type;
		
		this.specific = new HashMap<String, Integer>();
	}
	
	public String getFile() {
		return file;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CKMetricValues other = (CKMetricValues) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}

	public int getDITValue() {
		return DIT;
	}

	public void setDITValue(int dit) {
		this.DIT = dit;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getClassName() {
		return className;
	}

	public void incrementNOC() {
		this.NOC++;
	}
	
	public int getNOCValue() {
		return NOC;
	}

	public void setWMCValue(int cc) {
		this.WMC = cc;
	}
	
	public int getWMCValue() {
		return WMC;
	}


	public int getCBOValue() {
		return CBO;
	}

	public void setCBOValue(int cbo) {
		this.CBO = cbo;
	}

	public void setLCOMValue(int lcom) {
		this.LCOM = lcom;
	}
	public int getLCOMValue() {
		return LCOM;
	}

	public void setRFCValue(int rfc) {
		this.RFC = rfc;
	}
	
	public int getRFCValue() {
		return RFC;
	}

	public void setNOMValue(int nom) {
		this.NOM = nom;
	}
	public int getNOMValue() {
		return NOM;
	}
	
	public int getSpecific(String key) {
		if (!specific.containsKey(key))
			return -1;
		return specific.get(key);
	}
	
	public void addSpecific(String key, int value) {
		specific.put(key, value);
	}

	public String getType() {
		return type;
	}

	
	public int getLOCValue() {
		return LOC;
	}

	public void setLOCValue(int loc) {
		this.LOC = loc;
	}


	public boolean isError() {
		return error;
	}
	
	public void error() {
		this.error = true;
	}

	@Override
	public String toString() {
		return "CKNumber [file=" + file + ", className=" + className + ", type=" + type + ", dit=" + DIT + ", noc="
				+ NOC + ", wmc=" + WMC + ", cbo=" + CBO + ", lcom=" + LCOM + ", rfc=" + RFC + ", nom=" + NOM + ", loc=" + LOC + ", specific=" + specific + ", error=" + error + "]";
	}
	
	
	

}
