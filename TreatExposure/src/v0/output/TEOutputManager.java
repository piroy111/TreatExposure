package v0.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import basicmethods.BasicDateInt;
import basicmethods.BasicPrintMsg;
import step0treatrawdata.objects.BKAsset;
import step0treatrawdata.objects.BKAssetManager;
import v0.main.TEManagerV0;

public class TEOutputManager {
	
	public TEOutputManager(TEManagerV0 _sTEManager) {
		pTEManager = _sTEManager;
	}
	
	/*
	 * Data
	 */
	private TEManagerV0 pTEManager;
	
	/**
	 * 
	 */
	public final void run() {
		BasicPrintMsg.displayTitle(this, "Display exposures of Physical + Papers - Economies = Directinal");
		BasicPrintMsg.display(this, "Source= Refiners + Clients + OANDA + InteractiveBroker - Economies");
		BasicPrintMsg.display(this, null);
		Map<BKAsset, Double> lMapBKAssetToAmount = pTEManager.getpTEComputeExposures().getpMapBKAssetToAmount();
		List<BKAsset> lListBKAsset = new ArrayList<>(lMapBKAssetToAmount.keySet());
		Collections.sort(lListBKAsset);
		for (BKAsset lBKAsset : lListBKAsset) {
			double lAmount = lMapBKAssetToAmount.get(lBKAsset);
			/*
			 * Case of oil
			 */
			if (lBKAsset.getpIsPaperCurrency()) {
				display(lBKAsset, null, lAmount, 0.);
			} 
			/*
			 * Case of metals
			 */
			else if (lBKAsset.getpIsBar()) {
				String lNamePaper = null;
				if (lBKAsset.getpName().contains("SILVER")) {
					lNamePaper = "XAG";
				} else if (lBKAsset.getpName().contains("GOLD")) {
					lNamePaper = "XAU";
				} else if (lBKAsset.getpName().contains("PLATINUM")) {
					lNamePaper = "XPT";
				}				
				BKAsset lBKAssetPaper = BKAssetManager.getpAndCheckBKAsset(lNamePaper);
				double lAmountPaper = lMapBKAssetToAmount.get(lBKAssetPaper);
				display(lBKAssetPaper, lBKAsset, lAmount, lAmountPaper);
			}
		}
		BasicPrintMsg.display(this, null);
		BasicPrintMsg.display(this, null);
		BasicPrintMsg.display(this, null);
	}
	
	/**
	 * 
	 * @param _sBKAssetPaper
	 * @param _sBKAssetBar
	 * @param _sAmountPaper
	 * @param _sAmountPhysical
	 */
	private void display(BKAsset _sBKAssetPaper, BKAsset _sBKAssetBar, double _sAmountPaper, double _sAmountPhysical) {
		/*
		 * Initiate
		 */
		String lMetal;
		if (_sBKAssetBar != null) {
			lMetal = _sBKAssetBar.getpMetalName();
		} else {
			lMetal = _sBKAssetPaper.getpName();
		}
		double lAmount = _sAmountPaper + _sAmountPhysical;
		int lDateLastMonth = BasicDateInt.getmEndOfMonth(BasicDateInt.getmPlusMonth(BasicDateInt.getmToday(), -1));
		double lAmountUSD = lAmount * _sBKAssetPaper.getpPriceUSD(lDateLastMonth);
		/*
		 * Display
		 */
		String lMsg1 = "Exposure in " + lMetal + " = " + BasicPrintMsg.afficheIntegerWithComma(lAmount);
		String lMsg2 = "; in USD= " + BasicPrintMsg.afficheIntegerWithComma(lAmountUSD) + " $";
		BasicPrintMsg.display(this, BasicPrintMsg.getJustifiedText(lMsg1, 38) + lMsg2, 50);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
