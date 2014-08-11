package nvt.com.location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddressLocation {
	private static String readContentURL(String latitude, String longitude){
		String link = String.format("http://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&sensor=false",latitude, longitude);
		try {
			URL url = new URL(link);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setReadTimeout(300*1000);			
			InputStream is = con.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String line  = "";
			StringBuilder sb = new StringBuilder();
			while((line = reader.readLine())!=null){
				sb.append(line);
			}
			return sb.toString().trim();
		} catch (MalformedURLException e) {			
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static String getAddress(String latitude, String longitude){
		String address = "";
		String contentURL = readContentURL(latitude,longitude);
		if(contentURL==null){
			return null;
		}
		
		try {
			JSONObject jsonObject = new JSONObject(contentURL);
			JSONArray jsonArray = jsonObject.optJSONArray("results");
			if(jsonArray==null){
				return null;
			}
			
			String addressTemp = "";
			for(int i = 0;i<jsonArray.length();i++){
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				
				if(i==0){
					addressTemp = jsonObject2.getString("formatted_address");
				}
				
				JSONArray jsonArrayTypes = jsonObject2.getJSONArray("types");
				if(!jsonArrayTypes.toString().matches(".*route.*|.*street_address.*")){
					continue;
				}
				
				address = jsonObject2.getString("formatted_address");
				break;
			}
			
			if(address.length()==0){
				address = addressTemp;
			}
			address = address.replaceAll(", Việt nam.*|, Vietnam.*|, Việt Nam.*|, việt Nam.*", "");
			
			return address;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
