package nvt.com.ui;

import nvt.com.activity.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class TransparentProgressDialog extends Dialog {

	private ImageView iv;
	private TextView tv;
	private String message;
	private int color;
	private boolean cancelable = false;
	
	public TransparentProgressDialog(Context context, String message) {
		super(context, R.style.TransparentProgressDialog);
		this.message = message;
		this.color = Color.RED;
		
		WindowManager.LayoutParams wlmp = getWindow().getAttributes();
		wlmp.gravity = Gravity.CENTER_HORIZONTAL;
		getWindow().setAttributes(wlmp);
		setCancelable(cancelable);
		setOnCancelListener(null);

		setContentView(R.layout.custom_dialog_progress);
		iv = (ImageView) findViewById(R.id.image);
	}
	
	public boolean isCancelable() {
		return cancelable;
	}

	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
		tv = (TextView) findViewById(R.id.text);
		if(tv.getVisibility()==View.INVISIBLE){
			tv.setVisibility(View.VISIBLE);
			tv.setText(message);
		}
	}

	

	public int getColor() {
		return color;
	}


	public void setColor(int color) {
		this.color = color;
	}


	@Override
	public void show() {
		super.show();
		tv = (TextView) findViewById(R.id.text);
		if (this.message != null) {
			tv.setText(this.message);
			tv.setTextColor(color);
			tv.setTypeface(Typeface.DEFAULT_BOLD);
			tv.setTextSize(15);
		}else{
			tv.setVisibility(View.GONE);
		}
		
		RotateAnimation anim = new RotateAnimation(0.0f, 360.0f,
				Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF,
				.5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(3000);
		iv.setAnimation(anim);
		iv.startAnimation(anim);
	}
}
