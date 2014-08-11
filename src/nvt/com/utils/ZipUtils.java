package nvt.com.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ZipUtils {
	public static boolean unpackZip(InputStream inputStream, OutputStream outputStream) {
		ZipInputStream zip;		
		try {			
			zip = new ZipInputStream(inputStream);
			ZipEntry entry;
			int count = 0;
			byte[] buffer = new byte[1024];
			
			while ((entry = zip.getNextEntry()) != null) {

				if (entry.isDirectory()) {
					continue;
				}

				while ((count = zip.read(buffer)) != -1) {
					outputStream.write(buffer, 0, count);
				}

				inputStream.close();
				outputStream.close();
				zip.closeEntry();
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return false;
	}
}
