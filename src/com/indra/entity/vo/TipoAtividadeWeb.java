package com.indra.entity.vo;

import java.io.Serializable;
import java.util.List;

import com.indra.entity.TipoAtividade;

public class TipoAtividadeWeb implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<TipoAtividade> listaTipoAtividade;

	public List<TipoAtividade> getListaTipoAtividade() {
		return listaTipoAtividade;
	}

	public void setListaTipoAtividade(List<TipoAtividade> listaTipoAtividade) {
		this.listaTipoAtividade = listaTipoAtividade;
	}
	
	
	
	
}
