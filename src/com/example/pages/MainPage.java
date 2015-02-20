package com.example.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.*;

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
		return By.cssSelector("button.catalog-items_add-basket");
//		return $("button.catalog-items_add-basket");
	}
	
	public String itemPriceLocator() {
		return "div.catalog-items_cost";
	}
	
	public SelenideElement compare_link(int itemNumber) {
		return getItemBySerialNumber(itemNumber).find("noindex>a.add-compare>span");
	}
	
	public SelenideElement comparePopup() {
		return $("div.popup-compare");
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
	
	public SelenideElement getItemBySerialNumber(int orderNumber) {
		return $("div.catalog-items", orderNumber);
	}
	
	public SelenideElement getItemByCode(SelenideElement parent_element, String item_code) {
		SelenideElement qsw = parent_element.find(byText(item_code));
		SelenideElement asd = qsw.closest("td");
		return asd;
	}
	
	//----------------------------------------------------------------------------------
	
	public String getItemCodeText(int itemNumber) {
//		return item.find("div.catalog-items_code").text();
		return getItemBySerialNumber(itemNumber).find("div.catalog-items_code").text();
	}

	public void addItemToBasket(SelenideElement item, int i) {
		setItemQuantity(item, i);
		putItemIntoBasket(item);
	}
	
	public void addItemToBasketFromComparePopup(String item_code, int i) {
		SelenideElement item = getItemByCode(comparePopup(), item_code);
		setItemQuantity(item, i);
		putItemIntoBasket(item);
	}

	private void setItemQuantity(SelenideElement item, int quantity) {
		for(int i=1; i<quantity; i++) {
			item.find("li.btn-count_add").click();
			sleep(100);
		}
	}
	
	public void putItemIntoBasket(SelenideElement item) {
		item.find(buttonAddToBasket()).click();
		item.find(buttonAddToBasket()).should(hasClass("added"));
	}
	
	public void checkThatItemMarkedAsAdded(SelenideElement item) {
		item.find(buttonAddToBasket()).should(hasClass("added"));
	}
	
	public String getItemPrice(SelenideElement item) {
		String priceText = item.find(itemPriceLocator()).text().trim().replace(" ", "");
		return priceText.substring(0, priceText.length()-1);
	}

	public void addItemToCompare(int itemNumber) {
		assertEquals(compare_link(itemNumber).text(), "Добавить к сравнению", "Возможно, товар уже добавлен к сравнению");
		compare_link(itemNumber).click();
		compare_link(itemNumber).parent().waitUntil(hasClass("added"), 20000);

	}

	public void openComparePopup(int itemNumber) {
		assertTrue(compare_link(itemNumber).text().startsWith("Сравнить"), "Возможно, товар еще не добавлен к сравнению");
		compare_link(itemNumber).waitUntil(enabled, 1000).click();
		(new WebDriverWait(getWebDriver(), 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return (!comparePopup().getCssValue("height").equals("0px"));
            }
        });
	}

	public void comparePopupShoudHave(String item_code) {
		String[] codes = comparePopup().findAll("div.catalog-items_code").getTexts();
		Boolean item_added = false;
		for (String code : codes) {
			if (code.equals(item_code)) {
				item_added = true;
				break;
			}
		}
		assertTrue(item_added, "Товар не добавлен к сравнению - его код не найден в поп-апе");
	}

	public void closeComparePopup() {
		comparePopup().find("a.close").click();
		(new WebDriverWait(getWebDriver(), 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return (comparePopup().getCssValue("height").equals("0px"));
            }
        });
	}

	public void removeItemFromCompare(String item_code) {
		SelenideElement item = getItemByCode(comparePopup(), item_code);
		item.find("span.delete>a").click();
		item.shouldNot(exist);
		
		
	}



}
