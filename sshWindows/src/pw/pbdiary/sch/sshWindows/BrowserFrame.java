package pw.pbdiary.sch.sshWindows;

import javax.swing.JFrame;

import org.cef.browser.CefBrowser;

public class BrowserFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private CefBrowser browser_ = null;
	
	public BrowserFrame() {
		this(null);
	}

	public BrowserFrame(String title) {
		super(title);
		
	}
}
