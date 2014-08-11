package nvt.com.activity;

import java.util.ArrayList;
import java.util.Locale;

import nvt.com.adapter.PlacesAutoCompleteAdapter;
import nvt.com.connection.ConnectionManager;
import nvt.com.database.DatabaseHelper;
import nvt.com.gps.GPSTracker;
import nvt.com.location.AddressLocation;
import nvt.com.object.Duong;
import nvt.com.thread.ThreadPlace;
import nvt.com.ui.TransparentProgressDialog;
import nvt.com.utils.AccentCharacter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends SherlockFragmentActivity implements
		OnMapClickListener, OnMapLongClickListener {

	final CharSequence[] items = {
			"Vietnamese",
			"English" };
	private final Context context = this;
	private GoogleMap map;
	private TransparentProgressDialog progressDialog;
//	private CustomAlertDialog alertDialog; // dung de thong bao exit, chon point
	private AlertDialog alert = null; // dung de chon ngon ngu
	private GPSTracker gpsTracker;

	private LatLng position;
	private LatLng myLocation;

	private DatabaseHelper db;

	private PlacesAutoCompleteAdapter adapter;
	private ArrayList<Duong> duong_list = new ArrayList<Duong>();

	private String nameSearch, latitude, longitude;
	private AutoCompleteTextView autoCompleteTextView;
	private ImageView imgSearch;
//	private TextView textView;

	private AdView adView;
	private LinearLayout layoutAdView;
	
	private ThreadPlace thread;
	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			// dùng để trả về kết quả của việc search
			case 2:
				thread.stopThread();
				Duong street = (Duong) msg.obj;
				if (street.getId() == 0) {
					Toast.makeText(getApplicationContext(),
							R.string.thongbao_khong_tim_thay_ket_qua,
							Toast.LENGTH_LONG).show();
				} else {
					latitude = street.getLatitude();
					longitude = street.getLongitude();
					String name = street.getName();
					Double lat = Double.parseDouble(latitude);
					Double lng = Double.parseDouble(longitude);

					position = new LatLng(lat, lng);
					Marker marker = map.addMarker(new MarkerOptions().position(
							position).title(name));

					marker.showInfoWindow();

					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(position).tilt(30).zoom(15).build();

					map.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
				} 
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		layoutAdView = (LinearLayout) findViewById(R.id.adView);
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(getString(R.string.adunitid));
		
		// create an ad request
		AdRequest adRequest = new AdRequest.Builder()
				//.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				//.addTestDevice("2994B7BBDF0E00F50916DEE3DAFAD4BC")
				.build();

		adView.loadAd(adRequest);
		layoutAdView.addView(adView);
		
//		textView = (TextView) findViewById(R.id.textview_intro_activity_main);
		progressDialog = new TransparentProgressDialog(this,null);
		progressDialog.setCancelable(true);
//		alertDialog = new CustomAlertDialog(context, R.style.CenterDialog);
		
//		Animation anim = AnimationUtils.loadAnimation(this,R.anim.slide_top_intro);
//		textView.startAnimation(anim);

		thread = new ThreadPlace(this, handler);
		db = new DatabaseHelper(this);
		gpsTracker = new GPSTracker(this);
		
		adapter = new PlacesAutoCompleteAdapter(this,android.R.layout.simple_list_item_1, duong_list);
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				duong_list = db.getAllDuong();
				adapter.addAll(duong_list);
			}
		});
		t.start();

		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_tv_activity_main);
		imgSearch = (ImageView) findViewById(R.id.img_search_activity_main);

		SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		map = supportMapFragment.getMap();
		map.setMyLocationEnabled(true);

		autoCompleteTextView.setAdapter(adapter);
		
		map.setOnMapClickListener(this);
		map.setOnMapLongClickListener(this);

		imgSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {				
				if (autoCompleteTextView.getText().length() > 0) {
					map.clear();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(
							autoCompleteTextView.getWindowToken(), 0);

					nameSearch = autoCompleteTextView.getText().toString().trim();
					nameSearch = removeAccent(nameSearch).trim();
					ThreadPlace thread = new ThreadPlace(MainActivity.this,
							handler, nameSearch, 2);
					thread.start();
					progressDialog.setMessage("Getting driving directions");
				} else {
					// Thong bao : Ban Nhap tu khoa
					Toast.makeText(getApplicationContext(),
							R.string.thongbao_ban_nhap_tu_khoa,
							Toast.LENGTH_LONG).show();
				}
			}
		});

		map.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {
				View v = getLayoutInflater().inflate(
						R.layout.layout_info_window, null);
				TextView note = (TextView) v
						.findViewById(R.id.layout_info_window_address);

				note.setText(marker.getTitle());
				return v;
			}
		});

		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				boolean flag = false;

				String markerTitle = marker.getTitle();
				if (markerTitle.length() > 0 && !markerTitle.contains(",")) {
					String nameEn = removeAccent(markerTitle);
					if (db != null) {
						db = new DatabaseHelper(MainActivity.this);
					}

					Duong d = db.getDuongByNameKhongDau(nameEn);
					if (d != null) {
						int id_duong = d.getId();
						String name = d.getName();
						Bundle extras = new Bundle();
						extras.putInt("id_duong", id_duong);
						extras.putString("name", name);
						Intent i = new Intent(MainActivity.this,
								ShowDescriptionActivity.class);
						i.putExtras(extras);
						startActivityForResult(i, 1);

						flag = true;
					}

					if (!flag) {
						Toast.makeText(MainActivity.this,
								R.string.thongbao_chua_co_du_lieu,
								Toast.LENGTH_LONG).show();
					}
				} else if (markerTitle.contains(",")) {
					String str[] = markerTitle.split(",");

					for (String s : str) {
						s = removeAccent(s);
						s = s.replaceAll("[0-9]*", "");
						// ví dụ : hầm Kim Liên
						s = s.replaceAll("ham", "").trim().toLowerCase();

						if (db != null) {
							db = new DatabaseHelper(MainActivity.this);
						}

						Duong d = db.getDuongByNameKhongDau(s);
						if (d != null) {
							int id_duong = d.getId();
							String name = d.getName();
							Bundle extras = new Bundle();
							extras.putInt("id_duong", id_duong);
							extras.putString("name", name);
							Intent i = new Intent(MainActivity.this,
									ShowDescriptionActivity.class);
							i.putExtras(extras);
							startActivityForResult(i, 1);

							flag = true;
							break;
						}
					}

					if (flag == false) {
						Toast.makeText(MainActivity.this,
								R.string.thongbao_chua_co_du_lieu,
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		if (ConnectionManager.isOnline(this)) {
			double latitude = gpsTracker.getLatitude();
			double longitude = gpsTracker.getLongitude();

			if (latitude == 0.0 || longitude == 0.0) {
				Toast.makeText(MainActivity.this,
						R.string.thongbao_khong_xac_dinh_duoc_vi_tri,
						Toast.LENGTH_LONG).show();
				latitude = Double.valueOf("21.032276");
				longitude = Double.valueOf("105.849638");
			}
			myLocation = new LatLng(latitude, longitude);

			// dùng để hiển thị marker của tọa độ của mình
			new DownloadTask().execute(myLocation);
		} else {
			gpsTracker.showSettingAlert();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				int id = extras.getInt("idDuong");
				if (db != null) {
					db = new DatabaseHelper(this);
					Duong d = db.getDuongById(id);
					if (d != null) {
						String lat = d.getLatitude();
						String lng = d.getLongitude();
						position = new LatLng(Double.valueOf(lat),Double.valueOf(lng));
						new DownloadTask().execute(position);
					}
				}
			}
		}
	}

	public void setCenterMarker(GoogleMap map, Marker marker) {
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(
				marker.getPosition(), 15));
	}
		
	// return address from latitude and longitude
	private class DownloadTask extends AsyncTask<LatLng, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.setMessage(getString(R.string.thongbao_loading));
			progressDialog.show();
		}

		@Override
		protected String doInBackground(LatLng... latlng) {
						
			String address = "";

			// đây là vị trí dùng để đặt marker
			position = new LatLng(latlng[0].latitude, latlng[0].longitude);

			try {
				String lat = String.valueOf(latlng[0].latitude);
				String lng = String.valueOf(latlng[0].longitude);
				address = AddressLocation.getAddress(lat, lng);
			} catch (Exception e) {
				Log.d("thang", e.toString());
			}
			return address;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progressDialog.dismiss();

			if(result==null){
				Toast.makeText(MainActivity.this,getString(R.string.notify_no_connection), Toast.LENGTH_LONG).show();
				return;
			}
			
			map.clear();
			if (result.length() > 0) {
				Marker marker = map
						.addMarker(new MarkerOptions()
								.position(position)
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
								.title(result));

				marker.showInfoWindow();
				setCenterMarker(map, marker);
			} else {
				Marker marker = map
						.addMarker(new MarkerOptions()
								.position(position)
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
								.title("Try again please"));
				marker.showInfoWindow();
				setCenterMarker(map, marker);

				Toast.makeText(MainActivity.this, "Request time out",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * Loại bỏ dấu tiếng việt
	 * 
	 * @param str
	 * @return
	 */
	public String removeAccent(String str) {
		String s = AccentCharacter.remove(str);
		return s;
	}

	@Override
	public void onMapLongClick(LatLng point) {
		position = point;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.thongbao_chon_dia_diem_nay_khong);
		builder.setCancelable(true);
		builder.setPositiveButton(R.string.thongbao_co, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new DownloadTask().execute(position);
			}
		});
		builder.setNegativeButton(R.string.thongbao_khong, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert = builder.create();
		alert.show();
	}
	
	@Override
	public void onMapClick(LatLng point) {
		position = point;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.thongbao_chon_dia_diem_nay_khong);
		builder.setCancelable(true);
		builder.setPositiveButton(R.string.thongbao_co, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				new DownloadTask().execute(position);
			}
		});
		builder.setNegativeButton(R.string.thongbao_khong, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert = builder.create();
		alert.show();
	}

	@Override
	public void onBackPressed() {
		showAlertExit();
	}

	public void showAlertExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.thongbao_ban_co_muon_thoat_khong);
		builder.setCancelable(true);
		builder.setPositiveButton(R.string.thongbao_co, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		});
		builder.setNegativeButton(R.string.thongbao_khong, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert = builder.create();
		alert.show();
	}
	
	public void showAlertChoiceLanguage() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.thongbao_choice_language);
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			Configuration configuration = new Configuration(getResources().getConfiguration());
			
			public void onClick(DialogInterface dialog, int which) {
				switch(which){				
				case 0:
					Locale locale = new Locale("vi");
					configuration.locale = locale;
					break;
				case 1:
					configuration.locale = Locale.ENGLISH;
					break;
				}
				getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
				alert.dismiss();
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
		});
		
		alert = builder.create();
		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {			
		getSupportMenuInflater().inflate(R.menu.menu_activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			Intent i = new Intent(MainActivity.this, PreferenceSetting.class);
			startActivity(i);
			return true;
//		case R.id.menu_language:
//			showAlertChoiceLanguage();
//			return true;
		case R.id.menu_exit:
			showAlertExit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
