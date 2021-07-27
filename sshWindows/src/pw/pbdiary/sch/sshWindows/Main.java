package pw.pbdiary.sch.sshWindows;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.JList;
import java.awt.GridLayout;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;

public class Main {

	private JFrame frame;
	private JTable timeTable;

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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		FlatLightLaf.setup();
		
		frame = new JFrame();
		frame.setTitle("schSSH");
		frame.setSize(900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 3, 5, 5));
		
		JPanel noticePanel = new JPanel();
		frame.getContentPane().add(noticePanel);
		GridBagLayout gbl_noticePanel = new GridBagLayout();
		gbl_noticePanel.columnWidths = new int[]{240, 0};
		gbl_noticePanel.rowHeights = new int[]{25, 0, 0};
		gbl_noticePanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_noticePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
		noticePanel.setLayout(gbl_noticePanel);
		
		JTabbedPane noticeTab = new JTabbedPane(JTabbedPane.TOP);
		noticeTab.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 14));
		GridBagConstraints gbc_noticeTab = new GridBagConstraints();
		gbc_noticeTab.gridx = 0;
		gbc_noticeTab.gridwidth = GridBagConstraints.REMAINDER;
		gbc_noticeTab.gridy = 0;
		gbc_noticeTab.weighty = 0.95;
		gbc_noticeTab.fill = GridBagConstraints.BOTH;
		noticePanel.add(noticeTab, gbc_noticeTab);
		
		GetNotice parser = new GetNotice();
		String[][] normalData = parser.getNormal();
		String[][] schoolScheduleData = parser.getSchool();
		
		JList<String> normalNoticeList = new JList<String>(normalData[0]);
		normalNoticeList.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		noticeTab.addTab("°øÁö»çÇ×", null, normalNoticeList, null);
		
		JList<String> scheduleNoticeList = new JList<String>(schoolScheduleData[0]);
		scheduleNoticeList.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		noticeTab.addTab("ÇÐ»ç°øÁö", null, scheduleNoticeList, null);
		
		JButton btnGoNotice = new JButton("\uBC14\uB85C\uAC00\uAE30");
		btnGoNotice.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		GridBagConstraints gbc_btnGoNotice = new GridBagConstraints();
		gbc_btnGoNotice.gridx = 0;
		gbc_btnGoNotice.gridwidth = GridBagConstraints.REMAINDER;
		gbc_btnGoNotice.weighty = 0.05;
		gbc_btnGoNotice.fill = GridBagConstraints.HORIZONTAL;
		noticePanel.add(btnGoNotice, gbc_btnGoNotice);
		
		// ¼ö¾÷ ½Ã°£Ç¥
		
		JPanel timeTablePanel = new JPanel();
		frame.getContentPane().add(timeTablePanel);
		GridBagLayout gbl_timeTablePanel = new GridBagLayout();
		gbl_timeTablePanel.columnWidths = new int[]{240, 0};
		gbl_timeTablePanel.rowHeights = new int[]{25, 0, 0};
		gbl_timeTablePanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_timeTablePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
		timeTablePanel.setLayout(gbl_timeTablePanel);
		

		JLabel timeTableLabel = new JLabel("\uC218\uC5C5 \uC2DC\uAC04\uD45C");
		timeTableLabel.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 14));
		GridBagConstraints gbc_timeTableLabel = new GridBagConstraints();
		gbc_timeTableLabel.gridx = 0;
		gbc_timeTableLabel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_timeTableLabel.gridy = 0;
		gbc_timeTableLabel.weighty = 0.05;
		gbc_timeTableLabel.fill = GridBagConstraints.HORIZONTAL;
		timeTablePanel.add(timeTableLabel, gbc_timeTableLabel);
		
		timeTable = new JTable();
		timeTable.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 14));
		GridBagConstraints gbc_timeTable = new GridBagConstraints();
		gbc_timeTable.gridx = 0;
		gbc_timeTable.gridwidth = GridBagConstraints.REMAINDER;
		gbc_timeTable.weighty = 0.9;
		gbc_timeTable.fill = GridBagConstraints.BOTH;
		timeTablePanel.add(timeTable, gbc_timeTable);
		
		JButton btnSyncTimeTable = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		btnSyncTimeTable.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSyncTimeTable.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		GridBagConstraints gbc_btnSyncTimeTable = new GridBagConstraints();
		gbc_btnSyncTimeTable.gridx = 0;
		gbc_btnSyncTimeTable.gridwidth = GridBagConstraints.REMAINDER;
		gbc_btnSyncTimeTable.weighty = 0.05;
		gbc_btnSyncTimeTable.fill = GridBagConstraints.HORIZONTAL;
		timeTablePanel.add(btnSyncTimeTable, gbc_btnSyncTimeTable);
		
		//¿À´ÃÀÇ ÇÒ ÀÏ
		JPanel todoPanel = new JPanel();
		frame.getContentPane().add(todoPanel);
		GridBagLayout gbl_todoPanel = new GridBagLayout();
		gbl_todoPanel.columnWidths = new int[]{240, 0};
		gbl_todoPanel.rowHeights = new int[]{25, 0, 0};
		gbl_todoPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_todoPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
		todoPanel.setLayout(gbl_todoPanel);
		
		JLabel todoLabel = new JLabel("\uC624\uB298\uC758 \uD560 \uC77C");
		todoLabel.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 14));
		GridBagConstraints gbc_todoLabel = new GridBagConstraints();
		gbc_todoLabel.gridx = 0;
		gbc_todoLabel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_todoLabel.gridy = 0;
		gbc_todoLabel.weighty = 0.05;
		gbc_todoLabel.fill = GridBagConstraints.HORIZONTAL;
		todoPanel.add(todoLabel, gbc_todoLabel);
		
		JList todoList = new JList();
		todoList.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 14));
		GridBagConstraints gbc_todoList = new GridBagConstraints();
		gbc_todoList.gridx = 0;
		gbc_todoList.weighty = 0.9;
		gbc_todoList.gridwidth = GridBagConstraints.REMAINDER;
		gbc_todoList.fill = GridBagConstraints.BOTH;
		todoPanel.add(todoList, gbc_todoList);
		
		JPanel todoManagePanel = new JPanel();
		GridBagConstraints gbc_todoManagePanel = new GridBagConstraints();
		gbc_todoManagePanel.gridx = 0;
		gbc_todoManagePanel.weighty = 0.05;
		gbc_todoManagePanel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_todoManagePanel.fill = GridBagConstraints.HORIZONTAL;
		todoPanel.add(todoManagePanel, gbc_todoManagePanel);
		todoManagePanel.setLayout(new BoxLayout(todoManagePanel, BoxLayout.X_AXIS));
		
		JButton btnDoneDo = new JButton("\uC644\uB8CC");
		btnDoneDo.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		todoManagePanel.add(btnDoneDo);
		
		JButton btnDeleteDo = new JButton("\uC0AD\uC81C");
		btnDeleteDo.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		todoManagePanel.add(btnDeleteDo);
		
		JButton btnManageDo = new JButton("\uAD00\uB9AC");
		btnManageDo.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		todoManagePanel.add(btnManageDo);
		
		JButton btnSyncDo = new JButton("\uB3D9\uAE30\uD654");
		btnSyncDo.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		todoManagePanel.add(btnSyncDo);
		
		// °£´Ü ¸Þ¸ð
		JPanel editorPanel = new JPanel();
		frame.getContentPane().add(editorPanel);
		GridBagLayout gbl_editorPanel = new GridBagLayout();
		gbl_editorPanel.columnWidths = new int[]{240, 0};
		gbl_editorPanel.rowHeights = new int[]{25, 0, 0};
		gbl_editorPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_editorPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
		editorPanel.setLayout(gbl_editorPanel);
		
		JLabel editorLabel = new JLabel("\uAC04\uB2E8 \uBA54\uBAA8");
		editorLabel.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 14));
		GridBagConstraints gbc_editorLabel = new GridBagConstraints();
		gbc_editorLabel.gridx = 0;
		gbc_editorLabel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_editorLabel.gridy = 0;
		gbc_editorLabel.weighty = 0.05;
		gbc_editorLabel.fill = GridBagConstraints.BOTH;
		editorPanel.add(editorLabel, gbc_editorLabel);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		GridBagConstraints gbc_editorPane = new GridBagConstraints();
		gbc_editorPane.gridx = 0;
		gbc_editorPane.gridwidth = GridBagConstraints.REMAINDER;
		gbc_editorPane.weighty = 0.9;
		gbc_editorPane.fill = GridBagConstraints.BOTH;
		editorPanel.add(editorPane, gbc_editorPane);
		
		JPanel editorManagePanel = new JPanel();
		GridBagConstraints gbc_editorManagePanel = new GridBagConstraints();
		gbc_editorManagePanel.gridx = 0;
		gbc_editorManagePanel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_editorManagePanel.weighty = 0.05;
		gbc_editorManagePanel.fill = GridBagConstraints.HORIZONTAL;
		editorPanel.add(editorManagePanel, gbc_editorManagePanel);
		editorManagePanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnExportSimpleMemo = new JButton("\uB0B4\uBCF4\uB0B4\uAE30");
		btnExportSimpleMemo.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		editorManagePanel.add(btnExportSimpleMemo);
		
		JButton btnSaveSimpleMemo = new JButton("\uC800\uC7A5");
		btnSaveSimpleMemo.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		editorManagePanel.add(btnSaveSimpleMemo);
		
		JButton btnRemoveAll = new JButton("\uBAA8\uB450 \uC9C0\uC6B0\uAE30");
		btnRemoveAll.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 12));
		editorManagePanel.add(btnRemoveAll);
		
		//³¯¾¾
		JPanel weatherPanel = new JPanel();
		frame.getContentPane().add(weatherPanel);
		GridBagLayout gbl_weatherPanel = new GridBagLayout();
		gbl_weatherPanel.columnWidths = new int[]{240, 0};
		gbl_weatherPanel.rowHeights = new int[]{25, 0, 0};
		gbl_weatherPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_weatherPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
		weatherPanel.setLayout(gbl_weatherPanel);
		
		JLabel weatherLabel = new JLabel("\uB0A0\uC528");
		weatherLabel.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 14));
		GridBagConstraints gbc_weatherLabel = new GridBagConstraints();
		gbc_weatherLabel.gridx = 0;
		gbc_weatherLabel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_weatherLabel.gridy = 0;
		gbc_weatherLabel.weighty = 0.05;
		gbc_weatherLabel.fill = GridBagConstraints.HORIZONTAL;
		weatherPanel.add(weatherLabel, gbc_weatherLabel);
		
		JLabel weatherInfoTemp = new JLabel("TEST");
		weatherLabel.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 14));
		GridBagConstraints gbc_weatherInfoTemp = new GridBagConstraints();
		gbc_weatherInfoTemp.gridx = 0;
		gbc_weatherInfoTemp.gridwidth = GridBagConstraints.REMAINDER;
		gbc_weatherInfoTemp.weighty = 0.95;
		gbc_weatherInfoTemp.fill = GridBagConstraints.BOTH;
		weatherPanel.add(weatherInfoTemp, gbc_weatherInfoTemp);
				
		JPanel menuPanel = new JPanel();
		frame.getContentPane().add(menuPanel);
		menuPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnWebMail = new JButton("\uC6F9 \uBA54\uC77C 0\uAC74");
		btnWebMail.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 18));
		btnWebMail.setHorizontalAlignment(SwingConstants.LEFT);
		menuPanel.add(btnWebMail);
		
		JButton btnGoPortal = new JButton("\uC21C\uCC9C\uD5A5\uB300 \uD3EC\uD138");
		btnGoPortal.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 18));
		btnGoPortal.setHorizontalAlignment(SwingConstants.LEFT);
		menuPanel.add(btnGoPortal);
		
		JButton btnGoInfoSys = new JButton("\uC885\uD569\uC815\uBCF4\uC2DC\uC2A4\uD15C");
		btnGoInfoSys.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 18));
		btnGoInfoSys.setHorizontalAlignment(SwingConstants.LEFT);
		menuPanel.add(btnGoInfoSys);
		
		JButton btnSettings = new JButton("\uC124\uC815");
		btnSettings.setFont(new Font("¸¼Àº °íµñ Semilight", Font.PLAIN, 18));
		btnSettings.setHorizontalAlignment(SwingConstants.LEFT);
		menuPanel.add(btnSettings);
		
		frame.setVisible(true);
	}

}
