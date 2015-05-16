package com.indra.appesforcodiario.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class ActivityUtil extends BaseActivity {

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	@Override
	protected void inicializarComponentes() {
		
	}
	
	public static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Não encontrou o package: " + e);
	    }
	}
	
	public static boolean checkPlayServices(Activity activity) {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i("APPESFORCODIARIO", "Celular não suportado!");
	            activity.finish();
	        }
	        return false;
	    }
	    return true;
	}

}
