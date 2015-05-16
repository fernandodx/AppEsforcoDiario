package com.indra.appesforcodiario.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.indra.appesforcodiario.R;
import com.indra.appesforcodiario.adapters.OrdemServicoComboAdapter;
import com.indra.appesforcodiario.adapters.TipoAtividadeComboAdapter;
import com.indra.appesforcodiario.application.service.GCMApplication;
import com.indra.appesforcodiario.util.GMailSender;
import com.indra.dao.EsforcoDiarioDAO;
import com.indra.dao.OrdemServicoDAO;
import com.indra.dao.ServiceDAO;
import com.indra.dao.TipoAtividadeDAO;
import com.indra.entity.EsforcoDiario;
import com.indra.entity.OrdemServico;
import com.indra.entity.TipoAtividade;

public class MainActivity extends ActionBarActivity {

	private static final int CRONOMETRO_INICIO = 1;
	private static final int CRONOMETRO_FIM = 2;
	private static final int RESULT_ADD_OS = 1000;
	private static final int RESULT_STOP_ATIVIDADE = 1001;
	
	private ImageButton btAddOrdemServico;
	private ImageButton btStartStopAtividade;
	private Button btVisualizarHistorico;
	private Button btVisualizarTotalHistorico;
	private Chronometer timer;
	private Spinner listaOrdemServicoCombo;
	private List<OrdemServico> listaOrdemServico;
	private Spinner listaTipoAtividadeCombo;
	private List<TipoAtividade> listaTipoAtividade;
	private GoogleCloudMessaging gcm;
	private ProgressDialog barraProgresso;
	private boolean isAtividadeIniciada;
	
	private EsforcoDiario esforcoDiario = new EsforcoDiario();
	
	private Handler handler = new Handler();
	
	private void inicializarComponentes() {
		btAddOrdemServico = (ImageButton) findViewById(R.id.btAddOrdemServico);
		btStartStopAtividade = (ImageButton) findViewById(R.id.btIniciarAtividade);
		timer = (Chronometer) findViewById(R.id.timer);
		listaOrdemServicoCombo = (Spinner) findViewById(R.id.listaOrdemServicoCombo);
		listaTipoAtividadeCombo = (Spinner) findViewById(R.id.listaTipoAtividadeCombo);
		btVisualizarHistorico = (Button) findViewById(R.id.btVisualizarHistorico);
		btVisualizarTotalHistorico = (Button) findViewById(R.id.btVisualizarTotalHistorico);
		
		barraProgresso = new ProgressDialog(MainActivity.this);
	}
	
	private void showBarraProgresso() {
		barraProgresso.setMessage("Carregando...");
		barraProgresso.setIndeterminate(true);
		barraProgresso.setCancelable(false);
		barraProgresso.show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(getIntent().getExtras() != null 
				&& getIntent().getExtras().getSerializable(getString(R.string.cronometro_handler_inicio)) != null) {
			
			esforcoDiario = (EsforcoDiario) getIntent().getExtras().getSerializable(getString(R.string.cronometro_handler_inicio));
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				GMailSender gmail = new GMailSender("nando.djx@gmail.com", "roller2836");
				try {
					gmail.sendMail("Assunto Teste1", "1234456fdsfsdfsdf7786867", "nando.djx@gmail.com", "fernandodx@hotmail.com");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
		
		if(getIntent().getExtras() != null 
				&& getIntent().getExtras().getBoolean(EsforcoDiario.IS_ESFORCO_INICIADO)) {
			isAtividadeIniciada = getIntent().getExtras().getBoolean(EsforcoDiario.IS_ESFORCO_INICIADO);
		}
		
		if(ActivityUtil.checkPlayServices(this)){
			
			SharedPreferences preferences = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
			
			GCMApplication gcmApp = new GCMApplication(this, gcm, MainActivity.class, preferences);
			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
			String registroId = gcmApp.getRegistroId();
			
			if(registroId.isEmpty()){
				gcmApp.resgistroInBackground();
				
			}
			
		}
		
		inicializarComponentes();
	//	iniciarAlarmAtualizarListaOrdemServico();

		ServiceDAO dao = new ServiceDAO(this);
		
		atualizarCronometro();
//		showBarraProgresso();
//		carregarListaOrdemServicoCombo();
//		carregarListaAtividadeCombo();
		
		if(!isAtividadeIniciada){
			btStartStopAtividade.setImageDrawable(getResources().getDrawable(R.drawable.play64x));
		}else{
			btStartStopAtividade.setImageDrawable(getResources().getDrawable(R.drawable.stop64x));
		}
		
		
		btAddOrdemServico.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaAddOrdemServico = new Intent(MainActivity.this, CadastrarOrdemServicoActivity.class);
				startActivityForResult(telaAddOrdemServico, RESULT_ADD_OS);
			}
		});
		
		btStartStopAtividade.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isAtividadeIniciada){
					finalizarAtividade();
					listaOrdemServicoCombo.setEnabled(true);
					listaOrdemServicoCombo.setClickable(true);
					listaTipoAtividadeCombo.setEnabled(true);
					listaTipoAtividadeCombo.setClickable(true);
					btAddOrdemServico.setEnabled(true);
					btAddOrdemServico.setClickable(true);
					
				}else{
					iniciarAtividade();
					listaOrdemServicoCombo.setEnabled(false);
					listaOrdemServicoCombo.setClickable(false);
					listaTipoAtividadeCombo.setEnabled(false);
					listaTipoAtividadeCombo.setClickable(false);
					btAddOrdemServico.setEnabled(false);
					btAddOrdemServico.setClickable(false);
				}
			}
		});
		
//		btPausarAtividade.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				timer.stop();
//				esforcoDiario.setTempoPausado(timer.getBase() - SystemClock.elapsedRealtime());
//				esforcoDiario.setIsPausado(true);
//				stopService(new Intent(getString(R.string.phone_service)));
//				
//				Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//				vibrator.vibrate(500);
//			}
//		});
		
		
		
		btVisualizarHistorico.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent telaHistorico = new Intent(MainActivity.this, ResultadoEsforcoDiarioActivity.class);
				startActivity(telaHistorico);
			}
		});
		
		btVisualizarTotalHistorico.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent telaTotalizadores = new Intent(MainActivity.this, ResultadoTotalEsforcoDiarioActivity.class);
				startActivity(telaTotalizadores);
			}
		});
		
	}
	
	private void iniciarAtividade() {
		
		isAtividadeIniciada = true;
		
		if(esforcoDiario.getIsPausado()){
			timer.setBase(SystemClock.elapsedRealtime() + esforcoDiario.getTempoPausado());
			
			Intent timerService = new Intent(getString(R.string.phone_service));
			timerService.putExtra(EsforcoDiario.ESFORCO_DIARIO_SERIAL, esforcoDiario);
			timerService.putExtra(EsforcoDiario.IS_ESFORCO_INICIADO, isAtividadeIniciada);
			
			startService(timerService);
			timer.start();
			
			esforcoDiario.setIsPausado(false);
			esforcoDiario.setTempoPausado(null);
			
		}else{
			
			timer.setBase(SystemClock.elapsedRealtime());
			timer.start();
			
			OrdemServico os = (OrdemServico) listaOrdemServicoCombo.getSelectedItem();
			TipoAtividade tipo = (TipoAtividade) listaTipoAtividadeCombo.getSelectedItem();
			
			esforcoDiario.setDtInicioAtividade(Calendar.getInstance());
			esforcoDiario.setOrdemServico(os);
			esforcoDiario.setTipoAtividade(tipo);
			esforcoDiario.setTimerContador(timer.getBase());
			
			Intent timerService = new Intent(getString(R.string.phone_service));
			timerService.putExtra(EsforcoDiario.ESFORCO_DIARIO_SERIAL, esforcoDiario);
			timerService.putExtra(EsforcoDiario.IS_ESFORCO_INICIADO, isAtividadeIniciada);
			
			startService(timerService);
		}
		
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(500);
		
		btStartStopAtividade.setImageDrawable(getResources().getDrawable(R.drawable.stop64x));
		
	}
	
	private void finalizarAtividade() {
		try{
			
			timer.stop();
			timer.setBase(timer.getBase());
			
			OrdemServico os = (OrdemServico) listaOrdemServicoCombo.getSelectedItem();
			TipoAtividade tipo = (TipoAtividade) listaTipoAtividadeCombo.getSelectedItem();
			
			EsforcoDiario esforco = new EsforcoDiario();
			esforco.setOrdemServico(os);
			esforco.setTipoAtividade(tipo);
			esforco.setDtInicioAtividade(esforcoDiario.getDtInicioAtividade());
			esforco.setDtFimAtividade(Calendar.getInstance());
			esforco.setTempoGasto(timer.getText().toString());
			
			esforcoDiario.setDtFimAtividade(esforco.getDtFimAtividade());
			esforcoDiario.setTimerContador(timer.getBase());
			esforcoDiario.setTempoGasto(esforco.getTempoGasto());
			EsforcoDiarioDAO esforcoDao = new EsforcoDiarioDAO(MainActivity.this);
			
			esforcoDao.incluirEsforcoDiario(esforco);
			
			Intent stopServicoAtividade = new Intent(getString(R.string.phone_service));
			stopServicoAtividade.putExtra(EsforcoDiario.ESFORCO_DIARIO_SERIAL, esforcoDiario);
			stopService(stopServicoAtividade);
			
			Intent telaResultado = new Intent(MainActivity.this, ResultadoEsforcoDiarioActivity.class);
			telaResultado.putExtra(EsforcoDiario.ESFORCO_DIARIO_SERIAL, esforcoDiario);
			startActivityForResult(telaResultado, RESULT_STOP_ATIVIDADE);
			
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(500);
			
			btStartStopAtividade.setImageDrawable(getResources().getDrawable(R.drawable.play64x));
			isAtividadeIniciada = false;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    ActivityUtil.checkPlayServices(this);
	    System.out.println("_____ Resume ______ ");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void atualizarCronometro() {
		Handler handler = new Handler() {
			
			@Override
			public void handleMessage(Message msg){
				if(msg.what == CRONOMETRO_INICIO){
				
					EsforcoDiario esforco = (EsforcoDiario) msg.getData().getSerializable(getString(R.string.cronometro_handler_inicio));
					timer.setBase(esforco.getTimerContador());
					timer.start();
				
				}else if(msg.what == CRONOMETRO_FIM){
				
					EsforcoDiario esforco = (EsforcoDiario) msg.getData().getSerializable(getString(R.string.cronometro_handler_final));
					timer.setBase(SystemClock.elapsedRealtime() + esforco.getTimerContador());
					
					
				}
			}
			
		};
		
		if(getIntent() != null && getIntent().getExtras() != null) {
			Message msg = new Message();
			if(getIntent().getExtras().getSerializable(getString(R.string.cronometro_handler_inicio)) != null){
				msg.what = CRONOMETRO_INICIO;
			}else if(getIntent().getExtras().getSerializable(getString(R.string.cronometro_handler_final)) != null){
				msg.what = CRONOMETRO_FIM;
			}
			msg.setData(getIntent().getExtras());
			handler.sendMessage(msg);
		}
	}
	
	private void carregarListaOrdemServicoCombo() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					OrdemServicoDAO dao = new OrdemServicoDAO(MainActivity.this);
					Set<OrdemServico> listaUnificada = new TreeSet<OrdemServico>();
					
					listaUnificada.addAll(dao.consultarOrdemServicoWebService());
					listaUnificada.addAll(dao.consultarTodos());
					
					listaOrdemServico = new ArrayList<OrdemServico>();
					listaOrdemServico.addAll(listaUnificada);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						
						listaOrdemServicoCombo.setAdapter(new OrdemServicoComboAdapter(listaOrdemServico, MainActivity.this));
					}
				});
				
			}
		}).start();
	}
	
	private void carregarListaAtividadeCombo() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					TipoAtividadeDAO dao = new TipoAtividadeDAO(MainActivity.this);
					
					listaTipoAtividade = dao.consultarTipoAtividadeWeb();
					
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							listaTipoAtividadeCombo.setAdapter(new TipoAtividadeComboAdapter(listaTipoAtividade, MainActivity.this));
							barraProgresso.dismiss();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	
	
	public void iniciarAlarmAtualizarListaOrdemServico() {
		Intent atualizarLista = new Intent(getString(R.string.atualizar_ordem_servico));
		
		PendingIntent pedido = PendingIntent.getBroadcast(MainActivity.this, 0, atualizarLista, 0);
		
		Calendar dataTime = Calendar.getInstance();
		dataTime.setTimeInMillis(System.currentTimeMillis());
		dataTime.set(Calendar.SECOND, 5);
		
		long contagemTempoParaExecucao = 0;
		long tempoParaRepetirRotina = AlarmManager.INTERVAL_DAY;
		
		AlarmManager alarmeRotina = (AlarmManager) getSystemService(ALARM_SERVICE);
	//	alarmeRotina.set(AlarmManager.RTC_WAKEUP, dataTime.getTimeInMillis(), pedido);
		alarmeRotina.setRepeating(AlarmManager.RTC_WAKEUP, contagemTempoParaExecucao,tempoParaRepetirRotina, pedido);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		
			case RESULT_ADD_OS:{
				carregarListaOrdemServicoCombo();
				break;
			}
			
			case RESULT_STOP_ATIVIDADE: {
				timer.stop();
				timer.setBase(SystemClock.elapsedRealtime());
				timer.refreshDrawableState();
				break;
			}
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("PAUSADO ______ ");
	}
	
}
