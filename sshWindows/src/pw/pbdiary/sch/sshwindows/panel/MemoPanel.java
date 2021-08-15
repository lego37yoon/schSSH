package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;

public class MemoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private DatabaseController db;
	private JEditorPane editorPane;

	public MemoPanel(Font titleFont, Font contentFont, DatabaseController dbController) {
		this.db = dbController;
		CustomGridBagLayout gbl_editorPanel = new CustomGridBagLayout();
		this.setLayout(gbl_editorPanel);
		
		JLabel editorLabel = new JLabel("\uAC04\uB2E8 \uBA54\uBAA8");
		editorLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_editorLabel = new CustomGridBagConstraints("title");
		this.add(editorLabel, gbc_editorLabel);
		
		editorPane = new JEditorPane();
		editorPane.setFont(contentFont);
		editorPane.setText(dbController.getMemo());
		JScrollPane scrollPane = new JScrollPane(editorPane);
		CustomGridBagConstraints gbc_editorPane = new CustomGridBagConstraints("content");
		this.add(scrollPane, gbc_editorPane);
		
		JPanel editorManagePanel = new JPanel();
		CustomGridBagConstraints gbc_editorManagePanel = new CustomGridBagConstraints("menu");
		this.add(editorManagePanel, gbc_editorManagePanel);
		editorManagePanel.setLayout(new GridLayout(1, 3, 0, 0));
		
		JButton btnExportSimpleMemo = new JButton("��������");
		btnExportSimpleMemo.setFont(contentFont);
		btnExportSimpleMemo.addMouseListener(new ExportMemoListener());
		editorManagePanel.add(btnExportSimpleMemo);
		
		JButton btnSaveSimpleMemo = new JButton("����");
		btnSaveSimpleMemo.setFont(contentFont);
		btnSaveSimpleMemo.addMouseListener(new SaveMemoListener());
		editorManagePanel.add(btnSaveSimpleMemo);
		
		JButton btnRemoveAll = new JButton("�����");
		btnRemoveAll.setFont(contentFont);
		btnRemoveAll.addMouseListener(new RemoveMemoListener());
		editorManagePanel.add(btnRemoveAll);	
	}
	
	private class ExportMemoListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			String exportText = editorPane.getText();
			fileSave(exportText);
		}
	}
	
	private class SaveMemoListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			db.saveMemo(editorPane.getText());
		}
	}
	
	private class RemoveMemoListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			db.deleteMemo();
		}
	}
	
	private void fileSave(String exportText) {
		JFileChooser chooser = new JFileChooser();
		int result = chooser.showSaveDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				File file = chooser.getSelectedFile();
				FileWriter writer = new FileWriter(file);
				writer.write(exportText);
				writer.close();
				
				JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.", "�ȳ�", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				System.out.println("������ ���� ���߽��ϴ�.");
			}
		}
	}
}
