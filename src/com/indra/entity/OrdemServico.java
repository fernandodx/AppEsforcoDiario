package com.indra.entity;

import java.io.Serializable;

public class OrdemServico implements Comparable<OrdemServico>, Serializable {
	private static final long serialVersionUID = 652341691069876283L;

	public static final String ID_ORDEM_SERVICO_BD = "ID_ORDEM_SERVICO";
	public static final String NM_ORDEM_SERVICO_BD = "NM_ORDEM_SERVICO";
	public static final String NR_ORDEM_SERVICO_BD = "NR_ORDEM_SERVICO";
	
	public static final String NOME_TABELA = "TB_ORDEM_SERVICO";
	
	private Long idOrdemServico;
	private Integer nrOrdemServico;
	private String nmOrdemServico;
	
	public static String[] getColunasBanco() {
		return new String[]{ID_ORDEM_SERVICO_BD, NM_ORDEM_SERVICO_BD, NR_ORDEM_SERVICO_BD};
	}
	
	
	public OrdemServico() {
		super();
	}
	
	
	
	public OrdemServico(Long idOrdemServico) {
		super();
		this.idOrdemServico = idOrdemServico;
	}



	public OrdemServico(Long idOrdemServico, Integer nrOrdemServico,
			String nmOrdemServico) {
		super();
		this.idOrdemServico = idOrdemServico;
		this.nrOrdemServico = nrOrdemServico;
		this.nmOrdemServico = nmOrdemServico;
	}



	public static String[] colunasBanco() {
		return new String[]{ID_ORDEM_SERVICO_BD, NM_ORDEM_SERVICO_BD, NR_ORDEM_SERVICO_BD};
	}
	
	public Long getIdOrdemServico() {
		return idOrdemServico;
	}
	public void setIdOrdemServico(Long idOrdemServico) {
		this.idOrdemServico = idOrdemServico;
	}
	public Integer getNrOrdemServico() {
		return nrOrdemServico;
	}
	public void setNrOrdemServico(Integer nrOrdemServico) {
		this.nrOrdemServico = nrOrdemServico;
	}

	public String getNmOrdemServico() {
		return nmOrdemServico;
	}

	public void setNmOrdemServico(String nmOrdemServico) {
		this.nmOrdemServico = nmOrdemServico;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nrOrdemServico == null) ? 0 : nrOrdemServico.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		if (nrOrdemServico == null) {
			if (other.nrOrdemServico != null)
				return false;
		} else if (!nrOrdemServico.equals(other.nrOrdemServico))
			return false;
		return true;
	}


	@Override
	public int compareTo(OrdemServico os) {
		return this.getNrOrdemServico().compareTo(os.getNrOrdemServico());
	}
	
	
	
	
	
	
	
}
