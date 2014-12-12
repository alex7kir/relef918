package com.example.fw;

//import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase{
	
	public NavigationHelper(ApplicationManager manager) {
		super(manager);
	}
	
//	public void goToGroupListPage() {
//		click(By.linkText("groups"));
//	}

	public void openMainPage() {
//		openUrl(manager.getProperty("baseUrl"));
		open(manager.getProperty("baseUrl"));
	}
	
//	public void openAnyPage(String url) {
//		openUrl(url);
//	}
//	
//	public void returnToMainPage() {
//		driver.findElement(By.linkText("home page")).click();
//	}
//	
//	public void openPrintPhonesPage() {
//		openUrl(manager.getProperty("baseUrl") + "view.php?all&print&phones");
//	}
//
//	public void clickExport() {
//		openUrl(manager.getProperty("baseUrl") + "csv.php");
//	}
		
	public void openPage(String url) {
		open(url);
	}
	
	public void runSearch(String query) {
		$(By.name("q")).setValue(query).pressEnter();
		
	}

}
