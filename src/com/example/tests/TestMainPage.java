package com.example.tests;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;
import com.example.pages.MainPage;

public class TestMainPage extends TestBase{
	
	private MainPage mainPage;
	
	@DataProvider(name = "test1")
	public Object[][] createData1() {
	 return new Object[][] {
	   { 0, 1},
	   { 2, 2}
	 };
	}
	
	@BeforeClass
	public void prepareToTest() throws InterruptedException {
		clearBrowserCache();
		mainPage = open("/", MainPage.class);
		mainPage.login(app.getProperty("buyer_login"), app.getProperty("buyer_password"));
		mainPage.chooseDealer(app.getProperty("shop1_city"), app.getProperty("shop1_name"));
	}
	
	@Test (dataProvider = "test1")
	public void testAddItemToBasket(int item_number, int quantity) throws InterruptedException {
		int oldItemsNumber = mainPage.getNumberOfItemsInBasketFromHeader();
		double oldItemsPrice = mainPage.getSumInBasketFromHeader();
		//Берем пока тупо товары по порядку нахождения на странице
		SelenideElement item = mainPage.getItem(item_number);
		String itemPrice = mainPage.getItemPrice(item);
		mainPage.addItemToBasket(item, quantity);
		int newItemsNumber = oldItemsNumber + quantity;
		double newItemsPrice = oldItemsPrice + quantity*Double.parseDouble(itemPrice);
		String basketText = mainPage.basketText().text();
		assert basketText.startsWith(Integer.toString(newItemsNumber) + " товар");
		assert (newItemsPrice == mainPage.getSumInBasketFromHeader());
	}

}
