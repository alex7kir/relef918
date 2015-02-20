package com.example.tests;

import org.testng.annotations.Test;

import com.example.pages.BasketPage;
import com.example.pages.CatalogPage;
import com.example.pages.ItemPage;
import com.example.pages.OrderConfirmPage;
import com.example.pages.OrderDonePage;
import com.example.pages.ProfilePage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;


public class Test_E2E_918 extends TestBase{
	
	private CatalogPage catalogPage;
	private ItemPage itemPage;
	private BasketPage basketPage;
	private OrderConfirmPage orderConfirmPage;
	private OrderDonePage orderDonePage;
	private ProfilePage profilePage;
	
	@Test 
	public void testDemo918EndToEnd() throws Exception {
		String code = app.getProperty("item_code");
		catalogPage = open("/", CatalogPage.class);
			// Login
		catalogPage.login(app.getProperty("buyer_login"), app.getProperty("buyer_password"));
			// ����� ������
		catalogPage.chooseDealer(app.getProperty("shop1_city"), app.getProperty("shop1_name"));
			// �����
		itemPage = catalogPage.search(code);
			// ���������� � �������
		itemPage.buttonAddToBasket().should(exist);
		itemPage.buttonAddToBasketShoudBeActive();
//		itemPage.buttonAddToBasket().shouldHave(value("� �������"));
		itemPage.putItemIntoBasket();
		String price = itemPage.getItemPrice();
		itemPage.basketText().shouldHave(text("1 товар на сумму " + price));
			// ���������� ������
		basketPage = itemPage.openBasketPage();
		basketPage.basketShouldContainItem(code);
		orderConfirmPage = basketPage.continueOrder();
//		orderConfirmPage.finalOrderSumm().shouldHave(text("������� ��:" + price)); ���� ����� ������ ����� (� ����)
		orderDonePage = orderConfirmPage.submitOrder();
		String order = orderDonePage.getOrderId();
			// Logout
		orderDonePage.logout();
			// ������������ �������
		profilePage = catalogPage.login_as_dealer(app.getProperty("dealer_login"), app.getProperty("dealer_password"));
		profilePage.headerShouldBe("Новые заказы");
		profilePage.shouldContainOrder(order, price);
		profilePage.logout();
	}
}
