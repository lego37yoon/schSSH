package pw.pbdiary.sch.sshWindows;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetNotice {
	private Document getMainDocument() throws IOException {
		return Jsoup.connect("https://home.sch.ac.kr/sch/index.jsp").get();
	}
	
	private Document getScheduleDocument() throws IOException {
		Connection con = Jsoup.connect("https://home.sch.ac.kr/sch/05/010000.jsp?board_no=20110224223754285127&defparam-year_month=2021-08")
				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:90.0) Gecko/20100101 Firefox/90.0")
				.referrer("https://home.sch.ac.kr/sch/index.jsp")
				.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
				.header("Accept-Encoding", "gzip, deflate, br")
				.header("Accept-Language", "ko-KR,ko;q=0.8,en-US,q=0.5,en;q=0.3");
		con.followRedirects(true);
		return con.get();
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
	
	private Element getScheduleElement(Document doc, int month) throws IOException {
		return doc.getElementsByClass(switch(month) {
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
		}).get(0).child(1).child(0);
	}
	
	private ArrayList<LocalDate> getScheduleDate(int month, String schedule) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d(E)");
		ArrayList<LocalDate> availableDate = new ArrayList<LocalDate>();
		
		if (schedule.contains("~")) {
			LocalDate startDate = LocalDate.parse(LocalDate.now().getYear() + "/" + month + "/" + schedule.split(" ")[0].split("~")[0], formatter);
			availableDate.add(startDate);
			
			if (schedule.split(" ")[0].split("~")[1].contains("/")) {
				LocalDate endDate = LocalDate.parse(LocalDate.now().getYear() + "/" + schedule.split(" ")[0].split("~")[1], formatter);
				availableDate.add(endDate);
			} else {
				LocalDate endDate = LocalDate.parse(LocalDate.now().getYear() + "/" + month + "/" + schedule.split(" ")[0].split("~")[1], formatter);
				availableDate.add(endDate);
			}
		} else {
			LocalDate date = LocalDate.parse(LocalDate.now().getYear() + "/" + month + "/" + schedule.split(" ")[0], formatter);
			availableDate.add(date);
			availableDate.add(date);
		}
		
		return availableDate;
	}
	
	public ArrayList<String> getSchedule() {
		ArrayList<String> scheduleList = new ArrayList<String>();
		
		GetDate gd = new GetDate();
		int month = gd.getMonth();
		int day = gd.getTodayDay();
		
		try {
			Document doc = getScheduleDocument();
			Element thisMonthSchedule = getScheduleElement(doc, month);
			
			for (int i = 1; i < thisMonthSchedule.childrenSize(); i += 2) {
				if (thisMonthSchedule.child(i).html().contains("<ul>")) { //한 날짜에 둘 이상의 일정 있을 시 처리
					for (int j = 0; j < thisMonthSchedule.child(i).child(0).childrenSize(); j++) {
						String schedule = thisMonthSchedule.child(i).child(0).child(j).html();
						ArrayList<LocalDate> dateData = getScheduleDate(month, schedule);
						
						/*
						 * 오늘로부터 7일 이내의 다가올 혹은 다가온 일정 조회
						 */
						if (dateData.get(0).isBefore(LocalDate.now().plusDays(7)) 
								&& (dateData.get(0).isEqual(LocalDate.now()) || dateData.get(0).isAfter(LocalDate.now()))) {
							scheduleList.add(schedule);
						} else if (dateData.get(1) != null) {
							if (dateData.get(1).isBefore(LocalDate.now().plusDays(7)) 
									&& (dateData.get(1).isEqual(LocalDate.now()) || dateData.get(1).isAfter(LocalDate.now()))) {
								scheduleList.add(schedule);
							}
						}
					}
				} else {
					String schedule = thisMonthSchedule.child(i).html();
					ArrayList<LocalDate> dateData = getScheduleDate(month, schedule);
					
					/*
					 * 오늘로부터 7일 이내의 다가올 혹은 다가온 일정 조회
					 */
					if (dateData.get(0).isBefore(LocalDate.now().plusDays(7)) 
							&& (dateData.get(0).isEqual(LocalDate.now()) || dateData.get(0).isAfter(LocalDate.now()))) {
						scheduleList.add( schedule);
					} else if (dateData.get(1) != null) {
						if (dateData.get(1).isBefore(LocalDate.now().plusDays(7)) 
								&& (dateData.get(1).isEqual(LocalDate.now()) || dateData.get(1).isAfter(LocalDate.now()))) {
							scheduleList.add( schedule);
						}
					}
				}
			}
			
			if ((month == 2 && day > 20) || (day > 22)) {
				Element nextMonthSchedule = getScheduleElement(doc, month + 1);
				
				for (int i = 1; i < nextMonthSchedule.childrenSize(); i += 2) {
					if (nextMonthSchedule.child(i).html().contains("<ul>")) { //한 날짜에 둘 이상의 일정 있을 시 처리
						for (int j = 0; j < nextMonthSchedule.child(i).child(0).childrenSize(); j++) {
							String schedule = nextMonthSchedule.child(i).child(0).child(j).html();
							ArrayList<LocalDate> dateData = getScheduleDate(month + 1, schedule);
							
							/*
							 * 오늘로부터 7일 이내의 다가올 혹은 다가온 일정 조회
							 */
							if (dateData.get(0).isBefore(LocalDate.now().plusDays(7)) 
									&& (dateData.get(0).isEqual(LocalDate.now()) || dateData.get(0).isAfter(LocalDate.now()))) {
								scheduleList.add(schedule);
							} else if (dateData.get(1) != null) {
								if (dateData.get(1).isBefore(LocalDate.now().plusDays(7)) 
										&& (dateData.get(1).isEqual(LocalDate.now()) || dateData.get(1).isAfter(LocalDate.now()))) {
									scheduleList.add(schedule);
								}
							}
						}
					} else {
						String schedule = nextMonthSchedule.child(i).html();
						ArrayList<LocalDate> dateData = getScheduleDate(month + 1, schedule);
						
						/*
						 * 오늘로부터 7일 이내의 다가올 혹은 다가온 일정 조회
						 */
						if (dateData.get(0).isBefore(LocalDate.now().plusDays(7)) 
								&& (dateData.get(0).isEqual(LocalDate.now()) || dateData.get(0).isAfter(LocalDate.now()))) {
							scheduleList.add(schedule);
						} else if (dateData.get(1) != null) {
							if (dateData.get(1).isBefore(LocalDate.now().plusDays(7)) 
									&& (dateData.get(1).isEqual(LocalDate.now()) || dateData.get(1).isAfter(LocalDate.now()))) {
								scheduleList.add(schedule);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("웹페이지를 가져오지 못했습니다. 인터넷 연결을 확인하세요.");
		}
		
		return scheduleList;
	}
}
