package com.example.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

public class ProfilePage extends BasePage{
	
	public void headerShouldBe(String header) {
		$("h1").shouldHave(text(header));
	}
	
	public void shouldContainOrder(String order, String price) {
		$(By.xpath("//a[contains(@title,'" + order + "')]/parent::p")).shouldHave(text("1 позиция, " + price));
	}

}
