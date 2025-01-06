package v0.output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import step0treatrawdata.objects.BKAsset;
import step1loadtransactions.accounts.BKAccount;
import v0.main.TEManagerV0;
import v0.main.TETransactionV0;

public class TEComputeExposures {

	public TEComputeExposures(TEManagerV0 _sTEManager) {
		pTEManager = _sTEManager;
	}
	
	/*
	 * Data
	 */
	private TEManagerV0 pTEManager;
	private Map<BKAsset, Double> pMapBKAssetToAmount;

	/**
	 * 
	 */
	public final void run() {
		pMapBKAssetToAmount = new HashMap<>();
		for (BKAsset lBKAsset : pTEManager.getpTETransactionManager().getpMapBKAssetToMapBKAccountToListTETransaction().keySet()) {
			double lQuantity = 0;
			Map<BKAccount, List<TETransactionV0>> lMapBKAccountToListTETransaction = pTEManager.getpTETransactionManager().getpMapBKAssetToMapBKAccountToListTETransaction().get(lBKAsset);
			for (List<TETransactionV0> lListTETransaction : lMapBKAccountToListTETransaction.values()) {
				for (TETransactionV0 lTETransaction : lListTETransaction) {
					lQuantity += lTETransaction.getpQuantity();
				}
			}
			pMapBKAssetToAmount.put(lBKAsset, lQuantity);
		}
	}

	/*
	 * Getters & Setters
	 */
	public final Map<BKAsset, Double> getpMapBKAssetToAmount() {
		return pMapBKAssetToAmount;
	}
	
}
