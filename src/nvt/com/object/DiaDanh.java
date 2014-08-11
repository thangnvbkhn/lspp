package nvt.com.object;

public abstract class DiaDanh {
	int id;
	String name;
	String name_khong_dau;
	String link;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public abstract String getInfo();	
}
