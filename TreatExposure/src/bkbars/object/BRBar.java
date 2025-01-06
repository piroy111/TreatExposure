package bkbars.object;

import java.util.ArrayList;
import java.util.List;

import basicmethods.AMNumberTools;
import basicmethods.BasicPrintMsg;

public class BRBar extends BRBarAbstract {

	protected BRBar(String _sNameMetal, double _sWeight, String _sReference, BRBarManager _sBRBarManager) {
		super(_sNameMetal, _sWeight);
		setpReference(_sReference);
		pBRBarManager = _sBRBarManager;
		/*
		 * 
		 */
		if (pReference.equals("")) {
			BasicPrintMsg.error("Reference cannot be blank");
		}
		/*
		 * 
		 */
		pIsWasDelivered = false;
		pKeyStr = getUniqueKey(_sNameMetal, _sReference);
		pListFileNameOriginCompta = new ArrayList<>();
		pListFileNameOriginMalca = new ArrayList<>();
	}

	/*
	 * Data
	 */
	private BRBarManager pBRBarManager;
	private String pKeyStr;
	private List<String> pListFileNameOriginCompta;
	private List<String> pListFileNameOriginMalca;
	private Boolean pIsWasDelivered;
	private boolean pIsNotCurved;

	/**
	 * 
	 * @return
	 */
	public final boolean getpIsInMalca() {
		return pNbBarInMalca > 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public final boolean getpIsInCompta() {
		return pNbBarInCompta > 0 && !pIsWasDelivered;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getUniqueKey(String _sNameMetal, String _sReference) {
		return _sNameMetal.toUpperCase() + ";;" + _sReference.toUpperCase();
	}

	/**
	 * 
	 * @param _sFileNameOriginCompta
	 */
	public final void addNewFileNameOriginCompta(String _sDir, String _sFileNameOriginCompta, double _sWeight) {
		String lNameFile = getpLastOfDir(_sDir) + _sFileNameOriginCompta;
		if (!pListFileNameOriginCompta.contains(lNameFile)) {
			pListFileNameOriginCompta.add(lNameFile);
		}
		if (Math.abs(_sWeight - pWeight) < 0.005) {
			pNbBarInCompta++;
		} else if (Math.abs(_sWeight + pWeight) < 0.005) {
			pNbBarInCompta--;
		} else {
			pBRBarManager.getpBRManager().getpBRErrorWriter().addNewMessageInError(this, "", "Error file compta");
		}
		/*
		 * Check if the bar was delivered to the client --> in this case we will count it at zero later on
		 */
		if (_sFileNameOriginCompta.startsWith("Delivery_") && _sWeight < 0) {
			pIsWasDelivered = true;
		}
	}

	/**
	 * 
	 * @param _sFileNameOriginMalca
	 */
	public final void addNewFileNameOriginMalca(String _sDir, String _sFileNameOriginMalca, double _sWeight) {
		String lNameFile = getpLastOfDir(_sDir) + _sFileNameOriginMalca;
		if (!pListFileNameOriginMalca.contains(lNameFile)) {
			pListFileNameOriginMalca.add(lNameFile);
		}
		if (AMNumberTools.isEqual(_sWeight, pWeight)) {
			pNbBarInMalca++;
		} else if (AMNumberTools.isEqual(_sWeight, -pWeight)) {
			pNbBarInMalca--;
		} else {
			pBRBarManager.getpBRManager().getpBRErrorWriter().addNewMessageInError(this, "", "Error file malca");
		}
	}
	
	/**
	 * 
	 * @param _sDir
	 * @return
	 */
	private String getpLastOfDir(String _sDir) {
		String[] lWords = _sDir.split("/", -1);
		if (lWords.length >= 0) {
			return "";
		}
		return lWords[lWords.length - 1];
	}

	@Override
	public String getpWasDeliveredToClient() {
		return pIsWasDelivered.toString();
	}

	
	/*
	 * Getters & Setters
	 */
	public final String getpKeyStr() {
		return pKeyStr;
	}
	public final BRBarManager getpBRBarManager() {
		return pBRBarManager;
	}
	public final List<String> getpListFileNameOriginCompta() {
		return pListFileNameOriginCompta;
	}
	public final List<String> getpListFileNameOriginMalca() {
		return pListFileNameOriginMalca;
	}
	public final Boolean getpIsWasDelivered() {
		return pIsWasDelivered;
	}
	public final boolean getpIsNotCurved() {
		return pIsNotCurved;
	}
	public final void setpIsNotCurved(boolean pIsNotCurved) {
		this.pIsNotCurved = pIsNotCurved;
	}

	




}
