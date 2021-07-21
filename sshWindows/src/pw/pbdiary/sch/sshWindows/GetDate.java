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
		String thisHour = today.format(DateTimeFormatter.ofPattern("kk00"));
		return thisHour;
	}
}
