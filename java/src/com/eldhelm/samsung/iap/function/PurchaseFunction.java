package com.eldhelm.samsung.iap.function;

import android.content.Intent;
import android.os.Bundle;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.eldhelm.samsung.iap.InAppExtensionContext;
import com.eldhelm.samsung.iap.InAppPaymentActivity;

public class PurchaseFunction implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		InAppExtensionContext frecontext = (InAppExtensionContext) arg0;

		String _itemGroupId = null;
		String _itemId = null;

		try {
			_itemGroupId = arg1[0].getAsString();
			_itemId = arg1[1].getAsString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Intent startActivityIntent = new Intent(frecontext.getActivity(),
				InAppPaymentActivity.class);

		Bundle purchaseBundle = new Bundle();
		purchaseBundle.putString("THIRD_PARTY_NAME", frecontext.getActivity()
				.getPackageName());
		purchaseBundle.putString("ITEM_GROUP_ID", _itemGroupId);
		purchaseBundle.putString("ITEM_ID", _itemId);
		startActivityIntent.putExtras(purchaseBundle);

		frecontext.getActivity().startActivity(startActivityIntent);

		return null;
	}

}
