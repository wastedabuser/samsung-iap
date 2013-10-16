package com.eldhelm.samsung.iap.data;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREReadOnlyException;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class FREIapItem extends FREObject {
	
	public static String CLASS_NAME = "com.eldhelm.samsung.iap.data.IapItem";
	
	public FREIapItem(String jsonData)
			throws FRETypeMismatchException, FREInvalidObjectException,
			FREASErrorException, FRENoSuchNameException,
			FREWrongThreadException, IllegalStateException, FREReadOnlyException {
		super(CLASS_NAME);

		setProperty("jsonData", FREObject.newObject(jsonData));
	}
	
}
