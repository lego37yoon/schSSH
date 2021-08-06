package pw.pbdiary.sch.sshWindows;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class LoginToSchool {
	private static final String ua = "";
	
	private void loginViaAuth(String id, String pw) {
		try {
			Connection.Response loginData = Jsoup.connect("https://sso.sch.ac.kr/oa/au/auth/verify")
					.userAgent(ua)
					.referrer("https://sso.sch.ac.kr/oa/au/auth?login_endpoint=oauth&retUrl=")
					.followRedirects(true)
					.method(Connection.Method.POST)
					.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
