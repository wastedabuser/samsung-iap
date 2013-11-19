package com.eldhelm.samsung.iap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class InAppAccountActivity extends Activity {
	
	ProgressDialog pd;
	
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
    	InAppPurchase.context.sendWarning("Received requestCode: " + _requestCode + " resultCode: " + _resultCode);
    	if (_requestCode == 1001) {
			if (_resultCode == Activity.RESULT_OK) {
				InAppPurchase.context.accountCertificationSuccessfull();
			}
		}
    	
    	finish();
	}
	
}
