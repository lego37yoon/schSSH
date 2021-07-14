package sshWindows;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;

public class GetWeather {
	static String apiKey = "D8y+brPeE6ZR41tB8WEIPi4XUVQo7FZ7dtM/8QrN8y0xQ4r9F/eKsl4GSlZKPwFQYIgDXaPApbQxyU8W/7A6oQ==";
	static String host = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst";
	static int sinchang_x = 59;
	static int sinchang_y = 110;
	
	
	public double getCelsius() {
		double tem = 0.0;
		URL url;
		try {
			url = new URL (host + 
					"?serviceKey=" + apiKey + 
					"&pageNo=1&numOfRows=10&dataType=JSON&base_date=" + 
					"&base_time=" + 
					"&nx=" + sinchang_x + 
					"&ny=" + sinchang_y);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return tem;
	}
}
