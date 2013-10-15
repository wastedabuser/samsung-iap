package com.eldhelm.samsung.iap.data;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREReadOnlyException;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class FREIapPurchasedItem extends FREIapItem {

	public static String CLASS_NAME = "com.eldhelm.samsung.iap.data.IapPurchasedItem";
	
	public FREIapPurchasedItem(String jsonData)
			throws FRETypeMismatchException, FREInvalidObjectException,
			FREASErrorException, FRENoSuchNameException,
			FREWrongThreadException, IllegalStateException,
			FREReadOnlyException {
		super(CLASS_NAME, jsonData);
	}

}
