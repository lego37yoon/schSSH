package pw.pbdiary.sch.sshWindows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {
	private static final String toDo = "todo.db";
	private static final String simpleMemo = "memo.db";
	
	private static void initDatabase(String fileName) {
		String url = "jdbc:sqlite:" + fileName;
		
		try {
			Connection conn = DriverManager.getConnection(url);
			
			if (conn != null) {
				Statement stm = conn.createStatement();
				stm.setQueryTimeout(30);
				
				if (fileName.equals(toDo)) {
					stm.executeUpdate("CREATE TABLE IF NOT EXISTS TODO ("
							+ "ID INTEGER NOT NULL," //할 일 ID
							+ "DATE TEXT NOT NULL," //
							+ "TITLE TEXT NOT NULL,"
							+ "ALARM TEXT,"
							+ "DONE INTEGER NOT NULL,"
							+ "PRIMARY KEY (ID))");
				} else if (fileName.equals(simpleMemo)) {
					stm.executeUpdate("CREATE TABLE IF NOT EXISTS MEMO ("
							+ "MEMO TEXT NOT NULL)");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int createNewDo(LocalDate day, String title, boolean done) { //할 일 생성 (알림 없음)
		int id = 0;
		initDatabase(toDo);
		
		return id;
	}
	
	public int createNewDo(LocalDate day, LocalDateTime alarm, String title, boolean done) { //할 일 생성 (알림 있음)
		int id = 0;
		initDatabase(toDo);
		
		return id;
	}
	
	public boolean deleteDo(int id) { //할 일 삭제
		initDatabase(toDo);
		
		return true;
	}
	
	public int editDo(int id, LocalDate day) { //날짜 변경
		initDatabase(toDo);
		
		return id;
	}
	
	public int editDo(int id, LocalDateTime alarm) { //알림 변경 혹은 추가
		initDatabase(toDo);
		
		return id;
	}
	
	public int deleteAlarm(int id) { //알림 삭제
		initDatabase(toDo);
		
		return id;
	}
	
	public int editDo(int id, String title) { //할 일 제목 변경
		initDatabase(toDo);
		
		return id;
	}
	
	public int editDo(int id, boolean done) { //완료 여부 변경
		initDatabase(toDo);
		
		return id;
	}
	
	public boolean saveMemo(String memo) { //간단 메모 저장
		initDatabase(simpleMemo);
		
		return true;
	}
	
	public boolean deleteMemo() { //간단 메모 삭제
		initDatabase(simpleMemo);
		
		return true;
	}
} 
