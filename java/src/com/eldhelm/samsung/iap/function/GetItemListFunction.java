package com.eldhelm.samsung.iap.function;

import java.util.ArrayList;

import android.os.Bundle;

import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import com.adobe.fre.FREObject;
import com.eldhelm.samsung.iap.InAppExtensionContext;
import com.eldhelm.samsung.iap.data.FREIapItem;

import com.sec.android.iap.IAPConnector;

public class GetItemListFunction implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		InAppExtensionContext frecontext = (InAppExtensionContext) arg0;
		IAPConnector mIAPConnector = frecontext.mIAPConnector;

		FREArray itemList = null;
		try {
			itemList = FREArray.newArray(FREIapItem.CLASS_NAME, 0, false);
		} catch (Exception e) {
			frecontext.sendException(e);
		}

		String _itemGroupId = null;
		int _startNum = 0;
		int _endNum = 0;
		String _itemType = null;

		try {
			_itemGroupId = arg1[0].getAsString();
			_startNum = arg1[1].getAsInt();
			_endNum = arg1[2].getAsInt();
			_itemType = arg1[3].getAsString();
		} catch (Exception e) {
			frecontext.sendException(e);
		}

		try {
			int mode = InAppExtensionContext.mMode;
			String packageName = frecontext.getActivity().getPackageName();
			Bundle bundle = mIAPConnector.getItemList(mode, packageName,
					_itemGroupId, _startNum, _endNum, _itemType);

			if (null != bundle) {
				int statusCode = bundle.getInt("STATUS_CODE");

				if (statusCode == 0) {
					ArrayList<String> arrayList = bundle
							.getStringArrayList("RESULT_LIST");

					int i = 0;
					for (String itemString : arrayList) {
						itemList.setObjectAt(i++, new FREIapItem(itemString));
					}
				} else if (statusCode == -1001) {
					frecontext.updatePakcage(bundle);
				} else {
					frecontext.sendError("getItemList " + statusCode);
				}

			} else {
				frecontext.sendError("getItemList bundle is null");
			}

		} catch (Exception e) {
			frecontext.sendException(e);
		}

		return itemList;
	}

}
