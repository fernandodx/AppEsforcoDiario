package com.indra.appesforcodiario.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.indra.appesforcodiario.R;
import com.indra.appesforcodiario.dto.EsforcoTotalDemandaDTO;

public class EsforcoDiarioTotalAdapter extends BaseAdapter {

	private List<EsforcoTotalDemandaDTO> listaEsforcoTotal;
	private Context context;
	
	public EsforcoDiarioTotalAdapter(
			List<EsforcoTotalDemandaDTO> listaEsforcoTotal, Context context) {
		super();
		this.listaEsforcoTotal = listaEsforcoTotal;
		this.context = context;
	}

	@Override
	public int getCount() {
		return listaEsforcoTotal.size();
	}

	@Override
	public Object getItem(int position) {
		return listaEsforcoTotal.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view =  inflater.inflate(R.layout.lista_historico_totalizadores, null);
		
		
		EsforcoTotalDemandaDTO dto = listaEsforcoTotal.get(position);
		
		TextView dataEsforco = (TextView) view.findViewById(R.id.txDtTotalEsforco);
		TextView nrEsforco = (TextView) view.findViewById(R.id.txTotalNrProjeto);
		TextView nmEsforco = (TextView) view.findViewById(R.id.txNmProjeto);
		TextView total = (TextView) view.findViewById(R.id.txTotalTempo);
		
		dataEsforco.setText(dto.getData());
		nrEsforco.setText(dto.getNrOrdemServico());
		nmEsforco.setText(dto.getNmOrdemServico());
		total.setText(dto.getTotalDemanda());
		
		return view;
	}

}
