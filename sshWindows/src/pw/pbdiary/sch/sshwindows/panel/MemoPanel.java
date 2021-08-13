package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;

public class MemoPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public MemoPanel(Font titleFont, Font contentFont, DatabaseController dbController) {
		CustomGridBagLayout gbl_editorPanel = new CustomGridBagLayout();
		this.setLayout(gbl_editorPanel);
		
		JLabel editorLabel = new JLabel("\uAC04\uB2E8 \uBA54\uBAA8");
		editorLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_editorLabel = new CustomGridBagConstraints("title");
		this.add(editorLabel, gbc_editorLabel);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setFont(contentFont);
		editorPane.setContentType("text/html");
		editorPane.setText(dbController.getMemo());
		CustomGridBagConstraints gbc_editorPane = new CustomGridBagConstraints("content");
		this.add(editorPane, gbc_editorPane);
		
		JPanel editorManagePanel = new JPanel();
		CustomGridBagConstraints gbc_editorManagePanel = new CustomGridBagConstraints("menu");
		this.add(editorManagePanel, gbc_editorManagePanel);
		editorManagePanel.setLayout(new GridLayout(1, 3, 0, 0));
		
		JButton btnExportSimpleMemo = new JButton("\uB0B4\uBCF4\uB0B4\uAE30");
		btnExportSimpleMemo.setFont(contentFont);
		editorManagePanel.add(btnExportSimpleMemo);
		
		JButton btnSaveSimpleMemo = new JButton("\uC800\uC7A5");
		btnSaveSimpleMemo.setFont(contentFont);
		editorManagePanel.add(btnSaveSimpleMemo);
		
		JButton btnRemoveAll = new JButton("\uC9C0\uC6B0\uAE30");
		btnRemoveAll.setFont(contentFont);
		editorManagePanel.add(btnRemoveAll);
		
	}
}
