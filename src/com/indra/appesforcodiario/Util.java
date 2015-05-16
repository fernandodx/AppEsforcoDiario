package com.indra.appesforcodiario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

	public static String formatarData(Calendar data) {
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatador.format(data.getTime());
	}
	
	public static String formatarSomenteData(Calendar data) {
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(data.getTime());
	}
	
	public static String formatarData(Date data) {
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatador.format(data.getTime());
	}
	
	public static String formatarTempo(Calendar tempo) {
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
		return formatador.format(tempo.getTime());
	}
	
	public static String formatarDataParaBanco(Calendar data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(data.getTime());
	}
	
	public static Calendar StringBancoToDate(String dataStr) {
		Calendar data = Calendar.getInstance();
		try {
			SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			data.setTime(formatador.parse(dataStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static Calendar somarTempo(String tempoStr, Calendar totalGeralTempo) {
		
		String totalFormatado = null;
		
		if(tempoStr.length() < 8){
			totalFormatado = "00:"+tempoStr;
		}else{
			totalFormatado = tempoStr;
		}
		
		String[] tempo = totalFormatado.split(":");
		
		if(!tempo[0].equals("00")){
			totalGeralTempo.add(Calendar.HOUR, Integer.parseInt(tempo[0]));
		}
		if(!tempo[1].equals("00")){
			totalGeralTempo.add(Calendar.MINUTE, Integer.parseInt(tempo[1]));		
		}
		if(!tempo[2].equals("00")){
			totalGeralTempo.add(Calendar.SECOND, Integer.parseInt(tempo[2]));
		}
		return totalGeralTempo;
	}
	
	public static String getUrlWebService(String funcao) {
		return Constantes.URL_WEB_SERVICE + funcao;
	}
	
}
