package exposuremetals.writefile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicPrintMsg;
import exposuremetals.TEManager;
import exposuremetals.accountasset.TEAccountAsset;
import exposuremetals.accounts.TEAccount;
import exposuremetals.assets.TEAsset;
import exposuremetals.metal.TEMetal;
import staticdata.StaticDir;
import staticdata.StaticNames;
import step1loadtransactions.transactions.BKTransaction;

public class TEFileOutputWriter {

	public TEFileOutputWriter(TEManager _sTEManager) {
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
		BasicPrintMsg.displayTitle(this, "Write files output");
		writeFileAssets();
		writeFileExpoBunker();
		writeBKTransactionsKept();
	}
	
	/**
	 * 
	 */
	private void writeFileExpoBunker() {
		List<String> lListLineToWrite = new ArrayList<>();
		for (TEMetal lTEMetal : pTEManager.getpTEMetalManager().getpListTEMetal()) {
			String lLine = lTEMetal.getpNameMetal() 
					+ "," + lTEMetal.getpExposureBunker()
					+ "," + lTEMetal.getpExposureBunkerUSD();
			lListLineToWrite.add(lLine);
		}
		String lDir = StaticDir.getOUTPUT_EXPOSURE_REAL_TIME_STAND_ALONE();
		String lNameFile = BasicDateInt.getmToday() + StaticNames.getOUTPUT_EXPOSURE_SUMMARY_REAL_TIME_STAND_ALONE();
		String lHeader = "Metal,Net exposure for Bunker in Oz (=Bars+BarsLoan+Paper),Net exposure for Bunker in US$";
		BasicFichiers.writeFile(lDir, lNameFile, lHeader, lListLineToWrite);
		BasicPrintMsg.display(this, "File written successfully: '" + lDir + lNameFile + "'");
	}
	
	/**
	 * 
	 */
	private void writeFileAssets() {
		/*
		 * Initiate
		 */
		List<TEAsset> lListTEAsset = pTEManager.getpTEAssetManager().getpListTEAsset();
		Collections.sort(lListTEAsset);
		String lHeader = "TEAccount";
		for (TEAsset lTEAsset : lListTEAsset) {
			lHeader += "," + lTEAsset.getpBKAsset().getpName();
		}
		/*
		 * File content
		 */
		List<String> lListLineToWrite = new ArrayList<>();
		for (TEAccount lTEAccount : pTEManager.getpTEAccountManager().getpListTEAccount()) {
			String lMsg0 = "TEAccount= '" + lTEAccount.getpNameAccount() + "'";
			String lLine = lTEAccount.getpNameAccount();
			for (TEAsset lTEAsset : lListTEAsset) {
				TEAccountAsset lTEAccountAsset = lTEAccount.getpMapTEAssetToTEAccountAsset().get(lTEAsset);
				/*
				 * Display
				 */
				if (lTEAccountAsset != null) {
					String lMsg = lMsg0 + "; TEAsset= '" + lTEAccountAsset.getpTEAsset().getpBKAsset().getpName() + "'"
							+ "; Quantity= " + lTEAccountAsset.getpQuantity();
					BasicPrintMsg.display(this, lMsg);
				}
				/*
				 * File content
				 */
				lLine += ",";
				if (lTEAccountAsset != null) {
					lLine += lTEAccountAsset.getpQuantity();
				}
			}
			lListLineToWrite.add(lLine);
		}
		/*
		 * Write file
		 */
		String lDir = StaticDir.getOUTPUT_EXPOSURE_REAL_TIME_STAND_ALONE();
		String lNameFile = BasicDateInt.getmToday() + StaticNames.getOUTPUT_EXPOSURE_REAL_TIME_STAND_ALONE();
		BasicFichiers.writeFile(lDir, lNameFile, lHeader, lListLineToWrite);
		BasicPrintMsg.display(this, null);
		BasicPrintMsg.display(this, "File written successfully: '" + lDir + lNameFile + "'");
	}
	
	/**
	 * 
	 */
	private void writeBKTransactionsKept() {
		/*
		 * File content
		 */
		List<String> lListLineToWrite = new ArrayList<>();
		for (BKTransaction lBKTransaction : pTEManager.getpTEAccountAssetManager().getpListBKTransactionKept()) {
			lListLineToWrite.add(lBKTransaction.getpLineInFile());
		}
		/*
		 * Write file
		 */
		String lDir = StaticDir.getOUTPUT_EXPOSURE_REAL_TIME_STAND_ALONE();
		String lNameFile = BasicDateInt.getmToday() + StaticNames.getOUTPUT_EXPOSURE_REAL_TIME_STAND_ALONE_BKTRANSACTIONS();
		String lHeader = BKTransaction.getHeaderForFile();
		BasicFichiers.writeFile(lDir, lNameFile, lHeader, lListLineToWrite);
		BasicPrintMsg.display(this, null);
		BasicPrintMsg.display(this, "File written successfully: '" + lDir + lNameFile + "'");
	}
	
	/*
	 * Getters & Setters
	 */
	public TEManager getpTEManager() {
		return pTEManager;
	}
}
