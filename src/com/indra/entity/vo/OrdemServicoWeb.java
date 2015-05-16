package com.indra.entity.vo;

import java.io.Serializable;
import java.util.List;

import com.indra.entity.OrdemServico;

public class OrdemServicoWeb implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<OrdemServico> listaOrdemServico;

	public List<OrdemServico> getListaOrdemServico() {
		return listaOrdemServico;
	}

	public void setListaOrdemServico(List<OrdemServico> listaOrdemServico) {
		this.listaOrdemServico = listaOrdemServico;
	}
	
	
	
}
