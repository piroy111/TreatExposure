package bkbars.writer;

import java.util.ArrayList;
import java.util.List;

import basicmethods.BasicDateInt;
import basicmethods.BasicFichiers;
import basicmethods.BasicPrintMsg;
import bkbars.object.BRBar;
import bkbars.object.BRBarAbstract;
import bkbars.object.BRBarNotCurved;
import staticdata.StaticDir;

public class BRFile {

	protected BRFile(String _sNameFile) {
		pNameFile = BasicDateInt.getmToday() + "_" + _sNameFile + ".csv";
		/*
		 * 
		 */
		pListLineToWrite = new ArrayList<>();
	}
	
	/*
	 * Data
	 */
	private String pNameFile;
	private List<String> pListLineToWrite;
	
	/**
	 * 
	 * @param _sBRBAr
	 */
	protected final void addNewLineToWrite(BRBar _sBRBAr) {
		/*
		 * Determine problems
		 */
		String lMessage = "Ok";
		if (_sBRBAr.getpIsInCompta()) {
			if (!_sBRBAr.getpIsInMalca()) {
				lMessage = "In Compta but not in Malca";
			}
		} else if (_sBRBAr.getpIsInMalca()) {
			lMessage = "In Malca but not in Compta";
		}
		/*
		 * 
		 */
		addNewLineToWrite(lMessage, _sBRBAr);
	}
	
	/**
	 * 
	 * @param _sBRBAr
	 */
	protected final void addNewLineToWrite(BRBarNotCurved _sBRBarNotCurved) {
		String lMessage = "Ok";
		_sBRBarNotCurved.compute();
		if (!_sBRBarNotCurved.getpIsMatchComptaAndMalca()) {
			lMessage = "Mismatch: NbBarsCompta<>NbBarsMalca";
		}
		/*
		 * 
		 */
		addNewLineToWrite(lMessage, _sBRBarNotCurved);
	}
	
	/**
	 * 
	 * @param _sLine
	 */
	private final void addNewLineToWrite(String _sMessage, BRBarAbstract _sBRBarAbstract) {
		
		/*
		 * Write line
		 */
		String lLine = _sBRBarAbstract.getpNameMetal()
				+ "," + _sBRBarAbstract.getpReference()
				+ "," + _sBRBarAbstract.getpWeight()
				+ "," + _sBRBarAbstract.getpNbBarInCompta()
				+ "," + _sBRBarAbstract.getpNbBarInMalca()
				+ "," + _sBRBarAbstract.getpWasDeliveredToClient()
				+ "," + _sMessage
				+ "," + _sBRBarAbstract.getpListFileNameOriginCompta().toString().replaceAll(",", ";")
				+ "," + _sBRBarAbstract.getpListFileNameOriginMalca().toString().replaceAll(",", ";");
		pListLineToWrite.add(lLine);
	}
	
	/**
	 * 
	 */
	protected final void writeFile() {
		String lDir = StaticDir.getOUTPUT_BARS_MALCA_AMIT();
		String lHeader = "Metal,Reference,Weight,Quantity in compta,Quantity in Malca,Was delivered to client? (Compta),Message,List files origin in Compta,List files origin in Malca";
		BasicFichiers.writeFile(lDir, pNameFile, lHeader, pListLineToWrite);
		BasicPrintMsg.display(this, "File written successfully '" + lDir + pNameFile + "'");
	}
}
