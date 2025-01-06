package exposuremetals;

import basicmethods.BasicDateInt;
import exposuremetals.accountasset.TEAccountAssetManager;
import exposuremetals.accounts.TEAccountManager;
import exposuremetals.assets.TEAssetManager;
import exposuremetals.loadbktransactions.TECreateFilesTreated;
import exposuremetals.metal.TEMetalManager;
import exposuremetals.writefile.TEFileOutputWriter;
import staticdata.StaticDate;
import staticdata.StaticDir;
import step0treatrawdata.objects.BKAssetManager;
import step1loadtransactions.transactions.BKTransactionManager;
import step2loans.createloanstransactions.LNManager;

public class TEManager {

	public TEManager() {
		pBKTransactionManager = new BKTransactionManager();
		pTECreateFilesTreated = new TECreateFilesTreated(this);
		pTEAccountAssetManager = new TEAccountAssetManager(this);
		pTEAccountManager = new TEAccountManager(this);
		pTEAssetManager = new TEAssetManager(this);
		pTEFileOutputWriter = new TEFileOutputWriter(this);
		pTEMetalManager = new TEMetalManager(this);
	}
	
	
	/*
	 * Data
	 */
	private BKTransactionManager pBKTransactionManager;
	private TECreateFilesTreated pTECreateFilesTreated;
	private TEAccountAssetManager pTEAccountAssetManager;
	private TEAccountManager pTEAccountManager;
	private TEAssetManager pTEAssetManager;
	private TEFileOutputWriter pTEFileOutputWriter;
	private TEMetalManager pTEMetalManager;
	
	/**
	 * 
	 */
	public final void run() {
		StaticDate.setDATE_MAX(BasicDateInt.getmToday());
		/*
		 * Compute loans and write file treated loans
		 */
		LNManager lLNManager = new LNManager();
		lLNManager.run(true);
		/*
		 * write files treated
		 */
		BKAssetManager.loadFileHisto();
		pTECreateFilesTreated.run();
		/*
		 * Load BKTransaction from files treated
		 */
		pBKTransactionManager.loadBKTransactionsOnlyForMetals(false);
		pBKTransactionManager.readFile(StaticDir.getTREATED_LOANS());
		/*
		 * Create the TEAsset from the BKTransactions
		 */		
		pTEAccountAssetManager.createFromBKTransaction();
		/*
		 * Write the exposures
		 */
		pTEFileOutputWriter.run();
	}

	/*
	 * Getters & Setters
	 */
	public BKTransactionManager getpBKTransactionManager() {
		return pBKTransactionManager;
	}
	public TECreateFilesTreated getpTECreateFilesTreated() {
		return pTECreateFilesTreated;
	}
	public TEAccountAssetManager getpTEAccountAssetManager() {
		return pTEAccountAssetManager;
	}
	public TEAccountManager getpTEAccountManager() {
		return pTEAccountManager;
	}
	public TEAssetManager getpTEAssetManager() {
		return pTEAssetManager;
	}
	public final TEMetalManager getpTEMetalManager() {
		return pTEMetalManager;
	}
	
	
}
