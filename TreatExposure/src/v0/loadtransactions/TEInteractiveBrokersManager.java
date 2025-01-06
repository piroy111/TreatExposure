package v0.loadtransactions;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicPrintMsg;
import staticdata.StaticDir;
import step0treatrawdata.brokers.interactivebrokers.IBManager;
import step0treatrawdata.brokers.interactivebrokers.transactions.IBTransaction;
import v0.main.TEManagerV0;
import v0.main.TETransactionV0;

public class TEInteractiveBrokersManager {

	public TEInteractiveBrokersManager(TEManagerV0 _sTEManager) {
		pTEManager = _sTEManager;
	}
	
	/*
	 * Data
	 */
	private TEManagerV0 pTEManager;
	
	/**
	 * Create TETransactions
	 */
	public final void run() {
		BasicPrintMsg.displayTitle(this, "Load Interactive Brokers");
		/*
		 * Load transactions treated
		 */
		pTEManager.getpTELoadTreatedManager().load(this, StaticDir.getTREATED_BROKERS_IB());
		/*
		 * Load new transactions
		 */
		BasicPrintMsg.display(this, null);
		BasicPrintMsg.display(this, "Load new IBTransactions", 50);
		IBManager lIBManager = new IBManager();
		lIBManager.runForStandAlone();
		List<TETransactionV0> lListTETransaction = new ArrayList<>();
		for (IBTransaction lIBTransaction : lIBManager.getpIBTransactionManager().getpListIBTransaction()) {
			TETransactionV0 lTETransaction = pTEManager.getpTETransactionManager()
					.createNewTETransaction(lIBTransaction.getpBKAccount(), lIBTransaction.getpBKAsset(), lIBTransaction.getpAmount());
			if (lTETransaction != null) {
				lListTETransaction.add(lTETransaction);
			}
		}
		/*
		 * Communications
		 */
		pTEManager.getpTEDisplay().display(this, lListTETransaction);
	}
	
	
	
}
