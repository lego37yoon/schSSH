package pw.pbdiary.sch.sshWindows;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{355, 69, 1, 0};
		gridBagLayout.rowHeights = new int[]{36, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane noticeTab = new JTabbedPane(JTabbedPane.TOP);
		noticeTab.setFont(new Font("Noto Sans CJK KR Light", Font.PLAIN, 14));
		GridBagConstraints gbc_noticeTab = new GridBagConstraints();
		gbc_noticeTab.anchor = GridBagConstraints.NORTHWEST;
		gbc_noticeTab.insets = new Insets(0, 0, 0, 5);
		gbc_noticeTab.gridx = 1;
		gbc_noticeTab.gridy = 0;
		frame.getContentPane().add(noticeTab, gbc_noticeTab);
		
		JList normalNotice = new JList();
		normalNotice.setFont(new Font("Noto Sans CJK KR Light", Font.PLAIN, 14));
		noticeTab.addTab("New tab", null, normalNotice, null);
		
		JList todoList = new JList();
		todoList.setFont(new Font("Noto Sans CJK KR Light", Font.PLAIN, 14));
		GridBagConstraints gbc_todoList = new GridBagConstraints();
		gbc_todoList.anchor = GridBagConstraints.WEST;
		gbc_todoList.gridx = 2;
		gbc_todoList.gridy = 0;
		frame.getContentPane().add(todoList, gbc_todoList);
	}

}
