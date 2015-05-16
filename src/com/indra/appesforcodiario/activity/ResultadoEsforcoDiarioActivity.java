package com.indra.appesforcodiario.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.indra.appesforcodiario.Constantes;
import com.indra.appesforcodiario.R;
import com.indra.appesforcodiario.Util;
import com.indra.appesforcodiario.adapters.EsforcoDiarioAdapter;
import com.indra.dao.EsforcoDiarioDAO;
import com.indra.entity.EsforcoDiario;

public class ResultadoEsforcoDiarioActivity extends BaseActivity {

	private static final int RESULT_CADASTRO_EMAIL = 2000;
	
	private TextView txDtincio;
	private TextView txDtFim;
	private TextView txTempoGasto;
	private TextView txEsforco;
	private TextView txAtividade;
	private ListView listaHistoricoEsforco;
	private Button btEnviarEsforco;
	private SharedPreferences preferences;
	private Handler handler = new Handler();
	
	
	
	
	@Override
	protected void inicializarComponentes() {
		
		if(getIntent().getExtras() != null 
				&& getIntent().getExtras().getSerializable(EsforcoDiario.ESFORCO_DIARIO_SERIAL) != null) {
			txDtincio = (TextView) findViewById(R.id.txDtInicio);
			txDtFim = (TextView) findViewById(R.id.txDtFim);
			txTempoGasto = (TextView) findViewById(R.id.txTempoGasto);
			txEsforco = (TextView) findViewById(R.id.txDsEsforco);
			txAtividade = (TextView) findViewById(R.id.txDsAtividade);
			listaHistoricoEsforco = (ListView) findViewById(R.id.listaAtividadesAgora);
			btEnviarEsforco = (Button) findViewById(R.id.btEnviarEsforco);
		}else{
			listaHistoricoEsforco = (ListView) findViewById(R.id.listaAtividadesAgora);
		}
	}

	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		if(getIntent().getExtras() != null 
				&& getIntent().getExtras().getSerializable(EsforcoDiario.ESFORCO_DIARIO_SERIAL) != null) {
			setContentView(R.layout.tela_resultado);
		}else{
			setContentView(R.layout.tela_historico);
		}
		
		try {
		
			inicializarComponentes();
			
			if(getIntent().getExtras() != null 
					&& getIntent().getExtras().getSerializable(EsforcoDiario.ESFORCO_DIARIO_SERIAL) != null) {
				
				EsforcoDiario esforco = (EsforcoDiario) getIntent().getExtras().getSerializable(EsforcoDiario.ESFORCO_DIARIO_SERIAL);
			
				txEsforco.setText(esforco.getOrdemServico().getNmOrdemServico());
				txAtividade.setText(esforco.getTipoAtividade().getDsTipoAtividade());
				txDtincio.setText(Util.formatarData(esforco.getDtInicioAtividade()));
				txDtFim.setText(Util.formatarData(esforco.getDtFimAtividade()));
				txTempoGasto.setText(esforco.getTempoGasto());
			
			}
			
			EsforcoDiarioDAO esforcoDao = new EsforcoDiarioDAO(ResultadoEsforcoDiarioActivity.this);
			
			List<EsforcoDiario> listaEsforco = new ArrayList<EsforcoDiario>();
			if(getIntent().getExtras() != null 
					&& getIntent().getExtras().getSerializable(EsforcoDiario.ESFORCO_DIARIO_SERIAL) != null) {
				listaEsforco = esforcoDao.consultarEsforcoHoje();
			}else{
				listaEsforco = esforcoDao.consultarTodos();
			}
		
			listaHistoricoEsforco.setAdapter(
					new EsforcoDiarioAdapter(listaEsforco, ResultadoEsforcoDiarioActivity.this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(getIntent().getExtras() != null 
				&& getIntent().getExtras().getSerializable(EsforcoDiario.ESFORCO_DIARIO_SERIAL) != null) {
			
			btEnviarEsforco.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					try {
					
						preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
						
						if(preferences.getString(Constantes.EMAIL_CHEFE, "").isEmpty()){
							
							startActivityForResult(
									new Intent(
											ResultadoEsforcoDiarioActivity.this, CadastrarEmailActivity.class), RESULT_CADASTRO_EMAIL);
							
						}else{
							showBarraProgresso("Enviando esforço diário...");
							enviarEsforcoDiario();
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	private void enviarEsforcoDiario() throws Exception {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					
					EsforcoDiario esforco = (EsforcoDiario) getIntent().getExtras().getSerializable(EsforcoDiario.ESFORCO_DIARIO_SERIAL);
					EsforcoDiarioDAO esforcoDao = new EsforcoDiarioDAO(ResultadoEsforcoDiarioActivity.this);
					
					preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
					
					String emailUsuario = preferences.getString(Constantes.EMAIL_ENVIO_USUARIO, "");
					String emailChefe = preferences.getString(Constantes.EMAIL_CHEFE, "");
					String nrMatricula = preferences.getString(Constantes.MATRICULA_USUARIO, "");
					
					Boolean isEnviado =  esforcoDao.enviarEmailPlanilhaWebService(Util.getUrlWebService("enviarEsforcoDiario"), esforco,emailUsuario, emailChefe, nrMatricula);
					
					if(isEnviado) {
						
						stopBarraProgresso();
						
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(ResultadoEsforcoDiarioActivity.this, "Esforço enviado com sucesso!", Toast.LENGTH_LONG).show();
							}
						});
						
					}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == RESULT_CADASTRO_EMAIL){
			try {
				enviarEsforcoDiario();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
	
}
