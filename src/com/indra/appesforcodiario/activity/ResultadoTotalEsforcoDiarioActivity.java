package com.indra.appesforcodiario.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.indra.appesforcodiario.R;
import com.indra.appesforcodiario.Util;
import com.indra.appesforcodiario.adapters.EsforcoDiarioAdapter;
import com.indra.appesforcodiario.adapters.EsforcoDiarioTotalAdapter;
import com.indra.appesforcodiario.dto.EsforcoTotalDemandaDTO;
import com.indra.dao.EsforcoDiarioDAO;
import com.indra.entity.EsforcoDiario;

public class ResultadoTotalEsforcoDiarioActivity extends BaseActivity {

	private ListView listaTotalHistorico;
	
	
	@Override
	protected void inicializarComponentes() {
		listaTotalHistorico = (ListView) findViewById(R.id.listaTotalizadoresHistorico);
	}
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.tela_historico_totalizadores);
		inicializarComponentes();
		
		EsforcoDiarioDAO esforcoDao = new EsforcoDiarioDAO(this);
		
		try {
			List<EsforcoDiario> listaEsforcoDiario = esforcoDao.consultarTodos();
			List<EsforcoTotalDemandaDTO> listaTotal = new ArrayList<EsforcoTotalDemandaDTO>();
			List<List<EsforcoDiario>> listaTotalDiario = new ArrayList<List<EsforcoDiario>>();
			List<EsforcoDiario> listaTemp = new ArrayList<EsforcoDiario>();
			
			EsforcoDiario esforcoDiarioBase = listaEsforcoDiario.get(0);
			
			
			for(EsforcoDiario esforcoDiario : listaEsforcoDiario) {
				
				if(Util.formatarSomenteData(esforcoDiarioBase.getDtInicioAtividade()).equals(
						Util.formatarSomenteData(esforcoDiario.getDtInicioAtividade()))
						){
					listaTemp.add(esforcoDiario.cloneNovoObj());
				}else{
					listaTotalDiario.add(listaTemp);
					listaTemp = new ArrayList<EsforcoDiario>();
					listaTemp.add(esforcoDiario.cloneNovoObj());
					esforcoDiarioBase = esforcoDiario.cloneNovoObj();
				}
			}
			
			listaTotalDiario.add(listaTemp);
			
			
			for(List<EsforcoDiario> listaDia : listaTotalDiario) {
				
				Collections.sort(listaDia, new Comparator<EsforcoDiario>() {
					@Override
					public int compare(EsforcoDiario e1, EsforcoDiario e2) {
						return e1.getOrdemServico().getNrOrdemServico().compareTo(e2.getOrdemServico().getNrOrdemServico());
					}
				});
				
				
				EsforcoTotalDemandaDTO dto = new  EsforcoTotalDemandaDTO();
				
				EsforcoDiario esforcoBase = listaDia.get(0);
				Calendar totalDiaTempo = Calendar.getInstance();
				SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
				totalDiaTempo.setTime(hora.parse("00:00:00"));
				inicializarTempo(totalDiaTempo);
				boolean isUltimoDaLista = listaDia.size() == 1 ? true : false;
				
				
				for(int i=0; i < listaDia.size() ; i++) {
					
					if(esforcoBase.getOrdemServico().getNrOrdemServico().equals(listaDia.get(i).getOrdemServico().getNrOrdemServico())){
						Util.somarTempo(listaDia.get(i).getTempoGasto(), totalDiaTempo);
					}else{
						dto.setData(Util.formatarSomenteData(listaDia.get(i-1).getDtInicioAtividade()));
						dto.setNmOrdemServico(listaDia.get(i-1).getOrdemServico().getNmOrdemServico());
						dto.setNrOrdemServico(listaDia.get(i-1).getOrdemServico().getNrOrdemServico().toString());
						dto.setTotalDemanda(Util.formatarTempo(totalDiaTempo));
						
						listaTotal.add(dto);
						dto = new EsforcoTotalDemandaDTO();
						inicializarTempo(totalDiaTempo);
						esforcoBase = listaDia.get(i);
						totalDiaTempo.setTime(hora.parse("00:00:00"));
						if(i == listaDia.size() - 1){
							isUltimoDaLista = true;
						}
					}
				}
				
				if(isUltimoDaLista){
					Util.somarTempo(esforcoBase.getTempoGasto(), totalDiaTempo);
					dto.setData(Util.formatarSomenteData(esforcoBase.getDtInicioAtividade()));
					dto.setNmOrdemServico(esforcoBase.getOrdemServico().getNmOrdemServico());
					dto.setNrOrdemServico(esforcoBase.getOrdemServico().getNrOrdemServico().toString());
					dto.setTotalDemanda(Util.formatarTempo(totalDiaTempo));
					listaTotal.add(dto);
				}
				
			}
			
			
			
			listaTotalHistorico.setAdapter(new EsforcoDiarioTotalAdapter(listaTotal, this));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	private void inicializarTempo(Calendar tempo) throws ParseException {
		SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
		tempo = Calendar.getInstance();
		tempo.setTime(hora.parse("00:00:00"));
	}

	public ListView getListaTotalHistorico() {
		return listaTotalHistorico;
	}


	public void setListaTotalHistorico(ListView listaTotalHistorico) {
		this.listaTotalHistorico = listaTotalHistorico;
	}

	
	
	
}
