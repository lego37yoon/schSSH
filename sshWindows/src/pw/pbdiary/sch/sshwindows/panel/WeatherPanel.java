package pw.pbdiary.sch.sshwindows.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.batik.swing.JSVGCanvas;

import pw.pbdiary.sch.sshwindows.func.BrowserFrame;
import pw.pbdiary.sch.sshwindows.func.GetAirPM;
import pw.pbdiary.sch.sshwindows.func.GetWeather;

public class WeatherPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static BrowserFrame bf;

	public WeatherPanel(Font titleFont, Font celciusFont, Font contentFont, BrowserFrame bf) {
		WeatherPanel.bf = bf;
		CustomGridBagLayout gbl_weatherPanel = new CustomGridBagLayout();
		this.setLayout(gbl_weatherPanel);
		
		JLabel weatherLabel = new JLabel("\uB0A0\uC528");
		weatherLabel.setFont(titleFont);
		CustomGridBagConstraints gbc_weatherLabel = new CustomGridBagConstraints("title");
		this.add(weatherLabel, gbc_weatherLabel);
		
		//날씨 정보 얻기
		GetWeather gw = new GetWeather();
		GetAirPM pm = new GetAirPM();
		
		JPanel wIconAndTemp = new JPanel();
		wIconAndTemp.setLayout(new GridLayout(1, 2, 0, 0));
		wIconAndTemp.setBackground(new Color(255, 255, 255));
		CustomGridBagConstraints gbc_wIconAndTemp = new CustomGridBagConstraints("horizontal", 0.8);
		this.add(wIconAndTemp, gbc_wIconAndTemp);
		
		JSVGCanvas weatherIcon = new JSVGCanvas();
		weatherIcon.setURI(switch(gw.getIfRain()) {
			case 1 -> "assets/icons/cloud-showers-heavy-solid.svg";
			case 2 -> "assets/icons/umbrella-solid.svg";
			case 3 -> "assets/icons/snowflake-regular.svg";
			case 4 -> "assets/icons/cloud-rain-solid.svg";
			default -> "assets/icons/temperature-high-solid.svg";
		});
		wIconAndTemp.add(weatherIcon);
		
		JLabel weatherCelcius = new JLabel(gw.getCelsius());
		weatherCelcius.setFont(celciusFont);
		weatherCelcius.setVerticalAlignment(SwingConstants.CENTER);
		weatherCelcius.setHorizontalAlignment(SwingConstants.CENTER);
		wIconAndTemp.add(weatherCelcius);
		
		JLabel weatherOtherInfo = new JLabel("습도 " + gw.getHumidity() + "/ PM 2.5 " + pm.getPM25() + "/ PM 10 " + pm.getPM10());
		weatherOtherInfo.setFont(contentFont);
		weatherOtherInfo.setHorizontalAlignment(SwingConstants.CENTER);
		CustomGridBagConstraints gbc_weatherOtherInfo = new CustomGridBagConstraints("subcontent");
		this.add(weatherOtherInfo, gbc_weatherOtherInfo);
		
		JLabel weatherPMGrade = new JLabel("초미세먼지 " + pm.getPM25Grade() + " / 미세먼지 " + pm.getPM10Grade());
		weatherPMGrade.setFont(contentFont);
		weatherPMGrade.setHorizontalAlignment(SwingConstants.CENTER);
		CustomGridBagConstraints gbc_weatherPMGrade = new CustomGridBagConstraints("subcontent");
		this.add(weatherPMGrade, gbc_weatherPMGrade);
		
		JPanel weatherWebBtn = new JPanel();
		weatherWebBtn.setLayout(new GridLayout(1, 2, 0, 0));
		CustomGridBagConstraints gbc_weatherWebBtn = new CustomGridBagConstraints("menu");
		this.add(weatherWebBtn, gbc_weatherWebBtn);
		
		JButton kmaGo = new JButton("기상청");
		kmaGo.setFont(contentFont);
		kmaGo.addMouseListener(new BtnMouseEventListener(0));
		weatherWebBtn.add(kmaGo);
		
		JButton airKoreaGo = new JButton("에어코리아");
		airKoreaGo.setFont(contentFont);
		airKoreaGo.addMouseListener(new BtnMouseEventListener(1));
		weatherWebBtn.add(airKoreaGo);
	}

	private class BtnMouseEventListener extends MouseAdapter {
		private int type = 0;
		public BtnMouseEventListener(int type) {
			this.type = type;
		}
		
		public void mouseClicked(MouseEvent e) {
			switch(type) {
				case 0 -> bf.loadPage("https://www.weather.go.kr/w/index.do");
				case 1 -> bf.loadPage("https://www.airkorea.or.kr/index");
			}
		}
	}
}
