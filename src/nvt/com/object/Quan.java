package nvt.com.object;

public class Quan extends DiaDanh{	
	
	int id_thanhpho;
	public Quan(){
		
	}		

	public Quan(String name,String name_khong_dau,String link) {
		this.name = name;
		this.name_khong_dau = name_khong_dau;
		this.link = link;
	}

	public int getId_thanhpho() {
		return id_thanhpho;
	}

	public void setId_thanhpho(int id_thanhpho) {
		this.id_thanhpho = id_thanhpho;
	}
	
	@Override
	public String getInfo() {
		return this.name;
	}	
}
