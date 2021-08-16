package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

public class AddDoFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public AddDoFrame(Font contentFont, DatabaseController dbController) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2, 0, 10));
		getContentPane().add(panel);
		
		JLabel titleLabel = new JLabel("����");
		titleLabel.setFont(contentFont);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(titleLabel);
		
		JTextField titleField = new JTextField();
		titleField.setFont(contentFont);
		panel.add(titleField);
		
		JLabel dateLabel = new JLabel("��¥");
		dateLabel.setFont(contentFont);
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(dateLabel);
		
		DatePickerSettings formatSettings = new DatePickerSettings();
		formatSettings.setFormatForDatesCommonEra("yyyy-MM-dd");
		DatePicker datePicker = new DatePicker(formatSettings);
		datePicker.setFont(contentFont);
		datePicker.setDateToToday();
		panel.add(datePicker);
		
		JLabel alarmLabel = new JLabel("�˸�(����)");
		alarmLabel.setFont(contentFont);
		alarmLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(alarmLabel);
		
		DateTimePicker alarmPicker = new DateTimePicker();
		alarmPicker.setFont(contentFont);
		panel.add(alarmPicker);
		
		JLabel doneLabel = new JLabel("�Ϸ� ����");
		doneLabel.setFont(contentFont);
		doneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(doneLabel);
		
		JCheckBox doneCheckbox = new JCheckBox("", false);
		doneCheckbox.setFont(contentFont);
		panel.add(doneCheckbox);
		
		JButton addDo = new JButton("�߰�");
		addDo.setFont(contentFont);
		addDo.addMouseListener(new addDoListener(dbController, titleField, datePicker, alarmPicker, doneCheckbox));
		panel.add(addDo);
		
		JButton removeAll = new JButton("�ʱ�ȭ");
		removeAll.setFont(contentFont);
		removeAll.addMouseListener(new removeListener(titleField, datePicker, alarmPicker, doneCheckbox));
		panel.add(removeAll);
		
		pack();
		setSize(600, 200);
		setTitle("�� �� �߰�");
		setVisible(true);
	}
	
	private class addDoListener extends MouseAdapter {
		private DatabaseController dbController;
		private JTextField title;
		private DatePicker date;
		private DateTimePicker alarm;
		private JCheckBox done;
		
		public addDoListener(DatabaseController dbController, JTextField title, DatePicker date, DateTimePicker alarm, JCheckBox done) {
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
					dbController.createNewDo(date.getDate(), title.getText(), done.isSelected());
				} else {
					dbController.createNewDo(date.getDate(), alarm.getDateTimeStrict(), title.getText(), done.isSelected());
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
