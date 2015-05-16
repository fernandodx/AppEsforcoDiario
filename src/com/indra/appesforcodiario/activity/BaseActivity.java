package com.indra.appesforcodiario.activity;

import android.app.Activity;
import android.app.ProgressDialog;

public abstract class BaseActivity extends Activity {

	protected abstract void inicializarComponentes();
	
	private ProgressDialog barraProgresso;
	
	
	protected void showBarraProgresso(String msg) {
		getBarraProgresso().setMessage(msg);
		getBarraProgresso().setIndeterminate(true);
		getBarraProgresso().setCancelable(false);
		getBarraProgresso().show();
	}
	
	protected void stopBarraProgresso() {
		getBarraProgresso().dismiss();
	}

	public ProgressDialog getBarraProgresso() {
		if(barraProgresso == null) {
			barraProgresso = new ProgressDialog(this);
		}
		return barraProgresso;
	}

	public void setBarraProgresso(ProgressDialog barraProgresso) {
		this.barraProgresso = barraProgresso;
	}
	
	
	
	
	
}
