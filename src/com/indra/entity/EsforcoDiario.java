package com.indra.entity;

import java.io.Serializable;
import java.util.Calendar;

import com.google.android.gms.drive.internal.GetDriveIdFromUniqueIdentifierRequest;

public class EsforcoDiario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ESFORCO_DIARIO_SERIAL ="esforcoDiarioSerial";
	public static final String IS_ESFORCO_INICIADO ="isEsforcoIniciado";
	public static final String ID_ESFORCO_DIARIO_BD = "ID_ESFORCO_DIARIO";
	public static final String ID_ORDEM_SERVICO_BD = "ID_ORDEM_SERVICO";
	public static final String ID_TIPO_ATIVIDADE_BD = "ID_TIPO_ATIVIDADE";
	public static final String DT_INICIO_ESFORCO_BD = "DT_INICIO_ESFORCO";
	public static final String DT_FIM_ESFORCO_BD = "DT_FIM_ESFORCO";
	public static final String TEMPO_GASTO_BD = "TEMPO_GASTO";
	
	public static final String NOME_TABELA = "TB_ORDEM_SERVICO";
	
	private Long idEsforcoDiario;
	private Calendar dtInicioAtividade;
	private Calendar dtFimAtividade;
	private OrdemServico ordemServico;
	private TipoAtividade tipoAtividade;
	private Long timerContador;
	private String tempoGasto;
	private Boolean isPausado;
	private Long tempoPausado;
	
	public static String[] getColunasbanco(){
		return new String[]{ID_ESFORCO_DIARIO_BD, ID_ORDEM_SERVICO_BD, 
				ID_TIPO_ATIVIDADE_BD, DT_INICIO_ESFORCO_BD, DT_FIM_ESFORCO_BD, TEMPO_GASTO_BD};
	}
 	
	public Long getIdEsforcoDiario() {
		return idEsforcoDiario;
	}
	public void setIdEsforcoDiario(Long idEsforcoDiario) {
		this.idEsforcoDiario = idEsforcoDiario;
	}
	public OrdemServico getOrdemServico() {
		return ordemServico;
	}
	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}
	public TipoAtividade getTipoAtividade() {
		return tipoAtividade;
	}
	public void setTipoAtividade(TipoAtividade tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}
	public Calendar getDtInicioAtividade() {
		return dtInicioAtividade;
	}
	public void setDtInicioAtividade(Calendar dtInicioAtividade) {
		this.dtInicioAtividade = dtInicioAtividade;
	}
	public Calendar getDtFimAtividade() {
		return dtFimAtividade;
	}
	public void setDtFimAtividade(Calendar dtFimAtividade) {
		this.dtFimAtividade = dtFimAtividade;
	}
	public Long getTimerContador() {
		return timerContador;
	}
	public void setTimerContador(Long timerContador) {
		this.timerContador = timerContador;
	}
	public String getTempoGasto() {
		return tempoGasto;
	}
	public void setTempoGasto(String tempoGasto) {
		this.tempoGasto = tempoGasto;
	}

	public Boolean getIsPausado() {
		if(isPausado == null) {
			return false;
		}
		return isPausado;
	}

	public void setIsPausado(Boolean isPausado) {
		this.isPausado = isPausado;
	}

	public Long getTempoPausado() {
		return tempoPausado;
	}

	public void setTempoPausado(Long tempoPausado) {
		this.tempoPausado = tempoPausado;
	}
	
	public EsforcoDiario cloneNovoObj() {
		
		EsforcoDiario esforco = new EsforcoDiario();
		esforco.setDtFimAtividade(this.dtFimAtividade);
		esforco.setDtInicioAtividade(this.dtInicioAtividade);
		esforco.setIdEsforcoDiario(this.idEsforcoDiario);
		esforco.setIsPausado(this.isPausado);
		esforco.setOrdemServico(this.ordemServico);
		esforco.setTempoGasto(this.tempoGasto);
		esforco.setTempoPausado(this.tempoPausado);
		esforco.setTimerContador(this.timerContador);
		esforco.setTipoAtividade(this.tipoAtividade);
		
		return esforco;
	}
	
	
	
	
	
	
	

}
