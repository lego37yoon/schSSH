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
		
		JLabel titleLabel = new JLabel("제목");
		titleLabel.setFont(contentFont);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(titleLabel);
		
		JTextField titleField = new JTextField();
		titleField.setFont(contentFont);
		try {
			titleField.setText(rs.getString("TITLE"));
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "제목을 읽어오지 못했습니다.", "수정 불가", JOptionPane.ERROR_MESSAGE);
		}
		panel.add(titleField);
		
		JLabel dateLabel = new JLabel("날짜");
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
			JOptionPane.showMessageDialog(null, "날짜를 읽어오지 못했습니다.", "수정 불가", JOptionPane.ERROR_MESSAGE);
		}
		panel.add(datePicker);
		
		JLabel alarmLabel = new JLabel("알림(선택)");
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
		
		JLabel doneLabel = new JLabel("완료 여부");
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
			JOptionPane.showMessageDialog(null, "완료 여부를 읽어오지 못했습니다.", "수정 불가", JOptionPane.ERROR_MESSAGE);
		}
		panel.add(doneCheckbox);
		
		JButton saveDo = new JButton("저장");
		saveDo.setFont(contentFont);
		saveDo.addMouseListener(new saveDoListener(index, dbController, titleField, datePicker, alarmPicker, doneCheckbox));
		panel.add(saveDo);
		
		JButton removeAll = new JButton("초기화");
		removeAll.setFont(contentFont);
		removeAll.addMouseListener(new removeListener(titleField, datePicker, alarmPicker, doneCheckbox));
		panel.add(removeAll);
		
		pack();
		setSize(600, 200);
		setTitle("할 일 수정");
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
				JOptionPane.showMessageDialog(null, "비워둘 수 없는 항목을 비웠습니다.", "저장 실패", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "저장이 완료되었습니다.", "안내", JOptionPane.INFORMATION_MESSAGE);
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
