package com.example.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

public class OrderConfirmPage extends BasePage{
	
	public SelenideElement finalOrderSumm() {
		return $("div.total-pay p");
	}
	
	public OrderDonePage submitOrder() {
		$(By.name("submitbutton")).click();
		$("div#order_form_div b").waitUntil(text("Заказ сформирован"), 30000);
		return page(OrderDonePage.class);
	}

}
