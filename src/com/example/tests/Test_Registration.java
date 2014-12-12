package com.example.tests;

import static com.codeborne.selenide.Selenide.open;

import java.io.IOException;

import org.testng.annotations.Test;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.web.JSONResource;
import us.monoid.web.TextResource;

import com.example.pages.BasePage;

public class Test_Registration extends TestBase{
	
	private BasePage basePage;
	
	@Test 
	public void testRegisterValidUser() throws Exception {
//		basePage = open("/", BasePage.class);
		app.getMailinatorHelper().getEmailsNumber();
//		basePage.fillInRegistrationForm();
		
		//Step 1: Register valid user
		//		Step 1-1: Fill and send form
		//		Step 1-2: Get confirm email
		//		Step 1-3: Confirm registration
		//		Step 1-4: Check relogin
		//		Step 1-1: Register valid user
		//		Step 1-1: Register valid user
		//Step 2: Check that user successfully registered
		
	}

}
