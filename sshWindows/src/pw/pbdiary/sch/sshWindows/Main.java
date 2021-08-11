package pw.pbdiary.sch.sshWindows;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
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

	// Initialize the contents of the frame.
	private void initialize() {
		
		//DB ��� ���� ���� (���� �ð�ǥ, �� ��, �޸�, �� ����, ���������ý���, ����)
		DatabaseController dbController = new DatabaseController();
		//���� �� ����
		GetNotice parser = new GetNotice();
		//�ð� �޾ƿ���
		GetDate gd = new GetDate();
		//UI ����
		FlatLightLaf.setup();
		//�۲� ����
		Font celciusFont = new Font("���� ��� Semilight", Font.PLAIN, 24);
		Font titleFont = new Font("���� ��� Semilight", Font.PLAIN, 18);
		Font contentFont = new Font("���� ��� Semilight", Font.PLAIN, 14);
		
		
		frame = new JFrame();
		frame.setTitle("schSSH");
		frame.setSize(900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 3, 5, 5));
		
		JPanel noticePanel = new JPanel();
		frame.getContentPane().add(noticePanel);
		CustomGridBagLayout gbl_noticePanel = new CustomGridBagLayout();
		noticePanel.setLayout(gbl_noticePanel);
		
		JTabbedPane noticeTab = new JTabbedPane(JTabbedPane.TOP);
		noticeTab.setFont(titleFont);
		GridBagConstraints gbc_noticeTab = new GridBagConstraints();
		gbc_noticeTab.gridx = 0;
		gbc_noticeTab.gridwidth = GridBagConstraints.REMAINDER;
		gbc_noticeTab.gridy = 0;
		gbc_noticeTab.weighty = 0.85;
		gbc_noticeTab.fill = GridBagConstraints.BOTH;
		noticePanel.add(noticeTab, gbc_noticeTab);
		
		//�л�����
		
		JPanel scheduleOfUniv = new JPanel();
		scheduleOfUniv.setBackground(new Color(255, 255, 255));
		scheduleOfUniv.setLayout(new GridLayout(3, 0, 0, 0));
		noticeTab.addTab("�л�����", null, scheduleOfUniv, null);
		
		JLabel beforeNoticeSchedule = new JLabel(gd.getTodayDateHReadable());
		beforeNoticeSchedule.setFont(titleFont);
		beforeNoticeSchedule.setHorizontalAlignment(SwingConstants.CENTER);
		scheduleOfUniv.add(beforeNoticeSchedule);
		
		String scheduleHTML = ""; //�л����� ���� ���ڿ�
		
		for (String element : parser.getSchedule()) { //�л����� �׸� �� <br> �±� ����
			scheduleHTML += element + "<br>";
		}
		
		JLabel scheduleList = new JLabel("<html><p style='text-align: center;'>" + scheduleHTML + "</p></html>");
		scheduleList.setFont(contentFont);
		scheduleList.setHorizontalAlignment(SwingConstants.CENTER);
		scheduleOfUniv.add(scheduleList);
		
		JLabel seeMoreInfo = new JLabel("<html><p style='text-align: center;'>���� ������<br>�����⸦ �����ϼ���</p></html>");
		seeMoreInfo.setFont(contentFont);
		seeMoreInfo.setHorizontalAlignment(SwingConstants.CENTER);
		scheduleOfUniv.add(seeMoreInfo);
		
		//�������� & �л����
		
		String[][] normalData = parser.getNormal();
		String[][] schoolScheduleData = parser.getSchool();
		
		JList<String> normalNoticeList = new JList<String>(normalData[0]);
		normalNoticeList.setFont(contentFont);
		noticeTab.addTab("��������", null, normalNoticeList, null);
		
		JList<String> scheduleNoticeList = new JList<String>(schoolScheduleData[0]);
		scheduleNoticeList.setFont(contentFont);
		noticeTab.addTab("�л����", null, scheduleNoticeList, null);
		
		//�л�����, ��������, �л���� �� ������ ��ư
		
		JButton btnGoNotice = new JButton("������");
		btnGoNotice.setFont(contentFont);
		GridBagConstraints gbc_btnGoNotice = new GridBagConstraints();
		gbc_btnGoNotice.gridx = 0;
		gbc_btnGoNotice.gridwidth = GridBagConstraints.REMAINDER;
		gbc_btnGoNotice.weighty = 0.15;
		gbc_btnGoNotice.fill = GridBagConstraints.HORIZONTAL;
		noticePanel.add(btnGoNotice, gbc_btnGoNotice);
		
		// ���� �ð�ǥ
		
		JPanel timeTablePanel = new JPanel();
		frame.getContentPane().add(timeTablePanel);
		CustomGridBagLayout gbl_timeTablePanel = new CustomGridBagLayout();
		timeTablePanel.setLayout(gbl_timeTablePanel);
		

		JLabel timeTableLabel = new JLabel("\uC218\uC5C5 \uC2DC\uAC04\uD45C");
		timeTableLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_timeTableLabel = new CustomGridBagConstraints("title");
		timeTablePanel.add(timeTableLabel, gbc_timeTableLabel);
		
		timeTable = new JTable();
		timeTable.setFont(contentFont);
		GridBagConstraints gbc_timeTable = new GridBagConstraints();
		gbc_timeTable.gridx = 0;
		gbc_timeTable.gridwidth = GridBagConstraints.REMAINDER;
		gbc_timeTable.weighty = 0.9;
		gbc_timeTable.fill = GridBagConstraints.BOTH;
		timeTablePanel.add(timeTable, gbc_timeTable);
		
		JButton btnSyncTimeTable = new JButton("���ΰ�ħ");
		btnSyncTimeTable.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSyncTimeTable.setFont(contentFont);
		CustomGridBagConstraints gbc_btnSyncTimeTable = new CustomGridBagConstraints("menu");
		timeTablePanel.add(btnSyncTimeTable, gbc_btnSyncTimeTable);
		
		//������ �� ��
		
		JPanel todoPanel = new JPanel();
		frame.getContentPane().add(todoPanel);
		CustomGridBagLayout gbl_todoPanel = new CustomGridBagLayout();
		todoPanel.setLayout(gbl_todoPanel);
		
		JLabel todoLabel = new JLabel("\uC624\uB298\uC758 \uD560 \uC77C");
		todoLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_todoLabel = new CustomGridBagConstraints("title");
		todoPanel.add(todoLabel, gbc_todoLabel);
		
		ResultSet todoListQuery = dbController.getDo(gd.getTodayByLocalDate());
		ArrayList<Boolean> todoIfDone = new ArrayList<Boolean>();
		ArrayList<String> todoTitle = new ArrayList<String>();
		try {
			while (todoListQuery.next()) {
				todoTitle.add(todoListQuery.getString("TITLE"));
				if (todoListQuery.getInt("DONE") == 1) {
					todoIfDone.add(true);
				} else {
					todoIfDone.add(false);
				}
			}
			
			todoListQuery.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//���� â ����
			System.out.println("�� �� �����͸� �ҷ����� ���߽��ϴ�.");
		}
		
		DefaultListModel<String> todoListModel = new DefaultListModel<String>();
		for (String element : todoTitle) {
			todoListModel.addElement(element);
		}
		
		JList<String> todoList = new JList<String>(todoListModel);
		todoList.setFont(contentFont);
		todoList.setCellRenderer(new CheckboxListCellRenderer());
		JScrollPane todoListPane = new JScrollPane(todoList);
		CustomGridBagConstraints gbc_todoListPane = new CustomGridBagConstraints("content");
		todoPanel.add(todoListPane, gbc_todoListPane);
		
		JPanel todoManagePanel = new JPanel();
		CustomGridBagConstraints gbc_todoManagePanel = new CustomGridBagConstraints("menu");
		todoPanel.add(todoManagePanel, gbc_todoManagePanel);
		todoManagePanel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JButton btnAddDo = new JButton("�߰�");
		btnAddDo.setFont(contentFont);
		todoManagePanel.add(btnAddDo);
		
		JButton btnDeleteDo = new JButton("����");
		btnDeleteDo.setFont(contentFont);
		todoManagePanel.add(btnDeleteDo);
		
		JButton btnManageDo = new JButton("����");
		btnManageDo.setFont(contentFont);
		todoManagePanel.add(btnManageDo);
		
		JButton btnSyncDo = new JButton("����ȭ");
		btnSyncDo.setFont(contentFont);
		todoManagePanel.add(btnSyncDo);
		
		// ���� �޸�
		JPanel editorPanel = new JPanel();
		frame.getContentPane().add(editorPanel);
		CustomGridBagLayout gbl_editorPanel = new CustomGridBagLayout();
		editorPanel.setLayout(gbl_editorPanel);
		
		JLabel editorLabel = new JLabel("\uAC04\uB2E8 \uBA54\uBAA8");
		editorLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_editorLabel = new CustomGridBagConstraints("title");
		editorPanel.add(editorLabel, gbc_editorLabel);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setFont(contentFont);
		editorPane.setContentType("text/html");
		editorPane.setText(dbController.getMemo());
		CustomGridBagConstraints gbc_editorPane = new CustomGridBagConstraints("content");
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
		
		//����
		JPanel weatherPanel = new JPanel();
		frame.getContentPane().add(weatherPanel);
		CustomGridBagLayout gbl_weatherPanel = new CustomGridBagLayout();
		weatherPanel.setLayout(gbl_weatherPanel);
		
		JLabel weatherLabel = new JLabel("\uB0A0\uC528");
		weatherLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_weatherLabel = new CustomGridBagConstraints("title");
		weatherPanel.add(weatherLabel, gbc_weatherLabel);
		
		//���� ���� ���
		GetWeather gw = new GetWeather();
		GetAirPM pm = new GetAirPM();
		
		JPanel wIconAndTemp = new JPanel();
		wIconAndTemp.setLayout(new GridLayout(1, 2, 0, 0));
		wIconAndTemp.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_wIconAndTemp = new GridBagConstraints();
		gbc_wIconAndTemp.gridx = 0;
		gbc_wIconAndTemp.weighty = 0.8;
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
		
		JLabel weatherOtherInfo = new JLabel("���� " + gw.getHumidity() + "/ PM 2.5 " + pm.getPM25() + "/ PM 10 " + pm.getPM10());
		weatherOtherInfo.setFont(contentFont);
		weatherOtherInfo.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_weatherOtherInfo = new GridBagConstraints();
		gbc_weatherOtherInfo.gridx = 0;
		gbc_weatherOtherInfo.weighty = 0.05;
		gbc_weatherOtherInfo.gridwidth = GridBagConstraints.REMAINDER;
		gbc_weatherOtherInfo.fill = GridBagConstraints.HORIZONTAL;
		weatherPanel.add(weatherOtherInfo, gbc_weatherOtherInfo);
		
		JLabel weatherPMGrade = new JLabel("�ʹ̼����� " + pm.getPM25Grade() + " / �̼����� " + pm.getPM10Grade());
		weatherPMGrade.setFont(contentFont);
		weatherPMGrade.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_weatherPMGrade = new GridBagConstraints();
		gbc_weatherPMGrade.gridx = 0;
		gbc_weatherPMGrade.weighty = 0.05;
		gbc_weatherPMGrade.gridwidth = GridBagConstraints.REMAINDER;
		gbc_weatherPMGrade.fill = GridBagConstraints.HORIZONTAL;
		weatherPanel.add(weatherPMGrade, gbc_weatherPMGrade);
		
		JPanel weatherWebBtn = new JPanel();
		weatherWebBtn.setLayout(new GridLayout(1, 2, 0, 0));
		CustomGridBagConstraints gbc_weatherWebBtn = new CustomGridBagConstraints("menu");
		weatherPanel.add(weatherWebBtn, gbc_weatherWebBtn);
		
		JButton kmaGo = new JButton("���û");
		kmaGo.setFont(contentFont);
		weatherWebBtn.add(kmaGo);
		
		JButton airKoreaGo = new JButton("�����ڸ���");
		airKoreaGo.setFont(contentFont);
		weatherWebBtn.add(airKoreaGo);
		
		//�޴� �г�
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
