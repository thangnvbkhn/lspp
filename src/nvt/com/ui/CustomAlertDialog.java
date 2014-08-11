package nvt.com.ui;

import nvt.com.activity.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class CustomAlertDialog extends Dialog{
	
	public CustomAlertDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_alert_dialog);
		
//		getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		setCancelable(true);
	}
}
