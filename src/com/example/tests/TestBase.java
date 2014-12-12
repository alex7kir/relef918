package com.example.tests;

import java.io.FileReader;
import java.util.Properties;

import org.testng.annotations.*;

import com.example.fw.ApplicationManager;
import com.example.pages.BasePage;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.*;

public class TestBase {

	protected ApplicationManager app;
	protected BasePage basePage;
	

	@BeforeClass
	@Parameters({"configFile"})
	public void setUp(@Optional String configFile) throws Exception {
		if (configFile == null) {
			configFile = System.getProperty("configFile");
		}
		if (configFile == null) {
			configFile = System.getenv("configFile");
		}
		if (configFile == null) {
			configFile = "application.properties";
		}
		Properties props = new Properties();
		props.load(new FileReader(configFile));
		app = ApplicationManager.getInstance();
		app.setProperties(props);
		baseUrl = app.getProperty("baseUrl");
		timeout = Long.valueOf(app.getProperty("selenide_timeout"));
	}

	@AfterTest
	public void tearDown() throws Exception {
		ApplicationManager.getInstance().stop();
	}

	
	

}