package com.example.fw;

import java.util.Properties;

public class ApplicationManager {
	
	private static ApplicationManager singleton;
	private NavigationHelper navigationHelper;
	private MailinatorHelper mailinatorHelper;
//	private WebDriverHelper webDriverHelper;
	private Properties props;




	public static ApplicationManager getInstance() {
		if (singleton == null) {
			singleton = new ApplicationManager();
		}
		return singleton;
	}
	
	public void stop() {
//		if (webDriverHelper != null) {
//			webDriverHelper.stop();
//		}
	}
	
	public void setProperties(Properties props) {
		this.props = props;
	}
	
	public String getProperty(String key) {
		return props.getProperty(key);
	}
	
	public String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
	
//	public GroupHelper getGroupHelper() {
//		if (groupHelper == null) {
//			groupHelper = new GroupHelper(this);
//		}
//		return groupHelper;
//	}
	
	public NavigationHelper getNavigationHelper() {
		if (navigationHelper == null) {
			navigationHelper = new NavigationHelper(this);
		}
		return navigationHelper;
	}
	
	public MailinatorHelper getMailinatorHelper() {
		if (mailinatorHelper == null) {
			mailinatorHelper = new MailinatorHelper(this);
		}
		return mailinatorHelper;
	}
	
//	public WebDriverHelper getWebDriverHelper() {
//		if (webDriverHelper == null) {
//			webDriverHelper = new WebDriverHelper(this);
//		}
//		return webDriverHelper;
//	}

}
