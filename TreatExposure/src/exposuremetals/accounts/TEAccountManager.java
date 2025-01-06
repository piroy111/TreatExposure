package exposuremetals.accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exposuremetals.TEManager;
import step1loadtransactions.accounts.BKAccount;

public class TEAccountManager {

	public TEAccountManager(TEManager _sTEManager) {
		pTEManager = _sTEManager;
		/*
		 * 
		 */
		pMapNameToTEAccount = new HashMap<>();
		pListTEAccount = new ArrayList<>();
				
	}

	/*
	 * Data
	 */
	private TEManager pTEManager;
	private Map<String, TEAccount> pMapNameToTEAccount;
	private List<TEAccount> pListTEAccount;
	
	/**
	 * 
	 * @param _sBKAccount
	 * @return
	 */
	public final TEAccount getpTEAccountFromBKAccount(BKAccount _sBKAccount) {
		return getpOrCreateTEAccount(_sBKAccount.getpEmailAddress());
	}
	
	/**
	 * 
	 * @param _sBKAccount
	 * @return
	 */
	private final TEAccount getpOrCreateTEAccount(String _sNameAccount) {
		TEAccount lTEAccount = pMapNameToTEAccount.get(_sNameAccount);
		if (lTEAccount == null) {
			lTEAccount = new TEAccount(_sNameAccount, this);
			pMapNameToTEAccount.put(_sNameAccount, lTEAccount);
			pListTEAccount.add(lTEAccount);
			Collections.sort(pListTEAccount);
		}
		return lTEAccount;
	}
	
	/*
	 * Getters & Setters
	 */
	public TEManager getpTEManager() {
		return pTEManager;
	}
	public Map<String, TEAccount> getpMapNameToTEAccount() {
		return pMapNameToTEAccount;
	}
	public List<TEAccount> getpListTEAccount() {
		return pListTEAccount;
	}
	
	
}
