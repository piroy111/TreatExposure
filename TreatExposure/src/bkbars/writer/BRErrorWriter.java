package bkbars.writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import bkbars.BRManager;
import staticdata.StaticDir;

public class BRErrorWriter {

	public BRErrorWriter(BRManager _sBRManager) {
		pBRManager = _sBRManager;
		/*
		 * 
		 */
		pMapHeaderToListMessageInError = new HashMap<>();
	}
	
	/*
	 * Data
	 */
	private BRManager pBRManager;
	private Map<String, List<String>> pMapHeaderToListMessageInError;
	
	/**
	 * 
	 */
	public final void run() {
		String lDir = StaticDir.getOUTPUT_BARS_MALCA_AMIT();
		String lNameFile = BasicDateInt.getmToday() + "_" + this.getClass().getSimpleName() + ".csv";
		List<String> lListLineToWrite = new ArrayList<>();
		for (String lHeader : pMapHeaderToListMessageInError.keySet()) {
			if (lListLineToWrite.size() > 0) {
				lListLineToWrite.add("");
			}
			lListLineToWrite.add("#" + lHeader);
			List<String> lListMsgError = pMapHeaderToListMessageInError.get(lHeader);
			for (String lMsgError : lListMsgError) {
				lListLineToWrite.add(lMsgError);
			}
		}
		BasicFichiers.writeFile(lDir, lNameFile, null, lListLineToWrite);
		System.err.println("Error in the program. The list of errors is written in the file:"
				+ "\nDir= '" + lDir + "'"
				+ "\nNameFile= '" + lNameFile + "'");
	}
	
	/**
	 * 
	 * @param _sHeader
	 * @param _sMsgError
	 */
	public final void addNewMessageInError(Object _sObjectSender, String _sHeader, String _sMsgError) {
		String lHeader = "Class," + _sHeader;
		List<String> lListMsgError = pMapHeaderToListMessageInError.get(lHeader);
		if (lListMsgError == null) {
			lListMsgError = new ArrayList<>();
			pMapHeaderToListMessageInError.put(lHeader, lListMsgError);
		}
		lListMsgError.add(_sObjectSender.getClass().getSimpleName() + "," + _sMsgError);
	}

	/*
	 * Getters & Setters
	 */
	public final BRManager getpBRManager() {
		return pBRManager;
	}
	public final Map<String, List<String>> getpMapHeaderToListMessageInError() {
		return pMapHeaderToListMessageInError;
	}
	
	
}
