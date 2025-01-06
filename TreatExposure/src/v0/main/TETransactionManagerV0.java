package v0.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import staticdata.StaticAsset;
import step0treatrawdata.objects.BKAsset;
import step1loadtransactions.accounts.BKAccount;
import step1loadtransactions.accounts.BKAccountManager;

public class TETransactionManagerV0 {

	protected TETransactionManagerV0(TEManagerV0 _sTEManager) {
		pTEManager = _sTEManager;
		/*
		 * Initiate
		 */
		pMapBKAccountToMapBKAssetToListTETransaction = new HashMap<>();
		pMapBKAssetToMapBKAccountToListTETransaction = new HashMap<>();
		pListTETransaction = new ArrayList<>();
	}
	
	/*
	 * Data
	 */
	private TEManagerV0 pTEManager;
	private List<TETransactionV0> pListTETransaction;
	private Map<BKAccount, Map<BKAsset, List<TETransactionV0>>> pMapBKAccountToMapBKAssetToListTETransaction;
	private Map<BKAsset, Map<BKAccount, List<TETransactionV0>>> pMapBKAssetToMapBKAccountToListTETransaction;
	
	/**
	 * create a new TETransaction and add it to the list and the Map
	 * @param _sBKAccount
	 * @param _sBKAsset
	 * @param _sQuantity
	 */
	public final TETransactionV0 createNewTETransaction(BKAccount _sBKAccount, BKAsset _sBKAsset, double _sQuantity) {
		/*
		 * Get and create if the asset is not correct
		 */
		TETransactionV0 lTETransaction = new TETransactionV0(_sBKAccount, _sBKAsset, _sQuantity);
		if (!getpIsKeepTETransaction(lTETransaction)) {
			return null;
		}
		/*
		 * If the account is a client, then we change the sign of the amount
		 */
		if (!_sBKAccount.equals(BKAccountManager.getpBKAccountBunker()) && !_sBKAccount.equals(BKAccountManager.getpBKAccountPierreRoy())) {
			lTETransaction.changeSignAmount();
		}

		/*
		 * Add to List
		 */
		pListTETransaction.add(lTETransaction);
		/*
		 * Add to Map account to asset
		 */
		Map<BKAsset, List<TETransactionV0>> lMapBKAssetToListTETransaction = pMapBKAccountToMapBKAssetToListTETransaction.get(_sBKAccount);
		if (lMapBKAssetToListTETransaction == null) {
			lMapBKAssetToListTETransaction = new HashMap<>();
			pMapBKAccountToMapBKAssetToListTETransaction.put(_sBKAccount, lMapBKAssetToListTETransaction);
		}
		List<TETransactionV0> lListTETransaction = lMapBKAssetToListTETransaction.get(_sBKAsset);
		if (lListTETransaction == null) {
			lListTETransaction = new ArrayList<>();
			lMapBKAssetToListTETransaction.put(_sBKAsset, lListTETransaction);
		}
		lListTETransaction.add(lTETransaction);
		/*
		 * Add to Map asset to account
		 */
		Map<BKAccount, List<TETransactionV0>> lMapBKAccountToListTETransaction = pMapBKAssetToMapBKAccountToListTETransaction.get(_sBKAsset);
		if (lMapBKAccountToListTETransaction == null) {
			lMapBKAccountToListTETransaction = new HashMap<>();
			pMapBKAssetToMapBKAccountToListTETransaction.put(_sBKAsset, lMapBKAccountToListTETransaction);
		}
		List<TETransactionV0> lListTETransaction2 = lMapBKAccountToListTETransaction.get(_sBKAccount);
		if (lListTETransaction2 == null) {
			lListTETransaction2 = new ArrayList<>();
			lMapBKAccountToListTETransaction.put(_sBKAccount, lListTETransaction2);
		}
		lListTETransaction2.add(lTETransaction);
		return lTETransaction;
	}

	/**
	 * 
	 * @param _sTETransaction
	 * @return
	 */
	private boolean getpIsKeepTETransaction(TETransactionV0 _sTETransaction) {
		if (_sTETransaction.getpBKAsset().getpName().equals(StaticAsset.getOIL())) {
			return true;
		}
		if (!_sTETransaction.getpBKAsset().getpIsBar()
				&& !(_sTETransaction.getpBKAsset().getpIsPaper() && !_sTETransaction.getpBKAsset().getpIsPaperCurrency())) {
			return false;
		}
		return true;
	}
	
	
	/*
	 * Getters & Setters
	 */
	public final TEManagerV0 getpTEManager() {
		return pTEManager;
	}
	public final List<TETransactionV0> getpListTETransaction() {
		return pListTETransaction;
	}
	public final Map<BKAccount, Map<BKAsset, List<TETransactionV0>>> getpMapBKAccountToMapBKAssetToListTETransaction() {
		return pMapBKAccountToMapBKAssetToListTETransaction;
	}
	public final Map<BKAsset, Map<BKAccount, List<TETransactionV0>>> getpMapBKAssetToMapBKAccountToListTETransaction() {
		return pMapBKAssetToMapBKAccountToListTETransaction;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
