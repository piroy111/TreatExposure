package exposuremetals.accountasset;

import java.util.ArrayList;
import java.util.List;

import exposuremetals.accounts.TEAccount;
import exposuremetals.assets.TEAsset;
import step1loadtransactions.transactions.BKTransaction;

public class TEAccountAsset {

	protected TEAccountAsset(TEAccount _sTEAccount, TEAsset _sTEAsset, TEAccountAssetManager _sTEAccountAssetManager) {
		pTEAccount = _sTEAccount;
		pTEAsset = _sTEAsset;
		pTEAccountAssetManager = _sTEAccountAssetManager;
		/*
		 * 
		 */
		pKeyStr = getUniqueKeyStr(pTEAccount, pTEAsset);
		pListBKTransaction = new ArrayList<>();
	}
	
	/*
	 * Data
	 */
	private TEAccount pTEAccount;
	private TEAsset pTEAsset;
	private TEAccountAssetManager pTEAccountAssetManager;
	private String pKeyStr;
	private double pQuantity;
	private List<BKTransaction> pListBKTransaction;
	
	/**
	 * 
	 * @param _sBKTransaction
	 */
	public final void declareNewBKTransaction(BKTransaction _sBKTransaction) {
		if (!pListBKTransaction.contains(_sBKTransaction)) {
			pListBKTransaction.add(_sBKTransaction);
			pQuantity += _sBKTransaction.getpQuantity();
		}
	}
	
	/**
	 * 
	 * @param _sTEAccount
	 * @param _sTEAsset
	 * @return
	 */
	public static String getUniqueKeyStr(TEAccount _sTEAccount, TEAsset _sTEAsset) {
		return _sTEAccount.getpNameAccount() + ";" + _sTEAsset.getpBKAsset().getpName();
	}

	/*
	 * Getters & Setters
	 */
	public TEAccount getpTEAccount() {
		return pTEAccount;
	}
	public TEAsset getpTEAsset() {
		return pTEAsset;
	}
	public String getpKeyStr() {
		return pKeyStr;
	}
	public TEAccountAssetManager getpTEAccountAssetManager() {
		return pTEAccountAssetManager;
	}
	public double getpQuantity() {
		return pQuantity;
	}
	public void setpQuantity(double pQuantity) {
		this.pQuantity = pQuantity;
	}
	
	
	
}
