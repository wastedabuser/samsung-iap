package com.eldhelm.samsung.iap;

import com.eldhelm.samsung.iap.data.FREIapPurchase;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class InAppPaymentActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ComponentName com = new ComponentName("com.sec.android.iap",
				"com.sec.android.iap.activity.PaymentMethodListActivity");
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(com);
		intent.putExtras(getIntent().getExtras());
		startActivityForResult(intent, 1000);
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode,
			Intent _intent) {

		InAppExtensionContext frecontext = InAppPurchase.context;
		frecontext.sendWarning("Received requestCode: " + _requestCode
				+ " resultCode: " + _resultCode);

		if (_requestCode == 1000) {
			if (null == _intent) {
				return;
			}

			Bundle extras = _intent.getExtras();
			String itemId = "";
			String thirdPartyName = "";
			int statusCode = 1;
			String errorString = "";
			String purchaseData = "";

			if (null != extras) {
				thirdPartyName = extras.getString("THIRD_PARTY_NAME");
				statusCode = extras.getInt("STATUS_CODE");
				errorString = extras.getString("ERROR_STRING");
				itemId = extras.getString("ITEM_ID");
				purchaseData = extras.getString("RESULT_OBJECT");
			} else {
				frecontext.paymentFailed();
			}

			frecontext.sendWarning("Extras received: " + thirdPartyName + ";"
					+ statusCode + ";" + errorString + ";" + itemId + ";"
					+ purchaseData);

			if (RESULT_OK == _resultCode) {

				try {
					frecontext.sendAsyncResult("payment_completed",
							new FREIapPurchase(itemId, purchaseData,
									statusCode, errorString));
				} catch (Exception e) {
					frecontext.sendException(e);
				}

			} else if (RESULT_CANCELED == _resultCode) {
				frecontext.paymentCanceled();
			} else {
				frecontext.paymentFailed();
			}

		}

	}

}
