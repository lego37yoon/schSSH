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
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefLoadHandlerAdapter;

import pw.pbdiary.sch.sshwindows.func.DatabaseController;

public class BrowserFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static CefApp ca;
	private static CefClient cc;
	private static CefBrowser cb;

	public BrowserFrame(DatabaseController dbController) {
		CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
			@Override
			public void stateHasChanged(org.cef.CefApp.CefAppState state) {
				if (state == CefAppState.TERMINATED) {
					System.out.println("JCEF Session Closed");
				}
			}
		});
		CefSettings settings = new CefSettings();
		settings.windowless_rendering_enabled = false;
		settings.locale = "ko";
		ca = CefApp.getInstance(settings);
		cc = ca.createClient();
		cc.addDisplayHandler(new CefDisplayHandlerAdapter() {
			@Override
			public void onTitleChange(CefBrowser browser, String title) {
				setTitle(title);
			}
		});
		cc.addLoadHandler(new BrowserFrameLoadAdapter(dbController));
//		cc.addDownloadHandler(downloadDialog);
//		DownloadDialog downloadDialog = new DownloadDialog(this);
		
		pack();
		setSize(1200, 800);
		setTitle("JCEF Browser");
	}
	
	public void loadPage(String url, boolean visibility) {
		setVisible(visibility);
		if (cb == null) {
			cb = cc.createBrowser(url, false, false);
			Component browserUserInterface = cb.getUIComponent();
			getContentPane().add(browserUserInterface, BorderLayout.CENTER);
		} else {
			cb.loadURL(url);
		}
	}
	
	public void loadPage(String url) {
		loadPage(url, true);
	}
	
	public void portalLogin() {
		loadPage("https://portal.sch.ac.kr/_custom/sch/oauth/login_portal.jsp");
	}
	
	public void portalLogin(String url, DatabaseController dbController) {
		loadPage("https://portal.sch.ac.kr/_custom/sch/oauth/login_portal.jsp");
		dbController.saveSetting("REDIRECT_URL", url);
	}
	
	public void disposeFrame() {
		CefApp.getInstance().dispose();
		dispose();
	}
	
	private class BrowserFrameLoadAdapter extends CefLoadHandlerAdapter {
		private DatabaseController dbController;
		
		public BrowserFrameLoadAdapter(DatabaseController dbController) {
			this.dbController = dbController;
		}
		
		@Override
		public void onLoadingStateChange(CefBrowser browser, boolean isLoading, boolean canGoBack, boolean canGoForward) {
			if (browser.getURL().startsWith("https://sso.sch.ac.kr/oa/au/auth?login_endpoint=oauth&retUrl=")) {
				browser.executeJavaScript("document.getElementById('id').value='" + dbController.getSettings("ID") + "';", "", 0);
				browser.executeJavaScript("document.getElementById('passw').value='" + dbController.getSettings("PW") + "';", "", 0);
				browser.executeJavaScript("document.getElementsByClassName('btn-type-skyblue')[0].click();", "", 0);
			}
			if (browser.getURL().startsWith("https://sso.sch.ac.kr/oa/im/modify/modifyPwd")) {
				browser.executeJavaScript("document.getElementById('pw').value='" + dbController.getSettings("PW") + "';", "", 0);
				browser.executeJavaScript("alert('비밀번호 변경 시 설정 > 순천향 PW 값도 변경해주셔야 정상적인 로그인 처리가 됩니다.');", "", 0);
			}
			if (browser.getURL().startsWith("https://portal.sch.ac.kr/p") && !dbController.getSettings("REDIRECT_URL").equals("")) {
				browser.executeJavaScript("window.location.href='" + dbController.getSettings("REDIRECT_URL") + "';", "", 0);
				dbController.saveSetting("REDIRECT_URL", "");
			}
		}
	}
}
