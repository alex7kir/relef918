package com.example.pages;

import static com.codeborne.selenide.Selenide.$;

public class OrderDonePage extends BasePage{
	
	public String getOrderId() {
		String order = $("table.sale_order_full_table b").getText().substring(1);
		return order;
	}

}
