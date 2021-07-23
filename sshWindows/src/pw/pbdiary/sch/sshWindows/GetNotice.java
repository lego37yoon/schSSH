package pw.pbdiary.sch.sshWindows;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetNotice {
	private Document getMainDocument() throws IOException {
		return Jsoup.connect("https://homepage.sch.ac.kr/sch/index.jsp").get();
	}
	
	
	public String[] getNormal() {
		String[] noticeList = {"", "", "", "", ""};
		
		try {
			Document doc = getMainDocument();
			Elements notices = doc.getElementById("jwxe_1298005370608").nextElementSibling().getElementsByClass("mlist");
			
			for (int i = 0; i < 5; i++) {
				noticeList[i] = notices.get(i).child(0).html();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return noticeList;
	}
	
	public String[] getSchool() {
		String [] noticeSchedule = {"", "", "", "", ""};
		
		try {
			Document doc = getMainDocument();
			Elements notices = doc.getElementById("jwxe_1298005426312").nextElementSibling().getElementsByClass("mlist");
			
			for (int i = 0; i < 5; i++) {
				noticeSchedule[i] = notices.get(i).child(0).html();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return noticeSchedule;
	}
}
