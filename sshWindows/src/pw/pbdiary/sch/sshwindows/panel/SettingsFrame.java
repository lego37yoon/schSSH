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
		settingsPanel.setLayout(new GridLayout(5, 2, 0, 10));
		
		JLabel schIDLabel = new JLabel("��õ�� ID");
		schIDLabel.setFont(contentFont);
		settingsPanel.add(schIDLabel);
		
		JTextField schIDField = new JTextField();
		schIDField.setFont(contentFont);
		schIDField.setText(dbController.getSettings("ID"));
		settingsPanel.add(schIDField);
		
		JLabel schPWLabel = new JLabel("��õ�� PW");
		schPWLabel.setFont(contentFont);
		settingsPanel.add(schPWLabel);
		
		JPasswordField schPWField = new JPasswordField();
		schPWField.setFont(contentFont);
		schPWField.setText(dbController.getSettings("PW"));
		settingsPanel.add(schPWField);
		
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
		saveSettings.addMouseListener(new SaveBtnListener(dbController, schIDField, schPWField, themeCombo));
		settingsPanel.add(saveSettings);
		
		JButton removeValues = new JButton("�ʱ�ȭ");
		removeValues.setFont(contentFont);
		removeValues.addMouseListener(new RemoveBtnListener(dbController, schIDField, schPWField, themeCombo));
		settingsPanel.add(removeValues);
		
		setContentPane(settingsPanel);
		pack();
		setSize(400, 300);
		setTitle("����");
		setVisible(true);
	}
	
	private class SaveBtnListener extends MouseAdapter {
		private JTextField id;
		private JPasswordField pw;
		private JComboBox<String> theme;
		private DatabaseController db;
		
		public SaveBtnListener(DatabaseController dbController, JTextField id, JPasswordField pw, JComboBox<String> theme) {
			this.id = id;
			this.pw = pw;
			this.db = dbController;
			this.theme = theme;
		}
		
		public void mouseClicked(MouseEvent e) {
			HashMap<String, String> settings = new HashMap<String, String>();
			settings.put("ID", id.getText());
			settings.put("PW", String.valueOf(pw.getPassword()));
			settings.put("THEME", (String)theme.getSelectedItem());
			db.saveSettings(settings);
			JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.\n�׸� ������ ���α׷��� ������ؾ� ����˴ϴ�.", "�ȳ�", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}
	
	private class RemoveBtnListener extends MouseAdapter {
		private JTextField id;
		private JPasswordField pw;
		private JComboBox<String> theme;
		private DatabaseController db;
		
		public RemoveBtnListener(DatabaseController dbController, JTextField id, JPasswordField pw, JComboBox<String> theme) {
			this.id = id;
			this.pw = pw;
			this.db = dbController;
			this.theme = theme;
		}
		
		public void mouseClicked(MouseEvent e) {
			id.setText("");
			pw.setText("");
			theme.setSelectedIndex(0);
			HashMap<String, String> settings = new HashMap<String, String>();
			settings.put("ID", "");
			settings.put("PW", "");
			settings.put("THEME", "light");
			db.saveSettings(settings);
		}
	}
}
