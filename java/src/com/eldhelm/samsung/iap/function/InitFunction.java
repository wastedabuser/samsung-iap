package com.eldhelm.samsung.iap.function;

import android.os.Bundle;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.eldhelm.samsung.iap.InAppExtensionContext;
import com.sec.android.iap.IAPConnector;

public class InitFunction implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		InAppExtensionContext frecontext = (InAppExtensionContext) arg0;
		
		try {
			InAppExtensionContext.mMode = arg1[0].getAsInt();
		}  catch (Exception e) {
			frecontext.sendException(e);
		}
		
		IAPConnector mIAPConnector = frecontext.mIAPConnector;

		Bundle bundle;
		try {
			bundle = mIAPConnector.init(InAppExtensionContext.mMode);

			if (null != bundle) {
				int statusCode = bundle.getInt("STATUS_CODE");				
				if (statusCode == 0) {
					frecontext.initSuccessfull();
				}
				if (statusCode == -1001) {
					frecontext.updatePakcage(bundle);
				}
			}

		} catch (Exception e) {
			frecontext.sendException(e);
		}

		return null;
	}

}
