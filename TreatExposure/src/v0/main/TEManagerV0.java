package v0.main;

import v0.loadtransactions.TEDirectionalManager;
import v0.loadtransactions.TEEconomiesManager;
import v0.loadtransactions.TEInteractiveBrokersManager;
import v0.loadtransactions.TELoadTreatedManager;
import v0.loadtransactions.TEOandaManager;
import v0.loadtransactions.TETreatClientsAndRefinersManager;
import v0.output.TEComputeExposures;
import v0.output.TEDisplay;
import v0.output.TEOutputManager;

public class TEManagerV0 {

	public TEManagerV0() {
		instantiate();
	}
	
	/*
	 * Data
	 */
	private TETransactionManagerV0 pTETransactionManager;
	private TEOandaManager pTEOandaManager;
	private TEComputeExposures pTEComputeExposures;
	private TEInteractiveBrokersManager pTEInteractiveBrokersManager;
	private TELoadTreatedManager pTELoadTreatedManager;
	private TETreatClientsAndRefinersManager pTETreatClientsAndRefinersManager;
	private TEOutputManager pTEOutputManager;
	private TEDisplay pTEDisplay;
	private TEEconomiesManager pTEEconomiesManager;
	private TEDirectionalManager pTEDirectionalManager;
	
	/**
	 * Instantiate managers
	 */
	private void instantiate() {
		pTETransactionManager = new TETransactionManagerV0(this);
		pTEOandaManager = new TEOandaManager(this);
		pTEComputeExposures = new TEComputeExposures(this);
		pTEInteractiveBrokersManager = new TEInteractiveBrokersManager(this);
		pTELoadTreatedManager = new TELoadTreatedManager(this);
		pTETreatClientsAndRefinersManager = new TETreatClientsAndRefinersManager(this);
		pTEOutputManager = new TEOutputManager(this);
		pTEDisplay = new TEDisplay();
		pTEEconomiesManager = new TEEconomiesManager(this);
		pTEDirectionalManager = new TEDirectionalManager(this);
	}

	/**
	 * 
	 */
	public final void run() {
		/*
		 * Load
		 */
		pTEOandaManager.run();
		pTEInteractiveBrokersManager.run();
		pTETreatClientsAndRefinersManager.run();
		pTEEconomiesManager.run();
		pTEDirectionalManager.run();
		/*
		 * Output
		 */
		pTEComputeExposures.run();
		pTEOutputManager.run();
	}
	
	
	/*
	 * Getters & Setters
	 */
	public final TETransactionManagerV0 getpTETransactionManager() {
		return pTETransactionManager;
	}
	public final TEComputeExposures getpTEComputeExposures() {
		return pTEComputeExposures;
	}
	public final TEOandaManager getpTEOandaManager() {
		return pTEOandaManager;
	}
	public final TELoadTreatedManager getpTELoadTreatedManager() {
		return pTELoadTreatedManager;
	}
	public final TEOutputManager getpTEOutputManager() {
		return pTEOutputManager;
	}
	public final TEDisplay getpTEDisplay() {
		return pTEDisplay;
	}

	public final TEDirectionalManager getpTEDirectionalManager() {
		return pTEDirectionalManager;
	}
	
}
