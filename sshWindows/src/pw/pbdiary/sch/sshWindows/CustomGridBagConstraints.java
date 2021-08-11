package pw.pbdiary.sch.sshWindows;

import java.awt.GridBagConstraints;

public class CustomGridBagConstraints extends GridBagConstraints{
	private static final long serialVersionUID = 1L;

	CustomGridBagConstraints(String type) {
		switch (type) {
			case  "title" -> {
				gridx = 0;
				gridwidth = GridBagConstraints.REMAINDER;
				gridy = 0;
				weighty = 0.05;
				fill = GridBagConstraints.HORIZONTAL;
			}
			case "content" -> {
				gridx = 0;
				gridwidth = GridBagConstraints.REMAINDER;
				weighty = 0.9;
				fill = GridBagConstraints.BOTH;
			}
			case "menu" -> {
				gridx = 0;
				gridwidth = GridBagConstraints.REMAINDER;
				weighty = 0.05;
				fill = GridBagConstraints.HORIZONTAL;
			}
		}
	}
}
