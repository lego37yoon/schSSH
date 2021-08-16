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
		stm.executeUpdate("CREATE TABLE IF NOT EXISTS TODO (" //�� ��
				+ "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," //�� �� ID - PRIMARY KEY, �ڵ� ����
				+ "DATE TEXT NOT NULL," //�� ���� ��¥
				+ "TITLE TEXT NOT NULL," //����
				+ "ALARM TEXT," //�˸� ����
				+ "DONE INTEGER NOT NULL);"); //�Ϸ� ����
		stm.executeUpdate("CREATE TABLE IF NOT EXISTS MEMO (" //���� �޸�
				+ "ID INTEGER NOT NULL PRIMARY KEY," //�޸� ID - PRIMARY KEY, �ڵ� ���� �ɼ��� �����ϳ�, �޸�� ���� �ϳ��� ����
				+ "MEMO TEXT)"); //�޸� ���� �ؽ�Ʈ
		
		stm.executeUpdate("INSERT OR IGNORE INTO MEMO (ID, MEMO) VALUES(1, '<html></html>');");
		
		stm.executeUpdate("CREATE TABLE IF NOT EXISTS SETTINGS (" //����
				+ "TYPE TEXT NOT NULL PRIMARY KEY," //������ �̸�
				+ "VALUE TEXT NOT NULL);"); //������ ����
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
			System.out.println("���� �������� �ʾҽ��ϴ�.");
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
			System.out.println("�� �ϸ� ������ ���߽��ϴ�.");
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
			System.out.println("�޸� �ҷ����� ���߽��ϴ�.");
		} finally {
			try {
				stm.close();
			} catch (SQLException e) {
			}
		}
		
		return "<html></html>";
	}
	
	public boolean createNewDo(LocalDate day, String title, boolean done) { //�� �� ���� (�˸� ����)
		
		String date = "'" + day.toString() + "'";
		String titleSQL = "'" + title + "'";
		
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("INSERT INTO TODO (DATE, TITLE, DONE) VALUES (" + date + titleSQL + done +");"); //�� �� �߰�
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
			return false;
		}
		
		
		return true;
	}
	
	public boolean createNewDo(LocalDate day, LocalDateTime alarm, String title, boolean done) { //�� �� ���� (�˸� ����)
		String date = "'" + day.toString() + "'";
		String titleSQL = "'" + title + "'";
		String alarmSQL = "'" + alarm.toString() + "'";
		
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("INSERT INTO TODO (DATE, TITLE, ALARM, DONE) VALUES (" + date + titleSQL + alarmSQL + done +");"); //�� �� �߰�
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
			return false;
		}
		
		return true;
	}
	
	public boolean deleteDo(int id) { //�� �� ����
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("DELETE FROM TODO WHERE ID=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
			return false;
		}

		return true;
	}
	
	public int editDo(int id, LocalDate day) { //��¥ ����
		String date = "'" + day.toString() + "'";
		
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("UPDATE TODO SET DATE=" + date + " WHERE ID=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
		}
		
		return id;
	}
	
	public int editDo(int id, LocalDateTime alarm) { //�˸� ���� Ȥ�� �߰�
		String alarmSQL = "'" + alarm.toString() + "'";
		
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("UPDATE TODO SET ALARM=" + alarmSQL + " WHERE ID=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
		}
		
		return id;
	}
	
	public int deleteAlarm(int id) { //�˸� ����
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("UPDATE TODO SET ALARM=NULL WHERE ID=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
		}
		
		return id;
	}
	
	public int editDo(int id, String title) { //�� �� ���� ����
		String titleSQL = "'" + title + "'";
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("UPDATE TODO SET TITLE=" + titleSQL + " WHERE ID=" + id +";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
		}
		
		return id;
	}
	
	public int editDo(int id, boolean done) { //�Ϸ� ���� ����
		try {
			Statement stm = initDatabase();
			if (done == true) {
				stm.executeUpdate("UPDATE TODO SET DONE=0 WHERE ID=" + id + ";");
			} else {
				stm.executeUpdate("UPDATE TODO SET DONE=1 WHERE ID=" + id + ";");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
		}
		
		return id;
	}
	
	public boolean saveMemo(String memo) { //���� �޸� ����
		String memoString = "'" + memo + "'";
		
		//UPSET ���� - SQLite 3.24.0���� ����
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("INSERT INTO MEMO (ID, MEMO) VALUES(1, " + memoString + ") ON CONFLICT(ID) DO UPDATE SET MEMO=excluded.MEMO;");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
		}
		
		return true;
	}
	
	public boolean deleteMemo() { //���� �޸� ����
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("INSERT INTO MEMO (ID, MEMO) VALUES(1, NULL) ON CONFLICT(ID) DO UPDATE SET MEMO=excluded.MEMO;");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
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
			System.out.println("�����ͺ��̽� �۾��� �����Ͽ����ϴ�.");
			return false;
		}
	}
}