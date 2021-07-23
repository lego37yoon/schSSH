package pw.pbdiary.sch.sshWindows;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetNotice {
	private Document getMainDocument() throws IOException {
		return Jsoup.connect("https://homepage.sch.ac.kr/sch/index.jsp").get();
	}
	
	
	public String[][] getNormal() {
		String[][] noticeList = new String[2][5];
		
		try {
			Document doc = getMainDocument();
			Elements notices = doc.getElementById("jwxe_1298005370608").nextElementSibling().getElementsByClass("mlist");
			
			for (int i = 0; i < 5; i++) {
				noticeList[0][i] = notices.get(i).child(0).html();
				noticeList[1][i] = notices.get(i).child(0).attr("abs:href");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return noticeList;
	}
	
	public String[][] getSchool() {
		String[][] noticeSchedule = new String[2][5];
		
		try {
			Document doc = getMainDocument();
			Elements notices = doc.getElementById("jwxe_1298005426312").nextElementSibling().getElementsByClass("mlist");
			
			for (int i = 0; i < 5; i++) {
				noticeSchedule[0][i] = notices.get(i).child(0).html();
				noticeSchedule[1][i] = notices.get(i).child(0).attr("abs:href");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return noticeSchedule;
	}
}
