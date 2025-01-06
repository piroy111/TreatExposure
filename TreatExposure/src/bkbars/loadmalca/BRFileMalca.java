package bkbars.loadmalca;

import java.util.List;

import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.LitUnFichierEnLignes;
import bkbars.object.BRBar;
import bkbars.object.BRBarNotCurved;

public class BRFileMalca {

	protected BRFileMalca(String _sDir, String _sNameFile, BRMalcaLoader _sBRMalcaLoader) {
		pDir = _sDir;
		pNameFile = _sNameFile;
		pBRMalcaLoader = _sBRMalcaLoader;
	}

	/*
	 * Data
	 */
	private String pDir;
	private String pNameFile;
	private BRMalcaLoader pBRMalcaLoader;
	private int pSizeMaxLine;

	/**
	 * 
	 */
	public final void loadFileIntoBRBar() {
		/*
		 * Interpret the header
		 */
		LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(pDir, pNameFile, true);
		int lIdxBKAsset = -1;
		int lIdxQty = -1;
		int lIdxSerial = -1;
		int lIdxUnit = -1;
		int lIdxWeight = -1;
		pSizeMaxLine = -1;
		for (int lIdxLine = 0; lIdxLine < 5; lIdxLine++) {
			List<String> lListHeader = lReadFile.getmContenuFichierListe().get(lIdxLine);
			for (int lIdx = 0; lIdx < lListHeader.size(); lIdx++) {
				String lHeader = lListHeader.get(lIdx);
				if (lHeader.equals("Qty")) {
					lIdxQty = checkBrandNew(lIdxQty, lIdx);
				} else if (lHeader.equals("BAR")) {
					lIdxBKAsset = checkBrandNew(lIdxBKAsset, lIdx);
				} else if (lHeader.equals("Serial")) {
					lIdxSerial = checkBrandNew(lIdxSerial, lIdx);
				} else if (lHeader.equals("Weight")) {
					lIdxWeight = checkBrandNew(lIdxWeight, lIdx);
				} else if (lHeader.equals("KG") || lHeader.equals("GRAM") || lHeader.equals("TOZ")) {
					lIdxUnit = checkBrandNew(lIdxUnit, lIdx);
				}
			}
			if (lIdxBKAsset != -1 && lIdxQty != -1 && lIdxSerial != -1 && lIdxWeight != -1 && lIdxUnit != -1) {
				break;
			}
		}
		String lMsgIdx = "\n   lIdxBKAsset= " + lIdxBKAsset
				+ "\n   lIdxQty= " + lIdxQty
				+ "\n   lIdxSerial= " + lIdxSerial
				+ "\n   lIdxUnit= " + lIdxUnit
				+ "\n   lIdxWeight= " + lIdxWeight;
		if (lIdxBKAsset == -1 || lIdxQty == -1 || lIdxSerial == -1 || lIdxUnit == -1 || lIdxWeight == -1) {
			String lMsgError = "The header is missing the column to determine the bars. I am looking for 'BAR', 'Serial', 'Weight', etc."
					+ lMsgIdx
					+ "\nDir= '" + pDir + "'"
					+ "\nFile= '" + pNameFile + "'";			
			BasicPrintMsg.error(lMsgError);
		}
		BasicPrintMsg.display(this, "Header detected in file= '" + pNameFile + "': " + lMsgIdx);
		/*
		 * Read file
		 */
		String lNameMetal = null;
		for (List<String> lListLine : lReadFile.getmContenuFichierListe()) {
			if (lListLine.size() < pSizeMaxLine) {
				continue;
			}
			/*
			 * Detect title for name of metal
			 */
			String lWord1 = lListLine.get(0);
			if (lWord1.trim().startsWith("ITEM:")) {
				if (lWord1.contains("GOLD")) {
					lNameMetal = "GOLD BAR OZ";
				} else if (lWord1.contains("SILVER")) {
					lNameMetal = "SILVER BAR OZ";
				} else if (lWord1.contains("PLATINUM")) {
					lNameMetal = "PLATINUM BAR OZ";
				} else {
					BasicPrintMsg.error(lWord1);
				}
			}
			/*
			 * Load line
			 */
			String lUnit = lListLine.get(lIdxUnit);
			String lBKAssetStr = lListLine.get(lIdxBKAsset);
			String lPackage = lListLine.get(lIdxBKAsset + 1);
			String lSerial = lListLine.get(lIdxSerial);
			int lQty = BasicString.getInt(lListLine.get(lIdxQty));
			double lWeight = BasicString.getDouble(lListLine.get(lIdxWeight));
			/*
			 * Check if it is a bar and find the weight
			 */
			if (lBKAssetStr.equals("BAR") || lBKAssetStr.equals("INGOT")) {
				if (lUnit.equals("KG")) {
					lWeight = lWeight * 1000 / 31.1034768;
				} else if (lUnit.equals("GRAM")) {
					lWeight = lWeight / 31.1034768;
				} else if (!lUnit.equals("TOZ")) {
					BasicPrintMsg.error("Unknown unit; Unit= " + lUnit
							+ "\nDir= " + pDir
							+ "\nFile= " + pNameFile);
				}
				/*
				 * Case of the serial empty --> it means the bar is not curved
				 */
				boolean lIsNotCurved = false;
				if (lSerial.equals("")) {
					lIsNotCurved = true;
					lWeight = lWeight / lQty;
					lSerial = lPackage;
				}
				/*
				 * Create the object and link it to this file
				 */
				BRBar lBRBar = pBRMalcaLoader.getpBRManager().getpBRBarManager().getpOrCreateBRBar(lNameMetal, lSerial, lWeight);
				for (int lQtyLoop = 0; lQtyLoop < lQty; lQtyLoop++) {
					lBRBar.addNewFileNameOriginMalca(pDir, pNameFile, lWeight);
				}
				/*
				 * Case of bar not curved -> we create the object BRBarNotCurved which is a grouping of BRBar of the same type
				 */
				if (lIsNotCurved) {
					BRBarNotCurved lBRBarNotCurved = pBRMalcaLoader.getpBRManager().getpBRBarManager()
							.getpOrCreateBRBarNotCurved(lNameMetal, lWeight);
					lBRBarNotCurved.declareNewBRBarMalca(lBRBar);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param _sIdxPrevious
	 * @param _sIdxNew
	 * @return
	 */
	private int checkBrandNew(int _sIdxPrevious, int _sIdxNew) {
		if (_sIdxPrevious != -1 && _sIdxPrevious != _sIdxNew) {
			BasicPrintMsg.error("Error. We fill twice the same Idx with 2 different values"
					+ "\n_sIdxPrevious= " + _sIdxPrevious
					+ "\n_sIdxNew= " + _sIdxNew);
		}
		pSizeMaxLine = Math.max(pSizeMaxLine, _sIdxNew);
		return _sIdxNew;
	}
	

}
