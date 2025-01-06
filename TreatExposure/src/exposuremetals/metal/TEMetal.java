package exposuremetals.metal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import step0treatrawdata.objects.BKAsset;

public class TEMetal implements Comparable<TEMetal> {

	public TEMetal(String _sNameMetal, TEMetalManager _sTEMetalManager) {
		pNameMetal = _sNameMetal;
		pTEMetalManager = _sTEMetalManager;
	}

	/*
	 * Data
	 */
	private String pNameMetal;
	private TEMetalManager pTEMetalManager;
	private BKAsset pBKAssetPaper;
	private double pExposureBunker;
	private double pPriceUSD;

	/**
	 * 
	 * @param _sAddExposure
	 */
	protected final void addExposureBunker(double _sAddExposure) {
		pExposureBunker += _sAddExposure;
	}


	@Override public int compareTo(TEMetal _sTEMetal) {
		return pNameMetal.compareTo(_sTEMetal.pNameMetal);
	}

	public final void setpBKAssetPaper(BKAsset _sBKAssetPaper) {
		pBKAssetPaper = _sBKAssetPaper;
		List<Integer> lListDate = new ArrayList<>(_sBKAssetPaper.getpMapDateToPrice().keySet());
		Collections.sort(lListDate);
		Collections.reverse(lListDate);
		int lDate = lListDate.get(0);
		pPriceUSD = pBKAssetPaper.getpPriceUSD(lDate);
	}

	/**
	 * 
	 * @return
	 */
	public final double getpExposureBunkerUSD() {
		return pExposureBunker * pPriceUSD;
	}

	/*
	 * Getters & Setters
	 */
	public final String getpNameMetal() {
		return pNameMetal;
	}
	public final TEMetalManager getpTEMetalManager() {
		return pTEMetalManager;
	}
	public final BKAsset getpBKAssetPaper() {
		return pBKAssetPaper;
	}
	public final double getpExposureBunker() {
		return pExposureBunker;
	}




}
