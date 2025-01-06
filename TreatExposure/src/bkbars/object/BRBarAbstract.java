package bkbars.object;

import java.util.List;

public abstract class BRBarAbstract {

	
	protected BRBarAbstract(String _sMetalName, double _sWeight) {
		pNameMetal = _sMetalName;
		pWeight = _sWeight;
	}
	
	/*
	 * Abstract
	 */
	public abstract List<String> getpListFileNameOriginCompta();
	public abstract List<String> getpListFileNameOriginMalca();
	public abstract String getpWasDeliveredToClient();
	/*
	 * Data
	 */
	protected String pNameMetal;
	protected double pWeight;
	protected int pNbBarInCompta;
	protected int pNbBarInMalca;
	protected String pReference;

	/*
	 * Getters & Setters
	 */
	public final String getpNameMetal() {
		return pNameMetal;
	}
	public final double getpWeight() {
		return pWeight;
	}
	public final int getpNbBarInCompta() {
		return pNbBarInCompta;
	}
	public final int getpNbBarInMalca() {
		return pNbBarInMalca;
	}
	public final String getpReference() {
		return pReference;
	}
	public final void setpReference(String pReference) {
		this.pReference = pReference;
	}
	
	
	
	
}
