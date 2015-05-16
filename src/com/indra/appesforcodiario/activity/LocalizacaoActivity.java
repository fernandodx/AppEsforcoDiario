package com.indra.appesforcodiario.activity;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.indra.appesforcodiario.R;

public class LocalizacaoActivity extends BaseActivity implements LocationListener {

	private GoogleMap map;
	private LocationManager localizacaoGps;
	
	
	@Override
	protected void inicializarComponentes() {

		localizacaoGps = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		
		if(localizacaoGps.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			//GPS ATIVO
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
		
		}else{
			//GPS INATIVO
			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		}
		
		String provider = localizacaoGps.getBestProvider(criteria, false);
		Location local = localizacaoGps.getLastKnownLocation(provider);
		
		android.location.LocationListener listener = new android.location.LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				
				
			}
			
			@Override
			public void onLocationChanged(Location localizacao) {
				LatLng localAtual = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
				Marker ponto =  map.addMarker(new MarkerOptions().position(localAtual).title("Local"));
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(localAtual, 17));
				map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
			}
		};
		
		
		localizacaoGps.requestLocationUpdates(provider, 100, 1, listener);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapaGoogle)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		if(local != null){
			final LatLng iesb = new LatLng(local.getLatitude()	, local.getLongitude());
			Marker ponto =  map.addMarker(new MarkerOptions().position(iesb).title("Iesb - ASA SUL"));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(iesb, 16));
			map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_mapa_localizacao);
		inicializarComponentes();
	}

	@Override
	public void onLocationChanged(Location localizacao) {
		LatLng localAtual = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
		Marker ponto =  map.addMarker(new MarkerOptions().position(localAtual).title("Iesb - ASA SUL"));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(localAtual, 17));
		map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
		
	}
	
	

}
