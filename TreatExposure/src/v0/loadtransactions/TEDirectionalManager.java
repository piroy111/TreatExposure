package v0.loadtransactions;

import java.util.List;

import basicmethods.BasicPrintMsg;
import v0.main.TEManagerV0;
import v0.main.TETransactionV0;

public class TEDirectionalManager {

	public TEDirectionalManager(TEManagerV0 _sTEManager) {
		pTEManager = _sTEManager;
	}
	
	/*
	 * Data
	 */
	private TEManagerV0 pTEManager;
	
	/**
	 * 
	 */
	public final void run() {
		BasicPrintMsg.displayTitle(this, "Load acknowledged directional");
		String lDir = "F:/BUNKER_V2/11_COMPTA/01_Import_csv/04_Dump/Directional/";
		List<TETransactionV0> lListTETransaction = pTEManager.getpTELoadTreatedManager().load(this, lDir);
		for (TETransactionV0 lTETransaction : lListTETransaction) {
			lTETransaction.changeSignAmount();
		}
	}
	
}
