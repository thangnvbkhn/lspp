package nvt.com.object;

public class ThanhPho extends DiaDanh{	
	
	public ThanhPho(){
		
	}
	
	public ThanhPho(String name,String name_khong_dau,String link) {
		this.name = name;
		this.name_khong_dau = name_khong_dau;
		this.link = link;
	}

	@Override
	public String getInfo() {
		return this.name;
	}	
}
