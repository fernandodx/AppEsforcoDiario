package com.indra.appesforcodiario.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.indra.appesforcodiario.R;
import com.indra.appesforcodiario.Util;
import com.indra.entity.EsforcoDiario;

public class EsforcoDiarioAdapter extends BaseAdapter {

	private List<EsforcoDiario> listaEsforcoDiario;
	private Context context;
	
	public EsforcoDiarioAdapter(List<EsforcoDiario> listaEsforcoDiario,
			Context context) {
		super();
		this.listaEsforcoDiario = listaEsforcoDiario;
		this.context = context;
	}
	
	

	@Override
	public int getCount() {
		return getListaEsforcoDiario().size();
	}

	@Override
	public Object getItem(int position) {
		return getListaEsforcoDiario().get(position);
	}

	@Override
	public long getItemId(int position) {
		return getListaEsforcoDiario().get(position).getIdEsforcoDiario();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater layout = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view =  layout.inflate(R.layout.lista_esforco_diario, null);
		
		EsforcoDiario esforco = getListaEsforcoDiario().get(position);
		
		TextView nmOs = (TextView) view.findViewById(R.id.txNmOrdemServicoInflater);
		TextView nrOs = (TextView) view.findViewById(R.id.txNrOrdemServicoInflater);
		TextView dtInicio = (TextView) view.findViewById(R.id.txDtinicioInflater);
		TextView dtFim = (TextView) view.findViewById(R.id.txDtFimInflater);
		TextView tempoGasto = (TextView) view.findViewById(R.id.txTempoGastoResultadoInflater);
		
		nmOs.setText(esforco.getOrdemServico().getNmOrdemServico().toString());
		nrOs.setText(esforco.getOrdemServico().getNrOrdemServico().toString());
		dtInicio.setText(Util.formatarData(esforco.getDtInicioAtividade()));
		dtFim.setText(Util.formatarData(esforco.getDtFimAtividade()));
		tempoGasto.setText(esforco.getTempoGasto().toString());
		
		return view;
	}



	public List<EsforcoDiario> getListaEsforcoDiario() {
		if(listaEsforcoDiario == null) {
			listaEsforcoDiario = new ArrayList<EsforcoDiario>();
		}
		return listaEsforcoDiario;
	}



	public void setListaEsforcoDiario(List<EsforcoDiario> listaEsforcoDiario) {
		this.listaEsforcoDiario = listaEsforcoDiario;
	}



	public Context getContext() {
		return context;
	}



	public void setContext(Context context) {
		this.context = context;
	}
	
	

}
