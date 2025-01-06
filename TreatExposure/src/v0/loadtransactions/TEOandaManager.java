package v0.loadtransactions;

import basicmethods.BasicPrintMsg;
import staticdata.StaticDir;
import step0treatrawdata.brokers.oanda.OAManager;
import v0.main.TEManagerV0;

public class TEOandaManager {

	public TEOandaManager(TEManagerV0 _sTEManager) {
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
		BasicPrintMsg.displayTitle(this, "Load OANDA");
		/*
		 * Run OAManager to write the files treated
		 */
		new OAManager().run();
		/*
		 * Create the TETransactions
		 */
		BasicPrintMsg.display(this, null);
		pTEManager.getpTELoadTreatedManager().load(this, StaticDir.getTREATED_BROKERS_OANDA());
		pTEManager.getpTELoadTreatedManager().load(this, StaticDir.getTREATED_BROKERS_OANDA_CRYPTO());
	}
	
	
}
