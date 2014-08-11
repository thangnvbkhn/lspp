package nvt.com.database;

import java.util.ArrayList;

import nvt.com.object.CauHoi;
import nvt.com.object.DanhNhan;
import nvt.com.object.Duong;
import nvt.com.object.Phuong;
import nvt.com.object.Quan;
import nvt.com.object.ThanhPho;

import org.apache.commons.codec.binary.Base64;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public Context context;	
	private static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "db";

	// TABLE THANHPHO_LINK
	public static final String THANHPHO_LINK_TABLE = "thanhpho_link";
	public static final String THANHPHO_LINK_KEY_ID = "id";
	public static final String THANHPHO_LINK_KEY_NAME = "name";
	public static final String THANHPHO_LINK_KEY_NAME_KHONG_DAU = "name_khong_dau";
	public static final String THANHPHO_LINK_KEY_LINK = "link";
	
	// TABLE QUAN_LINK
	private static final String QUAN_LINK_TABLE = "quan_link";
	private static final String QUAN_LINK_KEY_ID = "id";
	private static final String QUAN_LINK_KEY_ID_THANHPHO = "id_thanhpho";
	private static final String QUAN_LINK_KEY_NAME = "name";
	private static final String QUAN_LINK_KEY_NAME_KHONG_DAU = "name_khong_dau";
	private static final String QUAN_LINK_KEY_LINK = "link";

	// TABLE PHUONG_LINK
	private static final String PHUONG_LINK_TABLE = "phuong_link";
	private static final String PHUONG_LINK_KEY_ID = "id";
	private static final String PHUONG_LINK_KEY_ID_QUAN = "id_quan";
	private static final String PHUONG_LINK_KEY_NAME = "name";
	private static final String PHUONG_LINK_KEY_NAME_KHONG_DAU = "name_khong_dau";
	private static final String PHUONG_LINK_KEY_LINK = "link";

	// TABLE DUONG_INFO
	private static final String DUONG_INFO_TABLE = "duong_info";
	public static final String DUONG_INFO_KEY_ID = "id";
	public static final String DUONG_INFO_KEY_ID_PHUONG = "id_phuong";
	public static final String DUONG_INFO_KEY_NAME = "name";
	public static final String DUONG_INFO_KEY_NAME_KHONG_DAU = "name_khong_dau";
	public static final String DUONG_INFO_KEY_LINK = "link";
	public static final String DUONG_INFO_KEY_LATITUDE = "latitude";
	public static final String DUONG_INFO_KEY_LONGITUDE = "longitude";
	public static final String DUONG_INFO_KEY_DESCRIPTION = "description";
	public static final String DUONG_INFO_KEY_IMAGES = "images";
	
	// TABLE DANH_NHAN
	private static final String DANHNHAN_TABLE = "danh_nhan";
	public static final String DANHNHAN_KEY_ID = "id";	
	public static final String DANHNHAN_KEY_NAME = "name";
	public static final String DANHNHAN_KEY_NAME_EN = "name_en";
	public static final String DANHNHAN__LINK = "link";
	public static final String DANHNHAN_DESCRIPTION = "description";
	public static final String DANHNHAN_IMAGES = "images";

	// TABLE CAU HOI
	public static final String CAU_HOI_TABLE = "cauhoi";
	public static final String CAU_HOI_KEY_ID = "id";
	public static final String CAU_HOI_KEY_CAU_HOI = "cauhoi";
	public static final String CAU_HOI_KEY_DAP_AN_1 = "dapan1";
	public static final String CAU_HOI_KEY_DAP_AN_2 = "dapan2";
	public static final String CAU_HOI_KEY_DAP_AN_3 = "dapan3";
	public static final String CAU_HOI_KEY_DAP_AN_4 = "dapan4";
	public static final String CAU_HOI_KEY_DAP_AN_DUNG = "dapandung";
	
	public static String CREATE_TABLE_DUONG = "CREATE TABLE "
			+ DUONG_INFO_TABLE + "(" + DUONG_INFO_KEY_ID
			+ " INTEGER PRIMARY KEY  NOT NULL , " + DUONG_INFO_KEY_ID_PHUONG
			+ " INTEGER," + DUONG_INFO_KEY_NAME + " VARCHAR,"
			+ DUONG_INFO_KEY_NAME_KHONG_DAU + " VARCHAR," + DUONG_INFO_KEY_LINK
			+ " VARCHAR," + DUONG_INFO_KEY_LATITUDE + " VARCHAR,"
			+ DUONG_INFO_KEY_LONGITUDE + " VARCHAR, "
			+ DUONG_INFO_KEY_DESCRIPTION + " TEXT, "
			+ DUONG_INFO_KEY_IMAGES + " VARCHAR);";

	public static String CREATE_TABLE_PHUONG = "CREATE TABLE "
			+ PHUONG_LINK_TABLE + "(" + PHUONG_LINK_KEY_ID
			+ " INTEGER PRIMARY KEY  NOT NULL , "
			+ PHUONG_LINK_KEY_ID_QUAN
			+ " INTEGER, " + PHUONG_LINK_KEY_NAME + " VARCHAR, "
			+ PHUONG_LINK_KEY_NAME_KHONG_DAU + " VARCHAR, "
			+ PHUONG_LINK_KEY_LINK + " VARCHAR);";

	public static String CREATE_TABLE_QUAN = "CREATE TABLE " + QUAN_LINK_TABLE
			+ "(" + QUAN_LINK_KEY_ID + " INTEGER PRIMARY KEY  NOT NULL, "
			+ QUAN_LINK_KEY_ID_THANHPHO + " INTEGER, "
			+ QUAN_LINK_KEY_NAME + " VARCHAR, " + QUAN_LINK_KEY_NAME_KHONG_DAU
			+ " VARCHAR, " + QUAN_LINK_KEY_LINK + " VARCHAR);";
	
	public static String CREATE_TABLE_THANHPHO = "CREATE TABLE " + THANHPHO_LINK_TABLE
			+ "(" + THANHPHO_LINK_KEY_ID + " INTEGER PRIMARY KEY  NOT NULL , "
			+ THANHPHO_LINK_KEY_NAME + " VARCHAR, " + THANHPHO_LINK_KEY_NAME_KHONG_DAU
			+ " VARCHAR, " + THANHPHO_LINK_KEY_LINK + " VARCHAR);";
	
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	
	private String decrypt(String content){
		byte[] encodedBytes = Base64.decodeBase64(content.getBytes());
		return new String(encodedBytes);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_THANHPHO);	
		db.execSQL(CREATE_TABLE_QUAN);		
		db.execSQL(CREATE_TABLE_PHUONG);		
		db.execSQL(CREATE_TABLE_DUONG);	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}
	
	/* QUERY VE DANH NHAN */
	public ArrayList<DanhNhan> getAllDanhNhanLimit(int offset){
		ArrayList<DanhNhan> data = new ArrayList<DanhNhan>();
		String selectQuery = "SELECT  * FROM " + DANHNHAN_TABLE +  " ORDER BY " +
						   DANHNHAN_KEY_NAME_EN + " ASC" + " LIMIT 10 OFFSET " + offset;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				DanhNhan danhnhan = new DanhNhan();
				danhnhan.setId(Integer.parseInt(cursor.getString(0)));
				danhnhan.setName(cursor.getString(1));
				danhnhan.setName_en(cursor.getString(2));
				danhnhan.setLink(cursor.getString(3));
				danhnhan.setDescription(cursor.getString(4));
				danhnhan.setImageLink(cursor.getString(5));
				data.add(danhnhan);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		
		return data;
	}
	
	public ArrayList<DanhNhan> getAllDanhNhan(){
		ArrayList<DanhNhan> list = new ArrayList<DanhNhan>();
		String selectQuery = "SELECT  * FROM " + DANHNHAN_TABLE + " ORDER BY "
				+ DANHNHAN_KEY_ID + " ASC";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				DanhNhan danhnhan = new DanhNhan();
				danhnhan.setId(Integer.parseInt(cursor.getString(0)));
				danhnhan.setName(cursor.getString(1));
				danhnhan.setName_en(cursor.getString(2));
				danhnhan.setLink(cursor.getString(3));
				danhnhan.setDescription(cursor.getString(4));
				danhnhan.setImageLink(cursor.getString(5));
				list.add(danhnhan);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
	}

	// name bao gom : name va name_en
	public DanhNhan getDanhNhan(String name){
		DanhNhan danhnhan = new DanhNhan();
		String selectQuery = "SELECT  * FROM " + DANHNHAN_TABLE + " WHERE "
				+ DANHNHAN_KEY_NAME_EN + " LIKE '%" + name + "%'" + 
				" OR " + DANHNHAN_KEY_NAME + " LIKE '%" + name + "%'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {				
				danhnhan.setId(Integer.parseInt(cursor.getString(0)));
				danhnhan.setName(cursor.getString(1));
				danhnhan.setName_en(cursor.getString(2));
				danhnhan.setLink(cursor.getString(3));
				danhnhan.setDescription(cursor.getString(4));
				danhnhan.setImageLink(cursor.getString(5));
			} while (cursor.moveToNext());
		}else{
			return null;
		}
		cursor.close();
		db.close();
		return danhnhan;
	}
	
	/* QUERY VE THANH PHO */	
	public ArrayList<ThanhPho> getAllThanhPho(){
		ArrayList<ThanhPho> list = new ArrayList<ThanhPho>();
		String selectQuery = "SELECT  * FROM " + THANHPHO_LINK_TABLE + " ORDER BY "
				+ THANHPHO_LINK_KEY_ID + " ASC";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				ThanhPho thanhpho = new ThanhPho();
				thanhpho.setId(Integer.parseInt(cursor.getString(0)));
				thanhpho.setName(cursor.getString(1));
				thanhpho.setName_khong_dau(cursor.getString(2));
				thanhpho.setLink(cursor.getString(3));
				list.add(thanhpho);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return list;
	}
	
	/* QUERY VE QUAN */
	public ArrayList<Quan> getAllQuanByID(int id_thanhpho) {
		ArrayList<Quan> quan_list = new ArrayList<Quan>();
		String selectQuery = "SELECT  * FROM " + QUAN_LINK_TABLE + 
				" WHERE " + QUAN_LINK_KEY_ID_THANHPHO + "=" + id_thanhpho + 
				" ORDER BY " + QUAN_LINK_KEY_ID + " ASC";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Quan quan = new Quan();
				quan.setId(Integer.parseInt(cursor.getString(0)));
				quan.setId_thanhpho(Integer.parseInt(cursor.getString(1)));
				quan.setName(cursor.getString(2));
				quan.setName_khong_dau(cursor.getString(3));
				quan.setLink(cursor.getString(4));
				quan_list.add(quan);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return quan_list;
	}
	
	/* QUERY VE PHUONG */

	public ArrayList<Phuong> getPhuongByName(String name_khong_dau) {
		ArrayList<Phuong> phuong_list = new ArrayList<Phuong>();
		/*
		 * select id from phuong_link where id_quan=(select id from quan_link
		 * where name_khong_dau='quan ha dong');
		 */
		String selectQuery = "SELECT  * FROM " + PHUONG_LINK_TABLE
				+ " WHERE id_quan=(select id from " + QUAN_LINK_TABLE
				+ " where name_khong_dau='" + name_khong_dau + "')";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Phuong phuong = new Phuong();
				phuong.setId(Integer.parseInt(cursor.getString(0)));
				phuong.setId_quan(Integer.parseInt(cursor.getString(1)));
				phuong.setName(cursor.getString(2));
				phuong.setName_khong_dau(cursor.getString(3));
				phuong.setLink(cursor.getString(4));
				phuong_list.add(phuong);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return phuong_list;
	}

	public ArrayList<Phuong> getAllPhuongById(int id_quan) {
		ArrayList<Phuong> phuong_list = new ArrayList<Phuong>();
		String selectQuery = "SELECT  * FROM " + PHUONG_LINK_TABLE
				+ " WHERE " + PHUONG_LINK_KEY_ID_QUAN + "=" + id_quan + " ORDER BY " + PHUONG_LINK_KEY_ID + " ASC";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Phuong phuong = new Phuong();
				phuong.setId(Integer.parseInt(cursor.getString(0)));
				phuong.setId_quan(Integer.parseInt(cursor.getString(1)));
				phuong.setName(cursor.getString(2));
				phuong.setName_khong_dau(cursor.getString(3));
				phuong.setLink(cursor.getString(4));
				phuong_list.add(phuong);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return phuong_list;
	}
	
	public ArrayList<Phuong> getAllPhuong() {
		ArrayList<Phuong> phuong_list = new ArrayList<Phuong>();
		String selectQuery = "SELECT  * FROM " + PHUONG_LINK_TABLE
				+ " ORDER BY " + PHUONG_LINK_KEY_ID + " ASC";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Phuong phuong = new Phuong();
				phuong.setId(Integer.parseInt(cursor.getString(0)));
				phuong.setId_quan(Integer.parseInt(cursor.getString(1)));
				phuong.setName(cursor.getString(2));
				phuong.setName_khong_dau(cursor.getString(3));
				phuong.setLink(cursor.getString(4));
				phuong_list.add(phuong);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return phuong_list;
	}

	/* QUERY VE DUONG */
	public Duong getDuongByNameKey(String nameKey){
		Duong duong = new Duong();
		
		String sql = "SELECT * FROM " + DUONG_INFO_TABLE
					+ " WHERE " + DUONG_INFO_KEY_NAME_KHONG_DAU + " LIKE "
					+ "'%" + nameKey + "%' OR " + DUONG_INFO_KEY_NAME + " LIKE " + "'%" + nameKey + "%'";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		
		if (cursor.moveToFirst()) {
			duong.setId(Integer.parseInt(cursor.getString(0)));
			duong.setId_phuong(Integer.parseInt(cursor.getString(1)));
			duong.setName(cursor.getString(2));
			duong.setName_khong_dau(cursor.getString(3));
			duong.setLink(decrypt(cursor.getString(4)));
			duong.setLatitude(decrypt(cursor.getString(5)));
			duong.setLongitude(decrypt(cursor.getString(6)));
			duong.setDescription(decrypt(cursor.getString(7)));
			duong.setImagelink(decrypt(cursor.getString(8)));
		}
		return duong;
	}
	
	public Duong getDuongByName(String name){		
//		TrippleDes des = new TrippleDes();
//		des.loadSecretKey(arg0);
		Duong duong = new Duong();
		String selectQuery = "SELECT  * FROM " + DUONG_INFO_TABLE				
				+ " WHERE " + DUONG_INFO_KEY_NAME + "=" + 
				"'" + name + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			duong.setId(Integer.parseInt(cursor.getString(0)));
			duong.setId_phuong(Integer.parseInt(cursor.getString(1)));
			duong.setName(cursor.getString(2));
			duong.setName_khong_dau(cursor.getString(3));
			duong.setLink(cursor.getString(4));
			duong.setLatitude(cursor.getString(5));
			duong.setLongitude(cursor.getString(6));
			duong.setDescription(cursor.getString(7));
			duong.setImagelink(cursor.getString(8));
//			duong.setLink(des.decrypt(cursor.getString(4)));
//			duong.setLatitude(des.decrypt(cursor.getString(5)));
//			duong.setLongitude(des.decrypt(cursor.getString(6)));
//			duong.setDescription(des.decrypt(cursor.getString(7)));
//			duong.setImagelink(des.decrypt(cursor.getString(8)));
		}
		return duong;
	}
	
	public Duong getDuongByNameKhongDau(String name_khong_dau){
		Duong duong = new Duong();
		String selectQuery = "SELECT  * FROM " + DUONG_INFO_TABLE				
				+ " WHERE " + DUONG_INFO_KEY_NAME_KHONG_DAU + " LIKE " + 
				"'%" + name_khong_dau + "%'";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			duong.setId(Integer.parseInt(cursor.getString(0)));
			duong.setId_phuong(Integer.parseInt(cursor.getString(1)));
			duong.setName(cursor.getString(2));
			duong.setName_khong_dau(cursor.getString(3));
			duong.setLink(decrypt(cursor.getString(4)));
			duong.setLatitude(decrypt(cursor.getString(5)));
			duong.setLongitude(decrypt(cursor.getString(6)));
			duong.setDescription(decrypt(cursor.getString(7)));
			duong.setImagelink(decrypt(cursor.getString(8)));
			return duong;
		}else{
			return null;
		}
	}	
	
	public ArrayList<Duong> getAllDuong() {
		ArrayList<Duong> streets = new ArrayList<Duong>();

		String selectQuery = "SELECT  * FROM " + DUONG_INFO_TABLE
				+ " ORDER BY id ASC";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Duong street = new Duong();
				street.setId(Integer.parseInt(cursor.getString(0)));
				street.setId_phuong(Integer.parseInt(cursor.getString(1)));
				street.setName(cursor.getString(2));
				street.setName_khong_dau(cursor.getString(3));
				street.setLink(decrypt(cursor.getString(4)));
				street.setLatitude(decrypt(cursor.getString(5)));
				street.setLongitude(decrypt(cursor.getString(6)));
				street.setDescription(decrypt(cursor.getString(7)));
				street.setImagelink(decrypt(cursor.getString(8)));
				streets.add(street);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return streets;
	}
	
	public Duong getDuongById(int id_duong) {
		Duong duong = new Duong();
		String selectQuery = "SELECT  * FROM " + DUONG_INFO_TABLE
				+ " WHERE " + DUONG_INFO_KEY_ID + "=" + id_duong;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				duong.setId(Integer.parseInt(cursor.getString(0)));
				duong.setId_phuong(Integer.parseInt(cursor.getString(1)));
				duong.setName(cursor.getString(2));
				duong.setName_khong_dau(cursor.getString(3));
				duong.setLink(decrypt(cursor.getString(4)));
				duong.setLatitude(decrypt(cursor.getString(5)));
				duong.setLongitude(decrypt(cursor.getString(6)));
				duong.setDescription(decrypt(cursor.getString(7)));
				duong.setImagelink(decrypt(cursor.getString(8)));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return duong;
	}
	
	public ArrayList<Duong> getAllDuongByIdPhuong(int id_phuong) {
		ArrayList<Duong> duong_list = new ArrayList<Duong>();
		String selectQuery = "SELECT  * FROM " + DUONG_INFO_TABLE
				+ " WHERE " + DUONG_INFO_KEY_ID_PHUONG + "=" + id_phuong;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Duong duong = new Duong();
				duong.setId(Integer.parseInt(cursor.getString(0)));
				duong.setId_phuong(Integer.parseInt(cursor.getString(1)));
				duong.setName(cursor.getString(2));
				duong.setName_khong_dau(cursor.getString(3));
				duong.setLink(cursor.getString(4));
				duong.setLatitude(cursor.getString(5));
				duong.setLongitude(cursor.getString(6));
				duong.setDescription(cursor.getString(7));
				duong.setImagelink(cursor.getString(8));
				duong_list.add(duong);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return duong_list;
	}
	
	/* QUERY VE CAU HOI */
	public int getTotalQuestion(){
		int size = 0;
		
		String selectQuery = "SELECT count(*) FROM " + CAU_HOI_TABLE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);				
		
		if (cursor.moveToFirst()) {
			size = cursor.getInt(0);
		}
		return size;
	}
	
	public ArrayList<CauHoi> getAllCauHoi(int question[]) {
		ArrayList<CauHoi> cauhoi_list = new ArrayList<CauHoi>();
		String selectQuery = "SELECT  * FROM "
				+ CAU_HOI_TABLE + " WHERE " + CAU_HOI_KEY_ID + " IN(";
		for(int i=0;i<10;i++){
			selectQuery += question[i] + ",";
		}
		
		selectQuery = selectQuery.substring(0,selectQuery.length()-1);
		selectQuery += ")";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				CauHoi cauhoi = new CauHoi();
				cauhoi.setId(Integer.parseInt(cursor.getString(0)));				
				cauhoi.setNameQuestion(cursor.getString(1));
				cauhoi.setChoice1(cursor.getString(2));
				cauhoi.setChoice2(cursor.getString(3));
				cauhoi.setChoice3(cursor.getString(4));
				cauhoi.setChoice4(cursor.getString(5));
				cauhoi.setTrueAnswer(cursor.getString(6));
				cauhoi_list.add(cauhoi);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return cauhoi_list;
	}
	
	public ArrayList<CauHoi> getAllCauHoi() {
		ArrayList<CauHoi> cauhoi_list = new ArrayList<CauHoi>();
		String selectQuery = "SELECT  * FROM " + CAU_HOI_TABLE + " ORDER BY ID ASC LIMIT 10";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				CauHoi cauhoi = new CauHoi();
				cauhoi.setId(Integer.parseInt(cursor.getString(0)));				
				cauhoi.setNameQuestion(cursor.getString(1));
				cauhoi.setChoice1(cursor.getString(2));
				cauhoi.setChoice2(cursor.getString(3));
				cauhoi.setChoice3(cursor.getString(4));
				cauhoi.setChoice4(cursor.getString(5));
				cauhoi.setTrueAnswer(cursor.getString(6));
				cauhoi_list.add(cauhoi);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return cauhoi_list;
	}
}
