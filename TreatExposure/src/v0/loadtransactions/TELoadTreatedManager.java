package v0.loadtransactions;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import basicmethods.BasicString;
import basicmethods.LitUnFichierEnLignes;
import step0treatrawdata.objects.BKAsset;
import step0treatrawdata.objects.BKAssetManager;
import step1loadtransactions.accounts.BKAccount;
import step1loadtransactions.accounts.BKAccountManager;
import v0.main.TEManagerV0;
import v0.main.TETransactionV0;

public class TELoadTreatedManager {

	public TELoadTreatedManager(TEManagerV0 _sTEManager) {
		pTEManager = _sTEManager;
	}
	
	/*
	 * Data
	 */
	private TEManagerV0 pTEManager;
	
	/**
	 * 
	 * @param _sDir
	 */
	public List<TETransactionV0> load(Object _sLauncher, String _sDir) {
		/*
		 * Communication
		 */
		BasicPrintMsg.display(_sLauncher, "Launch treated for dir= " + _sDir, 50);
		BasicPrintMsg.display(_sLauncher, null);
		/*
		 * Load all the files of the directory
		 */
		List<TETransactionV0> lListTETransaction = new ArrayList<>();
		List<Path> lListPath = BasicFichiersNio.getListFilesAndSubFiles(Paths.get(_sDir));
		for (Path lPath : lListPath) {
			LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(lPath, true);
			/*
			 * Identify the place of the columns
			 */
			int lIdxBKAsset = lReadFile.getmHeadersAndCommentList().get(0).indexOf("BKAsset");
			int lIdxBKAccount = lReadFile.getmHeadersAndCommentList().get(0).indexOf("BKAccount");
			int lIdxAmount = lReadFile.getmHeadersAndCommentList().get(0).indexOf("Amount");
			if (lIdxBKAsset == -1 || lIdxBKAccount == -1 || lIdxAmount == -1) {
				BasicPrintMsg.error("Error");
			}
			/*
			 * Load file
			 */			
			for (List<String> lLineStr : lReadFile.getmContenuFichierListe()) {
				if (lLineStr.size() == 0) {
					continue;
				}
				/*
				 * Load
				 */
				BKAsset lBKAsset = BKAssetManager.getpAndCheckBKAsset(lLineStr.get(lIdxBKAsset));
				double lAmount = BasicString.getDouble(lLineStr.get(lIdxAmount));
				BKAccount lBKAccount = BKAccountManager.getpBKAccount(lLineStr.get(lIdxBKAccount));
				/*
				 * Create TETransaction
				 */
				TETransactionV0 lTETransaction = pTEManager.getpTETransactionManager().createNewTETransaction(lBKAccount, lBKAsset, lAmount);
				if (lTETransaction != null) {
					lListTETransaction.add(lTETransaction);
				}
			}
		}
		/*
		 * Communications
		 */
		pTEManager.getpTEDisplay().display(_sLauncher, lListTETransaction);
		return lListTETransaction;
	}
	
}
