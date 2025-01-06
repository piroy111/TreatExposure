package v0.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basicmethods.BasicDateInt;
import basicmethods.BasicPrintMsg;
import step0treatrawdata.objects.BKAsset;
import v0.main.TETransactionV0;

public class TEDisplay {

	
	/**
	 * 
	 * @param _sLauncher
	 * @param _sListTETransaction
	 */
	public final void display(Object _sLauncher, List<TETransactionV0> _sListTETransaction) {
		/*
		 * Put the TETransaction in a map of BKAsset and add the amounts per BKAsset
		 */
		Map<BKAsset, Double> lMapBKAssetToAmount = new HashMap<>();
		for (TETransactionV0 lTETransaction : _sListTETransaction) {
			BKAsset lBKAsset = lTETransaction.getpBKAsset();
			double lAmount = lTETransaction.getpQuantity();
			Double lAmount0 = lMapBKAssetToAmount.get(lBKAsset);
			if (lAmount0 == null) {
				lAmount0 = 0.;
			}
			lAmount0 += lAmount;
			lMapBKAssetToAmount.put(lBKAsset, lAmount0);
		}
		/*
		 * Display
		 */
		List<BKAsset> lListBKAsset = new ArrayList<>(lMapBKAssetToAmount.keySet());
		Collections.sort(lListBKAsset);
		for (BKAsset lBKAsset : lListBKAsset) {
			double lAmount = lMapBKAssetToAmount.get(lBKAsset);
			int lDateLastMonth = BasicDateInt.getmEndOfMonth(BasicDateInt.getmPlusMonth(BasicDateInt.getmToday(), -1));
			double lAmountUSD = lAmount * lBKAsset.getpPriceUSD(lDateLastMonth);
			BasicPrintMsg.display(_sLauncher, "BKasset= " + lBKAsset 
					+ "; Amount= " + BasicPrintMsg.afficheIntegerWithComma(lAmount)
					+ "; Amount$= " + BasicPrintMsg.afficheIntegerWithComma(lAmountUSD) + " $");
		}
	}
	
}
