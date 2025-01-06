package bkholdings;

import basicmethods.BasicDateInt;

public class STL_BKHoldingsRealTime_LaunchMe {

	/**
	 * Write the files of BKHoldings so we can RECONCILIATE what is on the platform and the files used by the COMPTA<br>
	 * Goes until the last available date (does not stop at the end of last month, as COMPTA does)
	 * @param _sArgs
	 */
	public static void main(String[] _sArgs) {
		new TKManager().run();
	}

	/*
	 * Date from which we will write the file BKTRansactions (-1 if we write all)
	 */
	public static int DATE_START_BK_TRANSACTIONS = BasicDateInt.getmEndOfMonth(BasicDateInt.getmPlusMonth(BasicDateInt.getmToday(), -2));
	
	
}
