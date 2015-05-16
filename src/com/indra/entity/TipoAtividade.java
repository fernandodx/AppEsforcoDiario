package com.indra.entity;

import java.io.Serializable;

public class TipoAtividade implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String NOME_TABELA = "TB_TIPO_ATIVIDADE";
	public static final String ID_TIPO_ATIVIDADE_BD = "ID_TIPO_ATIVIDADE";
	public static final String DS_TIPO_ATIVIDADE_BD = "DS_TIPO_ATIVIDADE";
	
	private Long idTipoAtividade;
	private String dsTipoAtividade;
	
	public static final String[] getColunas() {
		return new String[]{ID_TIPO_ATIVIDADE_BD, DS_TIPO_ATIVIDADE_BD};
	}
	
	public Long getIdTipoAtividade() {
		return idTipoAtividade;
	}
	public void setIdTipoAtividade(Long idTipoAtividade) {
		this.idTipoAtividade = idTipoAtividade;
	}
	
	
	public String getDsTipoAtividade() {
		return dsTipoAtividade;
	}
	public void setDsTipoAtividade(String dsTipoAtividade) {
		this.dsTipoAtividade = dsTipoAtividade;
	}
	
	
	

}
