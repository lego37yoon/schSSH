package pw.pbdiary.sch.sshwindows;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatLightLaf;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;
import pw.pbdiary.sch.sshwindows.func.GetDate;
import pw.pbdiary.sch.sshwindows.func.GetNotice;
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
		
		//DB ��� ���� ���� (�� ��, �޸�)
		DatabaseController dbController = new DatabaseController();
		//���� �� ����
		GetNotice parser = new GetNotice();
		//�ð� �޾ƿ���
		GetDate gd = new GetDate();
		//UI ����
		FlatLightLaf.setup();
		//�۲� ����
		Font celciusFont = new Font("���� ��� Semilight", Font.PLAIN, 28);
		Font titleFont = new Font("���� ��� Semilight", Font.PLAIN, 18);
		Font contentFont = new Font("���� ��� Semilight", Font.PLAIN, 14);
		
		
		frame = new JFrame();
		frame.setTitle("schSSH");
		frame.setSize(900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 3, 5, 5));
		
		//�л�����, ��������, �л����
		
		JPanel noticePanel = new NoticePanel(titleFont, contentFont, parser, gd);
		frame.getContentPane().add(noticePanel);
		
		// ���� �ð�ǥ
		
		JPanel timeTablePanel = new TimeTablePanel(titleFont, contentFont);
		frame.getContentPane().add(timeTablePanel);
		
		//������ �� ��
		
		JPanel todoPanel = new ToDoPanel(titleFont, contentFont, dbController, gd);
		frame.getContentPane().add(todoPanel);
		
		// ���� �޸�
		JPanel editorPanel = new MemoPanel(titleFont, contentFont, dbController);
		frame.getContentPane().add(editorPanel);
		
		//����
		JPanel weatherPanel = new WeatherPanel(titleFont, celciusFont, contentFont);
		frame.getContentPane().add(weatherPanel);
		
		//�޴� �г�
		JPanel menuPanel = new MenuPanel(titleFont, contentFont);
		frame.getContentPane().add(menuPanel);
		
		frame.setVisible(true);
	}
}
