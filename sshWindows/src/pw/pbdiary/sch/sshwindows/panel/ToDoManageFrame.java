package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;

public class ToDoManageFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JList<String> todoList;
	private static ArrayList<Integer> todoIDIndex;

	public ToDoManageFrame(Font contentFont, DatabaseController dbController) {
		JPanel manager = new JPanel();
		CustomGridBagLayout gbl_manager = new CustomGridBagLayout();
		manager.setLayout(gbl_manager);
		
		ResultSet todoListQuery = dbController.getAllDo();
		ArrayList<String>todoArrayList = new ArrayList<String>();
		todoIDIndex = new ArrayList<Integer>();
		try {
			while (todoListQuery.next()) {
				todoArrayList.add(todoListQuery.getString("TITLE"));
				todoIDIndex.add(todoListQuery.getInt("ID"));
			}
			
			todoListQuery.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//오류 창 띄우기
			System.out.println("할 일 데이터를 불러오지 못했습니다.");
		}
		
		DefaultListModel<String> todoListModel = new DefaultListModel<String>();
		for (String element : todoArrayList) {
			todoListModel.addElement(element);
		}
		
		todoList = new JList<String>(todoListModel);
		todoList.setFont(contentFont);
		JScrollPane todoListPane = new JScrollPane(todoList);
		CustomGridBagConstraints gbc_todoListPane = new CustomGridBagConstraints("content");
		manager.add(todoListPane, gbc_todoListPane);
		
		JPanel todoManagePanel = new JPanel();
		CustomGridBagConstraints gbc_todoManagePanel = new CustomGridBagConstraints("menu");
		manager.add(todoManagePanel, gbc_todoManagePanel);
		todoManagePanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnAddDo = new JButton("추가");
		btnAddDo.setFont(contentFont);
		btnAddDo.addMouseListener(new AddDoListener(contentFont, dbController));
		todoManagePanel.add(btnAddDo);
		
		JButton btnModifyDo = new JButton("수정");
		btnModifyDo.setFont(contentFont);
		btnModifyDo.addMouseListener(new EditDoListener(contentFont, dbController, todoList));
		todoManagePanel.add(btnModifyDo);
		
		JButton btnDeleteDo = new JButton("삭제");
		btnDeleteDo.setFont(contentFont);
		btnDeleteDo.addMouseListener(new DeleteDoListener(dbController, todoList));
		todoManagePanel.add(btnDeleteDo);
		
		setContentPane(manager);
		pack();
		setSize(400, 400);
		setTitle("할 일 관리");
		setVisible(true);
		addWindowListener(new RefreshListListener(dbController, todoList, todoIDIndex));
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
	
	private class EditDoListener extends MouseAdapter {
		private Font contentFont;
		private DatabaseController dbController;
		private JList<String> list;
		
		public EditDoListener(Font contentFont, DatabaseController dbController, JList<String> list) {
			this.contentFont = contentFont;
			this.dbController = dbController;
			this.list = list;
		}
		
		public void mouseClicked(MouseEvent e) {
			new EditDoFrame(contentFont, dbController, todoIDIndex.get(list.getSelectedIndex()));
		}
	}
	
	private class DeleteDoListener extends MouseAdapter {
		private DatabaseController dbController;
		private JList<String> list;
		
		public DeleteDoListener(DatabaseController dbController, JList<String> list) {
			this.dbController = dbController;
			this.list = list;
		}
		
		public void mouseClicked(MouseEvent e) {
			dbController.deleteDo(todoIDIndex.get(list.getSelectedIndex()));
			refreshList(dbController, list, todoIDIndex);
		}
	}
	
	private class RefreshListListener extends WindowAdapter {
		private DatabaseController dbController;
		private JList<String> list;
		private ArrayList<Integer> indexList;
		
		public RefreshListListener(DatabaseController dbController, JList<String> list, ArrayList<Integer> indexList) {
			this.dbController = dbController;
			this.list = list;
			this.indexList = indexList;
		}
		
		public void windowActivated(WindowEvent e) {
			refreshList(dbController, list, indexList);
		}
	}
	
	private void refreshList(DatabaseController dbController, JList<String> list, ArrayList<Integer> indexList) {
		ResultSet rs = dbController.getAllDo();
		DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
		model.removeAllElements();
		indexList = new ArrayList<Integer>();
		try {
			while(rs.next()) {
				model.addElement(rs.getString("TITLE"));
				indexList.add(rs.getInt("ID"));
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
