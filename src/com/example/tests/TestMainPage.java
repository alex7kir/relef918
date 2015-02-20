package com.example.tests;

import static com.codeborne.selenide.Condition.*;
//import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
//import static org.testng.Assert.assertEquals;
//import static com.codeborne.selenide.Selenide.sleep;

import org.testng.annotations.*;

import com.codeborne.selenide.SelenideElement;
import com.example.pages.MainPage;

public class TestMainPage extends TestBase{
	
	private MainPage mainPage;
	
	@DataProvider(name = "test1")
	public Object[][] createData1() {
	 return new Object[][] {
	   { 0, 1},
	   { 1, 2}
	 };
	}
	
	@DataProvider(name = "compare")
	public Object[][] compareItemData() {
	 return new Object[][] {
	   {2, 3},
	 };
	}
	
	@DataProvider(name = "test2")
	public Object[][] addItemToBasketFromComparePopupData() {
	 return new Object[][] {
	   {2, 1},
	 };
	}
	
	@DataProvider(name = "remove_from_compare")
	public Object[][] removeItemFromCompareData() {
	 return new Object[][] {
	   {2},
	 };
	}
	
	@BeforeClass
	public void prepareToTest() throws InterruptedException {
		clearBrowserCache();
		mainPage = open(app.getProperty("shop1_absolute_url"), MainPage.class);
//		mainPage.login(app.getProperty("buyer_login"), app.getProperty("buyer_password"));
	}
	
	@Test (dataProvider = "test1")
	public void testAddItemToBasket(int item_number, int quantity) throws InterruptedException {
		int oldItemsNumber = mainPage.getNumberOfItemsInBasketFromHeader();
		double oldItemsPrice = mainPage.getSumInBasketFromHeader();
		//Берем пока тупо товары по порядку нахождения на странице
		SelenideElement item = mainPage.getItemBySerialNumber(item_number);
		String itemPrice = mainPage.getItemPrice(item);
		mainPage.addItemToBasket(item, quantity);
		checkThatItemIsAddedCorrectly(mainPage, oldItemsNumber, oldItemsPrice, quantity, itemPrice);
		refresh();
		mainPage.checkThatItemMarkedAsAdded(item);
	}
	
	@Test (dataProvider = "compare")
	public void testAddItemToCompare(int item1_number, int item2_number)  {
		String item1_code = mainPage.getItemCodeText(item1_number);
		String item2_code = mainPage.getItemCodeText(item2_number);
		mainPage.addItemToCompare(item1_number);
		mainPage.compare_link(item1_number).shouldHave(text("Сравнить 1 товар"));
		mainPage.openComparePopup(item1_number);
		mainPage.comparePopupShoudHave(item1_code);
		mainPage.closeComparePopup();
		mainPage.addItemToCompare(item2_number);
		mainPage.compare_link(item1_number).shouldHave(text("Сравнить 2 товара"));
		mainPage.compare_link(item2_number).shouldHave(text("Сравнить 2 товара"));
		mainPage.openComparePopup(item2_number);
		mainPage.comparePopupShoudHave(item1_code);
		mainPage.comparePopupShoudHave(item2_code);
		mainPage.closeComparePopup();
	}
		
	@Test (dataProvider = "test2", dependsOnMethods = {"testAddItemToCompare"})
	public void testAddItemToBasketFromComparePopup(int item_number, int quantity) {
		int oldItemsNumber = mainPage.getNumberOfItemsInBasketFromHeader();
		double oldItemsPrice = mainPage.getSumInBasketFromHeader();
		SelenideElement item = mainPage.getItemBySerialNumber(item_number);
		String itemPrice = mainPage.getItemPrice(item);
		mainPage.openComparePopup(item_number);
		String item_code = mainPage.getItemCodeText(item_number);
		mainPage.addItemToBasketFromComparePopup(item_code, 1);
		checkThatItemIsAddedCorrectly(mainPage, oldItemsNumber, oldItemsPrice, quantity, itemPrice);
		mainPage.closeComparePopup();
		refresh();
		mainPage.checkThatItemMarkedAsAdded(item);
	}
	
	@Test (dataProvider = "remove_from_compare", dependsOnMethods = {"testAddItemToBasketFromComparePopup"})
	public void testRemoveItemFromCompare(int item_number)  {
		String item_code = mainPage.getItemCodeText(item_number);
		mainPage.openComparePopup(item_number);
		mainPage.removeItemFromCompare(item_code);
		mainPage.closeComparePopup();
		mainPage.compare_link(item_number).shouldHave(text("Добавить к сравнению"));
	}
	
	
	
//	@Test (dataProvider = "compare")
//	public void testAddItemToCompare2(int item1_number, int item2_number)  {
//		String item1_code = mainPage.getItemCodeText(item1_number);
//		String item2_code = mainPage.getItemCodeText(item2_number);
//		SelenideElement item = mainPage.getItemBySerialNumber(item1_number);
//		SelenideElement compare_link = item.find("noindex>a.add-compare>span");
//		assertEquals(compare_link.text(), "Добавить к сравнению", "Возможно, товар уже добавлен к сравнению");
//		compare_link.click();
//		compare_link.parent().waitUntil(hasClass("added"), 20000);
//		compare_link.shouldHave(text("Сравнить 1 товар"));
//		compare_link.click();
//		SelenideElement comparePopup = $("div.popup-compare");
//		sleep(1000);
//		comparePopup.find("a.close").click();
//		compare_link.shouldHave(text("Сравнить 1 товар"));
//	}
	
}
