package bkbars.object;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicPrintMsg;

public class BRBarNotCurved extends BRBarAbstract {

	
	public BRBarNotCurved(String _sNameMetal, double _sWeight, BRBarManager _sBRBarManager) {
		super(_sNameMetal, _sWeight);
		pBRBarManager = _sBRBarManager;
		/*
		 * 
		 */
		pKeyStr = getUniqueKey(_sNameMetal, _sWeight);
		setpReference(pKeyStr);
		pListBRBarCompta = new ArrayList<>();
		pListBRBarMalca = new ArrayList<>();
	}
	
	/*
	 * Data
	 */
	private BRBarManager pBRBarManager;
	private String pKeyStr;
	private List<BRBar> pListBRBarCompta;
	private List<BRBar> pListBRBarMalca;
	
	/**
	 * 
	 */
	public final void compute() {
		pNbBarInCompta = 0;
		for (BRBar lBRBar : pListBRBarCompta) {
			if (lBRBar.getpIsInCompta()) {
				pNbBarInCompta++;
			}
		}
		pNbBarInMalca = 0;
		for (BRBar lBRBar : pListBRBarMalca) {
			pNbBarInMalca += lBRBar.getpNbBarInMalca();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public final boolean getpIsMatchComptaAndMalca() {
		compute();
		return pNbBarInCompta == pNbBarInMalca;
	}
	
	/**
	 * 
	 * @param _sBRBarCompta
	 */
	public final void declareNewBRBarCompta(BRBar _sBRBarCompta) {
		pBRBarManager.declareNotCurved(_sBRBarCompta);
		if (!pListBRBarCompta.contains(_sBRBarCompta)) {
			pListBRBarCompta.add(_sBRBarCompta);
		}
		compute();
	}

	/**
	 * 
	 * @param _sBRBarMalca
	 */
	public final void declareNewBRBarMalca(BRBar _sBRBarMalca) {
		pBRBarManager.declareNotCurved(_sBRBarMalca);
		if (!pListBRBarMalca.contains(_sBRBarMalca)) {
			pListBRBarMalca.add(_sBRBarMalca);
		}
		compute();
	}
	
	@Override public List<String> getpListFileNameOriginCompta() {
		List<String> lListFileNameOriginCompta = new ArrayList<>();
		for (BRBar lBRBar : pListBRBarCompta) {
			for (String lFile : lBRBar.getpListFileNameOriginCompta()) {
				if (!lListFileNameOriginCompta.contains(lFile)) {
					lListFileNameOriginCompta.add(lFile);
				}
			}
		}
		return lListFileNameOriginCompta;
	}

	@Override public List<String> getpListFileNameOriginMalca() {
		List<String> lListFileNameOriginMalca = new ArrayList<>();
		for (BRBar lBRBar : pListBRBarMalca) {
			for (String lFile : lBRBar.getpListFileNameOriginMalca()) {
				if (!lListFileNameOriginMalca.contains(lFile)) {
					lListFileNameOriginMalca.add(lFile);
				}
			}
		}
		return lListFileNameOriginMalca;
	}
	
	@Override public String getpWasDeliveredToClient() {
		return "N/A";
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getUniqueKey(String _sNameMetal, double _sWeight) {
		return "Not curved - " + _sNameMetal.toUpperCase() + " - " + BasicPrintMsg.afficheDouble(_sWeight, 1);
	}
	public final String getpKeyStr() {
		return pKeyStr;
	}
	public final List<BRBar> getpListBRBarCompta() {
		return pListBRBarCompta;
	}
	public final List<BRBar> getpListBRBarMalca() {
		return pListBRBarMalca;
	}

	

	

	
	
}


