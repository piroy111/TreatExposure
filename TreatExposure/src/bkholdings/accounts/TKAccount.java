package bkholdings.accounts;

import java.util.HashMap;
import java.util.Map;

import basicmethods.AMNumberTools;
import step0treatrawdata.objects.BKAsset;
import step1loadtransactions.accounts.BKAccount;
import step1loadtransactions.bars.BKBar;
import step1loadtransactions.bars.BKBarManager;
import step1loadtransactions.transactions.BKTransaction;

public class TKAccount {

	protected TKAccount(BKAccount _sBKAccount, TKAccountManager _sTEAccountManager) {
		pBKAccount = _sBKAccount;
		pTEAccountManager = _sTEAccountManager;
		/*
		 * 
		 */
		pMapBKAssetToQuantity = new HashMap<>();
		pMapBKBarToHolding = new HashMap<>();		
	}
	
	/*
	 * Data
	 */
	private BKAccount pBKAccount;
	private TKAccountManager pTEAccountManager;
	private Map<BKBar, Integer> pMapBKBarToHolding;
	private Map<BKAsset, Double> pMapBKAssetToQuantity;
	
	/**
	 * 
	 * @param _sBKTransaction
	 */
	public final void declareNewBKTransaction(BKTransaction _sBKTransaction) {
		BKAsset lBKAsset = _sBKTransaction.getpBKAsset();
		/*
		 * Case of a bar --> we increment the holding
		 */
		if (lBKAsset.getpIsBar()) {
			BKBar lBKBar = BKBarManager.getpAndCheckBKBar(_sBKTransaction);
			Integer lHolding = pMapBKBarToHolding.get(lBKBar);
			if (lHolding == null) {
				lHolding = 0;
			}
			if (AMNumberTools.isPositiveStrict(_sBKTransaction.getpQuantity())) {
				lHolding++;
			} else if (AMNumberTools.isNegativeStrict(_sBKTransaction.getpQuantity())) {
				lHolding--;
			}
			pMapBKBarToHolding.put(lBKBar, lHolding);
		}
		/*
		 * Case of another asset --> we increment the quantity
		 */
		else {
			Double lQuantity = pMapBKAssetToQuantity.get(_sBKTransaction.getpBKAsset());
			if (lQuantity == null) {
				lQuantity = 0.;
			}
			lQuantity += _sBKTransaction.getpQuantity();
			pMapBKAssetToQuantity.put(_sBKTransaction.getpBKAsset(), lQuantity);
		}		
	}
	
	
	/*
	 * Getters & Setters
	 */
	public TKAccountManager getpTEAccountManager() {
		return pTEAccountManager;
	}
	public final BKAccount getpBKAccount() {
		return pBKAccount;
	}
	public final Map<BKBar, Integer> getpMapBKBarToHolding() {
		return pMapBKBarToHolding;
	}
	public final Map<BKAsset, Double> getpMapBKAssetToQuantity() {
		return pMapBKAssetToQuantity;
	}
	
}
