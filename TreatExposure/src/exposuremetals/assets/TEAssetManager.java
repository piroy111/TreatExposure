package exposuremetals.assets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exposuremetals.TEManager;
import step0treatrawdata.objects.BKAsset;

public class TEAssetManager {

	public TEAssetManager(TEManager _sTEManager) {
		pTEManager = _sTEManager;
		/*
		 * 
		 */
		pMapNameToTEAsset = new HashMap<>();
		pListTEAsset = new ArrayList<>();
	}

	/*
	 * Data
	 */
	private TEManager pTEManager;
	private Map<String, TEAsset> pMapNameToTEAsset;
	private List<TEAsset> pListTEAsset;
	
	/**
	 * 
	 * @param _sBKAsset
	 * @return
	 */
	public final boolean getpIsKeepBKAsset(BKAsset _sBKAsset) {
		if (_sBKAsset.getpIsBarLoan()) {
			return true;
		}
		if (_sBKAsset.getpIsBar()) {
			return true;
		}
		if (_sBKAsset.getpIsPaper() && !_sBKAsset.getpIsPaperCurrency()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param _sBKAsset
	 * @return
	 */
	public final TEAsset getpOrCreateTEAsset(BKAsset _sBKAsset) {
		String lName = _sBKAsset.getpName();
		TEAsset lTEAsset = pMapNameToTEAsset.get(lName);
		if (lTEAsset == null) {
			lTEAsset = new TEAsset(_sBKAsset, this);
			pMapNameToTEAsset.put(lName, lTEAsset);
			pListTEAsset.add(lTEAsset);
			Collections.sort(pListTEAsset);
		}
		return lTEAsset;
	}

	/*
	 * Getters & Setters
	 */
	public TEManager getpTEManager() {
		return pTEManager;
	}

	public final List<TEAsset> getpListTEAsset() {
		return pListTEAsset;
	}
}
