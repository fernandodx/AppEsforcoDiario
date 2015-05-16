package com.indra.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.indra.appesforcodiario.Util;
import com.indra.entity.TipoAtividade;
import com.indra.entity.vo.TipoAtividadeWeb;

public class TipoAtividadeDAO extends BaseDAO {

	public TipoAtividadeDAO(Context context) {
		super(context);
	}

	
	public List<TipoAtividade> consultarTipoAtividadeWeb() throws Exception {
		//http://feesom.com/json/atividade.php
		//http://10.0.3.2:8080/service/mobileservice/atividades
		TipoAtividadeWeb web = (TipoAtividadeWeb) consultarWebService(Util.getUrlWebService("atividades"), TipoAtividadeWeb.class);
		return web.getListaTipoAtividade();
	}
	
	
	public Long incluirTipoAtividade(TipoAtividade atividade) throws Exception {
		
		ContentValues valores = new ContentValues();
		valores.put(TipoAtividade.ID_TIPO_ATIVIDADE_BD, atividade.getIdTipoAtividade());
		valores.put(TipoAtividade.DS_TIPO_ATIVIDADE_BD, atividade.getDsTipoAtividade());
		
		return salvar(TipoAtividade.NOME_TABELA, valores);
	}
	
	public TipoAtividade consultarAtividadePorId(Long idTipoAtividade) throws Exception {
		Cursor cursor = consultarPorId(idTipoAtividade, TipoAtividade.ID_TIPO_ATIVIDADE_BD, TipoAtividade.NOME_TABELA, TipoAtividade.getColunas());
		TipoAtividade atividade = new TipoAtividade();
		
		if(cursor.getCount() > 0){
			
			cursor.moveToFirst();
			
			int indexId = cursor.getColumnIndex(TipoAtividade.ID_TIPO_ATIVIDADE_BD);
			int indexDsAtividade = cursor.getColumnIndex(TipoAtividade.DS_TIPO_ATIVIDADE_BD);
			
			atividade.setIdTipoAtividade(cursor.getLong(indexId));
			atividade.setDsTipoAtividade(cursor.getString(indexDsAtividade));
		}
	
		return atividade;
	}
	
}
