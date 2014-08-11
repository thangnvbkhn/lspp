package nvt.com.object;

public class Phuong extends DiaDanh{	
	private int id_quan;	
	
	public Phuong() {
		
	}
	
	public Phuong(String name,String name_khong_dau,String link) {
		this.name = name;
		this.name_khong_dau = name_khong_dau;
		this.link = link;
	}

	public int getId_quan() {
		return id_quan;
	}

	public void setId_quan(int id_quan) {
		this.id_quan = id_quan;
	}

	@Override
	public String getInfo() {
		return this.name;
	}		
}
