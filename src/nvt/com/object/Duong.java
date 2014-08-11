package nvt.com.object;

import android.os.Parcel;


public class Duong extends DiaDanh{
	/**
	 * 
	 */
	
	private int id_phuong;
	private String latitude;
	private String longitude;
	private String description;
	private String imagelink;
	
	
	
	public Duong(Parcel source){
		this.id_phuong = source.readInt();
		this.latitude = source.readString();
		this.longitude = source.readString();
		this.description = source.readString();
		this.imagelink = source.readString();
	}	

	public String getImagelink() {
		return imagelink;
	}

	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}

	public Duong(){
		
	}		
	
	public Duong(String name,String name_khong_dau,String link,String latitude,String longitude,String description) {
		this.name = name;
		this.name_khong_dau = name_khong_dau;
		this.latitude = latitude;
		this.longitude = longitude;
		this.link = link;
		this.description = description;
	}	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_phuong() {
		return id_phuong;
	}

	public void setId_phuong(int id_phuong) {
		this.id_phuong = id_phuong;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_khong_dau() {
		return name_khong_dau;
	}

	public void setName_khong_dau(String name_khong_dau) {
		this.name_khong_dau = name_khong_dau;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public String getInfo() {
		return super.name;
	}
}
