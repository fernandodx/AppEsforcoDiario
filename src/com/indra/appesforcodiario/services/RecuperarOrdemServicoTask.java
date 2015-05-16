package com.indra.appesforcodiario.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indra.dao.OrdemServicoDAO;
import com.indra.entity.OrdemServico;
import com.indra.entity.vo.OrdemServicoWeb;

public class RecuperarOrdemServicoTask extends AsyncTask<Void, Void, Void>{

	private Context context;

	public RecuperarOrdemServicoTask(Context context) {
		
		this.context = context;
		
	}
	
	public List<OrdemServico> consultarOrdemServicoWeb() {
		try{
			HttpClient http = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://10.0.3.2:8080/webService/helloworld/teste");
			ResponseHandler<String> response = new BasicResponseHandler();
			
			String resultado = http.execute(get, response);
		
			Gson gson = new GsonBuilder().create();
			OrdemServicoWeb osWeb = gson.fromJson(resultado, OrdemServicoWeb.class);

			http.getConnectionManager().shutdown();
			
			return osWeb.getListaOrdemServico();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<OrdemServico>();
	}
	
	private List<OrdemServico> atualizarOrdemServico() {
		List<OrdemServico> listaOsAdd = new ArrayList<OrdemServico>();
		try{
			HttpClient http = new DefaultHttpClient();
			HttpGet get = new HttpGet("http://10.0.3.2:8080/webService/helloworld/teste");
			ResponseHandler<String> response = new BasicResponseHandler();
			
			String resultado = http.execute(get, response);
		
			Gson gson = new GsonBuilder().create();
			OrdemServicoWeb osWeb = gson.fromJson(resultado, OrdemServicoWeb.class);
		
			OrdemServicoDAO osDao = new OrdemServicoDAO(getContext());
			List<OrdemServico> listaOs = osDao.consultarTodos();
			
			for(OrdemServico osWebService : osWeb.getListaOrdemServico()) {
				boolean isExisteNaListaWebService = false;
				
				for(OrdemServico os : listaOs){
					if(osWebService.getNrOrdemServico().compareTo(os.getNrOrdemServico()) == 0){
						isExisteNaListaWebService = true;
						break;
					}
				}
				if(!isExisteNaListaWebService){
					listaOsAdd.add(osWebService);
				}
			}
			
			for(OrdemServico osNew : listaOsAdd){
				OrdemServico os = new OrdemServico();
				os.setNmOrdemServico(osNew.getNmOrdemServico());
				os.setNrOrdemServico(osNew.getNrOrdemServico());
				
				osDao.incluirOrdemServico(os);
			}
			
			http.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaOsAdd;
	}

	@Override
	protected Void doInBackground(Void... param) {
		/*
		List<OrdemServico> listaOsAdd = atualizarOrdemServico();
		
		if(listaOsAdd != null && listaOsAdd.size() > 0){
			NotificationManager nf = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
			
			Intent aviso = new Intent(getContext(), MainActivity.class);
			
			PendingIntent pedido = PendingIntent.getActivity(getContext(), 0, aviso, Intent.FLAG_ACTIVITY_NEW_TASK);
			
			Notification notificacao = new Notification.Builder(getContext())
			.setContentIntent(pedido)
			.setContentText("Sua Lista de Ordem de Serviço foi atualizada!")
			.setContentTitle("LISTA ORDEM DE SERVIÇO ATUALIZADO")
			.setAutoCancel(true).build();
			
			nf.notify(0, notificacao);
			
		}*/
		return null;	
		
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
