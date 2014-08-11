package nvt.com.utils;

public class AccentCharacter {
	/**
	 * 
	 * Remove accents from string 
	 */
	public static String remove(String str){
		String str2 = str.trim();
		str2 = str2.replaceAll("à|á|ả|ã|ạ|â|ầ|ấ|ẩ|ẫ|ậ|ằ|ắ|ẳ|ẵ|ặ|ă", "a");
		str2 = str2.replaceAll("í|ì|ỉ|ĩ|ị","i");
		str2 = str2.replaceAll("ư|ứ|ừ|ử|ữ|ự|ú|ù|ủ|ũ|ụ","u");
		str2 = str2.replaceAll("ế|ề|ể|ễ|ệ|é|è|ẻ|ẽ|ẹ|ê","e");
		str2 = str2.replaceAll("ố|ồ|ổ|ỗ|ộ|ớ|ờ|ở|ỡ|ợ|ó|ò|ỏ|õ|ọ|ô|ơ","o");
		str2= str2.replaceAll("ý|ỳ|ỷ|ỹ|ỵ","y");
		
		str2 = str2.replaceAll("Ấ|Ầ|Ẩ|Ẫ|Ậ|Ắ|Ằ|Ẳ|Ẵ|Ặ|Á|À|Ả|Ã|Ạ|Â|Ă","A");
		str2 = str2.replaceAll("Í|Ì|Ỉ|Ĩ|Ị","I");
		str2 = str2.replaceAll("Ứ|Ừ|Ử|Ữ|Ự|Ú|Ù|Ủ|Ũ|Ụ|Ư","U");
		str2 = str2.replaceAll("Ế|Ề|Ể|Ễ|Ệ|É|È|Ẻ|Ẽ|Ẹ|Ê","E");
		str2 = str2.replaceAll("Ố|Ồ|Ổ|Ô|Ộ|Ớ|Ờ|Ở|Ỡ|Ợ|Ó|Ò|Ỏ|Õ|Ọ|Ô|Ơ","O");
		str2= str2.replaceAll("Ý|Ỳ|Ỷ|Ỹ|Ỵ","Y");
		str2 = str2.replaceAll("đ","d");
		str2 = str2.replaceAll("Đ","D");
		
		str2 = str2.replaceAll("ò", "");
		return str2;
	}
	
	public static void main(String[] args) {
		String str = remove("47 ngõ 26, đường Nguyên Hồng, phường Láng Hạ, Đống Đa, Hà Nội, Việt Nam");
		System.out.println(str);
	}
}