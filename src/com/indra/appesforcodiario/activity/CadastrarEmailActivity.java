package com.indra.appesforcodiario.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.indra.appesforcodiario.Constantes;
import com.indra.appesforcodiario.R;


public class CadastrarEmailActivity extends BaseActivity{

	private Button btEnviarEmail;
	private TextView txEmailUsuario;
	private TextView txEmailChefe;
	private TextView txNrMatricula;
	
	
	@Override
	protected void inicializarComponentes() {
		
		btEnviarEmail = (Button) findViewById(R.id.btEnviarEmail);
		txEmailUsuario = (TextView) findViewById(R.id.txEmail);
		txEmailChefe = (TextView) findViewById(R.id.txEmailChefe);
		txNrMatricula = (TextView) findViewById(R.id.txMatricula);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_cadastro_email);
		inicializarComponentes();
		
		btEnviarEmail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SharedPreferences preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString(Constantes.EMAIL_ENVIO_USUARIO, txEmailUsuario.getText().toString());
				editor.putString(Constantes.EMAIL_CHEFE, txEmailChefe.getText().toString());
				editor.putString(Constantes.MATRICULA_USUARIO, txNrMatricula.getText().toString());
				editor.commit();
				
				finish();
			}
		});
		
		
	}
	
	
	
	
}
