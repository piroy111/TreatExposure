package exposuremetals.accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exposuremetals.accountasset.TEAccountAsset;
import exposuremetals.assets.TEAsset;
import step1loadtransactions.accounts.BKAccountManager;

public class TEAccount implements Comparable<TEAccount> {

	protected TEAccount(String _sNameAccount, TEAccountManager _sTEAccountManager) {
		pNameAccount = _sNameAccount;
		pTEAccountManager = _sTEAccountManager;
		/*
		 * 
		 */
		pListTEAccountAsset = new ArrayList<>();
		pMapTEAssetToTEAccountAsset = new HashMap<>();
		/*
		 * 
		 */
		if (pNameAccount.equals(BKAccountManager.getpBKAccountBunker().getpEmailAddress())) {
			pRankForSort = 0;
		} else if (pNameAccount.equals(BKAccountManager.getpBKAccountPierreRoy().getpEmailAddress())) {
			pRankForSort = 1;
		} else {
			pRankForSort = 2;
		}
	}
	
	/*
	 * Data
	 */
	private String pNameAccount;
	private TEAccountManager pTEAccountManager;
	private List<TEAccountAsset> pListTEAccountAsset;
	private Map<TEAsset, TEAccountAsset> pMapTEAssetToTEAccountAsset;
	private int pRankForSort;
	
	/**
	 * 
	 * @param _sTEAccountAsset
	 */
	public final void declareNewTEAccountAsset(TEAccountAsset _sTEAccountAsset) {
		if (!pListTEAccountAsset.contains(_sTEAccountAsset)) {
			pListTEAccountAsset.add(_sTEAccountAsset);
			pMapTEAssetToTEAccountAsset.put(_sTEAccountAsset.getpTEAsset(), _sTEAccountAsset);
		}
	}
	
	@Override public int compareTo(TEAccount _sTEAccount) {
		if (pRankForSort == _sTEAccount.pRankForSort) {
			return pNameAccount.compareTo(_sTEAccount.pNameAccount);
		} else {
			return Integer.compare(pRankForSort, _sTEAccount.pRankForSort);
		}
	}
	
	/**
	 * 
	 */	
	public String toString() {
		return pNameAccount;
	}
	
	/*
	 * Getters & Setters
	 */
	public TEAccountManager getpTEAccountManager() {
		return pTEAccountManager;
	}
	public String getpNameAccount() {
		return pNameAccount;
	}

	public List<TEAccountAsset> getpListTEAccountAsset() {
		return pListTEAccountAsset;
	}

	public final Map<TEAsset, TEAccountAsset> getpMapTEAssetToTEAccountAsset() {
		return pMapTEAssetToTEAccountAsset;
	}



	
	
}
