package bkbars.writer;

import bkbars.BRManager;
import bkbars.object.BRBar;
import bkbars.object.BRBarNotCurved;

public class BRWriter {

	public BRWriter(BRManager _sBRManager) {
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
		/*
		 * Initiate
		 */
		BRFile lBRFile = new BRFile("All bars");
		BRFile lBRFileComptaNotMalca = new BRFile("Bars in Compta and not in Malca");
		BRFile lBRFileMalcaNotCompta = new BRFile("Bars in Malca and not in Compta");
		/*
		 * File content for bars curved
		 */
		for (BRBar lBRBar : pBRManager.getpBRBarManager().getpMapKeyStrToBRBar().values()) {
			if (!lBRBar.getpIsNotCurved()) {
				lBRFile.addNewLineToWrite(lBRBar);
				if (lBRBar.getpIsInCompta() && !lBRBar.getpIsInMalca()) {
					lBRFileComptaNotMalca.addNewLineToWrite(lBRBar);
				}
				if (lBRBar.getpIsInMalca() && !lBRBar.getpIsInCompta()) {
					lBRFileMalcaNotCompta.addNewLineToWrite(lBRBar);
				}
			}
		}
		/*
		 * File content for bars NOT curved
		 */
		for (BRBarNotCurved lBRBarNotCurved : pBRManager.getpBRBarManager().getpMapKeyStrToBRBarNotCurved().values()) {
			lBRFile.addNewLineToWrite(lBRBarNotCurved);
			if (!lBRBarNotCurved.getpIsMatchComptaAndMalca()) {
				lBRFileComptaNotMalca.addNewLineToWrite(lBRBarNotCurved);
				lBRFileMalcaNotCompta.addNewLineToWrite(lBRBarNotCurved);
			}
		}
		/*
		 * Write files output
		 */
		lBRFile.writeFile();
		lBRFileComptaNotMalca.writeFile();
		lBRFileMalcaNotCompta.writeFile();
	}


}
