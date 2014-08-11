package nvt.com.utils;

import android.os.Environment;
import android.os.StatFs;

public class StorageUtils {
	
	public static boolean isSdCardAvailable(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * @return Number of bytes available on external storage  
	 */
	public static long getAvailableSpaceInBytes(){
		long availableSpace = -1L;
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		availableSpace = (long)stat.getAvailableBlocks() * (long)stat.getBlockSize();
		
		return availableSpace;
	}
	
	/**
	 * 
	 * @return Number of kilo bytes available on external storage  
	 */
	public static long getAvailableSpaceInKB(){
		long SIZE_KB = 1024L;
		long availableSpace = -1L;
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		availableSpace = (long)stat.getAvailableBlocks() * (long)stat.getBlockSize();
		
		return availableSpace / SIZE_KB;
	}
	
	/**
	 * 
	 * @return Number of mega bytes available on external storage  
	 */
	public static long getAvailableSpaceInMB(){
		long SIZE_KB = 1024L;
		long SIZE_MB = SIZE_KB * SIZE_KB;
		long availableSpace = -1L;
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		availableSpace = (long)stat.getAvailableBlocks() * (long)stat.getBlockSize();
		
		return availableSpace / SIZE_MB;
	}
	
	/**
	 * 
	 * @return Number of gega bytes available on external storage  
	 */
	public static long getAvailableSpaceInGB(){
		long SIZE_KB = 1024L;
		long SIZE_GB = SIZE_KB * SIZE_KB * SIZE_KB;
		long availableSpace = -1L;
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		availableSpace = (long)stat.getAvailableBlocks() * (long)stat.getBlockSize();
		
		return availableSpace / SIZE_GB;
	}
}
