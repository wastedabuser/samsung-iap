package com.eldhelm.samsung.iap;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.eldhelm.samsung.iap.function.GetItemListFunction;
import com.eldhelm.samsung.iap.function.GetItemsInboxFunction;
import com.eldhelm.samsung.iap.function.GetPurchaseResultFunction;
import com.eldhelm.samsung.iap.function.InitFunction;
import com.eldhelm.samsung.iap.function.InitializeExtensionFunction;
import com.eldhelm.samsung.iap.function.PurchaseFunction;
import com.sec.android.iap.IAPConnector;

public class InAppExtensionContext extends FREContext {

	public static int mMode = 1;
	static final int IAP_SIGNATURE_HASHCODE = 0x7a7eaf4b;
	static final int FLAG_INCLUDE_STOPPED_PACKAGES = 32;

	public IAPConnector mIAPConnector;
	public ServiceConnection mServiceConn;

	public String purchaseData;
	public String itemId;
	public int statusCode;
	public String errorString;

	@Override
	public void dispose() {
		unbind();
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
		functions.put("initializeExtension", new InitializeExtensionFunction());
		functions.put("init", new InitFunction());
		functions.put("getItemList", new GetItemListFunction());
		functions.put("getItemsInbox", new GetItemsInboxFunction());
		functions.put("purchase", new PurchaseFunction());
		functions.put("getPurchaseResult", new GetPurchaseResultFunction());
		return functions;
	}

	public boolean isInstalledIapPackage() {
		PackageManager pm = getActivity().getPackageManager();
		try {
			pm.getApplicationInfo("com.sec.android.iap",
					PackageManager.GET_META_DATA);
			return true;
		} catch (NameNotFoundException e) {
			sendException(e);
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
			sendWarning("Loging in");

			try {
				Intent startActivityIntent = new Intent(getActivity()
						.getApplicationContext(), InAppAccountActivity.class);
				getActivity().startActivity(startActivityIntent);
			} catch (Exception e) {
				sendException(e);
			}
		}
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
			sendException(e);
			result = false;
		}
		return result;
	}

	public void iapPackageNotInstalled() {
		sendWarning("The com.sec.android.iap is not installed");

		try {
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
		} catch (Exception e) {
			sendException(e);
		}
	}

	public void bind() {
		if (mServiceConn != null)
			return;

		sendWarning("binding");
		mServiceConn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				mIAPConnector = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				try {
					mIAPConnector = IAPConnector.Stub.asInterface(service);
					onBind();
				} catch (Exception e) {
					sendException(e);
				}
			}
		};

		try {
			Intent serviceIntent = new Intent(
					"com.sec.android.iap.service.iapService");
			getActivity().bindService(serviceIntent, mServiceConn,
					Context.BIND_AUTO_CREATE);
		} catch (Exception e) {
			sendException(e);
		}
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

	public void accountCertificationSuccessfull() {
		bind();
	}

	public void onBind() {
		dispatchStatusEventAsync("Login successfull", "ready");
	}

	public void initSuccessfull() {
		dispatchStatusEventAsync("Init successfull", "init");
	}

	public void paymentFailed() {
		dispatchStatusEventAsync("payment_failed", "result");
	}

	public void paymentCanceled() {
		dispatchStatusEventAsync("payment_canceled", "result");
	}

	public void paymentCompleted(String itemId, String purchaseData,
			int statusCode, String errorString) {

		this.itemId = itemId;
		this.purchaseData = purchaseData;
		this.statusCode = statusCode;
		this.errorString = errorString;

		dispatchStatusEventAsync("payment_completed", "result");
	}

	public void sendException(Exception e) {
		sendException(e, "");
	}

	public void sendException(Exception e, String id) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String err = sw.toString();
		try {
			setActionScriptData(FREObject.newObject(err));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dispatchStatusEventAsync("Extension exception: " + id, "exception");
	}

	public void sendWarning(String msg) {
		dispatchStatusEventAsync(msg, "warning");
	}

	public void sendError(String msg) {
		dispatchStatusEventAsync(msg, "error");
	}

}
