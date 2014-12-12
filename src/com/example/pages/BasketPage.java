package com.example.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import org.openqa.selenium.By;

public class BasketPage extends BasePage{
	
	public void basketShouldContainItem(String code) {
		$("table.table-basket tbody td.table-basket_element_code").shouldHave(text(code));
	}
	
	public OrderConfirmPage continueOrder() {
		$(By.className("basket_action-next")).click();
		return page(OrderConfirmPage.class);
	}

}
