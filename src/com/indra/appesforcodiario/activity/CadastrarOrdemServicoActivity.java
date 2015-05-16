package com.indra.appesforcodiario.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.indra.appesforcodiario.R;
import com.indra.dao.OrdemServicoDAO;
import com.indra.entity.OrdemServico;

public class CadastrarOrdemServicoActivity extends BaseActivity {

	private TextView txNrOrdemServico;
	private TextView txNmOrdemServico;
	private Button btCadastrar;
	private Button btLimpar;
	
	
	@Override
	protected void inicializarComponentes() {
		txNrOrdemServico = (TextView) findViewById(R.id.txDtFimOrdemServico);
		txNmOrdemServico = (TextView) findViewById(R.id.txNmOrdemServico);
		btCadastrar = (Button) findViewById(R.id.btCadastrarOrdemServico);
		btLimpar = (Button) findViewById(R.id.btLimparOrdemServico);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastrar_ordem_servico);
		inicializarComponentes();
		
		btCadastrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				OrdemServico os = new OrdemServico();
				os.setNrOrdemServico(Integer.valueOf(txNrOrdemServico.getText().toString()));
				os.setNmOrdemServico(txNmOrdemServico.getText().toString());
				
				OrdemServicoDAO dao = new OrdemServicoDAO(CadastrarOrdemServicoActivity.this);
				dao.incluirOrdemServico(os);
				
				finish();
			}
		});
		
		
		btLimpar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				txNmOrdemServico.setText("");
				txNrOrdemServico.setText("");
			}
		});
		
	}


	
	
}
