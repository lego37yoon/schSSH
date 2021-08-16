package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private static ArrayList<JCheckBox> todoArrayList;
	private static ArrayList<Integer> todoIDIndex;
	private static JCheckboxList todoList;

	public ToDoPanel(Font titleFont, Font contentFont, DatabaseController dbController, GetDate gd) {
		CustomGridBagLayout gbl_todoPanel = new CustomGridBagLayout();
		this.setLayout(gbl_todoPanel);
		
		JLabel todoLabel = new JLabel("오늘의 할 일");
		todoLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_todoLabel = new CustomGridBagConstraints("title");
		this.add(todoLabel, gbc_todoLabel);
		
		ResultSet todoListQuery = dbController.getDo(gd.getTodayByLocalDate());
		todoArrayList = new ArrayList<JCheckBox>();
		todoIDIndex = new ArrayList<Integer>();
		try {
			while (todoListQuery.next()) {
				if (todoListQuery.getInt("DONE") == 1) {
					todoArrayList.add(new JCheckBox(todoListQuery.getString("TITLE"), true));
					todoIDIndex.add(todoListQuery.getInt("ID"));
				} else {
					todoArrayList.add(new JCheckBox(todoListQuery.getString("TITLE"), false));
					todoIDIndex.add(todoListQuery.getInt("ID"));
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
		
		todoList = new JCheckboxList(todoListModel);
		todoList.setFont(contentFont);
		todoList.addMouseListener(new ListClickListener(contentFont, dbController));
		JScrollPane todoListPane = new JScrollPane(todoList);
		CustomGridBagConstraints gbc_todoListPane = new CustomGridBagConstraints("content");
		this.add(todoListPane, gbc_todoListPane);
		
		JPanel todoManagePanel = new JPanel();
		CustomGridBagConstraints gbc_todoManagePanel = new CustomGridBagConstraints("menu");
		this.add(todoManagePanel, gbc_todoManagePanel);
		todoManagePanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnAddDo = new JButton("추가");
		btnAddDo.setFont(contentFont);
		btnAddDo.addMouseListener(new AddDoListener(contentFont, dbController));
		todoManagePanel.add(btnAddDo);
		
		JButton btnManageDo = new JButton("관리");
		btnManageDo.setFont(contentFont);
		btnManageDo.addMouseListener(new ManageDoListener(contentFont, dbController));
		todoManagePanel.add(btnManageDo);
		
		JButton btnSyncDo = new JButton("동기화");
		btnSyncDo.setFont(contentFont);
		todoManagePanel.add(btnSyncDo);
	}
	
	private class AddDoListener extends MouseAdapter {
		private Font contentFont;
		private DatabaseController dbController;
		
		public AddDoListener(Font contentFont, DatabaseController dbController) {
			this.contentFont = contentFont;
			this.dbController = dbController;
		}
		
		public void mouseClicked(MouseEvent e) {
			new AddDoFrame(contentFont, dbController);
		}
	}
	
	private class ManageDoListener extends MouseAdapter {
		private Font contentFont;
		private DatabaseController dbController;
		
		public ManageDoListener(Font contentFont, DatabaseController dbController) {
			this.contentFont = contentFont;
			this.dbController = dbController;
		}
		
		public void mouseClicked(MouseEvent e) {
			new ToDoManageFrame(contentFont, dbController);
		}
	}
	
	public class ListClickListener extends MouseAdapter {
		private Font contentFont;
		private DatabaseController dbController;
		
		public ListClickListener(Font contentFont, DatabaseController dbController) {
			this.contentFont = contentFont;
			this.dbController = dbController;
		}
		
		public void mouseClicked(MouseEvent e) {
			JCheckboxList list = (JCheckboxList)e.getSource();
			if (e.getClickCount() == 1) {
				int index = list.locationToIndex(e.getPoint());
				if (list.getSelectedValue().isSelected()) {
					dbController.editDo(todoIDIndex.get(index), true);
				} else {
					dbController.editDo(todoIDIndex.get(index), false);
				}
			} else if (e.getClickCount() == 2) {
				int index = list.locationToIndex(e.getPoint());
				new EditDoFrame(contentFont, dbController, todoIDIndex.get(index));
			}
		}
	}
	
	public void refreshList(DatabaseController dbController, GetDate gd) {
		ResultSet todoListQuery = dbController.getDo(gd.getTodayByLocalDate());
		todoArrayList = new ArrayList<JCheckBox>();
		todoIDIndex = new ArrayList<Integer>();
		DefaultListModel<JCheckBox> model = (DefaultListModel<JCheckBox>) todoList.getModel();
		model.removeAllElements();
		try {
			while (todoListQuery.next()) {
				if (todoListQuery.getInt("DONE") == 1) {
					todoArrayList.add(new JCheckBox(todoListQuery.getString("TITLE"), true));
					todoIDIndex.add(todoListQuery.getInt("ID"));
				} else {
					todoArrayList.add(new JCheckBox(todoListQuery.getString("TITLE"), false));
					todoIDIndex.add(todoListQuery.getInt("ID"));
				}
			}
			
			todoListQuery.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//오류 창 띄우기
			System.out.println("할 일 데이터를 불러오지 못했습니다.");
		}
		model.addAll(todoArrayList);
	}
}
