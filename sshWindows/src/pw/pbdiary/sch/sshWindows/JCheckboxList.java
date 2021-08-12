package pw.pbdiary.sch.sshWindows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

public class JCheckboxList extends JList<JCheckBox> {
	private static final long serialVersionUID = 1L;
	
	public JCheckboxList() {
		setCellRenderer(new CheckboxListCellRenderer());
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int index = locationToIndex(e.getPoint());
				if (index != -1) {
					JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
					checkbox.setSelected(!checkbox.isSelected());
					repaint();
				}
			}
		});
		
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
	
	public JCheckboxList(ListModel<JCheckBox> list) {
		this();
		setModel(list);
	}
}
