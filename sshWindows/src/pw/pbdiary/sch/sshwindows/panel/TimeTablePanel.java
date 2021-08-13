package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class TimeTablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable timeTable;
	
	public TimeTablePanel(Font titleFont, Font contentFont) {
		CustomGridBagLayout gbl_timeTablePanel = new CustomGridBagLayout();
		this.setLayout(gbl_timeTablePanel);
		

		JLabel timeTableLabel = new JLabel("\uC218\uC5C5 \uC2DC\uAC04\uD45C");
		timeTableLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_timeTableLabel = new CustomGridBagConstraints("title");
		this.add(timeTableLabel, gbc_timeTableLabel);
		
		timeTable = new JTable();
		timeTable.setFont(contentFont);
		CustomGridBagConstraints gbc_timeTable = new CustomGridBagConstraints("both", 0.9);
		this.add(timeTable, gbc_timeTable);
		
		JButton btnSyncTimeTable = new JButton("새로고침");
		btnSyncTimeTable.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSyncTimeTable.setFont(contentFont);
		CustomGridBagConstraints gbc_btnSyncTimeTable = new CustomGridBagConstraints("menu");
		this.add(btnSyncTimeTable, gbc_btnSyncTimeTable);
	}
}
