package com.eldhelm.samsung.iap;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class InAppAccountActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
		
		ComponentName com = new ComponentName("com.sec.android.iap",
				"com.sec.android.iap.activity.AccountActivity");
		Intent intent = new Intent();
		intent.setComponent(com);
		startActivityForResult(intent, 1001);
    }

    @Override
	protected void onActivityResult(int _requestCode, int _resultCode,
			Intent _intent) {
    	InAppPurchase.context.sendWarning("Received code " + _requestCode);
    	if (_requestCode == 1001) {
			if (_resultCode == Activity.RESULT_OK) {
				InAppPurchase.context.accountCertificationSuccessfull();
			}
		}
	}
	
}
