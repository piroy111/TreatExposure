package bkbars.loadcompta;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicFichiersNio;
import basicmethods.BasicPrintMsg;
import basicmethods.LitUnFichierEnLignes;
import bkbars.BRManager;
import staticdata.StaticDir;

public class BRComptaLoader {

	public BRComptaLoader(BRManager _sBRManager) {
		pBRManager = _sBRManager;
	}
	
	/*
	 * Data
	 */
	private BRManager pBRManager;
	private List<String> pListReferenceBarsNotCurved;
	
	/**
	 * 
	 */
	public final void run() {
		/*
		 * Load file CONF which gives the list of the bars in COMPTA which are not curved and whose number is virtual
		 */
		pListReferenceBarsNotCurved = new ArrayList<>();
		String lNameFile = "Bars not curved.csv";
		String lDir = StaticDir.getIMPORT_BARS_MALCA_AMIT();
		LitUnFichierEnLignes lReadFile = new LitUnFichierEnLignes(lDir, lNameFile, true);
		for (List<String> lListLine : lReadFile.getmContenuFichierListe()) {
			int lIdx = -1;
			++lIdx;
			String lReference = lListLine.get(++lIdx);
			++lIdx;
			pListReferenceBarsNotCurved.add(lReference);
		}
		BasicPrintMsg.display(this, "File of non curved bars read sucessfully; File= '" + lDir + lNameFile + "'");
		/*
		 * Load files COMPTA
		 */
		loadFilesFromDir(StaticDir.getIMPORT_REFINERS_PURCHASE());
		loadFilesFromDir(StaticDir.getIMPORT_CLIENTS_PURCHASE());
		loadFilesFromDir(StaticDir.getIMPORT_DELIVERY());
	}

	/**
	 * 
	 */
	private void loadFilesFromDir(String _sDir) {
		List<Path> lListPath = BasicFichiersNio.getListFilesAndSubFiles(Paths.get(_sDir));
		for (Path lPath : lListPath) {
			String lNameFile = lPath.getFileName().toString();
			BRFileCompta lBRFileCompta = new BRFileCompta(_sDir, lNameFile, this);
			lBRFileCompta.loadFileIntoBRBar();
		}
	}
	
	/*
	 * Getters & Setters
	 */
	public final BRManager getpBRManager() {
		return pBRManager;
	}
	public final List<String> getpListReferenceBarsNotCurved() {
		return pListReferenceBarsNotCurved;
	}
	
}
