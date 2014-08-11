package nvt.com.thread;

import java.util.ArrayList;

import nvt.com.database.DatabaseHelper;
import nvt.com.object.Duong;
import nvt.com.object.Phuong;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ThreadPlace extends Thread {
	private Handler handler;
	private Context context;
	private Duong place = new Duong();
	private String name_khong_dau;
	private int id_phuong;
	private boolean run = true;
	public int choice;

	public ThreadPlace(Context context, Handler handler) {
		this.handler = handler;
		this.context = context;
		this.choice = 0;
		this.run = true;
	}

	public ThreadPlace(Context context, Handler handler, String name_khong_dau,
			int c) {
		this.handler = handler;
		this.name_khong_dau = name_khong_dau;
		this.context = context;
		if (c == 1) {
			this.choice = 1;// getDuongByNameKhongDau
		} else if (c == 2) {
			this.choice = 2;
		} else if (c == 3) {
			this.choice = 3;// getPhuongByName
		}
		this.run = true;
	}

	public ThreadPlace(Context context, Handler handler, int id_phuong) {
		this.context = context;
		this.handler = handler;
		this.id_phuong = id_phuong;
		run = true;
		choice = 4; // getDuongByIdPhuong
	}

	public void stopThread() {
		run = false;
	}

	@Override
	public void run() {
		if (run) {
			DatabaseHelper db = new DatabaseHelper(context);
			Message msg;
			switch (choice) {
			case 0:
				ArrayList<Duong> streets = new ArrayList<Duong>();
				streets = db.getAllDuong();
				msg = Message.obtain(handler, 0, streets);
				msg.sendToTarget();
				break;
			case 1:
				place = db.getDuongByNameKhongDau(name_khong_dau);
				if (place.getId() == 0)
					place.setId(0);
				msg = Message.obtain(handler, 1, place);
				msg.sendToTarget();
				break;
			case 2:
				place = db.getDuongByNameKey(name_khong_dau);
				if (place.getId() == 0)
					place.setId(0);
				msg = Message.obtain(handler, 2, place);
				msg.sendToTarget();
				break;
			case 3:
				ArrayList<Phuong> phuong_list = new ArrayList<Phuong>();
				phuong_list = db.getPhuongByName(name_khong_dau);
				Log.d("size", phuong_list.size() + "");
				msg = Message.obtain(handler, 3, phuong_list);
				msg.sendToTarget();
				break;
			case 4:
				ArrayList<Duong> duong_list = new ArrayList<Duong>();
				duong_list = db.getAllDuongByIdPhuong(id_phuong);
				msg = Message.obtain(handler, 4, duong_list);
				msg.sendToTarget();
				break;
			}
		}
	}
}