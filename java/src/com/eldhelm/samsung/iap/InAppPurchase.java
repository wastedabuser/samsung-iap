package com.eldhelm.samsung.iap;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class InAppPurchase implements FREExtension {
	
	static InAppExtensionContext context;
	
	@Override
	public FREContext createContext(String arg0) {
		if (context == null) context = new InAppExtensionContext();
		return context;
	}

	@Override
	public void dispose() {
		context = null;	
	}

	@Override
	public void initialize() {
		
	}
	
}
