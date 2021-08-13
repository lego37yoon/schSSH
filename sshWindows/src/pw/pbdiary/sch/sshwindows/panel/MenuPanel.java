package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public MenuPanel(Font titleFont, Font contentFont) {
		this.setLayout(new GridLayout(4, 1, 0, 0));
		
		JButton btnWebMail = new JButton("\uC6F9 \uBA54\uC77C 0\uAC74");
		btnWebMail.setFont(titleFont);
		btnWebMail.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(btnWebMail);
		
		JButton btnGoPortal = new JButton("\uC21C\uCC9C\uD5A5\uB300 \uD3EC\uD138");
		btnGoPortal.setFont(titleFont);
		btnGoPortal.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(btnGoPortal);
		
		JButton btnGoInfoSys = new JButton("\uC885\uD569\uC815\uBCF4\uC2DC\uC2A4\uD15C");
		btnGoInfoSys.setFont(titleFont);
		btnGoInfoSys.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(btnGoInfoSys);
		
		JButton btnSettings = new JButton("\uC124\uC815");
		btnSettings.setFont(titleFont);
		btnSettings.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(btnSettings);
	}
}
