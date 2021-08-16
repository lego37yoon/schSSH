package pw.pbdiary.sch.sshwindows.panel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;

public class BrowserFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static CefApp ca;
	private static CefClient cc;
	private static CefBrowser cb;
	

	public BrowserFrame() {
		CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
			@Override
			public void stateHasChanged(org.cef.CefApp.CefAppState state) {
				if (state == CefAppState.TERMINATED) {
					System.out.println("JCEF Session Closed");
				}
			}
		});
		CefSettings settings = new CefSettings();
//		DownloadDialog downloadDialog = new DownloadDialog(this);
		settings.windowless_rendering_enabled = false;
		settings.locale = "ko";
		ca = CefApp.getInstance(settings);
		cc = ca.createClient();
//		cc.addDownloadHandler(downloadDialog);
		
		pack();
		setSize(800, 600);
		setTitle("JCEF Browser");
	}
	
	public void loadPage(String url) {
		setVisible(true);
		if (cb == null) {
			cb = cc.createBrowser(url, false, false);
			Component browserUserInterface = cb.getUIComponent();
			getContentPane().add(browserUserInterface, BorderLayout.CENTER);
		} else {
			cb.loadURL(url);
		}
	}
	
	public void disposeFrame() {
		CefApp.getInstance().dispose();
		dispose();
	}
}
