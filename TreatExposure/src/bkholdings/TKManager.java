package bkholdings;

import bkholdings.accounts.TKAccountManager;
import bkholdings.bktransaction.TKBKTransactionReader;
import bkholdings.writer.TKBKHoldingsWriter;
import step0treatrawdata.clientsrefinersbrokers.TREATManager;
import step1loadtransactions.transactions.BKTransactionManager;
import step2storage.SGManager;

public class TKManager {

	public TKManager() {
		pBKTransactionManager = new BKTransactionManager();
		pTKAccountManager = new TKAccountManager(this);
		pTKBKTransactionReader = new TKBKTransactionReader(this);
		pTKBKHoldingsWriter = new TKBKHoldingsWriter(this);
	}
	
	/*
	 * Data
	 */
	private BKTransactionManager pBKTransactionManager;
	private TKAccountManager pTKAccountManager;
	private TKBKTransactionReader pTKBKTransactionReader;
	private TKBKHoldingsWriter pTKBKHoldingsWriter;
	
	/**
	 * 
	 */
	public final void run() {
		/*
		 * Load files treated the same way the COMPTA does
		 */
		new TREATManager(true).run();
		/*
		 * Create all the BKTransactions, the same way the COMPTA does, except the loans because it does not concern the clients
		 */
		pBKTransactionManager.loadBKTransactionsExcludingLoans();
		new SGManager(pBKTransactionManager).run();
		/*
		 *  Create the files BKHoldings
		 */
		pTKBKTransactionReader.readAndAllocate();
		pTKBKHoldingsWriter.run();
	}

	/*
	 * Getters & Setters
	 */
	public final BKTransactionManager getpBKTransactionManager() {
		return pBKTransactionManager;
	}
	public final TKAccountManager getpTKAccountManager() {
		return pTKAccountManager;
	}
	public final TKBKHoldingsWriter getpTKBKHoldingsWriter() {
		return pTKBKHoldingsWriter;
	}
	
}
