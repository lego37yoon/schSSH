package pw.pbdiary.sch.sshWindows;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer<Object> {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		setComponentOrientation(list.getComponentOrientation());
		setFont(list.getFont());
		setBackground(list.getBackground());
		setForeground(list.getForeground());
		setSelected(isSelected);
		setEnabled(list.isEnabled());
		
		setText(value == null ? "" : value.toString());
		
		return this;
	}
}
