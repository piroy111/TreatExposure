package bkholdings.writer;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicPrintMsg;
import bkholdings.TKManager;
import bkholdings.accounts.TKAccount;
import staticdata.StaticDir;
import staticdata.StaticNames;
import step0treatrawdata.objects.BKAsset;
import step1loadtransactions.bars.BKBar;

public class TKBKHoldingsWriter {

	public TKBKHoldingsWriter(TKManager _sTKManager) {
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
		BasicPrintMsg.displayTitle(this, "Write file BKHoldings real time");
		/*
		 * Initiate
		 */
		String lDir = StaticDir.getOUTPUT_BKHOLDINGS_REAL_TIME() + BasicDateInt.getmToday() + "/";
		BasicFichiers.getOrCreateDirectory(lDir);
		String lNameFileRoot = BasicDateInt.getmToday() 
				+ StaticNames.getOUTPUT_BKHOLDINGS_REAL_TIME();
		BasicPrintMsg.display(this, "Files will be written in DIR= '" + lDir + "'");
		/*
		 * 
		 */
		for (TKAccount lTKAccount : pTKManager.getpTKAccountManager().getpListTKAccount()) {
			List<String> lListLineToWrite = new ArrayList<>();
			/*
			 * Account
			 */
			String lLineAccount = "Account" + "," + lTKAccount.getpBKAccount().getpEmailAddress();
			lListLineToWrite.add(lLineAccount);
			/*
			 * Other assets
			 */
			lListLineToWrite.add("");
			for (BKAsset lBKAsset : lTKAccount.getpMapBKAssetToQuantity().keySet()) {
				double lQuantity = lTKAccount.getpMapBKAssetToQuantity().get(lBKAsset);
				String lLineOtherAsset = lBKAsset.getpName() + "," + lQuantity;
				lListLineToWrite.add(lLineOtherAsset);
			}
			/*
			 * Bars
			 */
			lListLineToWrite.add("");
			lListLineToWrite.add("Metal,Bar code,Weight(Oz)");
			for (BKBar lBKBar : lTKAccount.getpMapBKBarToHolding().keySet()) {
				int lHolding = lTKAccount.getpMapBKBarToHolding().get(lBKBar);
				if (lHolding == 1) {
					String lLineBKBar = lBKBar.getpBKAsset().getpMetalName()
							+ "," + lBKBar.getpRef()
							+ "," + lBKBar.getpWeightOz();
					lListLineToWrite.add(lLineBKBar);
				} else if (lHolding != 0) {
					BasicPrintMsg.error("Error in holding"
							+ "\nBKBar= '" + lBKBar.getpRef() + "'"
							+ "\nHolding= " + lHolding);
				}
			}
			/*
			 * Write file
			 */
			String lNameFile = lNameFileRoot + lTKAccount.getpBKAccount().getpEmailAddress() + ".csv";
			BasicFichiers.writeFile(lDir, lNameFile, null, lListLineToWrite);
			BasicPrintMsg.display(this, "File written successfully: '" + lNameFile + "'");
		}
	}

	/*
	 * Getters & Setters
	 */
	public final TKManager getpTKManager() {
		return pTKManager;
	}
	
	
	
}
