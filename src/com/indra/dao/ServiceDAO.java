package com.indra.dao;

import android.content.Context;

public class ServiceDAO extends BaseDAO {

	private SqliteHelper sqlHelp;
	
	public ServiceDAO(Context context) {
		super(context);
		
		StringBuilder createTableOS = new StringBuilder();
		StringBuilder createTableAtividade = new StringBuilder();
		StringBuilder createTableEsforcoDiario = new StringBuilder();

		createTableOS.append(" CREATE TABLE IF NOT EXISTS TB_ORDEM_SERVICO  ( ");
		createTableOS.append(" ID_ORDEM_SERVICO INTEGER CONSTRAINT 'AUTO_INCREMENT' PRIMARY KEY ASC AUTOINCREMENT, ");
		createTableOS.append(" NM_ORDEM_SERVICO TEXT ( 80 ), ");
		createTableOS.append(" NR_ORDEM_SERVICO INTEGER); ");
		
		createTableAtividade.append(" CREATE TABLE IF NOT EXISTS TB_TIPO_ATIVIDADE ( ");
		createTableAtividade.append(" ID_TIPO_ATIVIDADE INTEGER CONSTRAINT 'AUTO_INCREMENT' PRIMARY KEY ASC AUTOINCREMENT, ");
		createTableAtividade.append(" DS_TIPO_ATIVIDADE TEXT ( 80 ) ); ");
		
		createTableEsforcoDiario.append(" CREATE TABLE IF NOT EXISTS TB_ESFORCO_DIARIO ( ");
		createTableEsforcoDiario.append(" ID_ESFORCO_DIARIO INTEGER CONSTRAINT 'AUTO_INCREMENT' PRIMARY KEY ASC AUTOINCREMENT, ");
		createTableEsforcoDiario.append(" ID_ORDEM_SERVICO INTERGER, ");
		createTableEsforcoDiario.append(" ID_TIPO_ATIVIDADE INTERGER, ");
		createTableEsforcoDiario.append(" DT_INICIO_ESFORCO DATETIME, ");
		createTableEsforcoDiario.append(" DT_FIM_ESFORCO DATETIME, ");
		createTableEsforcoDiario.append(" TEMPO_GASTO TEXT (8), ");
		createTableEsforcoDiario.append(" FOREIGN KEY(ID_TIPO_ATIVIDADE) REFERENCES TB_TIPO_ATIVIDADE(ID_TIPO_ATIVIDADE), ");
		createTableEsforcoDiario.append(" FOREIGN KEY(ID_ORDEM_SERVICO) REFERENCES TB_ORDEM_SERVICO(ID_ORDEM_SERVICO) ); ");
		
		String drop = new String(" DROP TABLE IF EXISTS TB_TIPO_ATIVIDADE; DROP TABLE IF EXISTS TB_ORDEM_SERVICO; DROP TABLE IF EXISTS TB_ESFORCO_DIARIO; ");
		
		
		sqlHelp = new SqliteHelper(context, NOME_BANCO, null, VERSAO, 
				new String[]{createTableAtividade.toString(), createTableOS.toString(), createTableEsforcoDiario.toString()}, drop);
		super.db = sqlHelp.getWritableDatabase();
	}

	@Override
	public void fechar() {
		super.fechar();
		if(sqlHelp != null) {
			sqlHelp.close();
		}
	}
	
	
}
