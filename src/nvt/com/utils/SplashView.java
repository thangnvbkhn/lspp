package nvt.com.utils;

import java.util.Random;

import nvt.com.activity.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class SplashView extends View {	
	private static final int[][] logoMatrix = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0 },
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1,
					0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0 },
			{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1,
					1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1,
					1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
					1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1,
					1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0 },
			{ 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1,
					1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0 },
			{ 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
					1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0 },
			{ 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1,
					1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0 },
			{ 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1,
					1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

	private int[] color = new int[3];
	private Paint paint = new Paint();

	private int mPixelSize = 5;
	private int mPixelDistance = 10;
	private Handler handle;
	private boolean stopAnim = false;
	private int i = 1;
	private int delta = 1;
	private int t = 0;
	private int rd = 0; // random int
	private int left = 0;
	private int top = 0;

	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	private int xText = 0;
	private int yText = 0;
	private float sizeText = 45.0F;

	// kích thước của ma trận
	private int wMatrix = 35;
	private int hMatrix = 20;
	
	private String nameApp;

	public boolean getStopAnim() {
		return this.stopAnim;
	}

	public SplashView(Context context,String nameApp) {
		super(context);
		this.nameApp = nameApp;
		rd = new Random().nextInt(3);

		handle = new Handler();
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		drawLogo(canvas);
	}

	public void drawLogo(Canvas canvas) {
		paint.setColor(Color.RED);
		paint.setColor(Color.rgb(this.color[0], this.color[1], this.color[2]));

		if (this.i <= 0) {
			stopAnim = true;
			return;
		}
		if (this.rd == 0) {
			for (int q = 1; q <= 35 - (11 - this.i) * 3; q++) {
				for (int p = 1; p <= 20; p++) {
					if (logoMatrix[(p - 1)][(q - 1)] == 1) {
						this.left = (q - 1) * this.mPixelDistance + this.x;
						this.top = (p - 1) * this.mPixelDistance + this.y;
						canvas.drawRect(this.left, this.top, this.left
								+ this.mPixelSize, this.top + this.mPixelSize,
								this.paint);
					}
				}
			}
		} else if (this.rd == 1) {
			for (int q = 1; q <= 35; q++) {
				for (int p = 1; p <= 20 - (11 - this.i) * 2; p++)
					if (logoMatrix[(p - 1)][(q - 1)] == 1) {
						this.left = (q - 1) * this.mPixelDistance + this.x;
						this.top = (p - 1) * this.mPixelDistance + this.y;
						canvas.drawRect(this.left, this.top, this.left
								+ this.mPixelSize, this.top + this.mPixelSize,
								this.paint);
					}
			}
		} else {
			for (int q = 1; q <= 35 - (11 - this.i) * 3; q++) {
				for (int p = 1; p <= 20 - (11 - this.i) * 2; p++) {
					if (logoMatrix[(p - 1)][(q - 1)] == 1) {
						this.left = (q - 1) * this.mPixelDistance + this.x;
						this.top = (p - 1) * this.mPixelDistance + this.y;
						canvas.drawRect(this.left, this.top, this.left
								+ this.mPixelSize, this.top + this.mPixelSize,
								this.paint);
					}
				}
			}
		}

		paint.setTextSize(sizeText);
		paint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(nameApp, this.xText, this.yText, paint);
	}

	public void startAnimation() {
		// start animation
		stopAnim = false;

		this.i = 1;

		// tinh toan size cua screen
		Display display = ((WindowManager) getContext().getSystemService(
				"window")).getDefaultDisplay();
		this.width = display.getWidth();
		this.height = display.getHeight();

		int sizeLarge = width >= height ? width : height;
		if (sizeLarge >= 480) {
			// this.mPixelDistance = 8;
			// this.mPixelSize = 4;
			this.sizeText = 35.0F;
		} else if (sizeLarge >= 320) {
			// this.mPixelDistance = 6;
			// this.mPixelSize = 3;
			this.sizeText = 28.0F;
		} else {
			// this.mPixelDistance = 4;
			// this.mPixelSize = 2;
			this.sizeText = 22.0F;
		}

		x = 20;

		mPixelDistance = (width - x * 2) / (wMatrix - 1);
		mPixelSize = mPixelDistance / 2;

		y = (height - hMatrix * mPixelDistance) / 2;

		this.xText = width / 2;
		this.yText = (int) (height * 0.75 - sizeText / 2) + 20;

		this.left = this.x;
		this.top = this.y;

		runAnimation();
	}

	public void runAnimation() {
		if (this.stopAnim)
			return;

		if (this.i > 0) {
			if ((this.i <= 11) && (this.delta > 0)) {
				this.color = new int[] { this.i * 20 + 30, this.i * 15,
						this.i + 20 };
				if (this.i < 11) {
					this.i += 1;
				}
				if (this.i == 11) {
					if (this.t < 10)
						this.t += 1;
					else
						this.delta = -1;
				}
			} else if ((this.i > 0) && (this.delta < 0)) {
				this.i -= 1;
				this.color = new int[] { this.i * 20 + 30, this.i * 15,
						this.i + 20 };
			}

			invalidate();			
			
			
			this.handle.postDelayed(new Runnable() {
				public void run() {
					if (stopAnim) {						
						return;
					}

					runAnimation();
				}
			}, 100L);
		}
	}

	public void stopAnimation() {
		stopAnim = true;
	}
}
