package pw.pbdiary.sch.sshWindows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.sql.Connection;
import java.sql.DriverManager;
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
				+ "ID INTEGER NOT NULL AUTOINCREMENT," //�޸� ID - PRIMARY KEY, �ڵ� ���� �ɼ��� �����ϳ�, �޸�� ���� �ϳ��� ����
				+ "MEMO TEXT NOT NULL)"); //�޸� ���� �ؽ�Ʈ
		stm.executeUpdate("CREATE TABLE IF NOT EXISTS SETTINGS (" //����
				+ "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," //������ ID
				+ "TYPE TEXT NOT NULL," //������ �̸�
				+ "VALUE TEXT NOT NULL);"); //������ ����
		
		return stm;
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
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
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
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
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
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
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
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
		}
		
		return id;
	}
	
	public int deleteAlarm(int id) { //�˸� ����
		try {
			Statement stm = initDatabase();
			stm.executeUpdate("UPDATE TODO SET ALARM=NULL WHERE ID=" + id + ";");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
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
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
		}
		
		return id;
	}
	
	public int editDo(int id, boolean done) { //�Ϸ� ���� ����
		try {
			initDatabase();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
		}
		
		return id;
	}
	
	public boolean saveMemo(String memo) { //���� �޸� ����
		try {
			initDatabase();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
		}
		
		return true;
	}
	
	public boolean deleteMemo() { //���� �޸� ����
		try {
			initDatabase();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�.");
		}
		
		return true;
	}
}