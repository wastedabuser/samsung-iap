package com.eldhelm.samsung.iap;

import com.eldhelm.samsung.iap.data.FREIapPurchase;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class InAppPaymentActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

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
				InAppPurchase.context.paymentFailed();
				/* InAppPurchase.context
						.showDialog(
								getString(R.string.dlg_title_payment_error),
								getString(R.string.msg_payment_was_not_processed_successfully)); */
			}

			if (RESULT_OK == _resultCode) {
				
				try {
					InAppPurchase.context.sendAsyncResult("payment_completed", new FREIapPurchase(itemId, purchaseData, errorString));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (RESULT_CANCELED == _resultCode) {
				InAppPurchase.context.showDialog(
						getString(R.string.dlg_title_payment_cancelled),
						"-itemId : " + itemId + "\n-thirdPartyName : "
								+ thirdPartyName + "\n-statusCode : "
								+ statusCode);
			} else {
				InAppPurchase.context.paymentFailed();
			}

		}

	}
	
}
