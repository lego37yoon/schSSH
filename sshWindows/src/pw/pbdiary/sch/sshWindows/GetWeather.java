package pw.pbdiary.sch.sshWindows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import org.json.*;

public class GetWeather {
	static final String apiKey = "D8y+brPeE6ZR41tB8WEIPi4XUVQo7FZ7dtM/8QrN8y0xQ4r9F/eKsl4GSlZKPwFQYIgDXaPApbQxyU8W/7A6oQ==";
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
			
			BufferedReader weatherData; //raw 데이터 담을 Buffered Reader 생성
			
			if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 300) {
				weatherData = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} else {
				weatherData = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return tem;
	}
}
