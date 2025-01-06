package v0.loadtransactions;

import basicmethods.BasicPrintMsg;
import staticdata.StaticDir;
import step0treatrawdata.clientsrefinersbrokers.TREATClients;
import step0treatrawdata.clientsrefinersbrokers.TREATManager;
import step0treatrawdata.clientsrefinersbrokers.TREATRefiners;
import step0treatrawdata.clientsrefinersbrokers.TREATRoot;
import v0.main.TEManagerV0;

public class TETreatClientsAndRefinersManager {

	public TETreatClientsAndRefinersManager(TEManagerV0 _sTEManager) {
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
		BasicPrintMsg.displayTitle(this, "Load Clients and refiners");
		/*
		 * convert new files into treated files for refiner and clients
		 */
		TREATManager lTREATManager = new TREATManager(true);
		for (TREATRoot lTREATRoot : lTREATManager.getpListTreatRoot()) {
			if (lTREATRoot instanceof TREATClients || lTREATRoot instanceof TREATRefiners) {
				lTREATRoot.treatDir();
			}
		}
		/*
		 * read treated files
		 */
		BasicPrintMsg.display(this, null);
		pTEManager.getpTELoadTreatedManager().load(this, StaticDir.getTREATED_CLIENTS_PURCHASE());
		BasicPrintMsg.display(this, null);
		pTEManager.getpTELoadTreatedManager().load(this, StaticDir.getTREATED_REFINERS_PURCHASE());
	}
	
}
