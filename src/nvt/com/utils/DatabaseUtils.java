package nvt.com.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;


public class DatabaseUtils {
	private Context context;
	
	public DatabaseUtils(Context context) {
		this.context = context;
	}
	
	public boolean checkExistFolder(String nameFolder){
		File dirData = new File(Environment.getDataDirectory().getPath()
				+ "/data/" + context.getPackageName() + "/databases/" + nameFolder);
		if (dirData.exists()) {
			return true;
		}
		return false;
	}
	
	public void initDatabase(String nameFile) {
		File dirData = new File(Environment.getDataDirectory().getPath()
				+ "/data/" + context.getPackageName() + "/databases");
		if (!dirData.exists()) {
			dirData.mkdirs();
		}

		File file = new File(dirData, "db");

		if (!file.exists()) {
			try {
				ZipUtils.unpackZip(context.getAssets().open(nameFile),
						new FileOutputStream(new File(dirData, "db")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
