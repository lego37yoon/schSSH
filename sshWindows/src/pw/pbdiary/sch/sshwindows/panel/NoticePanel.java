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
		
		//학사일정
		
		JPanel scheduleOfUniv = new JPanel();
		scheduleOfUniv.setBackground(new Color(255, 255, 255));
		scheduleOfUniv.setLayout(new GridLayout(3, 0, 0, 0));
		noticeTab.addTab("학사일정", null, scheduleOfUniv, null);
		
		JLabel beforeNoticeSchedule = new JLabel(gd.getTodayDateHReadable());
		beforeNoticeSchedule.setFont(titleFont);
		beforeNoticeSchedule.setHorizontalAlignment(SwingConstants.CENTER);
		scheduleOfUniv.add(beforeNoticeSchedule);
		
		String scheduleHTML = ""; //학사일정 담을 문자열
		
		for (String element : parser.getSchedule()) { //학사일정 항목 당 <br> 태그 적용
			scheduleHTML += element + "<br>";
		}
		
		JLabel scheduleList = new JLabel("<html><p style='text-align: center;'>" + scheduleHTML + "</p></html>");
		scheduleList.setFont(contentFont);
		scheduleList.setHorizontalAlignment(SwingConstants.CENTER);
		scheduleOfUniv.add(scheduleList);
		
		JLabel seeMoreInfo = new JLabel("<html><p style='text-align: center;'>이후 일정은<br>더보기를 참조하세요</p></html>");
		seeMoreInfo.setFont(contentFont);
		seeMoreInfo.setHorizontalAlignment(SwingConstants.CENTER);
		scheduleOfUniv.add(seeMoreInfo);
		
		//공지사항 & 학사공지
		
		String[][] normalData = parser.getNormal();
		String[][] schoolScheduleData = parser.getSchool();
		
		JList<String> normalNoticeList = new JList<String>(normalData[0]);
		normalNoticeList.setFont(contentFont);
		noticeTab.addTab("공지사항", null, normalNoticeList, null);
		
		JList<String> scheduleNoticeList = new JList<String>(schoolScheduleData[0]);
		scheduleNoticeList.setFont(contentFont);
		noticeTab.addTab("학사공지", null, scheduleNoticeList, null);
		
		//학사일정, 공지사항, 학사공지 밑 더보기 버튼
		
		JButton btnGoNotice = new JButton("더보기");
		btnGoNotice.setFont(contentFont);
		CustomGridBagConstraints gbc_btnGoNotice = new CustomGridBagConstraints("horizontal", 0.15);
		this.add(btnGoNotice, gbc_btnGoNotice);
	}
}
