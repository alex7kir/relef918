package com.example.pages;

import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

public class ItemPage extends BasePage{
	
	public SelenideElement buttonAddToBasket() {
//		return $("button.item-card_add-basket");
		return $(By.name("ACTIONADD2BASKET"));
	}
	
	public String getItemPrice() {
		String priceText = $("div.item-card_cost").text();
		return priceText.substring(0, priceText.indexOf(" "));
	}
	
	public void putItemIntoBasket() {
		buttonAddToBasket().click();
		buttonAddToBasket().waitUntil(hasClass("added"), 60000);
	}
	
	public void buttonAddToBasketShoudBeActive() {
		buttonAddToBasket().shouldNot(hasClass("added"));
	}

}
