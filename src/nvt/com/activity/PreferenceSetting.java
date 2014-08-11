package nvt.com.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.Window;

public class PreferenceSetting extends PreferenceActivity {
	private Context context;
	private Preference about_pref, version_pref, more_pref;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		this.context = this;
		about_pref = (Preference) findPreference("prefsAbout");
		version_pref = (Preference) findPreference("prefsVersion");
		more_pref = (Preference) findPreference("prefsMoreFromSIG");

		about_pref
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference arg0) {
						showDialog(arg0.getTitle().toString(), arg0
								.getSummary().toString());
						return true;
					}
				});

		version_pref
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference arg0) {
						showDialog(arg0.getTitle().toString(), arg0
								.getSummary().toString());
						return true;
					}
				});

		more_pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle(arg0.getTitle().toString());
				builder.setMessage(arg0.getSummary().toString());
				builder.setCancelable(false);				
				builder.setPositiveButton(R.string.thongbao_exit,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				
				builder.setNegativeButton(R.string.thongbao_go, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.cancel();
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse("market://search?q=pub:Semantic Innovation Group"));
						startActivity(i);
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
				return true;
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	private void showDialog(String title, String mesg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(mesg);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.thongbao_exit,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
