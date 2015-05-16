package com.indra.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indra.appesforcodiario.Util;
import com.indra.entity.OrdemServico;
import com.indra.entity.TipoAtividade;
import com.indra.entity.vo.OrdemServicoWeb;

public class OrdemServicoDAO extends BaseDAO {

	private static final String NOME_TABELA = "TB_ORDEM_SERVICO";
	
	public OrdemServicoDAO(Context context) {
		super(context);
	}
	
	private void setValores(ContentValues valores, OrdemServico os) {
		valores.put(OrdemServico.ID_ORDEM_SERVICO_BD, os.getIdOrdemServico());
		valores.put(OrdemServico.NM_ORDEM_SERVICO_BD, os.getNmOrdemServico());
		valores.put(OrdemServico.NR_ORDEM_SERVICO_BD, os.getNrOrdemServico());
	}
	

	
	public List<OrdemServico> consultarTodos() {
		
		Cursor cursor = getCursor(NOME_TABELA, OrdemServico.colunasBanco());
		
		List<OrdemServico> lista = new ArrayList<OrdemServico>();
		
		if(cursor.moveToFirst()){
			
			do{
				int idxIdOs = cursor.getColumnIndex(OrdemServico.ID_ORDEM_SERVICO_BD);
				int idxNmOs = cursor.getColumnIndex(OrdemServico.NM_ORDEM_SERVICO_BD);
				int idxNrOs = cursor.getColumnIndex(OrdemServico.NR_ORDEM_SERVICO_BD);
				
				OrdemServico os = new OrdemServico();
				os.setIdOrdemServico(cursor.getLong(idxIdOs));
				os.setNrOrdemServico(cursor.getInt(idxNrOs));
				os.setNmOrdemServico(cursor.getString(idxNmOs));
				
				lista.add(os);
				
			}while(cursor.moveToNext());
		}
		return lista;
		
	}
	
	public List<OrdemServico> consultarOrdemServicoWebService() throws Exception {
//		   http://10.0.3.2:8080/webService/helloworld/teste (GenyMotion)
//		   http://feesom.com/json/os.json
	     OrdemServicoWeb osWeb = (OrdemServicoWeb) consultarWebService(Util.getUrlWebService("ordemservico"), OrdemServicoWeb.class);
	      return osWeb.getListaOrdemServico();
	}
	
	
	public OrdemServico consultarOrdemServicoPorId(Long idOrdemServico) throws Exception {
		Cursor cursor = consultarPorId(idOrdemServico, OrdemServico.ID_ORDEM_SERVICO_BD, OrdemServico.NOME_TABELA, OrdemServico.getColunasBanco());
		OrdemServico os = new OrdemServico();
		
		if(cursor.getCount() > 0){
			
			cursor.moveToFirst();
			
			int indexId = cursor.getColumnIndex(OrdemServico.ID_ORDEM_SERVICO_BD);
			int indexNmOrdemServico = cursor.getColumnIndex(OrdemServico.NM_ORDEM_SERVICO_BD);
			int indexNrOrdemServico = cursor.getColumnIndex(OrdemServico.NR_ORDEM_SERVICO_BD);
			
			os.setIdOrdemServico(cursor.getLong(indexId));
			os.setNmOrdemServico(cursor.getString(indexNmOrdemServico));
			os.setNrOrdemServico(cursor.getInt(indexNrOrdemServico));
		}
	
		return os;
	}
	
	public Long incluirOrdemServico(OrdemServico os) {
		
		ContentValues valores = new ContentValues();
		setValores(valores, os);
		return salvar(NOME_TABELA, valores);
	}
	
}
