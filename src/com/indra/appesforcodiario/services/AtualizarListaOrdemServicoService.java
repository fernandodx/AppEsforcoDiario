package com.indra.appesforcodiario.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AtualizarListaOrdemServicoService extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		  Toast.makeText(context, "Executando Rotina",
			        Toast.LENGTH_LONG).show();
		  
		  RecuperarOrdemServicoTask rotina = new RecuperarOrdemServicoTask(context);
		  
		  rotina.execute();
	}

}
