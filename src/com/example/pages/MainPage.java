package com.example.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selectors.byText;

import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

public class MainPage extends BasePage{
	
	public SelenideElement first_price_block() {
		return $("div.carousel div.catalog-items form.catalog-items_price");
	}
	
	public SelenideElement replace_town_button() {
		return $("#shop-card_btn");
	}
	
	public SelenideElement select_town_button() {
		return $("a.b-dealers-map-popup-link");
	}
	
	public SelenideElement dealer_name() {
		return $("a.shop-card_btn");
	}
	
	public SelenideElement dealer_popup_input() {
		return $("input.b-dealers-map-popup__input");
	}
	
	public By buttonAddToBasket() {
		return By.name("ACTIONADD2BASKET");
	}
	
	public String itemPriceLocator() {
		return "div.catalog-items_cost";
	}
	
	public void chooseDealer(String city, String dealer) {
		if (!dealer_popup_input().isDisplayed()) {
			select_town_button().click();
		}
		dealer_popup_input().should(appear).val(city);
		$(By.partialLinkText(city)).should(appear).click();
		$("div.b-dealers-scroll__btn").should(appear);
		$(byText(dealer)).parent().find("div.b-dealers-scroll__btn").click();
//		dealer_popup_input().val(dealer);
//		$(By.partialLinkText(dealer)).should(appear).click();
//		$("div.b-dealers-scroll__btn").should(appear).click();
		$("div#shop-card a").waitUntil(hasAttribute("title", dealer), 120000);
		
	}

	public void replaceDealer() {
		expandDealerInfo();
		replace_town_button().click();
		replace_town_button().waitUntil(disappear, 10000);
		dealer_popup_input().waitUntil(appear, 10000);
	}

	private void expandDealerInfo() {
		dealer_name().click();
		replace_town_button().waitUntil(visible, 10000);
	}
	
	public SelenideElement getItem(int orderNumber) {
		return $("div.catalog-items", orderNumber);
	}

	public void addItemToBasket(SelenideElement item, int i) {
		setItemQuantity(item, i);
		putItemIntoBasket(item);
	}

	private void setItemQuantity(SelenideElement item, int quantity) {
		for(int i=1; i<quantity; i++) {
			item.$("li.btn-count_add").click();
			sleep(100);
		}
	}
	
	public void putItemIntoBasket(SelenideElement item) {
		item.find(buttonAddToBasket()).click();
		item.find(buttonAddToBasket()).should(hasClass("added"));
		
	}
	
	public String getItemPrice(SelenideElement item) {
		String priceText = item.find(itemPriceLocator()).text().trim().replace(" ", "");
		return priceText.substring(0, priceText.length()-1);
	}



}
