package pw.pbdiary.sch.sshWindows;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetNotice {
	private Document getMainDocument() throws IOException {
		return Jsoup.connect("https://home.sch.ac.kr/sch/index.jsp").get();
	}
	
	
	public String[][] getNormal() {
		String[][] noticeList = new String[2][6];
		
		try {
			Document doc = getMainDocument();
			Element notices = doc.getElementsByClass("notice").get(0).child(1);
			
			for (int i = 0; i < 6; i++) {
				noticeList[0][i] = notices.child(i).child(0).attr("title");
				noticeList[1][i] = notices.child(i).child(0).attr("abs:href");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("웹페이지를 가져오지 못했습니다. 인터넷 연결을 확인하세요.");
		}
		
		return noticeList;
	}
	
	public String[][] getSchool() {
		String[][] noticeSchedule = new String[2][6];
		
		try {
			Document doc = getMainDocument();
			Element notices = doc.getElementsByClass("notice").get(1).child(1);
			
			for (int i = 0; i < 6; i++) {
				noticeSchedule[0][i] = notices.child(i).child(0).attr("title");
				noticeSchedule[1][i] = notices.child(i).child(0).attr("abs:href");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("웹페이지를 가져오지 못했습니다. 인터넷 연결을 확인하세요.");
		}
	
		return noticeSchedule;
	}
}
