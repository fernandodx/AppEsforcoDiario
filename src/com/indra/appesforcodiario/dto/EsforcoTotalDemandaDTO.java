package com.indra.appesforcodiario.dto;

import java.io.Serializable;

public class EsforcoTotalDemandaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String data;
	private String nrOrdemServico;
	private String nmOrdemServico;
	private String totalDemanda;
	
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	public String getNrOrdemServico() {
		return nrOrdemServico;
	}
	public void setNrOrdemServico(String nrOrdemServico) {
		this.nrOrdemServico = nrOrdemServico;
	}
	
	
	public String getNmOrdemServico() {
		return nmOrdemServico;
	}
	public void setNmOrdemServico(String nmOrdemServico) {
		this.nmOrdemServico = nmOrdemServico;
	}
	
	
	public String getTotalDemanda() {
		return totalDemanda;
	}
	public void setTotalDemanda(String totalDemanda) {
		this.totalDemanda = totalDemanda;
	}
	
	
	
	
	
	
	
}
