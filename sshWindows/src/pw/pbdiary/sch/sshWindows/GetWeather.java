package pw.pbdiary.sch.sshWindows;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.*;

public class GetWeather {
	static final String apiKey = "D8y%2BbrPeE6ZR41tB8WEIPi4XUVQo7FZ7dtM%2F8QrN8y0xQ4r9F%2FeKsl4GSlZKPwFQYIgDXaPApbQxyU8W%2F7A6oQ%3D%3D";
	static final String host = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst";
	static final int sinchang_x = 59;
	static final int sinchang_y = 110;
	
	
	public double getCelsius() {
		double tem = 0.0;
		GetDate thisDate = new GetDate();
		
		URL url;
		try {
			url = new URL (host + 
					"?serviceKey=" + apiKey + 
					"&pageNo=1&numOfRows=10&dataType=JSON&base_date=" + thisDate.getTodayDate() +
					"&base_time=" + thisDate.getTodayHour() +
					"&nx=" + sinchang_x + 
					"&ny=" + sinchang_y); //기상청 API 주소 + 현재 날짜 & 시각 + 신창면 좌표
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET"); //GET 모드로 설정
			connection.setRequestProperty("Content-type", "application/json");
			
			String weatherData = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return tem;
	}
}
