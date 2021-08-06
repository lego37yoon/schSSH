package pw.pbdiary.sch.sshWindows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetDate {
	private LocalDateTime today = LocalDateTime.now();
	
	public String getTodayDate() {
		String todayDate = today.format(DateTimeFormatter.BASIC_ISO_DATE);
		return todayDate;
	}
	
	public String getTodayDateHReadable() {
		return today.format(DateTimeFormatter.ofPattern("yyyy�� MM�� dd�� E����"));
	}
	
	public String getYesterdayDate() {
		return today.minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
	}
	
	public String getTodayHour() {
		//���û �ʴܱ��Ȳ API Ư�� �� ���� �ð� ���� 40�к��� API �����ϹǷ�
		//�� �������� 1�ð� ���� ���� ����
		String thisHour = "0000";
		if (today.getMinute() < 41) {
			thisHour = today.minusHours(1).format(DateTimeFormatter.ofPattern("kk00"));
		} else {
			thisHour = today.format(DateTimeFormatter.ofPattern("kk00"));
		}
		return thisHour;
	}
	
	public int getMonth() {
		return today.getMonthValue();
	}
	
	public int getTodayDay() {
		return today.getDayOfMonth();
	}
}
