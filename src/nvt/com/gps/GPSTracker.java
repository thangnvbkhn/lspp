package nvt.com.gps;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener {

	Context context;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;
	Location location;
	double latitude;
	double longitude;
	static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	LocationManager locationManager;

	public GPSTracker(Context context) {
		this.context = context;		
		getLocation();
	}

	public Location getLocation() {
		locationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        
		if (!isGPSEnabled && !isNetworkEnabled) {
			Log.d("thang","Network not avaible");
			
		} else {
			this.canGetLocation = true;

			// First get location from Network Provider
			if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("Network", "Network");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }

			// if GPS Enabled get lat/lng from GPS Service
			if (isGPSEnabled) {
                if (location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
			}
		}
		return location;
	}

	
	public boolean isCanGetLocation() {
		return canGetLocation;
	}

	public void setCanGetLocation(boolean canGetLocation) {
		this.canGetLocation = canGetLocation;
	}

	public double getLatitude() {
		if(location!=null){
			latitude = location.getLatitude();
		}
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		if(location!=null){
			longitude = location.getLongitude();
		}
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void stopUsingGPS(){
		if(locationManager!=null){
			locationManager.removeUpdates(GPSTracker.this);
		}
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean canGetLocation(){
		return canGetLocation;
	}
	
	public void showSettingAlert(){
		AlertDialog.Builder alBuilder = new AlertDialog.Builder(context);
		alBuilder.setMessage("Không có kết nối mạng");
		alBuilder.setPositiveButton("Cài đặt mạng", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
			}
		});
//		alBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {				
//				dialog.cancel();
//			}
//		});
		alBuilder.show();
	}
	
	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
