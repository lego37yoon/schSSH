package pw.pbdiary.sch.sshwindows.func;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;

public class GetNewMail {
	private static final String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:90.0) Gecko/20100101 Firefox/90.0";
	
	public int get(String id, String pw) {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("userId_encrypt", "");
		dataMap.put("encAESKey", "");
		dataMap.put("language", "ko");
		dataMap.put("userId", id);
		dataMap.put("password", pw);
		dataMap.put("_isSave", "on");
		try {
			Connection.Response res = Jsoup.connect("https://mail.sch.ac.kr/account/login.do")
					.data(dataMap)
					.userAgent(ua)
					.method(Method.POST)
					.execute();
			Document doc = Jsoup.connect("https://mail.sch.ac.kr/mail/list.do")
					.cookies(res.cookies())
					.userAgent(ua)
					.get();
			
			String mailBoxData = doc.select("script[type='text/javascript']").get(59).data();
			String mailInfo = mailBoxData.substring(mailBoxData.lastIndexOf("mailinfo = ") + 11).split("}};")[0] + "}}";
			JSONObject mailObject = new JSONObject(mailInfo);
			JSONArray mailBoxArray = mailObject.getJSONObject("folderInfo").getJSONArray("mailboxlist");
			for (int i = 0; i < mailBoxArray.length(); i++) {
				if (mailBoxArray.getJSONObject(i).get("folderKey").equals("NEWMAIL")) {
					return mailBoxArray.getJSONObject(i).getInt("mailcount");
				}
			}
			System.out.println("안 읽은 메일을 불러오지 못했습니다.");
			return -1;
		} catch (IOException e) {
			System.out.println("네트워크 연결을 확인하세요");
			return -1;
		}
	}
}
