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
import com.indra.entity.TipoAtividade;

public class TipoAtividadeComboAdapter extends BaseAdapter {

	private List<TipoAtividade> listaTipoAtividade;
	private Context context;
	
	public TipoAtividadeComboAdapter(List<TipoAtividade> listaTipoAtividade,
			Context context) {
		super();
		this.listaTipoAtividade = listaTipoAtividade;
		this.context = context;
	}

	@Override
	public int getCount() {
		return getListaTipoAtividade().size();
	}

	@Override
	public Object getItem(int position) {
		return getListaTipoAtividade().get(position);
	}

	@Override
	public long getItemId(int position) {
		return getListaTipoAtividade().get(position).getIdTipoAtividade();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater layout = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layout.inflate(R.layout.lista_tipo_atividade_combo, null);
		TextView  txlabel = (TextView) view.findViewById(R.id.labelNmAtividade);
		
		TipoAtividade tipoAtividade = getListaTipoAtividade().get(position);
		txlabel.setText(tipoAtividade.getDsTipoAtividade());
		
		return view;
	}

	
	public List<TipoAtividade> getListaTipoAtividade() {
		if(listaTipoAtividade == null) {
			listaTipoAtividade = new ArrayList<TipoAtividade>();
		}
		return listaTipoAtividade;
	}

	public void setListaTipoAtividade(List<TipoAtividade> listaTipoAtividade) {
		this.listaTipoAtividade = listaTipoAtividade;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	
	

}
