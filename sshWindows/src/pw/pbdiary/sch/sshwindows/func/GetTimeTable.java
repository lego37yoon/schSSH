package pw.pbdiary.sch.sshwindows.func;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Connection.Method;

public class GetTimeTable {
	private static final String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:90.0) Gecko/20100101 Firefox/90.0";
	
	public ArrayList<String> get(String id, String pw, String year, String semester) {
		ArrayList<String> timeTable = new ArrayList<String>();
		
		HashMap<String, String> userMap = new HashMap<String, String>();
		userMap.put("userid", id);
		userMap.put("password", pw);
		userMap.put("redirect", "/");
		HashMap<String, String> semesterMap = new HashMap<String, String>();
		semesterMap.put("year", year);
		semesterMap.put("semester", semester);
		
		try {
			Connection.Response loginRes = Jsoup.connect("https://everytime.kr/user/login")
					.data(userMap)
					.userAgent(ua)
					.method(Method.POST)
					.execute();
			Connection.Response findTableRes = Jsoup.connect("https://api.everytime.kr/find/timetable/table/list/semester")
					.data(semesterMap)
					.cookies(loginRes.cookies())
					.userAgent(ua)
					.method(Method.POST)
					.execute();
			Document tableListDoc = Jsoup.parse(findTableRes.body());
			String firstTable = tableListDoc.select("table").first().attr("id");
			Connection.Response findTime = Jsoup.connect("https://api.everytime.kr/find/timetable/table")
					.data("id", firstTable)
					.cookies(loginRes.cookies())
					.userAgent(ua)
					.method(Method.POST)
					.execute();
			Document tableDoc = Jsoup.parse(findTime.body());
			for (int i = 0; i < tableDoc.select("subject").size(); i++) {
				Element subject = tableDoc.select("subject").get(i);
				timeTable.add(subject.select("name").first().attr("value") 
						+ "-" 
						+ subject.select("professor").first().attr("value") 
						+ "-" 
						+ subject.select("time").first().attr("value"));
			}
			return timeTable;
		} catch (IOException e) {
			e.printStackTrace();
			return timeTable;
		}
	}
}
