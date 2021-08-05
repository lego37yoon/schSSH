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
							+ "ID INTEGER NOT NULL," //�� �� ID
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
	
	public int createNewDo(LocalDate day, String title, boolean done) { //�� �� ���� (�˸� ����)
		int id = 0;
		initDatabase(toDo);
		
		return id;
	}
	
	public int createNewDo(LocalDate day, LocalDateTime alarm, String title, boolean done) { //�� �� ���� (�˸� ����)
		int id = 0;
		initDatabase(toDo);
		
		return id;
	}
	
	public boolean deleteDo(int id) { //�� �� ����
		initDatabase(toDo);
		
		return true;
	}
	
	public int editDo(int id, LocalDate day) { //��¥ ����
		initDatabase(toDo);
		
		return id;
	}
	
	public int editDo(int id, LocalDateTime alarm) { //�˸� ���� Ȥ�� �߰�
		initDatabase(toDo);
		
		return id;
	}
	
	public int deleteAlarm(int id) { //�˸� ����
		initDatabase(toDo);
		
		return id;
	}
	
	public int editDo(int id, String title) { //�� �� ���� ����
		initDatabase(toDo);
		
		return id;
	}
	
	public int editDo(int id, boolean done) { //�Ϸ� ���� ����
		initDatabase(toDo);
		
		return id;
	}
	
	public boolean saveMemo(String memo) { //���� �޸� ����
		initDatabase(simpleMemo);
		
		return true;
	}
	
	public boolean deleteMemo() { //���� �޸� ����
		initDatabase(simpleMemo);
		
		return true;
	}
} 
