package com.eldhelm.samsung.iap.function;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.eldhelm.samsung.iap.InAppExtensionContext;

public class InitializeExtensionFunction implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		InAppExtensionContext frecontext = (InAppExtensionContext) arg0;
		
		frecontext.sendWarning("Initializing");
		
		if (frecontext.isInstalledIapPackage())
			frecontext.iapPackageInstalled();
		else
			frecontext.iapPackageNotInstalled();
		
		return null;
	}

}
