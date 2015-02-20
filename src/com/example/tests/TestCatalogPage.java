package com.example.tests;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;
import com.example.pages.CatalogPage;

public class TestCatalogPage extends TestBase{
	
	private CatalogPage catalogPage;
	
	@DataProvider(name = "test1")
	public Object[][] createData1() {
	 return new Object[][] {
	   { 0, 1},
	   { 2, 2}
	 };
	}
	
	@DataProvider(name = "compare")
	public Object[][] compareItemData() {
	 return new Object[][] {
	   {0, 1},
	 };
	}
	
	@DataProvider(name = "test2")
	public Object[][] addItemToBasketFromComparePopupData() {
	 return new Object[][] {
	   {1, 1},
	 };
	}
	
	@DataProvider(name = "remove_from_compare")
	public Object[][] removeItemFromCompareData() {
	 return new Object[][] {
	   {0},
	 };
	}
	
	@BeforeClass
	public void prepareToTest() throws InterruptedException {
		clearBrowserCache();
		catalogPage = open(app.getProperty("shop1_absolute_url") + app.getProperty("catalog_page"), CatalogPage.class);
//		catalogPage.login(app.getProperty("buyer_login"), app.getProperty("buyer_password"));
	}
	
	@Test (dataProvider = "test1")
	public void testAddItemToBasketFromCatalogPage(int item_number, int quantity) throws InterruptedException {
		int oldItemsNumber = catalogPage.getNumberOfItemsInBasketFromHeader();
		double oldItemsPrice = catalogPage.getSumInBasketFromHeader();
		//Берем пока тупо товары по порядку нахождения на странице
		SelenideElement item = catalogPage.getItemBySerialNumber(item_number);
		String itemPrice = catalogPage.getItemPrice(item);
		catalogPage.addItemToBasket(item, quantity);
		checkThatItemIsAddedCorrectly(catalogPage, oldItemsNumber, oldItemsPrice, quantity, itemPrice);
		refresh();
		catalogPage.checkThatItemMarkedAsAdded(item);
	}
	
	@Test (dataProvider = "compare")
	public void testAddItemToCompareFromCatalogPage(int item1_number, int item2_number)  {
		String item1_code = catalogPage.getItemCodeText(item1_number);
		String item2_code = catalogPage.getItemCodeText(item2_number);
		catalogPage.addItemToCompare(item1_number);
		catalogPage.compare_link(item1_number).shouldHave(text("Сравнить 1 товар"));
		catalogPage.openComparePopup(item1_number);
		catalogPage.comparePopupShoudHave(item1_code);
		catalogPage.closeComparePopup();
		catalogPage.addItemToCompare(item2_number);
		catalogPage.compare_link(item1_number).shouldHave(text("Сравнить 2 товара"));
		catalogPage.compare_link(item2_number).shouldHave(text("Сравнить 2 товара"));
		catalogPage.openComparePopup(item2_number);
		catalogPage.comparePopupShoudHave(item1_code);
		catalogPage.comparePopupShoudHave(item2_code);
		catalogPage.closeComparePopup();
	}

	@Test (dataProvider = "test2", dependsOnMethods = {"testAddItemToCompareFromCatalogPage"})
	public void testAddItemToBasketFromComparePopupFromCatalogPage(int item_number, int quantity) {
		int oldItemsNumber = catalogPage.getNumberOfItemsInBasketFromHeader();
		double oldItemsPrice = catalogPage.getSumInBasketFromHeader();
		SelenideElement item = catalogPage.getItemBySerialNumber(item_number);
		String itemPrice = catalogPage.getItemPrice(item);
		catalogPage.openComparePopup(item_number);
		String item_code = catalogPage.getItemCodeText(item_number);
		catalogPage.addItemToBasketFromComparePopup(item_code, 1);
		checkThatItemIsAddedCorrectly(catalogPage, oldItemsNumber, oldItemsPrice, quantity, itemPrice);
		catalogPage.closeComparePopup();
		refresh();
		catalogPage.checkThatItemMarkedAsAdded(item);
	}
	
	@Test (dataProvider = "remove_from_compare", dependsOnMethods = {"testAddItemToBasketFromComparePopupFromCatalogPage"})
	public void testRemoveItemFromCompareFromCatalogPage(int item_number)  {
		String item_code = catalogPage.getItemCodeText(item_number);
		catalogPage.openComparePopup(item_number);
		catalogPage.removeItemFromCompare(item_code);
		catalogPage.closeComparePopup();
		catalogPage.compare_link(item_number).shouldHave(text("Добавить к сравнению"));
	}
}
