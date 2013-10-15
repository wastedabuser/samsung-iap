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
		try {
			InAppExtensionContext.mMode = arg1[0].getAsInt();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final InAppExtensionContext frecontext = (InAppExtensionContext) arg0;
		IAPConnector mIAPConnector = frecontext.mIAPConnector;

		Bundle bundle;
		try {
			bundle = mIAPConnector.init(InAppExtensionContext.mMode);

			if (null != bundle) {
				int statusCode = bundle.getInt("STATUS_CODE");				
				if (statusCode == 0) {
					frecontext.dispatchStatusEventAsync("Init successfull", "init");
				}
				if (statusCode == -1001) {
					frecontext.updatePakcage(bundle);
				}
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;
	}

}
