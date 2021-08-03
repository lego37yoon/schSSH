package pw.pbdiary.sch.sshWindows;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.formdev.flatlaf.FlatLightLaf;
import org.apache.batik.swing.JSVGCanvas;


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

	/*
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		FlatLightLaf.setup();
		
		frame = new JFrame();
		frame.setTitle("schSSH");
		frame.setSize(900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 3, 5, 5));
		
		Font celciusFont = new Font("맑은 고딕 Semilight", Font.PLAIN, 24);
		Font titleFont = new Font("맑은 고딕 Semilight", Font.PLAIN, 18);
		Font contentFont = new Font("맑은 고딕 Semilight", Font.PLAIN, 14);
		
		JPanel noticePanel = new JPanel();
		frame.getContentPane().add(noticePanel);
		GridBagLayout gbl_noticePanel = new GridBagLayout();
		gbl_noticePanel.columnWidths = new int[]{240, 0};
		gbl_noticePanel.rowHeights = new int[]{25, 0, 0};
		gbl_noticePanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_noticePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
		noticePanel.setLayout(gbl_noticePanel);
		
		JTabbedPane noticeTab = new JTabbedPane(JTabbedPane.TOP);
		noticeTab.setFont(titleFont);
		GridBagConstraints gbc_noticeTab = new GridBagConstraints();
		gbc_noticeTab.gridx = 0;
		gbc_noticeTab.gridwidth = GridBagConstraints.REMAINDER;
		gbc_noticeTab.gridy = 0;
		gbc_noticeTab.weighty = 0.95;
		gbc_noticeTab.fill = GridBagConstraints.BOTH;
		noticePanel.add(noticeTab, gbc_noticeTab);
		
		JPanel scheduleOfUniv = new JPanel();
		scheduleOfUniv.setLayout(new GridLayout(1, 0, 0, 0));
		noticeTab.addTab("학사일정", null, scheduleOfUniv, null);
		
		JTextArea recentSchedule = new JTextArea("9(월) ~ 13일(금)" + "에\n" + "2021학년도 2학기 수강과목 확인" + "\n이 예정되어 있습니다.");
		recentSchedule.setFont(contentFont);
		recentSchedule.setEditable(false);
		scheduleOfUniv.add(recentSchedule);
		
		GetNotice parser = new GetNotice();
		String[][] normalData = parser.getNormal();
		String[][] schoolScheduleData = parser.getSchool();
		
		JList<String> normalNoticeList = new JList<String>(normalData[0]);
		normalNoticeList.setFont(contentFont);
		noticeTab.addTab("공지사항", null, normalNoticeList, null);
		
		JList<String> scheduleNoticeList = new JList<String>(schoolScheduleData[0]);
		scheduleNoticeList.setFont(contentFont);
		noticeTab.addTab("학사공지", null, scheduleNoticeList, null);
		
		JButton btnGoNotice = new JButton("\uBC14\uB85C\uAC00\uAE30");
		btnGoNotice.setFont(contentFont);
		GridBagConstraints gbc_btnGoNotice = new GridBagConstraints();
		gbc_btnGoNotice.gridx = 0;
		gbc_btnGoNotice.gridwidth = GridBagConstraints.REMAINDER;
		gbc_btnGoNotice.weighty = 0.05;
		gbc_btnGoNotice.fill = GridBagConstraints.HORIZONTAL;
		noticePanel.add(btnGoNotice, gbc_btnGoNotice);
		
		// 수업 시간표
		
		JPanel timeTablePanel = new JPanel();
		frame.getContentPane().add(timeTablePanel);
		GridBagLayout gbl_timeTablePanel = new GridBagLayout();
		gbl_timeTablePanel.columnWidths = new int[]{240, 0};
		gbl_timeTablePanel.rowHeights = new int[]{25, 0, 0};
		gbl_timeTablePanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_timeTablePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
		timeTablePanel.setLayout(gbl_timeTablePanel);
		

		JLabel timeTableLabel = new JLabel("\uC218\uC5C5 \uC2DC\uAC04\uD45C");
		timeTableLabel.setFont(titleFont);
		GridBagConstraints gbc_timeTableLabel = new GridBagConstraints();
		gbc_timeTableLabel.gridx = 0;
		gbc_timeTableLabel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_timeTableLabel.gridy = 0;
		gbc_timeTableLabel.weighty = 0.05;
		gbc_timeTableLabel.fill = GridBagConstraints.HORIZONTAL;
		timeTablePanel.add(timeTableLabel, gbc_timeTableLabel);
		
		timeTable = new JTable();
		timeTable.setFont(contentFont);
		GridBagConstraints gbc_timeTable = new GridBagConstraints();
		gbc_timeTable.gridx = 0;
		gbc_timeTable.gridwidth = GridBagConstraints.REMAINDER;
		gbc_timeTable.weighty = 0.9;
		gbc_timeTable.fill = GridBagConstraints.BOTH;
		timeTablePanel.add(timeTable, gbc_timeTable);
		
		JButton btnSyncTimeTable = new JButton("\uC0C8\uB85C\uACE0\uCE68");
		btnSyncTimeTable.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSyncTimeTable.setFont(contentFont);
		GridBagConstraints gbc_btnSyncTimeTable = new GridBagConstraints();
		gbc_btnSyncTimeTable.gridx = 0;
		gbc_btnSyncTimeTable.gridwidth = GridBagConstraints.REMAINDER;
		gbc_btnSyncTimeTable.weighty = 0.05;
		gbc_btnSyncTimeTable.fill = GridBagConstraints.HORIZONTAL;
		timeTablePanel.add(btnSyncTimeTable, gbc_btnSyncTimeTable);
		
		//오늘의 할 일
		JPanel todoPanel = new JPanel();
		frame.getContentPane().add(todoPanel);
		GridBagLayout gbl_todoPanel = new GridBagLayout();
		gbl_todoPanel.columnWidths = new int[]{240, 0};
		gbl_todoPanel.rowHeights = new int[]{25, 0, 0};
		gbl_todoPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_todoPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
		todoPanel.setLayout(gbl_todoPanel);
		
		JLabel todoLabel = new JLabel("\uC624\uB298\uC758 \uD560 \uC77C");
		todoLabel.setFont(titleFont);
		GridBagConstraints gbc_todoLabel = new GridBagConstraints();
		gbc_todoLabel.gridx = 0;
		gbc_todoLabel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_todoLabel.gridy = 0;
		gbc_todoLabel.weighty = 0.05;
		gbc_todoLabel.fill = GridBagConstraints.HORIZONTAL;
		todoPanel.add(todoLabel, gbc_todoLabel);
		
		JList todoList = new JList();
		todoList.setFont(contentFont);
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
		todoManagePanel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JButton btnDoneDo = new JButton("\uC644\uB8CC");
		btnDoneDo.setFont(contentFont);
		todoManagePanel.add(btnDoneDo);
		
		JButton btnDeleteDo = new JButton("\uC0AD\uC81C");
		btnDeleteDo.setFont(contentFont);
		todoManagePanel.add(btnDeleteDo);
		
		JButton btnManageDo = new JButton("\uAD00\uB9AC");
		btnManageDo.setFont(contentFont);
		todoManagePanel.add(btnManageDo);
		
		JButton btnSyncDo = new JButton("\uB3D9\uAE30\uD654");
		btnSyncDo.setFont(contentFont);
		todoManagePanel.add(btnSyncDo);
		
		// 간단 메모
		JPanel editorPanel = new JPanel();
		frame.getContentPane().add(editorPanel);
		GridBagLayout gbl_editorPanel = new GridBagLayout();
		gbl_editorPanel.columnWidths = new int[]{240, 0};
		gbl_editorPanel.rowHeights = new int[]{25, 0, 0};
		gbl_editorPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_editorPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
		editorPanel.setLayout(gbl_editorPanel);
		
		JLabel editorLabel = new JLabel("\uAC04\uB2E8 \uBA54\uBAA8");
		editorLabel.setFont(titleFont);
		GridBagConstraints gbc_editorLabel = new GridBagConstraints();
		gbc_editorLabel.gridx = 0;
		gbc_editorLabel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_editorLabel.gridy = 0;
		gbc_editorLabel.weighty = 0.05;
		gbc_editorLabel.fill = GridBagConstraints.BOTH;
		editorPanel.add(editorLabel, gbc_editorLabel);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setFont(contentFont);
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
		editorManagePanel.setLayout(new GridLayout(1, 3, 0, 0));
		
		JButton btnExportSimpleMemo = new JButton("\uB0B4\uBCF4\uB0B4\uAE30");
		btnExportSimpleMemo.setFont(contentFont);
		editorManagePanel.add(btnExportSimpleMemo);
		
		JButton btnSaveSimpleMemo = new JButton("\uC800\uC7A5");
		btnSaveSimpleMemo.setFont(contentFont);
		editorManagePanel.add(btnSaveSimpleMemo);
		
		JButton btnRemoveAll = new JButton("\uC9C0\uC6B0\uAE30");
		btnRemoveAll.setFont(contentFont);
		editorManagePanel.add(btnRemoveAll);
		
		//날씨
		JPanel weatherPanel = new JPanel();
		frame.getContentPane().add(weatherPanel);
		GridBagLayout gbl_weatherPanel = new GridBagLayout();
		gbl_weatherPanel.columnWidths = new int[]{240, 0};
		gbl_weatherPanel.rowHeights = new int[]{25, 0, 0};
		gbl_weatherPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_weatherPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
		weatherPanel.setLayout(gbl_weatherPanel);
		
		JLabel weatherLabel = new JLabel("\uB0A0\uC528");
		weatherLabel.setFont(titleFont);
		GridBagConstraints gbc_weatherLabel = new GridBagConstraints();
		gbc_weatherLabel.gridx = 0;
		gbc_weatherLabel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_weatherLabel.gridy = 0;
		gbc_weatherLabel.weighty = 0.05;
		gbc_weatherLabel.fill = GridBagConstraints.HORIZONTAL;
		weatherPanel.add(weatherLabel, gbc_weatherLabel);
		
		//날씨 정보 얻기
		GetWeather gw = new GetWeather();
		
		JPanel wIconAndTemp = new JPanel();
		wIconAndTemp.setLayout(new GridLayout(1, 2, 0, 0));
		GridBagConstraints gbc_wIconAndTemp = new GridBagConstraints();
		gbc_wIconAndTemp.gridx = 0;
		gbc_wIconAndTemp.weighty = 0.85;
		gbc_wIconAndTemp.gridwidth = GridBagConstraints.REMAINDER;
		gbc_weatherLabel.fill = GridBagConstraints.HORIZONTAL;
		weatherPanel.add(wIconAndTemp, gbc_wIconAndTemp);
		
		JSVGCanvas weatherIcon = new JSVGCanvas();
		weatherIcon.setURI(switch(gw.getIfRain()) {
			case 1 -> "assets/icons/cloud-showers-heavy-solid.svg";
			case 2 -> "assets/icons/umbrella-solid.svg";
			case 3 -> "assets/icons/snowflake-regular.svg";
			case 4 -> "assets/icons/cloud-rain-solid.svg";
			default -> "assets/icons/temperature-high-solid.svg";
		});
		wIconAndTemp.add(weatherIcon);
		
		JLabel weatherCelcius = new JLabel(gw.getCelsius());
		weatherCelcius.setFont(celciusFont);
		weatherCelcius.setVerticalAlignment(SwingConstants.CENTER);
		weatherCelcius.setHorizontalAlignment(SwingConstants.CENTER);
		wIconAndTemp.add(weatherCelcius);
		
		JLabel weatherOtherInfo = new JLabel("습도 " + gw.getHumidity() + "/ PM 2.5" + "/ PM 10");
		weatherOtherInfo.setFont(contentFont);
		weatherOtherInfo.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_weatherOtherInfo = new GridBagConstraints();
		gbc_weatherOtherInfo.gridx = 0;
		gbc_weatherOtherInfo.weighty = 0.05;
		gbc_weatherOtherInfo.gridwidth = GridBagConstraints.REMAINDER;
		gbc_weatherOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		weatherPanel.add(weatherOtherInfo, gbc_weatherOtherInfo);
		
		JPanel weatherWebBtn = new JPanel();
		weatherWebBtn.setLayout(new GridLayout(1, 2, 0, 0));
		GridBagConstraints gbc_weatherWebBtn = new GridBagConstraints();
		gbc_weatherWebBtn.gridx = 0;
		gbc_weatherWebBtn.weighty = 0.05;
		gbc_weatherWebBtn.gridwidth = GridBagConstraints.REMAINDER;
		gbc_weatherWebBtn.fill = GridBagConstraints.HORIZONTAL;
		weatherPanel.add(weatherWebBtn, gbc_weatherWebBtn);
		
		JButton kmaGo = new JButton("기상청");
		kmaGo.setFont(contentFont);
		weatherWebBtn.add(kmaGo);
		
		JButton airKoreaGo = new JButton("에어코리아");
		airKoreaGo.setFont(contentFont);
		weatherWebBtn.add(airKoreaGo);
		
		//메뉴 패널
		JPanel menuPanel = new JPanel();
		frame.getContentPane().add(menuPanel);
		menuPanel.setLayout(new GridLayout(4, 1, 0, 0));
		
		JButton btnWebMail = new JButton("\uC6F9 \uBA54\uC77C 0\uAC74");
		btnWebMail.setFont(titleFont);
		btnWebMail.setHorizontalAlignment(SwingConstants.LEFT);
		menuPanel.add(btnWebMail);
		
		JButton btnGoPortal = new JButton("\uC21C\uCC9C\uD5A5\uB300 \uD3EC\uD138");
		btnGoPortal.setFont(titleFont);
		btnGoPortal.setHorizontalAlignment(SwingConstants.LEFT);
		menuPanel.add(btnGoPortal);
		
		JButton btnGoInfoSys = new JButton("\uC885\uD569\uC815\uBCF4\uC2DC\uC2A4\uD15C");
		btnGoInfoSys.setFont(titleFont);
		btnGoInfoSys.setHorizontalAlignment(SwingConstants.LEFT);
		menuPanel.add(btnGoInfoSys);
		
		JButton btnSettings = new JButton("\uC124\uC815");
		btnSettings.setFont(titleFont);
		btnSettings.setHorizontalAlignment(SwingConstants.LEFT);
		menuPanel.add(btnSettings);
		
		frame.setVisible(true);
	}

}
