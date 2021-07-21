package pw.pbdiary.sch.sshWindows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetDate {
	private LocalDateTime today = LocalDateTime.now();
	
	public String getTodayDate() {
		String todayDate = today.format(DateTimeFormatter.BASIC_ISO_DATE);
		return todayDate;
	}
	
	public String getTodayHour() {
		//기상청 초단기실황 API 특성 상 현재 시각 기준 40분부터 API 제공하므로
		//그 전까지는 1시간 이전 정보 제공
		String thisHour = "0000";
		if (today.getMinute() < 41) {
			thisHour = today.minusHours(1).format(DateTimeFormatter.ofPattern("kk00"));
		} else {
			thisHour = today.format(DateTimeFormatter.ofPattern("kk00"));
		}
		return thisHour;
	}
}
