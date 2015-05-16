package com.indra.appesforcodiario.services;

import android.R;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.indra.appesforcodiario.activity.MainActivity;


public class ServicoGCM extends IntentService {

	 public static final int NOTIFICATION_ID = 1;
	 private NotificationManager notificationManager;
	 NotificationCompat.Builder builder;
	
	public ServicoGCM() {
		super("ServicoGCM");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		try{
			
			Bundle extras = intent.getExtras();
			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
			
			String msgType = gcm.getMessageType(intent);
			
			if(!extras.isEmpty()) {
				if(msgType.equals(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR)){
					Log.e("APPESFORCODIARIO", extras.toString());
				}else if (msgType.equals(GoogleCloudMessaging.MESSAGE_TYPE_DELETED)){
					Log.e("APPESFORCODIARIO", extras.toString());
				}else if(msgType.equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)){
					
					for(int i=0; i<5 ; i++){
						Log.i("APPESFORCODIARIO", "Working... " + (i+1) + "/5 @ " + SystemClock.elapsedRealtime());
						
						try {
							Thread.sleep(5000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					enviarNotificacao(extras.toString());
				}
			}
			
			GCMBroadCastReceiver.completeWakefulIntent(intent);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void enviarNotificacao(String msg) throws Exception {
	
		NotificationManager nfm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		PendingIntent pedido = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.ic_dialog_alert)
		.setContentTitle("Notificação Vinda do Google")
		.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
		.setContentText(msg);
		
		builder.setContentIntent(pedido);
		
		nfm.notify(NOTIFICATION_ID, builder.build());
		
	}

	

}
