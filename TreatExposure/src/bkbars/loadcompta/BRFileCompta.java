package bkbars.loadcompta;

import java.util.List;

import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.LitUnFichierEnLignes;
import bkbars.object.BRBar;
import bkbars.object.BRBarNotCurved;
import step0treatrawdata.objects.BKAsset;
import step0treatrawdata.objects.BKAssetManager;

public class BRFileCompta {

	protected BRFileCompta(String _sDir, String _sNameFile, BRComptaLoader _sBRComptaLoader) {
		pDir = _sDir;
		pNameFile = _sNameFile;
		pBRComptaLoader = _sBRComptaLoader;
	}
	
	/*
	 * Data
	 */
	private String pDir;
	private String pNameFile;
	private BRComptaLoader pBRComptaLoader;
	
	/**
	 * 
	 */
	public final void loadFileIntoBRBar() {
		if (pNameFile.contains("pierre.roy")) {
			return;
		}
		LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(pDir, pNameFile, true);
		List<String> lListHeader = lReadFile.getmHeadersAndCommentList().get(0);
		/*
		 * Load header
		 */
		int lIdxBKAsset = -1;
		int lIdxAmount = -1;
		int lIdxComment = -1; 
		for (int lIdx = 0; lIdx < lListHeader.size(); lIdx++) {
			String lHeader = lListHeader.get(lIdx);
			if (lHeader.equals("Comment")) {
				lIdxComment = lIdx;
			} else if (lHeader.equals("Amount")) {
				lIdxAmount = lIdx;
			} else if (lHeader.equals("BKAsset")) {
				lIdxBKAsset = lIdx;
			}
		}
		if (lIdxBKAsset == -1 || lIdxAmount == -1 || lIdxComment == -1) {
			String lMsgError = "The header is missing the column to determine the bars. I am looking for 'BKAsset', 'Comment', 'Amount'"
					+ "\nHeader= '" + lListHeader + "'"
					+ "\nlIdxBKAsset= " + lIdxBKAsset
					+ "\nlIdxComment= " + lIdxComment
					+ "\nlIdxAmount= " + lIdxAmount
					+ "\nDir= '" + pDir + "'"
					+ "\nFile= '" + pNameFile + "'";			
			BasicPrintMsg.error(lMsgError);
		}
		/*
		 * Read file
		 */
		for (List<String> lListLine : lReadFile.getmContenuFichierListe()) {
			/*
			 * Load line
			 */
			String lBKAssetStr = lListLine.get(lIdxBKAsset);
			String lComment = lListLine.get(lIdxComment);
			double lAmount = BasicString.getDouble(lListLine.get(lIdxAmount));
			BKAsset lBKAsset = BKAssetManager.getpAndCheckBKAsset(lBKAssetStr);
			if (lBKAsset.getpIsBar()) {
				/*
				 * Case of a future bar --> we skip
				 */
				if (lComment.startsWith("FUTURE_BAR")) {
					continue;
				}
				/*
				 * Create the bar
				 */
				BRBar lBRBar = pBRComptaLoader.getpBRManager().getpBRBarManager().getpOrCreateBRBar(lBKAssetStr, lComment, lAmount);
				lBRBar.addNewFileNameOriginCompta(pDir, pNameFile, lAmount);
				/*
				 * Case the bar is not curved --> we put it in the object BRBarNotCurved, which is a grouping of same BRBar not curved
				 */
				if (pBRComptaLoader.getpListReferenceBarsNotCurved().contains(lComment)) {
					BRBarNotCurved lBRBarNotCurved = pBRComptaLoader.getpBRManager().getpBRBarManager()
							.getpOrCreateBRBarNotCurved(lBKAssetStr, lAmount);
					lBRBarNotCurved.declareNewBRBarCompta(lBRBar);
				}
			}
		}
	}
	
	
	
}
