package pw.pbdiary.sch.sshWindows;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatabaseController {
	
	public int createNew(LocalDate day, String title, boolean done) { //할 일 생성 (알림 없음)
		int id = 0;
		
		return id;
	}
	
	public int createNew(LocalDate day, LocalDateTime alarm, String title, boolean done) { //할 일 생성 (알림 있음)
		int id = 0;
		
		return id;
	}
	
	public boolean deleteDo(int id) { //할 일 삭제
	
		return true;
	}
	
	public int editDo(int id, LocalDate day) { //날짜 변경
		
		
		return id;
	}
	
	public int editDo(int id, LocalDateTime alarm) { //알림 변경 혹은 추가
		
		return id;
	}
	
	public int deleteAlarm(int id) { //알림 삭제
		
		return id;
	}
	
	public int editDo(int id, String title) { //할 일 제목 변경
		
		return id;
	}
	
	public int editDo(int id, boolean done) { //완료 여부 변경
		
		return id;
	}
}
