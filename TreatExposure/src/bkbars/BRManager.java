package bkbars;

import bkbars.loadcompta.BRComptaLoader;
import bkbars.loadmalca.BRMalcaLoader;
import bkbars.object.BRBarManager;
import bkbars.writer.BRErrorWriter;
import bkbars.writer.BRWriter;

public class BRManager {

	public BRManager() {
		pBRBarManager = new BRBarManager(this);
		pBRMalcaLoader = new BRMalcaLoader(this);
		pBRComptaLoader = new BRComptaLoader(this);
		pBRWriter = new BRWriter(this);
		pBRErrorWriter = new BRErrorWriter(this);
	}
	
	/*
	 * Data
	 */
	private BRBarManager pBRBarManager;
	private BRMalcaLoader pBRMalcaLoader;
	private BRComptaLoader pBRComptaLoader;
	private BRWriter pBRWriter;
	private BRErrorWriter pBRErrorWriter;
	
	/**
	 * 
	 */
	public final void run() {
		pBRMalcaLoader.run();
		pBRComptaLoader.run();
		if (pBRErrorWriter.getpMapHeaderToListMessageInError().size() > 0) {
			pBRErrorWriter.run();
		} else {
			pBRWriter.run();
		}
	}

	/*
	 * Getters & Setters
	 */
	public final BRBarManager getpBRBarManager() {
		return pBRBarManager;
	}
	public final BRMalcaLoader getpBRMalcaLoader() {
		return pBRMalcaLoader;
	}
	public final BRComptaLoader getpBRComptaLoader() {
		return pBRComptaLoader;
	}
	public final BRWriter getpBRWriter() {
		return pBRWriter;
	}
	public final BRErrorWriter getpBRErrorWriter() {
		return pBRErrorWriter;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
