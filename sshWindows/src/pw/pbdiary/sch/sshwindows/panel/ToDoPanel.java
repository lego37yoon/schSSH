package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;
import pw.pbdiary.sch.sshwindows.func.GetDate;

public class ToDoPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ToDoPanel(Font titleFont, Font contentFont, DatabaseController dbController, GetDate gd) {
		CustomGridBagLayout gbl_todoPanel = new CustomGridBagLayout();
		this.setLayout(gbl_todoPanel);
		
		JLabel todoLabel = new JLabel("\uC624\uB298\uC758 \uD560 \uC77C");
		todoLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_todoLabel = new CustomGridBagConstraints("title");
		this.add(todoLabel, gbc_todoLabel);
		
		ResultSet todoListQuery = dbController.getDo(gd.getTodayByLocalDate());
		ArrayList<JCheckBox> todoArrayList = new ArrayList<JCheckBox>();
		try {
			while (todoListQuery.next()) {
				if (todoListQuery.getInt("DONE") == 1) {
					todoArrayList.add(new JCheckBox(todoListQuery.getString("TITLE"), true));
				} else {
					todoArrayList.add(new JCheckBox(todoListQuery.getString("TITLE"), false));
				}
			}
			
			todoListQuery.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//오류 창 띄우기
			System.out.println("할 일 데이터를 불러오지 못했습니다.");
		}
		
		DefaultListModel<JCheckBox> todoListModel = new DefaultListModel<JCheckBox>();
		for (JCheckBox element : todoArrayList) {
			todoListModel.addElement(element);
		}
		
		JCheckboxList todoList = new JCheckboxList(todoListModel);
		todoList.setFont(contentFont);
		JScrollPane todoListPane = new JScrollPane(todoList);
		CustomGridBagConstraints gbc_todoListPane = new CustomGridBagConstraints("content");
		this.add(todoListPane, gbc_todoListPane);
		
		JPanel todoManagePanel = new JPanel();
		CustomGridBagConstraints gbc_todoManagePanel = new CustomGridBagConstraints("menu");
		this.add(todoManagePanel, gbc_todoManagePanel);
		todoManagePanel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JButton btnAddDo = new JButton("추가");
		btnAddDo.setFont(contentFont);
		todoManagePanel.add(btnAddDo);
		
		JButton btnDeleteDo = new JButton("삭제");
		btnDeleteDo.setFont(contentFont);
		todoManagePanel.add(btnDeleteDo);
		
		JButton btnManageDo = new JButton("관리");
		btnManageDo.setFont(contentFont);
		todoManagePanel.add(btnManageDo);
		
		JButton btnSyncDo = new JButton("동기화");
		btnSyncDo.setFont(contentFont);
		todoManagePanel.add(btnSyncDo);
	}
}
