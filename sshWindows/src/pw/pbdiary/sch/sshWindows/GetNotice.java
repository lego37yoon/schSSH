package pw.pbdiary.sch.sshWindows;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetNotice {
	private Document getMainDocument() throws IOException {
		return Jsoup.connect("https://homepage.sch.ac.kr/sch/index.jsp").get();
	}
	
	
	public String[] getNormal() {
		String[] noticeList = {"", "", "", "", ""};
		
		try {
			Document doc = getMainDocument();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return noticeList;
	}
	
	public String[] getSchool() {
		String [] noticeSchedule = {"", "", "", "", ""};
		
		try {
			Document doc = getMainDocument();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return noticeSchedule;
	}
}
