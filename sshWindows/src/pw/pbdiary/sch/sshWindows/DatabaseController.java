package pw.pbdiary.sch.sshWindows;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatabaseController {
	
	public int createNew(LocalDate day, String title, boolean done) { //�� �� ���� (�˸� ����)
		int id = 0;
		
		return id;
	}
	
	public int createNew(LocalDate day, LocalDateTime alarm, String title, boolean done) { //�� �� ���� (�˸� ����)
		int id = 0;
		
		return id;
	}
	
	public boolean deleteDo(int id) { //�� �� ����
	
		return true;
	}
	
	public int editDo(int id, LocalDate day) { //��¥ ����
		
		
		return id;
	}
	
	public int editDo(int id, LocalDateTime alarm) { //�˸� ���� Ȥ�� �߰�
		
		return id;
	}
	
	public int deleteAlarm(int id) { //�˸� ����
		
		return id;
	}
	
	public int editDo(int id, String title) { //�� �� ���� ����
		
		return id;
	}
	
	public int editDo(int id, boolean done) { //�Ϸ� ���� ����
		
		return id;
	}
}
