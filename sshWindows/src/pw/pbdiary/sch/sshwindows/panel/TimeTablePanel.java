package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;
import pw.pbdiary.sch.sshwindows.func.GetTimeTable;

public class TimeTablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public TimeTablePanel(Font titleFont, Font contentFont, DatabaseController dbController) {
		CustomGridBagLayout gbl_timeTablePanel = new CustomGridBagLayout();
		this.setLayout(gbl_timeTablePanel);
		ArrayList<String> tableArray = new ArrayList<String>();
		
		GetTimeTable gtt = new GetTimeTable();
		if (!dbController.getSettings("EVERY_ID").equals("")) {
			tableArray = gtt.get(dbController.getSettings("EVERY_ID"), dbController.getSettings("EVERY_PW"), dbController.getSettings("TIMETABLE_YEAR"), dbController.getSettings("TIMETABLE_SEMESTER"));
		}

		JLabel timeTableLabel = new JLabel("\uC218\uC5C5 \uC2DC\uAC04\uD45C");
		timeTableLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_timeTableLabel = new CustomGridBagConstraints("title");
		this.add(timeTableLabel, gbc_timeTableLabel);
		
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		for (String element : tableArray) {
			dlm.addElement(element);
		}
		
		JList<String> timeTable = new JList<String>(dlm);
		timeTable.setFont(contentFont);
		JScrollPane timeTableScroll = new JScrollPane(timeTable);
		CustomGridBagConstraints gbc_timeTable = new CustomGridBagConstraints("both", 0.9);
		this.add(timeTableScroll, gbc_timeTable);
		
		JButton btnSyncTimeTable = new JButton("새로고침");
		btnSyncTimeTable.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSyncTimeTable.setFont(contentFont);
		btnSyncTimeTable.addMouseListener(new RefreshTableListener(dbController, gtt, timeTable));
		CustomGridBagConstraints gbc_btnSyncTimeTable = new CustomGridBagConstraints("menu");
		this.add(btnSyncTimeTable, gbc_btnSyncTimeTable);
	}
	
	private class RefreshTableListener extends MouseAdapter {
		private DatabaseController dbController;
		private GetTimeTable gtt;
		private JList<String> timeTable;
		private ArrayList<String> tableArray = new ArrayList<String>();
		
		public RefreshTableListener(DatabaseController dbController, GetTimeTable gtt, JList<String> timeTable) {
			this.dbController = dbController;
			this.gtt = gtt;
			this.timeTable = timeTable;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			DefaultListModel<String> model = (DefaultListModel<String>) timeTable.getModel();
			model.removeAllElements();
			tableArray = gtt.get(dbController.getSettings("EVERY_ID"), dbController.getSettings("EVERY_PW"), dbController.getSettings("TIMETABLE_YEAR"), dbController.getSettings("TIMETABLE_SEMESTER"));
			for (String element : tableArray) {
				model.addElement(element);
			}
		}
	}
}
