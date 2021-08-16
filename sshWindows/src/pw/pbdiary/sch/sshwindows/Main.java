package pw.pbdiary.sch.sshwindows;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;
import pw.pbdiary.sch.sshwindows.func.GetDate;
import pw.pbdiary.sch.sshwindows.func.GetNotice;
import pw.pbdiary.sch.sshwindows.panel.BrowserFrame;
import pw.pbdiary.sch.sshwindows.panel.MemoPanel;
import pw.pbdiary.sch.sshwindows.panel.MenuPanel;
import pw.pbdiary.sch.sshwindows.panel.NoticePanel;
import pw.pbdiary.sch.sshwindows.panel.TimeTablePanel;
import pw.pbdiary.sch.sshwindows.panel.ToDoPanel;
import pw.pbdiary.sch.sshwindows.panel.WeatherPanel;

public class Main {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		initialize();
	}

	// Initialize the contents of the frame.
	private void initialize() {
		
		//DB 사용 영역 공통 (할 일, 메모)
		DatabaseController dbController = new DatabaseController();
		//공지 탭 공통
		GetNotice parser = new GetNotice();
		//시간 받아오기
		GetDate gd = new GetDate();
		//UI 설정
		String theme = dbController.getSettings("THEME");
		if (theme.equals("dark")) {
			FlatDarkLaf.setup();
		} else {
			FlatLightLaf.setup();
			System.out.println("기본값으로 설정합니다.");
		}
		//브라우저 인스턴스 미리 만들기
		BrowserFrame browserWindow = new BrowserFrame(dbController);
		dbController.saveSetting("REDIRECT_URL","");
		//글꼴 설정
		Font celciusFont = new Font("맑은 고딕 Semilight", Font.PLAIN, 28);
		Font titleFont = new Font("맑은 고딕 Semilight", Font.PLAIN, 18);
		Font contentFont = new Font("맑은 고딕 Semilight", Font.PLAIN, 14);
		
		
		frame = new JFrame();
		frame.setTitle("schSSH");
		frame.setSize(900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 3, 5, 5));
		
		//학사일정, 공지사항, 학사공지
		
		JPanel noticePanel = new NoticePanel(titleFont, contentFont, parser, gd, browserWindow);
		frame.getContentPane().add(noticePanel);
		
		// 수업 시간표
		
		JPanel timeTablePanel = new TimeTablePanel(titleFont, contentFont);
		frame.getContentPane().add(timeTablePanel);
		
		//오늘의 할 일
		
		JPanel todoPanel = new ToDoPanel(titleFont, contentFont, dbController, gd);
		frame.getContentPane().add(todoPanel);
		
		// 간단 메모
		JPanel editorPanel = new MemoPanel(titleFont, contentFont, dbController);
		frame.getContentPane().add(editorPanel);
		
		//날씨
		JPanel weatherPanel = new WeatherPanel(titleFont, celciusFont, contentFont, browserWindow);
		frame.getContentPane().add(weatherPanel);
		
		//메뉴 패널
		JPanel menuPanel = new MenuPanel(titleFont, contentFont, dbController, browserWindow);
		frame.getContentPane().add(menuPanel);
		
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				browserWindow.disposeFrame();
				frame.dispose();
			}
		});
	}
}
