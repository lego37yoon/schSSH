package pw.pbdiary.sch.sshWindows;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetNotice {
	private Document getMainDocument() throws IOException {
		return Jsoup.connect("https://home.sch.ac.kr/sch/index.jsp").get();
	}
	
	private Document getScheduleDocument() throws IOException {
		return Jsoup.connect("https://home.sch.ac.kr/sch/05/010000.jsp").get();
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
	
	public Element getScheduleElement(Document doc, int month) throws IOException {
		return doc.getElementsByClass(switch(month + 1) {
			case 1 -> "month_1";
			case 2 -> "month_2";
			case 3 -> "month_3";
			case 4 -> "month_4";
			case 5 -> "month_5";
			case 6 -> "month_6";
			case 7 -> "month_7";
			case 8 -> "month_8";
			case 9 -> "month_9";
			case 10 -> "month_10";
			case 11 -> "month_11";
			case 12 -> "month_12";
			default -> throw new IOException();
		}).get(0).child(1);
	}
	
	public HashMap<String, String> getSchedule() {
		HashMap<String, String> scheduleMap = new HashMap<String, String>();
		
		GetDate gd = new GetDate();
		int month = gd.getMonth();
		int day = gd.getTodayDay();
		
		try {
			Document doc = getScheduleDocument();
			Element thisMonthSchedule = getScheduleElement(doc, month);
			
			if ((month == 2 && day > 20) || (day > 22)) {
				Element nextMonthSchedule = getScheduleElement(doc, month + 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("웹페이지를 가져오지 못했습니다. 인터넷 연결을 확인하세요.");
		}
		
		return scheduleMap;
	}
}
