package bkholdings.bktransaction;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicPrintMsg;
import bkholdings.STL_BKHoldingsRealTime_LaunchMe;
import bkholdings.TKManager;
import bkholdings.accounts.TKAccount;
import staticdata.StaticDir;
import staticdata.StaticNames;
import step1loadtransactions.accounts.BKAccount;
import step1loadtransactions.accounts.BKAccountManager;
import step1loadtransactions.transactions.BKTransaction;

public class TKBKTransactionReader {

	public TKBKTransactionReader(TKManager _sTKManager) {
		pTKManager = _sTKManager;
	}

	/*
	 * Data
	 */
	private TKManager pTKManager;

	/**
	 * Allocate the BKTransaction to each TAAccount<br>
	 * create TAAccount<br>
	 * Write a file dump with all BKTransactions for debug purposes<br>
	 */
	public final void readAndAllocate() {
		List<String> lListeLineToWriteDebug = new ArrayList<>();
		List<String> lListeLineToWriteBKTransactions = new ArrayList<>();
		for (BKTransaction lBKTransaction : pTKManager.getpBKTransactionManager().getpListBKTransaction()) {
			BKAccount lBKAccount = lBKTransaction.getpBKAccount();
			if (lBKAccount.equals(BKAccountManager.getpBKAccountBunker())
					|| lBKAccount.equals(BKAccountManager.getpBKAccountPierreRoy())) {
				continue;
			}
			/*
			 * Create TKAccount and increment its data with BKTransaction (Currency + Bar holding)
			 */			
			TKAccount lTKAccount = pTKManager.getpTKAccountManager().getpOrCreateTKAccount(lBKAccount);
			lTKAccount.declareNewBKTransaction(lBKTransaction);
			/*
			 * Load BKTransaction for file debug
			 */
			String lLine = lBKTransaction.getpDate()
					+ "," + lBKTransaction.getpBKAsset().getpName()
					+ "," + lBKTransaction.getpComment()
					+ "," + lBKTransaction.getpQuantity()
					+ "," + lBKTransaction.getpBKPrice()
					+ "," + lBKTransaction.getpBKAccount().getpEmailAddress()
					+ "," + lBKTransaction.getpFileNameOrigin()
					+ "," + lBKTransaction.getpBKIncome()
					+ "," + lBKTransaction.getpValueUSD();
			lListeLineToWriteDebug.add(lLine);
			if (lBKTransaction.getpDate() > STL_BKHoldingsRealTime_LaunchMe.DATE_START_BK_TRANSACTIONS) {
				lListeLineToWriteBKTransactions.add(lLine);
			}
		}
		/*
		 * Write file debug
		 */
		String lDirDebug = StaticDir.getOUTPUT_DEBUG();
		String lNameFileDebug = BasicDateInt.getmToday() + "_" + this.getClass().getSimpleName() + "_BKTransactions.csv";
		String lHeader = "Date,BKAsset,Comment,Quantity,BKPrice,BKAccount,FileNameOrigin,BKIncome,ValueUSD";
		BasicFichiers.writeFile(lDirDebug, lNameFileDebug, lHeader, lListeLineToWriteDebug);
		BasicPrintMsg.display(this, null);
		BasicPrintMsg.display(this, "File written successfully: '" + lDirDebug + lNameFileDebug + "'");
		/*
		 * Write file BKTransactions real time
		 */
		String lDirBKTRansactions = StaticDir.getOUTPUT_BKTRANSACTIONS_REAL_TIME();
		BasicFichiers.getOrCreateDirectory(lDirBKTRansactions);
		String lNameFileBKTransactions = BasicDateInt.getmToday() + StaticNames.getOUTPUT_BKTRANSACTIONS_REAL_TIME();
		BasicFichiers.writeFile(lDirBKTRansactions, lNameFileBKTransactions, lHeader, lListeLineToWriteBKTransactions);
		BasicPrintMsg.display(this, null);
		BasicPrintMsg.display(this, "File written successfully: '" + lDirBKTRansactions + lNameFileBKTransactions + "'");
	}
	
}
















