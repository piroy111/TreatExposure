package exposuremetals.accountasset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicPrintMsg;
import exposuremetals.TEManager;
import exposuremetals.accounts.TEAccount;
import exposuremetals.assets.TEAsset;
import step1loadtransactions.transactions.BKTransaction;

public class TEAccountAssetManager {

	public TEAccountAssetManager(TEManager _sTEManager) {
		pTEManager = _sTEManager;
		/*
		 * 
		 */
		pMapKeyStrToTEAccountAsset = new HashMap<>();
		pListTEAccountAsset = new ArrayList<>();
		pListBKTransactionKept = new ArrayList<>();
	}

	/*
	 * Data
	 */
	private TEManager pTEManager;
	private Map<String, TEAccountAsset> pMapKeyStrToTEAccountAsset;
	private List<TEAccountAsset> pListTEAccountAsset;
	private List<BKTransaction> pListBKTransactionKept;
	
	/**
	 * 
	 */
	public final void createFromBKTransaction() {
		BasicPrintMsg.displayTitle(this, "Create TEAccountAsset from BKTransactions");
		for (BKTransaction lBKTransaction : pTEManager.getpBKTransactionManager().getpListBKTransaction()) {
			if (pTEManager.getpTEAssetManager().getpIsKeepBKAsset(lBKTransaction.getpBKAsset())) {
				/*
				 * 
				 */
				TEAccount lTEAccount = pTEManager.getpTEAccountManager()
						.getpTEAccountFromBKAccount(lBKTransaction.getpBKAccount());
				TEAsset lTEAsset = pTEManager.getpTEAssetManager().getpOrCreateTEAsset(lBKTransaction.getpBKAsset());
				TEAccountAsset lTEAccountAsset = getpOrCreateTEAccountAsset(lTEAccount, lTEAsset);
				/*
				 * 
				 */
				lTEAccount.declareNewTEAccountAsset(lTEAccountAsset);
				lTEAccountAsset.declareNewBKTransaction(lBKTransaction);
				lTEAsset.declareNewBKTransaction(lBKTransaction);
				pTEManager.getpTEMetalManager().declareNewBKTransactionForBunker(lBKTransaction);
				pListBKTransactionKept.add(lBKTransaction);
			}
		}
	}
	
	/**
	 * 
	 * @param _sBKAccountAsset
	 * @return
	 */
	public final TEAccountAsset getpOrCreateTEAccountAsset(TEAccount _sTEAccount, TEAsset _sTEAsset) {
		String lKeyStr = TEAccountAsset.getUniqueKeyStr(_sTEAccount, _sTEAsset);
		TEAccountAsset lTEAccountAsset = pMapKeyStrToTEAccountAsset.get(lKeyStr);
		if (lTEAccountAsset == null) {
			lTEAccountAsset = new TEAccountAsset(_sTEAccount, _sTEAsset, this);
			pMapKeyStrToTEAccountAsset.put(lKeyStr, lTEAccountAsset);
			pListTEAccountAsset.add(lTEAccountAsset);
		}
		return lTEAccountAsset;
	}

	/*
	 * Getters & Setters
	 */
	public TEManager getpTEManager() {
		return pTEManager;
	}
	public final List<BKTransaction> getpListBKTransactionKept() {
		return pListBKTransactionKept;
	}
}
