package bkbars.loadmalca;

import bkbars.BRManager;
import staticdata.StaticDir;

public class BRMalcaLoader {

	public BRMalcaLoader(BRManager _sBRManager) {
		pBRManager = _sBRManager;
	}
	
	/*
	 * Data
	 */
	private BRManager pBRManager;
	
	/**
	 * 
	 */
	public final void run() {
		String lDir = StaticDir.getIMPORT_BARS_MALCA_AMIT();
		String lNameFile = "Bunker Group Stock Balance.csv";
		BRFileMalca lBRFileMalca = new BRFileMalca(lDir, lNameFile, this);
		lBRFileMalca.loadFileIntoBRBar();
	}

	/*
	 * Getters & Setters
	 */
	public final BRManager getpBRManager() {
		return pBRManager;
	}
	
}
