package bkholdings.createfilestreated;

import basicmethods.BasicPrintMsg;
import bkholdings.TKManager;
import step0treatrawdata.clientsrefinersbrokers.TREATClients;
import step0treatrawdata.clientsrefinersbrokers.TREATDelivery;
import step0treatrawdata.clientsrefinersbrokers.TREATForex;
import step0treatrawdata.clientsrefinersbrokers.TREATManager;
import step0treatrawdata.clientsrefinersbrokers.TREATRoot;
import step0treatrawdata.fyadjustments.TREATFYAdjustments;
import step0treatrawdata.uob.versionnew.UOBTreatedManager;

/**
 * @deprecated
 *
 */
public class TKCreateFilesTreated {

	public TKCreateFilesTreated(TKManager _sTKManager) {
		pTKManager = _sTKManager;
	}
	
	/*
	 * Data
	 */
	private TKManager pTKManager;
	
	/**
	 * 
	 */
	public final void run() {
		BasicPrintMsg.displayTitle(this, "Write files treated for clients operations");
		/*
		 * Create for clients
		 */
		TREATManager lTREATManager = new TREATManager(false);
		new TREATClients(lTREATManager);
		new TREATDelivery(lTREATManager);
		new TREATForex(lTREATManager);
		/*
		 * Write files treated
		 */
		for (TREATRoot lTREATRoot : lTREATManager.getpListTreatRoot()) {
			lTREATRoot.treatDir();
		}
		new TREATFYAdjustments().run();
		/*
		 * Write the UOBTransactions from the raw files UOB into on single treated file
		 */
		UOBTreatedManager lUOBTreatedManager = new UOBTreatedManager();
		lUOBTreatedManager.loadFromRawFiles();
		lUOBTreatedManager.writeTreated();
	}

	/*
	 * Getters & Setters
	 */
	public TKManager getpTKManager() {
		return pTKManager;
	}
	
	
	
}
