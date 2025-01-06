package v0.main;

import step0treatrawdata.objects.BKAsset;
import step1loadtransactions.accounts.BKAccount;

public class TETransactionV0 {

	protected TETransactionV0(BKAccount _sBKAccount, BKAsset _sBKAsset, double _sQuantity) {
		pBKAccount = _sBKAccount;
		pBKAsset = _sBKAsset;
		pQuantity = _sQuantity;
	}
	
	/*
	 * Data
	 */
	private BKAccount pBKAccount;
	private BKAsset pBKAsset;
	private double pQuantity;
	
	/**
	 * Classic toString
	 */
	public String toString() {
		return "BKAccount= " + pBKAccount
				+ "; BKAsset= " + pBKAsset
				+ "; Quantity= " + pQuantity;
	}
	
	/**
	 * 
	 */
	public final void changeSignAmount() {
		pQuantity = -pQuantity;
	}
	
	/*
	 * Getters & Setters
	 */
	public final BKAccount getpBKAccount() {
		return pBKAccount;
	}
	public final BKAsset getpBKAsset() {
		return pBKAsset;
	}
	public final double getpQuantity() {
		return pQuantity;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
