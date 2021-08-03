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
	
	private JSONArray getData() throws MalformedURLException, IOException {
		GetDate thisDate = new GetDate();
		
		URL url = new URL (host + 
				"?serviceKey=" + apiKey + 
				"&pageNo=1&numOfRows=10&dataType=JSON&base_date=" + thisDate.getTodayDate() +
				"&base_time=" + thisDate.getTodayHour() +
				"&nx=" + sinchang_x + 
				"&ny=" + sinchang_y); //���û API �ּ� + ���� ��¥ & �ð� + ��â�� ��ǥ

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET"); //GET ���� ����
		connection.setRequestProperty("Content-type", "application/json");
			
		String weatherData = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);	
		
		JSONObject fullObjectedData = new JSONObject(weatherData);
		
		JSONArray currentData = fullObjectedData.getJSONObject("response")
				.getJSONObject("body")
				.getJSONObject("items")
				.getJSONArray("item");
		
		return currentData;
	}
	
	public String getCelsius() {
		JSONObject celciusData;
		String tem = "0.0��";
		
		try {
			celciusData = (JSONObject) getData().get(3);
			tem = celciusData.getString("obsrValue") + "��";
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		return tem;
	}
	
	public String getHumidity() {
		JSONObject humidityData;
		String hum = "0%";
		
		try {
			humidityData = (JSONObject) getData().get(1);
			hum = humidityData.getString("obsrValue") + "%";
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			System.out.println("�����͸� ����� �������� ���߽��ϴ�. ���ͳ� ������ Ȯ���ϼ���.");
		}
		
		return hum;
	}
	
	public int getIfRain() {
		JSONObject rainStatusData;
		int ifRain = -1;
		
		try {
			rainStatusData = (JSONObject) getData().get(0);
			ifRain = rainStatusData.getInt("obsrValue");
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			System.out.println("�����͸� ����� �������� ���߽��ϴ�. ���ͳ� ������ Ȯ���ϼ���.");
		}
		
		return ifRain; //����(-1), ����(0), ��(1), �� ��(2), ��(3), �ҳ���(4)
	}
}
