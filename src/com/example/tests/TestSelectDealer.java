package com.example.tests;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.example.pages.CatalogPage;
import com.example.pages.MainPage;

public class TestSelectDealer extends TestBase{
	
	private MainPage mainPage;
	
	@DataProvider(name = "test1")
	public Object[][] createData1() {
	 return new Object[][] {
	   { app.getProperty("shop1_city"), app.getProperty("shop1_name"), app.getProperty("shop1_domen")},
	 };
	}
	
	@DataProvider(name = "test2")
	public Object[][] newDealerData() {
	 return new Object[][] {
			 { app.getProperty("shop2_city"), app.getProperty("shop2_name"), app.getProperty("shop2_domen")},
	 };
	}
	
	@Test (dataProvider = "test1")
	public void testSelectDealerWithCodeByUnauthorizedUser(String city, String dealer, String dealer_code) {
		clearBrowserCache();
		mainPage = open("/", CatalogPage.class);
		checkThatPricesAreNotDisplayed();
		mainPage.chooseDealer(city, dealer);
		checkThatDealerIsDisplayed(dealer);
		checkThatDealerSubdomainIsApplied(dealer_code);
		checkThatPricesAreDisplayed();
	}
	
	@Test(dataProvider = "test2", dependsOnMethods = { "testSelectDealerWithCodeByUnauthorizedUser" })
	public void testChangeDealerWithCodeByUnauthorizedUser(String city, String dealer, String dealer_code) {
		mainPage.replaceDealer();
		checkThatDealerIsNotDisplayed();
		checkThatDealerSubdomainIsRemoved();
		checkThatPricesAreNotDisplayed();
		mainPage.chooseDealer(city, dealer);
		checkThatDealerIsDisplayed(dealer);
		checkThatDealerSubdomainIsApplied(dealer_code);
		checkThatPricesAreDisplayed();
	}
	
	@Test (dataProvider = "test1", dependsOnMethods = { "testChangeDealerWithCodeByUnauthorizedUser" })
	public void testSelectDealerWithCodeByAuthorizedUser(String city, String dealer, String dealer_code) throws InterruptedException {
		clearBrowserCache();
		mainPage = open("/", CatalogPage.class);
		mainPage.login(app.getProperty("buyer_login"), app.getProperty("buyer_password"));
		checkThatPricesAreNotDisplayed();
		mainPage.chooseDealer(city, dealer);
		checkThatDealerIsDisplayed(dealer);
		checkThatDealerSubdomainIsApplied(dealer_code);
		checkThatPricesAreDisplayed();
	}
	
	@Test(dataProvider = "test2", dependsOnMethods = { "testSelectDealerWithCodeByAuthorizedUser" })
	public void testChangeDealerWithCodeByAuthorizedUser(String city, String dealer, String dealer_code) {
		mainPage.replaceDealer();
		checkThatDealerIsNotDisplayed();
		checkThatDealerSubdomainIsRemoved();
		checkThatPricesAreNotDisplayed();
		mainPage.chooseDealer(city, dealer);
		checkThatDealerIsDisplayed(dealer);
		checkThatDealerSubdomainIsApplied(dealer_code);
		checkThatPricesAreDisplayed();
		clearBrowserCache();
	}

	
	
	
//-------------------------------------------------------------------------------

	public void checkThatDealerIsDisplayed(String dealer) {
		mainPage.dealer_name().should(hasAttribute("title", dealer));
	}
	
	public void checkThatDealerIsNotDisplayed() {
		mainPage.dealer_name().shouldNot(exist);
		mainPage.select_town_button().should(exist).shouldHave(text("Выбрать город"));
	}

	public void checkThatDealerSubdomainIsApplied(String dealer_code) {
		String required_url = "http://" + dealer_code + "." + baseUrl.replace("http://", "") + "/";
//		System.out.println("required url= " + required_url);
//		System.out.println("url()= " + url());
		assert url().equals(required_url);
	}
	
	public void checkThatDealerSubdomainIsRemoved() {
		String required_url = baseUrl + "/?IS_SELECT_DEALER=Y";
		assert url().equals(required_url);
	}
	
	private void checkThatPricesAreDisplayed() {
		mainPage.first_price_block().should(exist);
	}
	
	private void checkThatPricesAreNotDisplayed() {
		mainPage.first_price_block().shouldNot(exist);
	}

}
