package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;
import pw.pbdiary.sch.sshwindows.func.GetNewMail;

public class MenuPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public MenuPanel(Font titleFont, Font contentFont, DatabaseController dbController, BrowserFrame bf) {
		this.setLayout(new GridLayout(4, 1, 0, 0));
		
		JButton btnWebMail = new JButton("\uC6F9 \uBA54\uC77C NaN\uAC74");
		btnWebMail.setFont(titleFont);
		btnWebMail.addMouseListener(new GoMailListener(bf, dbController));
		btnWebMail.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(btnWebMail);
		
		if (!dbController.getSettings("MAIL_ID").equals("")) {
			GetNewMail gnm = new GetNewMail();
			int countMail = gnm.get(dbController.getSettings("MAIL_ID"), dbController.getSettings("MAIL_PW"));
			if (countMail != -1) {
				btnWebMail.setText("웹 메일 " + countMail + "건");
			}
		}
		
		JButton btnGoPortal = new JButton("\uC21C\uCC9C\uD5A5\uB300 \uD3EC\uD138");
		btnGoPortal.setFont(titleFont);
		btnGoPortal.addMouseListener(new GoPortalListener(bf));
		btnGoPortal.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(btnGoPortal);
		
		JButton btnGoInfoSys = new JButton("\uC885\uD569\uC815\uBCF4\uC2DC\uC2A4\uD15C");
		btnGoInfoSys.setFont(titleFont);
		btnGoInfoSys.addMouseListener(new GoInfoSysListener(bf, dbController));
		btnGoInfoSys.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(btnGoInfoSys);
		
		JButton btnSettings = new JButton("\uC124\uC815");
		btnSettings.setFont(titleFont);
		btnSettings.addMouseListener(new SettingsBtnListener(contentFont, dbController));
		btnSettings.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(btnSettings);
	}
	
	private class GoMailListener extends MouseAdapter {
		private BrowserFrame browser;
		private DatabaseController dbController;
		
		public GoMailListener(BrowserFrame bf, DatabaseController dbController) {
			this.browser = bf;
			this.dbController = dbController;
		}
		
		public void mouseClicked(MouseEvent e) {
			browser.portalLogin("https://portal.sch.ac.kr/r.jsp?url=https%3A%2F%2Fmail.sch.ac.kr%2Faccount%2FoAuthCheckUser.do", dbController);
		}
	}
	
	private class GoPortalListener extends MouseAdapter {
		private BrowserFrame browser;
		
		public GoPortalListener(BrowserFrame bf) {
			this.browser = bf;
		}
		
		public void mouseClicked(MouseEvent e) {
			browser.portalLogin();
		}
	}
	
	private class GoInfoSysListener extends MouseAdapter {
		private BrowserFrame browser;
		private DatabaseController dbController;
		
		public GoInfoSysListener(BrowserFrame bf, DatabaseController dbController) {
			this.browser = bf;
			this.dbController = dbController;
		}
		
		public void mouseClicked(MouseEvent e) {
			browser.portalLogin("https://portal.sch.ac.kr/r.jsp?url=https%3A%2F%2Fst.sch.ac.kr", dbController);
		}
	}
	
	private class SettingsBtnListener extends MouseAdapter {
		private Font font;
		private DatabaseController dbController;
		
		public SettingsBtnListener(Font contentFont, DatabaseController dbController) {
			this.font = contentFont;
			this.dbController = dbController;
		}
		
		public void mouseClicked(MouseEvent e) {
			new SettingsFrame(font, dbController);
		}
	}
}
