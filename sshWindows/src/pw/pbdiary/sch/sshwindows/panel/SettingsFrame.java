package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;

public class SettingsFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public SettingsFrame(Font contentFont, DatabaseController dbController) {
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new GridLayout(10, 2, 0, 10));
		
		JLabel schIDLabel = new JLabel("��õ�� ID");
		schIDLabel.setFont(contentFont);
		settingsPanel.add(schIDLabel);
		
		JTextField schIDField = new JTextField();
		schIDField.setFont(contentFont);
		schIDField.setText(dbController.getSettings("ID"));
		settingsPanel.add(schIDField);
		
		JLabel schPWLabel = new JLabel("��õ�� ��й�ȣ");
		schPWLabel.setFont(contentFont);
		settingsPanel.add(schPWLabel);
		
		JPasswordField schPWField = new JPasswordField();
		schPWField.setFont(contentFont);
		schPWField.setText(dbController.getSettings("PW"));
		settingsPanel.add(schPWField);
		
		JLabel mailIDLabel = new JLabel("���� ID");
		mailIDLabel.setFont(contentFont);
		settingsPanel.add(mailIDLabel);
		
		JTextField mailIDField = new JTextField();
		mailIDField.setFont(contentFont);
		mailIDField.setText(dbController.getSettings("MAIL_ID"));
		settingsPanel.add(mailIDField);
		
		JLabel mailPWLabel = new JLabel("���� ��й�ȣ");
		mailPWLabel.setFont(contentFont);
		settingsPanel.add(mailPWLabel);
		
		JPasswordField mailPWField = new JPasswordField();
		mailPWField.setFont(contentFont);
		mailPWField.setText(dbController.getSettings("MAIL_PW"));
		settingsPanel.add(mailPWField);
		
		JLabel everytimeIDLabel = new JLabel("���긮Ÿ�� ID");
		everytimeIDLabel.setFont(contentFont);
		settingsPanel.add(everytimeIDLabel);
		
		JTextField everytimeIDField = new JTextField();
		everytimeIDField.setFont(contentFont);
		everytimeIDField.setText(dbController.getSettings("EVERY_ID"));
		settingsPanel.add(everytimeIDField);
		
		JLabel everytimePWLabel = new JLabel("���긮Ÿ�� ��й�ȣ");
		everytimePWLabel.setFont(contentFont);
		settingsPanel.add(everytimePWLabel);
		
		JPasswordField everytimePWField = new JPasswordField();
		everytimePWField.setFont(contentFont);
		everytimePWField.setText(dbController.getSettings("EVERY_PW"));
		settingsPanel.add(everytimePWField);
		
		JLabel everytimeSemester = new JLabel("���� �ð�ǥ �б�");
		everytimeSemester.setFont(contentFont);
		settingsPanel.add(everytimeSemester);
		
		JPanel everySemesterField = new JPanel();
		everySemesterField.setLayout(new GridLayout(1, 4, 0, 0));
		settingsPanel.add(everySemesterField);
		
		JTextField everySemesterYear = new JTextField();
		everySemesterYear.setFont(contentFont);
		everySemesterYear.setText(dbController.getSettings("TIMETABLE_YEAR"));
		everySemesterField.add(everySemesterYear);
		
		JLabel everyYearLabel = new JLabel("��");
		everyYearLabel.setFont(contentFont);
		everySemesterField.add(everyYearLabel);
		
		JTextField everySemester = new JTextField();
		everySemester.setFont(contentFont);
		everySemester.setText(dbController.getSettings("TIMETABLE_SEMESTER"));
		everySemesterField.add(everySemester);
		
		JLabel everySemesterLabel = new JLabel("�б�");
		everySemesterLabel.setFont(contentFont);
		everySemesterField.add(everySemesterLabel);
		
		JLabel themeLabel = new JLabel("�׸� ����");
		themeLabel.setFont(contentFont);
		settingsPanel.add(themeLabel);
		
		String[] themes = {"light", "dark"};
		JComboBox<String> themeCombo = new JComboBox<String>(themes);
		themeCombo.setFont(contentFont);
		if (dbController.getSettings("THEME").equals("dark")) {
			themeCombo.setSelectedIndex(1);
		} else {
			themeCombo.setSelectedIndex(0);
		}
		settingsPanel.add(themeCombo);
		
		JLabel todoLabel = new JLabel("�� �� ����");
		todoLabel.setFont(contentFont);
		settingsPanel.add(todoLabel);
		
		JButton todoManageBtn = new JButton("����");
		todoManageBtn.setFont(contentFont);
		settingsPanel.add(todoManageBtn);
		
		JButton saveSettings = new JButton("����");
		saveSettings.setFont(contentFont);
		saveSettings.addMouseListener(new SaveBtnListener(dbController, schIDField, schPWField, mailIDField, mailPWField, everytimeIDField, everytimePWField, everySemesterYear, everySemester, themeCombo));
		settingsPanel.add(saveSettings);
		
		JButton removeValues = new JButton("�ʱ�ȭ");
		removeValues.setFont(contentFont);
		removeValues.addMouseListener(new RemoveBtnListener(schIDField, schPWField, mailIDField, mailPWField, everytimeIDField, everytimePWField, everySemesterYear, everySemester, themeCombo));
		settingsPanel.add(removeValues);
		
		setContentPane(settingsPanel);
		pack();
		setSize(400, 400);
		setTitle("����");
		setVisible(true);
	}
	
	private class SaveBtnListener extends MouseAdapter {
		private JTextField id;
		private JPasswordField pw;
		private JTextField mailID;
		private JPasswordField mailPW;
		private JTextField everyID;
		private JPasswordField everyPW;
		private JTextField ttYear;
		private JTextField ttSemester;
		private JComboBox<String> theme;
		private DatabaseController db;
		
		public SaveBtnListener(DatabaseController dbController, JTextField id, JPasswordField pw, JTextField mailID, JPasswordField mailPW, JTextField everyID, JPasswordField everyPW, JTextField ttYear, JTextField ttSemester, JComboBox<String> theme) {
			this.id = id;
			this.pw = pw;
			this.mailID = mailID;
			this.mailPW = mailPW;
			this.everyID = everyID;
			this.everyPW = everyPW;
			this.ttYear = ttYear;
			this.ttSemester = ttSemester;
			this.db = dbController;
			this.theme = theme;
		}
		
		public void mouseClicked(MouseEvent e) {
			HashMap<String, String> settings = new HashMap<String, String>();
			settings.put("ID", id.getText());
			settings.put("PW", String.valueOf(pw.getPassword()));
			settings.put("MAIL_ID", mailID.getText());
			settings.put("MAIL_PW", String.valueOf(mailPW.getPassword()));
			settings.put("EVERY_ID", everyID.getText());
			settings.put("EVERY_PW", String.valueOf(everyPW.getPassword()));
			settings.put("TIMETABLE_YEAR", ttYear.getText());
			settings.put("TIMETABLE_SEMESTER", ttSemester.getText());
			settings.put("THEME", (String)theme.getSelectedItem());
			db.saveSettings(settings);
			JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.\n�׸� ������ ���α׷��� ������ؾ� ����˴ϴ�.", "�ȳ�", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}
	
	private class RemoveBtnListener extends MouseAdapter {
		private JTextField id;
		private JPasswordField pw;
		private JTextField mailID;
		private JPasswordField mailPW;
		private JTextField everyID;
		private JPasswordField everyPW;
		private JTextField ttYear;
		private JTextField ttSemester;
		private JComboBox<String> theme;
		
		public RemoveBtnListener(JTextField id, JPasswordField pw, JTextField mailID, JPasswordField mailPW, JTextField everyID, JPasswordField everyPW, JTextField ttYear, JTextField ttSemester, JComboBox<String> theme) {
			this.id = id;
			this.pw = pw;
			this.mailID = mailID;
			this.mailPW = mailPW;
			this.everyID = everyID;
			this.everyPW = everyPW;
			this.ttYear = ttYear;
			this.ttSemester = ttSemester;
			this.theme = theme;
		}
		
		public void mouseClicked(MouseEvent e) {
			id.setText("");
			pw.setText("");
			mailID.setText("");
			mailPW.setText("");
			everyID.setText("");
			everyPW.setText("");
			ttYear.setText("");
			ttSemester.setText("");
			theme.setSelectedIndex(0);
		}
	}
}
