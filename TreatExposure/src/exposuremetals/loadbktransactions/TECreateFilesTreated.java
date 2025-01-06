package exposuremetals.loadbktransactions;

import exposuremetals.TEManager;
import step0treatrawdata.checkdeliveries.TRCheckDeliveries;
import step0treatrawdata.clientsrefinersbrokers.TREATClients;
import step0treatrawdata.clientsrefinersbrokers.TREATDirectional;
import step0treatrawdata.clientsrefinersbrokers.TREATManager;
import step0treatrawdata.clientsrefinersbrokers.TREATRefiners;
import step0treatrawdata.clientsrefinersbrokers.TREATRoot;

public class TECreateFilesTreated {

	public TECreateFilesTreated(TEManager _sTEManager) {
		pTEManager = _sTEManager;
	}

	/*
	 * Data
	 */
	private TEManager pTEManager;

	/**
	 * 
	 */
	public final void run() {
		/*
		 * Create
		 */
		TREATManager lTREATManager = new TREATManager(false);
		new TREATClients(lTREATManager);
		new TREATRefiners(lTREATManager);
		new TREATDirectional(lTREATManager);
		/*
		 * Write files treated
		 */
		for (TREATRoot lTREATRoot : lTREATManager.getpListTreatRoot()) {
			lTREATRoot.treatDir();
		}
		/*
		 * Check deliveries are not at the same date as a transaction
		 */
		new TRCheckDeliveries().run();
	}

	/*
	 * Getters & Setters
	 */
	public TEManager getpTEManager() {
		return pTEManager;
	}
	
	
}

