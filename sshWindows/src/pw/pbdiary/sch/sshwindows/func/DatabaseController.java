package pw.pbdiary.sch.sshwindows.func;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController {
	private static final String data = "data.db";
	
	private static Statement initDatabase() throws SQLException {
		String url = "jdbc:sqlite:" + data;
		
		Connection conn = DriverManager.getConnection(url);
			
		Statement stm = conn.createStatement();
		stm.setQueryTimeout(30);
		stm.executeUpdate("CREATE TABLE IF NOT EXISTS TODO (" //할 일
				+ "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," //할 일 ID - PRIMARY KEY, 자동 증가
				+ "DATE TEXT NOT NULL," //할 일의 날짜
				+ "TITLE TEXT NOT NULL," //제목
				+ "ALARM TEXT," //알림 설정
				+ "DONE INTEGER NOT NULL);"); //완료 여부
		stm.executeUpdate("CREATE TABLE IF NOT EXISTS MEMO (" //간단 메모
				+ "ID INTEGER NOT NULL PRIMARY KEY," //메모 ID - PRIMARY KEY, 자동 증가 옵션이 존재하나, 메모는 오직 하나만 존재
				+ "MEMO TEXT)"); //메모 내부 텍스트
		
		stm.executeUpdate("INSERT OR IGNORE INTO MEMO (ID, MEMO) VALUES(1, '<html></html>');");
		
		stm.executeUpdate("CREATE TABLE IF NOT EXISTS SETTINGS (" //설정
				+ "TYPE TEXT NOT NULL PRIMARY KEY," //설정값 이름
				+ "VALUE TEXT NOT NULL);"); //설정값 내용
		return stm;
	}
	
	public String getSettings(String type) {
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = initDatabase();
			rs = stm.executeQuery("SELECT VALUE FROM SETTINGS WHERE TYPE='" + type +"';");
			String returnValue = rs.getString("VALUE");
			rs.close();
			return returnValue;
		} catch (SQLException e) {
			System.out.println("값이 지정되지 않았습니다.");
			return "";
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public ResultSet getDo(LocalDate day) {
		String date = "'" + day.toString() + "'";
		Statement stm;
		ResultSet rs = null;
		try {
			stm = initDatabase();
			rs = stm.executeQuery("SELECT ID, TITLE, DONE FROM TODO WHERE DATE=" + date + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("할 일를 얻어오지 못했습니다.");
		}
		
		return rs;
	}
	
	public String getMemo() {
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = initDatabase();
			rs = stm.executeQuery("SELECT MEMO FROM MEMO WHERE ID=1;");
			if (rs != null) {
				return rs.getString("MEMO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("메모를 불러오지 못했습니다.");
		} finally {
			try {
				stm.close();
			} catch (SQLException e) {
			}
		}
		
		return "<html></html>";
	}
	
	public boolean createNewDo(LocalDate day, String title, boolean done) { //할 일 생성 (알림 없음)
		
		String date = "'" + day.toString() + "'";
		String titleSQL = "'" + title + "'";
		
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("INSERT INTO TODO (DATE, TITLE, DONE) VALUES (" + date + titleSQL + done +");"); //할 일 추가
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업을 실패하였습니다.");
			return false;
		}
		
		
		return true;
	}
	
	public boolean createNewDo(LocalDate day, LocalDateTime alarm, String title, boolean done) { //할 일 생성 (알림 있음)
		String date = "'" + day.toString() + "'";
		String titleSQL = "'" + title + "'";
		String alarmSQL = "'" + alarm.toString() + "'";
		
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("INSERT INTO TODO (DATE, TITLE, ALARM, DONE) VALUES (" + date + titleSQL + alarmSQL + done +");"); //할 일 추가
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업에 실패하였습니다.");
			return false;
		}
		
		return true;
	}
	
	public boolean deleteDo(int id) { //할 일 삭제
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("DELETE FROM TODO WHERE ID=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업에 실패하였습니다.");
			return false;
		}

		return true;
	}
	
	public int editDo(int id, LocalDate day) { //날짜 변경
		String date = "'" + day.toString() + "'";
		
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("UPDATE TODO SET DATE=" + date + " WHERE ID=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업에 실패하였습니다.");
		}
		
		return id;
	}
	
	public int editDo(int id, LocalDateTime alarm) { //알림 변경 혹은 추가
		String alarmSQL = "'" + alarm.toString() + "'";
		
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("UPDATE TODO SET ALARM=" + alarmSQL + " WHERE ID=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업에 실패하였습니다.");
		}
		
		return id;
	}
	
	public int deleteAlarm(int id) { //알림 삭제
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("UPDATE TODO SET ALARM=NULL WHERE ID=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업에 실패하였습니다.");
		}
		
		return id;
	}
	
	public int editDo(int id, String title) { //할 일 제목 변경
		String titleSQL = "'" + title + "'";
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("UPDATE TODO SET TITLE=" + titleSQL + " WHERE ID=" + id +";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업에 실패하였습니다.");
		}
		
		return id;
	}
	
	public int editDo(int id, boolean done) { //완료 여부 변경
		try {
			Statement stm = initDatabase();
			if (done == true) {
				stm.executeUpdate("UPDATE TODO SET DONE=0 WHERE ID=" + id + ";");
			} else {
				stm.executeUpdate("UPDATE TODO SET DONE=1 WHERE ID=" + id + ";");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업에 실패하였습니다.");
		}
		
		return id;
	}
	
	public boolean saveMemo(String memo) { //간단 메모 저장
		String memoString = "'" + memo + "'";
		
		//UPSET 문법 - SQLite 3.24.0부터 지원
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("INSERT INTO MEMO (ID, MEMO) VALUES(1, " + memoString + ") ON CONFLICT(ID) DO UPDATE SET MEMO=excluded.MEMO;");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업에 실패하였습니다.");
		}
		
		return true;
	}
	
	public boolean deleteMemo() { //간단 메모 삭제
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("INSERT INTO MEMO (ID, MEMO) VALUES(1, NULL) ON CONFLICT(ID) DO UPDATE SET MEMO=excluded.MEMO;");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업에 실패하였습니다.");
		}
		
		return true;
	}

	public boolean saveSettings(HashMap<String, String> settings) {
		try {
			Statement stm = initDatabase();
			for (String key : settings.keySet()) {
				stm.executeUpdate("INSERT INTO SETTINGS (TYPE, VALUE) VALUES('" + key + "', '" + settings.get(key) + "') ON CONFLICT(TYPE) DO UPDATE SET VALUE=excluded.VALUE;");
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이터베이스 작업에 실패하였습니다.");
			return false;
		}
	}
}