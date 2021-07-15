package pw.pbdiary.sch.sshWindows;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JInternalFrame;

public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		FlatLightLaf.setup();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JTabbedPane noticeTab = new JTabbedPane(JTabbedPane.TOP);
		noticeTab.setFont(new Font("Noto Sans CJK KR Light", Font.PLAIN, 14));
		frame.getContentPane().add(noticeTab);
		
		JList normalNotice = new JList();
		normalNotice.setFont(new Font("Noto Sans CJK KR Light", Font.PLAIN, 14));
		noticeTab.addTab("New tab", null, normalNotice, null);
		
		JList todoList = new JList();
		todoList.setFont(new Font("Noto Sans CJK KR Light", Font.PLAIN, 14));
		frame.getContentPane().add(todoList);
	}

}
