package nvt.com.activity;

import nvt.com.connection.ConnectionManager;
import nvt.com.database.DatabaseHelper;
import nvt.com.ui.TransparentProgressDialog;
import nvt.com.utils.DatabaseUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

public class SplashScreen extends Activity {
	public Runnable onStop;
	static boolean isNetwork = false;
	static boolean isWaitingNetwork = false;
	boolean isResume = false;
	DatabaseHelper db;
	TransparentProgressDialog dialog;
	DatabaseUtils databaseUtils;

	public Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);		
		
		databaseUtils = new DatabaseUtils(this);
		db = new DatabaseHelper(this);
		this.context = this;
		dialog = new TransparentProgressDialog(this,null);
		
		if (!ConnectionManager.isOnline(getBaseContext())) {
			showAlert();
		} else {
			if (databaseUtils.checkExistFolder("db")) {				
				Thread t = new Thread(				
				new Runnable() {
					
					@Override
					public void run() {
						try {
							while(true){
								Thread.sleep(3000);
								break;
							}
							Intent i = new Intent(SplashScreen.this, MainActivity.class);
							startActivity(i);
							finish();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
				t.start();
			} else {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						new DatabaseTask().execute("data.zip");
					}
				});
			}
		}
	}

	public class DatabaseTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			dialog.show();
		}

		@Override
		protected Void doInBackground(String... arg0) {
			databaseUtils.initDatabase(arg0[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Intent i = new Intent(SplashScreen.this, MainActivity.class);
			startActivity(i);
			dialog.dismiss();
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	public void showAlert() {
		AlertDialog.Builder alBuilder = new AlertDialog.Builder(context);
		alBuilder.setMessage(R.string.notify_no_connection);
		alBuilder.setNegativeButton(R.string.thongbao_exit,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						isWaitingNetwork = false;
						finish();
					}
				});

		alBuilder.setPositiveButton(R.string.thongbao_retry,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						isWaitingNetwork = true;
						if (!ConnectionManager.isOnline(getBaseContext())) {
							showAlert();
						}
						// startActivity(new
						// Intent("android.settings.WIRELESS_SETTINGS"));
					}
				});
		alBuilder.show();
	}

	public void tryAgain() {
		if (!ConnectionManager.isOnline(getBaseContext())) {
			AlertDialog.Builder alBuilder = new AlertDialog.Builder(context);
			alBuilder
					.setMessage(R.string.thongbao_khong_co_ket_noi_mang_try_again);
			alBuilder.setPositiveButton("Try Again",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							isWaitingNetwork = true;
							startActivity(new Intent(
									"android.settings.WIRELESS_SETTINGS"));
						}
					});
			alBuilder.show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}