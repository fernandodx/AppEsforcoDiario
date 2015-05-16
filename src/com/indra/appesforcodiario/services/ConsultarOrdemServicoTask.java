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

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indra.entity.OrdemServico;
import com.indra.entity.vo.OrdemServicoWeb;

public class ConsultarOrdemServicoTask extends AsyncTask<Void, Void, List<OrdemServico>> {

	@Override
	protected List<OrdemServico> doInBackground(Void... params) {
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

}
