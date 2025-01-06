package bkholdings.accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bkholdings.TKManager;
import step1loadtransactions.accounts.BKAccount;

public class TKAccountManager {

	public TKAccountManager(TKManager _sTKManager) {
		pTKManager = _sTKManager;
		/*
		 * 
		 */
		pMapNameToTKAccount = new HashMap<>();
		pListTKAccount = new ArrayList<>();
	}

	/*
	 * Data
	 */
	private TKManager pTKManager;
	private Map<String, TKAccount> pMapNameToTKAccount;
	private List<TKAccount> pListTKAccount;
	
	/**
	 * 
	 * @param _sBKAccount
	 * @return
	 */
	public final TKAccount getpOrCreateTKAccount(BKAccount _sBKAccount) {
		String lName = _sBKAccount.getpEmailAddress();
		TKAccount lTKAccount = pMapNameToTKAccount.get(lName);
		if (lTKAccount == null) {
			lTKAccount = new TKAccount(_sBKAccount, this);
			pMapNameToTKAccount.put(lName, lTKAccount);
			pListTKAccount.add(lTKAccount);
		}
		return lTKAccount;
	}
	
	
	
	
	/*
	 * Getters & Setters
	 */
	public Map<String, TKAccount> getpMapNameToTKAccount() {
		return pMapNameToTKAccount;
	}
	public List<TKAccount> getpListTKAccount() {
		return pListTKAccount;
	}
	public final TKManager getpTKManager() {
		return pTKManager;
	}
	
}
