package com.example.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

public class BasePage {
	
	public void login(String login, String password) throws InterruptedException {
		openAuthForm();
		$("#auth-login").setValue(login);
		$("#auth-pass").setValue(password);
//		$("#user_auth").click();
		$("form.auth-form.auth-form_auth button.auth-form_action-enter").click();
		$("div.b-authorized-user").waitUntil(visible, 60000);
		Thread.sleep(500); //TODO: think how not to use it
	}

	private void openAuthForm() {
		$(By.linkText("Личный кабинет")).click();
//		$(By.xpath("//form[contains(@name,'system_auth_form')]")).waitUntil(visible, 30000);
		$(By.cssSelector("form.auth-form.auth-form_auth")).waitUntil(visible, 40000);
	}
	
	public void login_as_dealer(String login, String password) {
		$(By.linkText("Вход для участников программы")).click();
		$("#loginInput").setValue(login);
		$("#passwordInput").setValue(password);
		$(By.name("Login")).click();
	}
	
	
	public ItemPage search(String query) {
		$(By.name("q")).setValue(query).pressEnter();
		return page(ItemPage.class); //TODO: �������� �������, ����� ���������� �������� ������
	}
	
	public SelenideElement basketText() {
		return $("div.b-new-cart__text");
	}
	
	public BasketPage openBasketPage() {
		$(By.linkText("Корзина")).click();
		return page(BasketPage.class);
	}
	
	public void logout() {
		open("/?logout=yes");
	}
	
	public ProfilePage openProfile() {
		$("a.b-authorized-user__name").click();
		return page(ProfilePage.class);
	}

	public void fillInRegistrationForm() {
		// TODO Auto-generated method stub
		
	}
	
	public int getNumberOfItemsInBasketFromHeader() {
		int number;
		String text = basketText().text().trim();
		if (text.contains("выберите товар из каталога")) {
			number = 0;
		} else {
			String s_number = text.substring(0, text.indexOf(" "));
			number = Integer.parseInt(s_number);
		}
		return number;
	}
	
	public double getSumInBasketFromHeader() {
		double price;
		String text = basketText().text().trim();
		if (text.contains("выберите товар из каталога")) {
			price = 0;
		} else {
			String s_number = text.substring(text.lastIndexOf("у")+2).replace(" ", "");
			price = Double.parseDouble(s_number);
		}
		return price;
	}

}
