package com.eldhelm.samsung.iap.function;

import java.util.ArrayList;

import android.os.Bundle;

import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.eldhelm.samsung.iap.InAppExtensionContext;
import com.eldhelm.samsung.iap.data.FREIapPurchasedItem;
import com.sec.android.iap.IAPConnector;

public class GetItemsInboxFunction implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		InAppExtensionContext frecontext = (InAppExtensionContext) arg0;
		IAPConnector mIAPConnector = frecontext.mIAPConnector;
		
		FREArray itemList = null;
		try {
			itemList = FREArray.newArray(FREIapPurchasedItem.CLASS_NAME, 0, false);
		} catch (Exception e) {
			frecontext.sendException(e);
		}
		
		String _itemGroupId = null;
		int _startNum = 0;
		int _endNum = 0;
		String _startDate = null;
		String _endDate = null;

		try {
			_itemGroupId = arg1[0].getAsString();
			_startNum = arg1[1].getAsInt();
			_endNum = arg1[2].getAsInt();
			_startDate = arg1[3].getAsString();
			_endDate = arg1[4].getAsString();
		} catch (Exception e) {
			frecontext.sendException(e);
		}

		try {
			Bundle bundle = mIAPConnector.getItemsInbox(frecontext
					.getActivity().getPackageName(), _itemGroupId, _startNum,
					_endNum, _startDate, _endDate);

			if (null != bundle) {
				int statusCode = bundle.getInt("STATUS_CODE");
				if (statusCode == 0) {

					ArrayList<String> arrayList = bundle
							.getStringArrayList("RESULT_LIST");

					int i = 0;
					for (String itemString : arrayList) {
						itemList.setObjectAt(i++, new FREIapPurchasedItem(itemString));
					}
				}
				if (statusCode == -1001) {
					frecontext.updatePakcage(bundle);
				}
			}

		} catch (Exception e) {
			frecontext.sendException(e);
		}

		return itemList;
	}

}
