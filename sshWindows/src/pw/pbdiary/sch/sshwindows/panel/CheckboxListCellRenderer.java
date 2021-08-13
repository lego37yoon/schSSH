package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer<JCheckBox> {

	private static final long serialVersionUID = 1L;

	public Component getListCellRendererComponent(JList<? extends JCheckBox> list, JCheckBox value, int index, boolean isSelected,
			boolean cellHasFocus) {
		setComponentOrientation(list.getComponentOrientation());
		setFont(list.getFont());
		setBackground(list.getBackground());
		setForeground(list.getForeground());
		setSelected(isSelected);
		setEnabled(list.isEnabled());
		
		setText(value == null ? "" : value.getText());
		setSelected(value.isSelected());
		
		return this;
	}
}
