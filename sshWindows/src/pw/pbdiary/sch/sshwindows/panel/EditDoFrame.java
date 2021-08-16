package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;

public class EditDoFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public EditDoFrame(Font contentFont, DatabaseController dbController, int index){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2, 0, 10));
		getContentPane().add(panel);
		
		ResultSet rs = dbController.getDoById(index);
		
		JLabel titleLabel = new JLabel("����");
		titleLabel.setFont(contentFont);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(titleLabel);
		
		JTextField titleField = new JTextField();
		titleField.setFont(contentFont);
		try {
			titleField.setText(rs.getString("TITLE"));
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "������ �о���� ���߽��ϴ�.", "���� �Ұ�", JOptionPane.ERROR_MESSAGE);
		}
		panel.add(titleField);
		
		JLabel dateLabel = new JLabel("��¥");
		dateLabel.setFont(contentFont);
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(dateLabel);
		
		DatePickerSettings formatSettings = new DatePickerSettings();
		formatSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
		DatePicker datePicker = new DatePicker(formatSettings);
		datePicker.setFont(contentFont);
		try {
			datePicker.setDate(LocalDate.parse(rs.getString("DATE")));
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "��¥�� �о���� ���߽��ϴ�.", "���� �Ұ�", JOptionPane.ERROR_MESSAGE);
		}
		panel.add(datePicker);
		
		JLabel alarmLabel = new JLabel("�˸�(����)");
		alarmLabel.setFont(contentFont);
		alarmLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(alarmLabel);
		
		DateTimePicker alarmPicker = new DateTimePicker();
		alarmPicker.setFont(contentFont);
		try {
			alarmPicker.setDateTimePermissive(LocalDateTime.parse(rs.getString("ALARM")));
		} catch (SQLException|NullPointerException e) {
			
		}
		panel.add(alarmPicker);
		
		JLabel doneLabel = new JLabel("�Ϸ� ����");
		doneLabel.setFont(contentFont);
		doneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(doneLabel);
		
		
		boolean isDone;
		JCheckBox doneCheckbox = new JCheckBox("", false);
		doneCheckbox.setFont(contentFont);
		try {
			if (rs.getInt("DONE") == 0) {
				isDone = false;
			} else {
				isDone = true;
			}
			doneCheckbox.setSelected(isDone);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�Ϸ� ���θ� �о���� ���߽��ϴ�.", "���� �Ұ�", JOptionPane.ERROR_MESSAGE);
		}
		panel.add(doneCheckbox);
		
		JButton saveDo = new JButton("����");
		saveDo.setFont(contentFont);
		saveDo.addMouseListener(new saveDoListener(index, dbController, titleField, datePicker, alarmPicker, doneCheckbox));
		panel.add(saveDo);
		
		JButton removeAll = new JButton("�ʱ�ȭ");
		removeAll.setFont(contentFont);
		removeAll.addMouseListener(new removeListener(titleField, datePicker, alarmPicker, doneCheckbox));
		panel.add(removeAll);
		
		pack();
		setSize(600, 200);
		setTitle("�� �� ����");
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		try {
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private class saveDoListener extends MouseAdapter {
		private int index;
		private DatabaseController dbController;
		private JTextField title;
		private DatePicker date;
		private DateTimePicker alarm;
		private JCheckBox done;
		
		public saveDoListener(int index, DatabaseController dbController, JTextField title, DatePicker date, DateTimePicker alarm, JCheckBox done) {
			this.index = index;
			this.dbController = dbController;
			this.title = title;
			this.date = date;
			this.alarm = alarm;
			this.done = done;
		}
		
		public void mouseClicked(MouseEvent e) {
			if (title.getText().equals("") || title.getText() == null || date.getDate() == null) {
				JOptionPane.showMessageDialog(null, "����� �� ���� �׸��� ������ϴ�.", "���� ����", JOptionPane.ERROR_MESSAGE);
			} else {
				if (alarm.getDateTimeStrict() == null) {
					dbController.editDo(index, done.isSelected());
					dbController.editDo(index, date.getDate());
					dbController.editDo(index, title.getText());
				} else {
					dbController.editDo(index, alarm.getDateTimeStrict());
					dbController.editDo(index, done.isSelected());
					dbController.editDo(index, date.getDate());
					dbController.editDo(index, title.getText());
				}
				JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.", "�ȳ�", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		}
	}
	
	private class removeListener extends MouseAdapter {
		private JTextField title;
		private DatePicker date;
		private DateTimePicker alarm;
		private JCheckBox done;
		
		public removeListener(JTextField title, DatePicker date, DateTimePicker alarm, JCheckBox done) {
			this.title = title;
			this.date = date;
			this.alarm = alarm;
			this.done = done;
		}
		
		public void mouseClicked(MouseEvent e) {
			title.setText("");
			date.setDateToToday();
			alarm.setDateTimePermissive(null);
			done.setSelected(false);
		}
	}
}
