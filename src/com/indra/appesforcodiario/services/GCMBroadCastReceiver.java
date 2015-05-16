package com.indra.appesforcodiario.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GCMBroadCastReceiver extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		ComponentName compName = new ComponentName(context, ServicoGCM.class.getName());
		Vibrator v = (Vibrator)   context.getSystemService(Context.VIBRATOR_SERVICE);
		 // Vibrate for 500 milliseconds
		 v.vibrate(500);
		startWakefulService(context, intent.setComponent(compName));
		setResultCode(Activity.RESULT_OK);
		
	}

}
