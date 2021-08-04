package pw.pbdiary.sch.sshWindows;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetAirPM {
	static final String apiKey = "D8y+brPeE6ZR41tB8WEIPi4XUVQo7FZ7dtM/8QrN8y0xQ4r9F/eKsl4GSlZKPwFQYIgDXaPApbQxyU8W/7A6oQ==";
	static final String host = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty";
	static final String msrstnName = "도고면";
	
	private JSONArray getData() throws MalformedURLException, IOException {
		GetDate gd = new GetDate();
		
		URL url = new URL(host + "?serviceKey=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8) 
				+ "&returnType=json&numOfRows=100&pageNo=1&stationName=" + URLEncoder.encode(msrstnName, StandardCharsets.UTF_8) 
				+ "&dataTerm=DAILY&ver=1.0");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET"); //GET 모드로 설정
		connection.setRequestProperty("Content-type", "application/json");
		
		String pmData = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);	
		
		JSONObject fullObjectedData = new JSONObject(pmData);
		
		JSONArray currentData = fullObjectedData.getJSONObject("response")
				.getJSONObject("body")
				.getJSONArray("items");
		
		return currentData;
	}
	
	public String getPM10() {
		JSONObject data;
		String pm10 = "0";
		
		try {
			data = (JSONObject) getData().get(0);
			pm10 = data.getString("pm10Value");
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		return pm10;
	}
	
	public String getPM25() {
		JSONObject data;
		String pm25 = "0";
		
		try {
			data = (JSONObject) getData().get(0);
			pm25 = data.getString("pm25Value");
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		return pm25;
	}
	
	public String getPM10Grade() {
		JSONObject data;
		String grade = "알 수 없음";
		
		try {
			data = (JSONObject) getData().get(0);
			grade = switch(data.getString("pm10Grade")) {
				case "1" -> "좋음";
				case "2" -> "보통";
				case "3" -> "나쁨";
				case "4" -> "매우 나쁨";
				default -> "알 수 없음";
			};
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		return grade;
	}
	
	public String getPM25Grade() {
		JSONObject data;
		String grade = "알 수 없음";
		
		try {
			data = (JSONObject) getData().get(0);
			grade = switch(data.getString("pm25Grade")) {
				case "1" -> "좋음";
				case "2" -> "보통";
				case "3" -> "나쁨";
				case "4" -> "매우 나쁨";
				default -> "알 수 없음";
			};
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		return grade;
	}
}
