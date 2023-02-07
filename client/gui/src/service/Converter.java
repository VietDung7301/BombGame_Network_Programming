package service;


public class Converter {
	public String paramToRequest(String code, String... params) {
		String str = code;
		for (String param: params) {
			str = str + '&' + param;
		}
		str = str + "$$";
		return str;
	}
	public String decodeGameStatus() {
		return null;
	}
}
