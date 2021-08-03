package pw.pbdiary.sch.sshWindows;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetAirPM {
	static final String apiKey = "D8y%2BbrPeE6ZR41tB8WEIPi4XUVQo7FZ7dtM%2F8QrN8y0xQ4r9F%2FeKsl4GSlZKPwFQYIgDXaPApbQxyU8W%2F7A6oQ%3D%3D";
	static final String host = "http://apis.data.go.kr/B552584/ArpltnStatsSvc";
	static final String msrstnName = "도고면";
	
	private JSONArray getData() throws MalformedURLException, IOException {
		GetDate gd = new GetDate();
		
		URL url = new URL(host + "?serviceKey=" + apiKey 
				+ "&returnType=json&numOfRows=100&pageNo=1&inqBginDt=" + gd.getYesterdayDate() 
				+ "&inqEndDt=" + gd.getTodayDate()
				+ "&msrstnName=" + msrstnName);
		
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
}
