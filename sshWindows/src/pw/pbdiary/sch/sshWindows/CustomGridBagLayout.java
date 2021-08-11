package pw.pbdiary.sch.sshWindows;

import java.awt.GridBagLayout;

public class CustomGridBagLayout extends GridBagLayout {
	private static final long serialVersionUID = 1L;

	CustomGridBagLayout() {
		columnWidths = new int[]{240, 0};
		rowHeights = new int[]{25, 0, 0};
		columnWeights = new double[]{0.0, Double.MIN_VALUE};
		rowWeights = new double[]{0.0, Double.MIN_VALUE, 0.0};
	}
}
