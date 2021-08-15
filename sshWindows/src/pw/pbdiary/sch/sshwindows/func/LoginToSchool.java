package pw.pbdiary.sch.sshwindows.func;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class LoginToSchool {
	private static final String ua = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:92.0) Gecko/20100101 Firefox/92.0";
	
	public void loginViaAuth() {
		try {
			Connection.Response loginPage = Jsoup.connect("https://portal.sch.ac.kr/_custom/sch/oauth/login_portal.jsp")
					.userAgent(ua)
					.followRedirects(true)
					.method(Connection.Method.GET)
					.execute();
			Map<String, String> cookies = loginPage.cookies();
			Document loginPageDoc = loginPage.parse();
//			Element idInput = loginPageDoc.getElementById("id");
//			idInput.text(id);
//			Element pwInput = loginPageDoc.getElementById("passw");
//			pwInput.text(pw);
//			
//			System.out.println(loginPageDoc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
