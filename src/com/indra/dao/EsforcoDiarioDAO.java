package com.indra.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.indra.appesforcodiario.Util;
import com.indra.entity.EsforcoDiario;
import com.indra.entity.OrdemServico;
import com.indra.entity.TipoAtividade;

public class EsforcoDiarioDAO extends BaseDAO {

	private static final String NOME_TABELA = "TB_ESFORCO_DIARIO";
	
	private Context context;
	
	public EsforcoDiarioDAO(Context context) {
		super(context);
		this.context = context;
	}

	public void incluirEsforcoDiario(EsforcoDiario esforco) throws Exception {
		
		TipoAtividadeDAO atividadeDao = new TipoAtividadeDAO(getContext());
		OrdemServicoDAO osDao = new OrdemServicoDAO(getContext());
		Long idOs = osDao.incluirOrdemServico(esforco.getOrdemServico());
		Long idAtividade = atividadeDao.incluirTipoAtividade(esforco.getTipoAtividade());
		
		ContentValues valores = new ContentValues();
		valores.put(EsforcoDiario.ID_ORDEM_SERVICO_BD, idOs < 1 ? esforco.getOrdemServico().getIdOrdemServico() : idOs);
		valores.put(EsforcoDiario.ID_TIPO_ATIVIDADE_BD, idAtividade < 1 ? esforco.getTipoAtividade().getIdTipoAtividade() : idAtividade);
		valores.put(EsforcoDiario.DT_INICIO_ESFORCO_BD, Util.formatarDataParaBanco(esforco.getDtInicioAtividade()));
		valores.put(EsforcoDiario.DT_FIM_ESFORCO_BD, Util.formatarDataParaBanco(esforco.getDtFimAtividade()));
		valores.put(EsforcoDiario.TEMPO_GASTO_BD, esforco.getTempoGasto());
		
		salvar(NOME_TABELA, valores);
	}
	
	public List<EsforcoDiario> consultarEsforcoHoje() throws Exception {
		
		 Cursor cursor = db.query(NOME_TABELA, EsforcoDiario.getColunasbanco(), " date("+EsforcoDiario.DT_FIM_ESFORCO_BD +")" + " = date('now') ", null, null, null, null);
		
		 return preencherCursoResultado(cursor);
	}
	
	
	public List<EsforcoDiario> consultarTodos() throws Exception {
		
		Cursor cursor = db.rawQuery("SELECT * FROM TB_ESFORCO_DIARIO esforco ORDER BY esforco.DT_FIM_ESFORCO DESC", new String[]{});
//				getCursor(NOME_TABELA, EsforcoDiario.getColunasbanco(), "'DT_FIM_ESFORCO ASC' " );
		
		return preencherCursoResultado(cursor);
		
	}
	
	private List<EsforcoDiario> preencherCursoResultado(Cursor cursor) throws Exception {
		List<EsforcoDiario> listaEsforco = new ArrayList<EsforcoDiario>();
		
		if(cursor != null && cursor.moveToFirst()){
			
			do{
				EsforcoDiario esforco = new EsforcoDiario();
				
				int indexId = cursor.getColumnIndex(EsforcoDiario.ID_ESFORCO_DIARIO_BD);
				int indexIdAtividade = cursor.getColumnIndex(EsforcoDiario.ID_TIPO_ATIVIDADE_BD);
				int indexIdOs = cursor.getColumnIndex(EsforcoDiario.ID_ORDEM_SERVICO_BD);
				int indexDtInicio = cursor.getColumnIndex(EsforcoDiario.DT_INICIO_ESFORCO_BD);
				int indexDtFim = cursor.getColumnIndex(EsforcoDiario.DT_FIM_ESFORCO_BD);
				int indexTempoGasto = cursor.getColumnIndex(EsforcoDiario.TEMPO_GASTO_BD);
				
				TipoAtividadeDAO atividadeDao = new TipoAtividadeDAO(getContext());
				OrdemServicoDAO osDao = new OrdemServicoDAO(getContext());
				
				TipoAtividade atividade = atividadeDao.consultarAtividadePorId(cursor.getLong(indexIdAtividade));
				OrdemServico os = osDao.consultarOrdemServicoPorId(cursor.getLong(indexIdOs));
				
				esforco.setIdEsforcoDiario(cursor.getLong(indexId));
				esforco.setOrdemServico(os);
				esforco.setTipoAtividade(atividade);
				esforco.setDtInicioAtividade(Util.StringBancoToDate(cursor.getString(indexDtInicio)));
				esforco.setDtFimAtividade(Util.StringBancoToDate(cursor.getString(indexDtFim)));
				esforco.setTempoGasto(cursor.getString(indexTempoGasto));
				
				listaEsforco.add(esforco);
				
			}while(cursor.moveToNext());
		}
		return listaEsforco;
	}
	
	public Boolean enviarEmailPlanilhaWebService(String urlService, EsforcoDiario esforco, String emailUsuario, String emailChefe, String nrMatricula) throws Exception {
		
		List<Map<String, Object>> listaEsforco = new ArrayList<Map<String,Object>>();
		List<EsforcoDiario> listaHoje = consultarEsforcoHoje();
		
		for(EsforcoDiario esforcoDiario : listaHoje) {
			Map<String, Object> hashMap = new LinkedHashMap<String, Object>();
			hashMap.put("matricula", nrMatricula);
			hashMap.put("nrOrdemServico", esforcoDiario.getOrdemServico().getNrOrdemServico().toString());
			hashMap.put("dataInicio", Util.formatarSomenteData(esforcoDiario.getDtInicioAtividade()));
			hashMap.put("horaInicio", Util.formatarTempo(esforcoDiario.getDtInicioAtividade()));
			hashMap.put("horaFim", Util.formatarTempo(esforcoDiario.getDtFimAtividade()));
			hashMap.put("atividade", esforcoDiario.getTipoAtividade().getDsTipoAtividade());
			
			//INFO EXTRAS
			hashMap.put("emailUsuario", emailUsuario);
			hashMap.put("emailChefe", emailChefe);
			hashMap.put("totalDemanda", esforcoDiario.getTempoGasto());
			
			listaEsforco.add(hashMap);
		}
		
		String json =  new Gson().toJson(listaEsforco, ArrayList.class);
		return enviarWebService(urlService, json);
	}
	

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	
	
	
}
