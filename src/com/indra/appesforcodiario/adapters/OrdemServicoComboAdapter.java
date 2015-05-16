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
import com.indra.entity.OrdemServico;

public class OrdemServicoComboAdapter extends BaseAdapter {

	private List<OrdemServico> listaOrdemServico;
	private Context context;
	
	public OrdemServicoComboAdapter(List<OrdemServico> listaOrdemServico,
			Context context) {
		super();
		this.listaOrdemServico = listaOrdemServico;
		this.context = context;
	}

	@Override
	public int getCount() {
		return getListaOrdemServico().size();
	}

	@Override
	public Object getItem(int position) {
		return getListaOrdemServico().get(position);
	}

	@Override
	public long getItemId(int position) {
		return getListaOrdemServico().get(position).getIdOrdemServico();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.lista_ordem_servico_combo, null);
		
		TextView nrOs = (TextView) view.findViewById(R.id.labelNrOs);
		TextView nmOs = (TextView) view.findViewById(R.id.labelNmOs);
		
		OrdemServico os = getListaOrdemServico().get(position);
		
		nmOs.setText(os.getNmOrdemServico());
		nrOs.setText(os.getNrOrdemServico().toString());
		
		return view;
	}

	public List<OrdemServico> getListaOrdemServico() {
		if(listaOrdemServico == null) {
			listaOrdemServico = new ArrayList<OrdemServico>();
		}
		return listaOrdemServico;
	}

	public void setListaOrdemServico(List<OrdemServico> listaOrdemServico) {
		this.listaOrdemServico = listaOrdemServico;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	
	

}
