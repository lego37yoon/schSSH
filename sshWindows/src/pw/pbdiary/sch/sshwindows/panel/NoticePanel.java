package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import pw.pbdiary.sch.sshwindows.func.GetDate;
import pw.pbdiary.sch.sshwindows.func.GetNotice;

public class NoticePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public NoticePanel(Font titleFont, Font contentFont, GetNotice parser, GetDate gd) {
		CustomGridBagLayout gbl_this = new CustomGridBagLayout();
		this.setLayout(gbl_this);
		
		JTabbedPane noticeTab = new JTabbedPane(JTabbedPane.TOP);
		noticeTab.setFont(titleFont);
		CustomGridBagConstraints gbc_noticeTab = new CustomGridBagConstraints("both", 0.85);
		this.add(noticeTab, gbc_noticeTab);
		
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
		CustomGridBagConstraints gbc_btnGoNotice = new CustomGridBagConstraints("horizontal", 0.15);
		this.add(btnGoNotice, gbc_btnGoNotice);
	}
}
