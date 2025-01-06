package exposuremetals.metal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exposuremetals.TEManager;
import step0treatrawdata.objects.BKAsset;
import step1loadtransactions.accounts.BKAccount;
import step1loadtransactions.accounts.BKAccountManager;
import step1loadtransactions.transactions.BKTransaction;

public class TEMetalManager {

	public TEMetalManager(TEManager _sTEManager) {
		pTEManager = _sTEManager;
		/*
		 * 
		 */
		pMapNameToTEMetal = new HashMap<>();
		pListTEMetal = new ArrayList<>();
	}

	/*
	 * Static
	 */
	public enum typeMetal {GOLD, SILVER, PLATINUM}
	/*
	 * Data
	 */
	private TEManager pTEManager;
	private Map<String, TEMetal> pMapNameToTEMetal;
	private List<TEMetal> pListTEMetal;

	/**
	 * 
	 * @param _sBKTransaction
	 */
	public final void declareNewBKTransactionForBunker(BKTransaction _sBKTransaction) {
		BKAccount lBKAccount = _sBKTransaction.getpBKAccount();
		BKAsset lBKAsset = _sBKTransaction.getpBKAsset();
		/*
		 * Case of Bunker --> EXPO = Bars + Loan + Paper
		 */
		if (lBKAccount.equals(BKAccountManager.getpBKAccountBunker())) {
			typeMetal lTypeMetal = getpNameMetal(lBKAsset);
			if (lTypeMetal != null) {
				TEMetal lTEMetal = getpOrCreateTEMetal(lTypeMetal);
				lTEMetal.addExposureBunker(_sBKTransaction.getpQuantity());
			}
		}
		/*
		 * Store BKAsset paper into TEMEtal in order to get the price in USD
		 */
		if (lBKAsset.getpIsPaper()) {
			typeMetal lTypeMetal = getpNameMetal(lBKAsset);
			if (lTypeMetal != null) {
				TEMetal lTEMetal = getpOrCreateTEMetal(lTypeMetal);
				lTEMetal.setpBKAssetPaper(lBKAsset);
			}	
		}
	}

	/**
	 * 
	 * @return null if not a metal. Counts bar and paper
	 */
	public final typeMetal getpNameMetal(BKAsset _sBKAsset) {
		if (_sBKAsset.getpIsBar() || _sBKAsset.getpIsBarLoan()) {
			String lMetalName = _sBKAsset.getpMetalName();
			typeMetal lTypeMetal = typeMetal.valueOf(lMetalName);
			return lTypeMetal;
		} else if (_sBKAsset.getpIsPaper()) {
			String lNameAsset = _sBKAsset.getpName();
			if (lNameAsset.equals("XAU")) {
				return typeMetal.GOLD;
			} else if (lNameAsset.equals("XAG")) {
				return typeMetal.SILVER;
			} else if (lNameAsset.equals("XPT")) {
				return typeMetal.PLATINUM;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param _sTEMetal
	 * @return
	 */
	public final TEMetal getpOrCreateTEMetal(typeMetal _sTypeMetal) {
		String lNameMetal = _sTypeMetal.toString();
		TEMetal lTEMetal = pMapNameToTEMetal.get(lNameMetal);
		if (lTEMetal == null) {
			lTEMetal = new TEMetal(lNameMetal, this);
			pMapNameToTEMetal.put(lNameMetal, lTEMetal);
			pListTEMetal.add(lTEMetal);
			Collections.sort(pListTEMetal);
		}
		return lTEMetal;
	}

	/*
	 * Getters & Setters
	 */
	public TEManager getpTEManager() {
		return pTEManager;
	}
	public final List<TEMetal> getpListTEMetal() {
		return pListTEMetal;
	}

}
