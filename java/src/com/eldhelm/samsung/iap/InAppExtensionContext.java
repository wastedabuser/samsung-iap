package com.eldhelm.samsung.iap;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;
import com.eldhelm.samsung.iap.function.GetItemListFunction;
import com.eldhelm.samsung.iap.function.GetItemsInboxFunction;
import com.eldhelm.samsung.iap.function.InitFunction;
import com.eldhelm.samsung.iap.function.PurchaseFunction;
import com.eldhelm.samsung.iap.R;
import com.sec.android.iap.IAPConnector;

public class InAppExtensionContext extends FREContext {

	public static int mMode = 1;
	static final int IAP_SIGNATURE_HASHCODE = 0x7a7eaf4b;
	static final int FLAG_INCLUDE_STOPPED_PACKAGES = 32;

	public IAPConnector mIAPConnector;
	ServiceConnection mServiceConn;

	InAppExtensionContext() {
		super();

		if (isInstalledIapPackage())
			iapPackageInstalled();
		else
			iapPackageNotInstalled();
	}

	@Override
	public void dispose() {
		unbind();
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
		functions.put("init", new InitFunction());
		functions.put("getItemList", new GetItemListFunction());
		functions.put("getItemsInbox", new GetItemsInboxFunction());
		functions.put("purchase", new PurchaseFunction());
		return functions;
	}

	public boolean isInstalledIapPackage() {
		PackageManager pm = getActivity().getPackageManager();
		try {
			pm.getApplicationInfo("com.sec.android.iap",
					PackageManager.GET_META_DATA);
			return true;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void iapPackageInstalled() {
		if (false == isValidIAPPackage()) {
			new AlertDialog.Builder(getActivity())
					.setTitle(R.string.in_app_purchase)
					.setMessage(R.string.invalid_iap_package)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									getActivity().finish();
								}
							}).show();
		} else {
			Intent startActivityIntent = new Intent(getActivity(),
					InAppAccountActivity.class);
			getActivity().startActivity(startActivityIntent);
		}
	}

	public void loginSuccessfull() {
		bind();
		dispatchStatusEventAsync("Login successfull", "ready");
	}

	public boolean isValidIAPPackage() {
		boolean result = true;
		try {
			Signature[] signs = getActivity().getPackageManager()
					.getPackageInfo("com.sec.android.iap",
							PackageManager.GET_SIGNATURES).signatures;
			if (signs[0].hashCode() != IAP_SIGNATURE_HASHCODE) {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public void iapPackageNotInstalled() {
		Intent intent = new Intent();
		intent.setData(Uri
				.parse("samsungapps://ProductDetail/com.sec.android.iap"));
		if (Build.VERSION.SDK_INT >= 12) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP
					| FLAG_INCLUDE_STOPPED_PACKAGES);
		} else {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		}
		getActivity().startActivity(intent);
	}

	public void bind() {
		mServiceConn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				mIAPConnector = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mIAPConnector = IAPConnector.Stub.asInterface(service);
			}
		};
	}

	public void unbind() {
		if (mServiceConn == null)
			return;

		getActivity().unbindService(mServiceConn);
		mServiceConn = null;
	}

	public void updatePakcage(Bundle bundle) {
		String errorString = bundle.getString("ERROR_STRING");
		final String extraString = bundle.getString("IAP_UPGRADE_URL");

		new AlertDialog.Builder(getActivity())
				.setTitle("IAP Upgrade")
				.setMessage(errorString)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent();
								intent.setData(Uri.parse(extraString));
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								try {
									getActivity().startActivity(intent);
								} catch (ActivityNotFoundException e) {
									e.printStackTrace();
								}
							}
						}).show();
	}

	public void showDialog(String title, String message) {
		new AlertDialog.Builder(getActivity())
				.setTitle(title)
				.setMessage(message)
				.show();
	}

	public void sendAsyncResult(FREObject obj) throws IllegalArgumentException, IllegalStateException, FREWrongThreadException{
		setActionScriptData(obj);
		dispatchStatusEventAsync("Result available", "result");
	}
	
}
