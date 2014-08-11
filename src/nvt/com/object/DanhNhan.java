package nvt.com.object;

public class DanhNhan {
	int id;
	String name;
	String name_en;
	String link;
	String description;
	String image_link;
	
	public DanhNhan() {
	}
	
	public DanhNhan(String name,String name_en,String link,String description){
		this.name = name;
		this.name_en = name_en;
		this.link = link;
		this.description = description;
	}

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

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setImageLink(String link){
		this.image_link = link;
	}
	
	public String getImageLink(){
		return this.image_link;
	}
	
	@Override
	public String toString() {
		return this.name + " " + this.name_en;
	}
}
