package nvt.com.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionManager {
	static ConnectivityManager connectivityManager;
	static NetworkInfo networkInfo;
	static boolean connected = false;
	
	public static boolean isOnline(Context context){
		connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfo = connectivityManager.getActiveNetworkInfo();
		connected = networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected();
		return connected;
	}	
}
