package com.indra.appesforcodiario.application.service;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.indra.appesforcodiario.activity.ActivityUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class GCMApplication {

	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String SENDER_ID = "398894017627";
    
    private Context context;
    private GoogleCloudMessaging cgm;
    private String idRegistroGoogle;
    private Class classe;
    private SharedPreferences preferences;
    
	public GCMApplication(Context context, GoogleCloudMessaging gcm, Class classe, SharedPreferences preferences) {
		super();
		this.context = context;
		this.cgm = gcm;
		this.classe = classe;
		this.preferences = preferences;
	}

	public String getRegistroId() {
		
		String registroId = getPreferences().getString(PROPERTY_REG_ID, "");
		
		if(registroId.isEmpty()){
			return "";
		}
		
		int registroVersao = getPreferences().getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int versaoAtual = ActivityUtil.getAppVersion(getContext());
		
		if(registroVersao != versaoAtual) {
			return "";
		}
		
		return registroId;
		
	}
	
	public void resgistroInBackground() {
		new AsyncTask<Void, Void, String>(){

			@Override
			protected String doInBackground(Void... params) {
				String idGoogle = new String();
				
				try {
					if(getCgm() == null) {
						setCgm(GoogleCloudMessaging.getInstance(getContext()));
					}
					
					idGoogle = getCgm().register(SENDER_ID);
					registrarPreferencesIdGoogle(idGoogle);
					setIdRegistroGoogle(idGoogle);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return idGoogle;
			}
			
			@Override
	        protected void onPostExecute(String msg) {
	            Log.e("APPESFORCODIARIO", msg);
	        }
			
		}.execute(null, null, null);
	}
	
	
	private void registrarIdGoogleWebService() throws Exception {
		//TODO falta implementar.
	}
	
	private void registrarPreferencesIdGoogle(String idGoogle) throws Exception {
		
		Integer versaoApp = ActivityUtil.getAppVersion(getContext());
		
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.putString(PROPERTY_REG_ID, idGoogle);
		editor.putInt(PROPERTY_APP_VERSION, versaoApp);
		editor.commit();
		
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public GoogleCloudMessaging getCgm() {
		return cgm;
	}

	public void setCgm(GoogleCloudMessaging cgm) {
		this.cgm = cgm;
	}

	public String getIdRegistroGoogle() {
		return idRegistroGoogle;
	}

	public void setIdRegistroGoogle(String idRegistroGoogle) {
		this.idRegistroGoogle = idRegistroGoogle;
	}

	public Class getClasse() {
		return classe;
	}

	public void setClasse(Class classe) {
		this.classe = classe;
	}

	public SharedPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}
	
	
	
	
	
	
	
}
