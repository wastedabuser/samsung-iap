package com.eldhelm.samsung.iap.data;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREReadOnlyException;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class FREIapPurchase extends FREObject {

	public static String CLASS_NAME = "com.eldhelm.samsung.iap.data.IapPurchase";
	
	public FREIapPurchase(String itemId, String jsonData, String errorString)
			throws FRETypeMismatchException, FREInvalidObjectException,
			FREASErrorException, FRENoSuchNameException,
			FREWrongThreadException, IllegalStateException, FREReadOnlyException {
		super(CLASS_NAME);
		
		setProperty("jsonData", FREObject.newObject(jsonData));
		setProperty("itemId", FREObject.newObject(itemId));
		setProperty("errorString", FREObject.newObject(errorString));
	}

}
