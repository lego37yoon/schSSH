package pw.pbdiary.sch.sshWindows;

import java.time.LocalDateTime;

public class GetDate {
	private LocalDateTime today = LocalDateTime.now();
	
	public String getTodayDate() {
		String todayDate = String.valueOf(today.getYear());
		if (today.getMonthValue() < 10) {
			todayDate += "0" + today.getMonthValue();
		} else {
			todayDate += today.getMonthValue();
		} //10�� ������ �տ� 0 ���̱�
		
		if (today.getDayOfMonth() < 10) {
			todayDate += "0" + today.getDayOfMonth();
		} else {
			todayDate += today.getDayOfMonth();
		} //10�� ������ �տ� 0 ���̱�
		
		return todayDate;
	}
	
	public String getTodayHour() {
		String thisHour = "0000";
		
		if (today.getHour() < 10) {
			thisHour = "0" + today.getHour() + "00";
		} else {
			thisHour = today.getHour() + "00";
		}
		
		return thisHour;
	}
}
