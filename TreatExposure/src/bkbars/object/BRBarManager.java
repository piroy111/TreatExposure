package bkbars.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bkbars.BRManager;

public class BRBarManager {

	public BRBarManager(BRManager _sBRManager) {
		pBRManager = _sBRManager;
		/*
		 * 
		 */
		pMapKeyStrToBRBar = new HashMap<>();
		pMapKeyStrToBRBarNotCurved = new HashMap<>();
		pListBRBarNotCurved = new ArrayList<>();
	}
	
	/*
	 * Data
	 */
	private BRManager pBRManager;
	private Map<String, BRBar> pMapKeyStrToBRBar;
	private Map<String, BRBarNotCurved> pMapKeyStrToBRBarNotCurved;
	private List<BRBar> pListBRBarNotCurved;
	
	/**
	 * 
	 */
	public final void run() {
		
	}

	/**
	 * 
	 * @return
	 */
	public final BRBar getpOrCreateBRBar(String _sNameMetal, String _sReference, double _sWeight) {
		double lWeight = Math.abs(_sWeight);
		String lKeyStr = BRBar.getUniqueKey(_sNameMetal, _sReference);
		BRBar lBRBar = pMapKeyStrToBRBar.get(lKeyStr);
		if (lBRBar == null) {
			lBRBar = new BRBar(_sNameMetal, lWeight, _sReference, this);
			pMapKeyStrToBRBar.put(lKeyStr, lBRBar);
			pListBRBarNotCurved.add(lBRBar);
		} else if (Math.abs(lWeight - lBRBar.getpWeight()) > 0.005) {
			String lMsgError = _sNameMetal + "," + _sReference + "," + _sWeight
					+ "," + lBRBar.getpWeight() + "," + lBRBar.getpListFileNameOriginCompta().toString().replaceAll(",", ";")
					+ "," + lBRBar.getpListFileNameOriginMalca().toString().replaceAll(",", ";");
			String lHeader = "_sNameMetal,_sReference,_sWeight (new),Weight (previously stored in Bar),Files origin Compta,Files origin Malca";
			pBRManager.getpBRErrorWriter().addNewMessageInError(this, lHeader, lMsgError);
		}
		return lBRBar;
	}
	
	/**
	 * 
	 * @param _sMetalName
	 * @param _sWeight
	 * @return
	 */
	public final BRBarNotCurved getpOrCreateBRBarNotCurved(String _sMetalName, double _sWeight) {
		double lWeight = Math.abs(_sWeight);
		String lKeyStr = BRBarNotCurved.getUniqueKey(_sMetalName, lWeight);
		BRBarNotCurved lBRBarNotCurved = pMapKeyStrToBRBarNotCurved.get(lKeyStr);
		if (lBRBarNotCurved == null) {
			lBRBarNotCurved = new BRBarNotCurved(_sMetalName, lWeight, this);
			pMapKeyStrToBRBarNotCurved.put(lKeyStr, lBRBarNotCurved);
		}
		return lBRBarNotCurved;
	}
	
	/**
	 * 
	 * @param _sBRBar
	 */
	protected final void declareNotCurved(BRBar _sBRBar) {
		_sBRBar.setpIsNotCurved(true);
		if (pListBRBarNotCurved.contains(_sBRBar)) {
			pListBRBarNotCurved.remove(_sBRBar);
		}
	}
	
	/*
	 * Getters & Setters
	 */
	public final BRManager getpBRManager() {
		return pBRManager;
	}
	public final Map<String, BRBar> getpMapKeyStrToBRBar() {
		return pMapKeyStrToBRBar;
	}
	public final List<BRBar> getpListBRBarNotCurved() {
		return pListBRBarNotCurved;
	}

	public final Map<String, BRBarNotCurved> getpMapKeyStrToBRBarNotCurved() {
		return pMapKeyStrToBRBarNotCurved;
	}
	
}
