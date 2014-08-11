package nvt.com.activity;

import nvt.com.database.DatabaseHelper;
import nvt.com.object.Duong;
import nvt.com.utils.AccentCharacter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class ShowDescriptionActivity extends Activity {
	protected TextView name_tv, des_tv, tv_name_title;
	protected ScrollView scrollView;
	private WebView webView;
	private ImageView img_view_click;

	private int id_duong = 1;
	private String name = "";
	protected String latitude, longitude;
	protected Intent i;
	private DatabaseHelper db;
	private Duong d = new Duong();

	private AdView adView;
	private LinearLayout layoutAdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_description);

		db = new DatabaseHelper(this);

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

		img_view_click = (ImageView) findViewById(R.id.img_view_click);
		img_view_click.setVisibility(View.VISIBLE);
		tv_name_title = (TextView) findViewById(R.id.textview_name_logo);
		webView = (WebView) findViewById(R.id.webview);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setDefaultTextEncodingName("UTF-8");

		Bundle extra = this.getIntent().getExtras();
		id_duong = extra.getInt("id_duong");
		name = extra.getString("name");
		
		if (id_duong == 0) {
			d = db.getDuongByName(name);
		} else {
			d = db.getDuongById(id_duong);
		}

		tv_name_title.setText(name);

		String des = d.getDescription();
		latitude = d.getLatitude();
		longitude = d.getLongitude();

		webView.loadData(formatContent(des), "text/html; charset=UTF-8","UTF-8");
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				String name_en = url.replaceAll("content://", "");

				Duong duong = db.getDuongByNameKhongDau(name_en);

				if (duong != null) {
					tv_name_title.setText(duong.getName());
					showContent(formatContent(duong.getDescription()));
				} else {
					Toast.makeText(ShowDescriptionActivity.this,
							R.string.thongbao_chua_co_du_lieu,
							Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});

		img_view_click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String nameDuong = tv_name_title.getText().toString();
				String nameDuongEn = AccentCharacter.remove(nameDuong)
						.toLowerCase().trim();
				Duong duong = db.getDuongByNameKhongDau(nameDuongEn);

				if (duong != null) {
					int id = duong.getId();
					Bundle extras = new Bundle();
					extras.putInt("idDuong", id);
					Intent i = new Intent(ShowDescriptionActivity.this,
							MainActivity.class);
					i.putExtras(extras);
					setResult(RESULT_OK, i);
					finish();
				} else {
					Toast.makeText(ShowDescriptionActivity.this,
							R.string.thongbao_chua_co_du_lieu,
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();
	}

	public String formatContent(String content) {
		StringBuilder htmlData = new StringBuilder();
		htmlData.append("<html><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n");
		htmlData.append("<body><font face=\"Arial\">");

		htmlData.append(content);

		htmlData.append("</font></body></html>");

		return htmlData.toString();
	}

	public void showContent(String content) {
		if (content != null) {
			webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8",
					"about:blank");
		}
	}
}