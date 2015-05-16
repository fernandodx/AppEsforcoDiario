package com.indra.dao;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseDAO {
	
	public static final String NOME_BANCO = "BD_ESFORCO";
	protected static int VERSAO = 2;
	
	protected SQLiteDatabase db;

	public BaseDAO(Context context) {
		db = context.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
	}

	protected Long salvar(String nomeTabela, ContentValues valores) {
		return db.insert(nomeTabela, null, valores);
	}
	
	protected Integer alterar(String nomeTabela, ContentValues valores, String where, String[] parametros) {
		return db.update(nomeTabela, valores, where , parametros);
	}
	
	protected Cursor getCursor(String nomeTabela, String[] colunas, String orderBy) {
		return db.query(nomeTabela , colunas, null, null, null, null, null, orderBy);
	}
	
	protected Cursor getCursor(String nomeTabela, String[] colunas) {
		return db.query(nomeTabela , colunas, null, null, null, null, null, null);
	}
	
	protected Cursor getCursorQuery(String query, String[] parametros) {
		return db.rawQuery(query, parametros);
	}

	protected Cursor consultarPorId(Long id,String identificacaoId, String nomeTabela, String[] nomesColunas) {
		return db.query(nomeTabela, nomesColunas, identificacaoId + "=" + id, null, null, null, null);
	}
	
	protected Object consultarWebService(String endHttp, Class classe) throws Exception {
			HttpClient http = new DefaultHttpClient();
			HttpGet get = new HttpGet(endHttp);
//			HttpPost post = new HttpPost(endHttp);
//			post.setHeader("content-type", "application/json");
			ResponseHandler<String> response = new BasicResponseHandler();
			
			String resultado = http.execute(get, response);
//			HttpResponse reponse = http.execute(post);
			
			
//			String resultado = EntityUtils.toString(reponse.getEntity());

			
			Gson gson = new GsonBuilder().create();
			
			Object obj = gson.fromJson(resultado, classe);

			http.getConnectionManager().shutdown();
			
			return obj;
	}
	
	protected Boolean enviarWebService(String endHttp, String json) throws Exception {
		HttpPost post = new HttpPost();

		post.setURI(new URI(endHttp));
		post.setEntity(new StringEntity(json));

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(post);
		Object retorno = null;
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			InputStream content = entity.getContent();
			
			try {
				//Read the server response and attempt to parse it as JSON
				Reader reader = new InputStreamReader(content);
				
//				GsonBuilder gsonBuilder = new GsonBuilder();
//				Gson gson = gsonBuilder.create();
//				retorno = gson.fromJson(reader, HashMap.class);
				content.close();

				//Possivelmente criar um handler se quiser exibir na tela.
				fechar();
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		fechar();
		
		return false;
		
	}
	
	public void fechar() {
		if(db != null) {
			db.close();
		}
		
	}
	
}
