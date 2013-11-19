package com.eldhelm.samsung.iap.function;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.eldhelm.samsung.iap.InAppExtensionContext;
import com.eldhelm.samsung.iap.data.FREIapPurchase;

public class GetPurchaseResultFunction implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		InAppExtensionContext frecontext = (InAppExtensionContext) arg0;
		FREIapPurchase result = null;

		frecontext.sendWarning("Purchase result is: " + frecontext.itemId + ";"
				+ frecontext.statusCode + ";" + frecontext.errorString + ";"
				+ frecontext.purchaseData);

		try {
			result = new FREIapPurchase(frecontext.purchaseData,
					frecontext.itemId, frecontext.statusCode,
					frecontext.errorString);
			
			frecontext.itemId = "";
			frecontext.statusCode = -1;
			frecontext.errorString = "";
			frecontext.purchaseData = "";
		} catch (Exception e) {
			frecontext.sendException(e);
		}

		return result;
	}

}
