package com.example.fw;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.CapabilityType;

import net.lightbody.bmp.proxy.ProxyServer;

import com.codeborne.selenide.WebDriverProvider;
import com.opera.core.systems.OperaDriver;

public class CustomWebDriverProvider implements WebDriverProvider {

	DesiredCapabilities caps = new DesiredCapabilities();
	ApplicationManager app = ApplicationManager.getInstance();
	protected static WebDriver driver;

	@Override
	public WebDriver createDriver(DesiredCapabilities caps) {

		ProxyServer bmp = new ProxyServer(8071);
		try {
			bmp.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		bmp.setSocketOperationTimeout(300000);
		bmp.setConnectionTimeout(300000);
		bmp.autoBasicAuthorization("", app.getProperty("basic_auth_login"), app.getProperty("basic_auth_password"));

		try {
			caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		String browser = app.getProperty("browser");
		if ("ie".equals(browser)) {
			driver = new InternetExplorerDriver(caps);
		} else if ("chrome".equals(browser)) {
        	driver = new ChromeDriver(caps);
		} else if ("opera".equals(browser)) {
			driver = new OperaDriver(caps);
		} else {
			driver = new FirefoxDriver(caps);
		}
//		driver.manage().timeouts().implicitlyWait(
//				Integer.parseInt(app.getProperty("implicitWait", "0")), TimeUnit.SECONDS);
		driver.manage().window().maximize();

		return driver;

	}
}
