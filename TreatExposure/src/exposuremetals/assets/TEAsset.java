package exposuremetals.assets;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicPrintMsg;
import step0treatrawdata.objects.BKAsset;
import step1loadtransactions.transactions.BKTransaction;

public class TEAsset implements Comparable<TEAsset> {

	protected TEAsset(BKAsset _sBKAsset, TEAssetManager _sTEAssetManager) {
		pBKAsset = _sBKAsset;
		/*
		 * 
		 */
		pListBKTransaction = new ArrayList<>();
		pQuantity = 0.;
	}
	
	/*
	 * Data
	 */
	private TEAssetManager pTEAssetManager;
	private BKAsset pBKAsset;
	private List<BKTransaction> pListBKTransaction;
	private double pQuantity;
	
	/**
	 * 
	 * @param _sBKTransaction
	 */
	public final void declareNewBKTransaction(BKTransaction _sBKTransaction) {
		if (!_sBKTransaction.getpBKAsset().equals(pBKAsset)) {
			BasicPrintMsg.error("Error");
		}
		pQuantity += _sBKTransaction.getpQuantity();
	}

	@Override public int compareTo(TEAsset _sTEAsset) {
		return pBKAsset.getpName().compareTo(_sTEAsset.getpBKAsset().getpName());
	}
	
	/*
	 * Getters & Setters
	 */
	public TEAssetManager getpTEAssetManager() {
		return pTEAssetManager;
	}
	public BKAsset getpBKAsset() {
		return pBKAsset;
	}
	public List<BKTransaction> getpListBKTransaction() {
		return pListBKTransaction;
	}
	public double getpQuantity() {
		return pQuantity;
	}

	
	
	
}
