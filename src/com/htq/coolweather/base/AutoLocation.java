package com.htq.coolweather.base;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class AutoLocation extends Service implements LocationListener{

	private LocationManager locationmanager;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(locationmanager.getProvider(LocationManager.NETWORK_PROVIDER) != null){
			locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		}else if(locationmanager.getProvider(LocationManager.GPS_PROVIDER) != null){
			locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		}else{
			Toast.makeText(this, "无法定位", Toast.LENGTH_SHORT).show();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean stopService(Intent name) {
		return super.stopService(name);
	}

	@Override
	public void onLocationChanged(Location location) {
		//通知Activity
		Intent intent = new Intent();
		intent.setAction("locationAction");
		intent.putExtra("location", location.toString());
		sendBroadcast(intent);
		//移除监听，停止服务
		locationmanager.removeUpdates(this);
		stopSelf();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}
	
}
