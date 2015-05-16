package com.indra.appesforcodiario.services;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import com.indra.appesforcodiario.R;
import com.indra.appesforcodiario.Util;
import com.indra.appesforcodiario.activity.MainActivity;
import com.indra.appesforcodiario.activity.ResultadoEsforcoDiarioActivity;
import com.indra.entity.EsforcoDiario;

public class PhoneService extends Service {

	private EsforcoDiario esforcoDiario;
	private Chronometer cronometro;
	private Boolean isAtividadeiniciada;
	
	@Override
	public void onCreate() {
		Log.i("------------->", "PhoneService.onCreate()");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if(intent.getExtras() != null
				&& intent.getExtras().getSerializable(EsforcoDiario.ESFORCO_DIARIO_SERIAL) != null){
			setEsforcoDiario((EsforcoDiario)intent.getExtras().getSerializable(EsforcoDiario.ESFORCO_DIARIO_SERIAL)) ;
			isAtividadeiniciada = intent.getExtras().getBoolean(EsforcoDiario.IS_ESFORCO_INICIADO);
		}
		setCronometro(new Chronometer(PhoneService.this));
		if(getEsforcoDiario().getIsPausado()){
			getCronometro().setBase(SystemClock.elapsedRealtime() +  getEsforcoDiario().getTempoPausado());
		}else{
			getCronometro().setBase(SystemClock.elapsedRealtime());
		}
		
		getCronometro().start();
		
		NotificationManager nf = (NotificationManager) PhoneService.this.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Intent intencao = new Intent(PhoneService.this, MainActivity.class);
		Bundle b = new Bundle();
		getEsforcoDiario().setTimerContador(getCronometro().getBase());
		b.putSerializable(getString(R.string.cronometro_handler_inicio), getEsforcoDiario());
		b.putBoolean(EsforcoDiario.IS_ESFORCO_INICIADO, isAtividadeiniciada);
		intencao.putExtras(b);
		
		PendingIntent pedido = PendingIntent.getActivity(PhoneService.this, 0, intencao, Intent.FLAG_ACTIVITY_NEW_TASK);
//		
//		Notification notificacao = new Notification.Builder(PhoneService.this)
//		.setContentIntent(pedido)
//		.setContentTitle("Atividade Iniciada! ")
//		.setContentText("Data/Hora - " +Util.formatarData(getEsforcoDiario().getDtInicioAtividade()))
//	    .setSmallIcon(R.drawable.ic_launcher)
//	    .setAutoCancel(true);
//		
		Notification notificacao = new Notification(
				R.drawable.ic_launcher,"Atividade Iniciada!", System.currentTimeMillis());
		notificacao.setLatestEventInfo(this, "Atividade Iniciada!", "Data/Hora - " +Util.formatarData(getEsforcoDiario().getDtInicioAtividade()), pedido);
		notificacao.vibrate = new long[]{100, 250, 100, 500};
		 nf.notify(0, notificacao);
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		
		
		getEsforcoDiario().setDtFimAtividade(Calendar.getInstance());
		
		getCronometro().stop();
		
		NotificationManager nf = (NotificationManager) PhoneService.this.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Intent intencao = new Intent(PhoneService.this, ResultadoEsforcoDiarioActivity.class);
		PendingIntent pedido = PendingIntent.getActivity(PhoneService.this, 0, intencao, Intent.FLAG_ACTIVITY_NEW_TASK);
		
//		Notification notificacao = new Notification.Builder(PhoneService.this)
//		.setContentIntent(pedido)
//		.setContentTitle("Atividade Parada! ")
//		.setContentText("Data/Hora - " +Util.formatarData(getEsforcoDiario().getDtFimAtividade()))
//	    .setSmallIcon(R.drawable.ic_launcher)
//	    .setAutoCancel(true).build();
		
		Notification notificacao = new Notification(
				R.drawable.ic_launcher,"Atividade Parada!", System.currentTimeMillis());
		notificacao.setLatestEventInfo(this, "Atividade Parada!", "Data/Hora - " +Util.formatarData(getEsforcoDiario().getDtFimAtividade()), pedido);
		notificacao.vibrate = new long[]{100, 250, 100, 500};
		 
		nf.notify(0, notificacao);
		 Log.i("------------->", "PhoneService.onDestroy");
		super.onDestroy();
	}

	public EsforcoDiario getEsforcoDiario() {
		if(esforcoDiario == null){
			esforcoDiario = new EsforcoDiario();
		}
		return esforcoDiario;
	}

	public void setEsforcoDiario(EsforcoDiario esforcoDiario) {
		this.esforcoDiario = esforcoDiario;
	}
	
	
	public Chronometer getCronometro() {
		return cronometro;
	}

	public void setCronometro(Chronometer cronometro) {
		this.cronometro = cronometro;
	}

	
	
	

}
