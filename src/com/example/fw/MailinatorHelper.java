package com.example.fw;

import static us.monoid.web.Resty.*;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

public class MailinatorHelper extends HelperBase {
	
	public MailinatorHelper(ApplicationManager manager) {
		super(manager);
	}
	
	public void getEmailsNumber() throws Exception {
		Resty r = new Resty();
		int id = r.json(
				"https://api.mailinator.com/api/inbox?to="
						+ manager.getProperty("mailinator_box_template")
						+ "&token="
						+ manager.getProperty("mailinator_api_token")).json(path("messages")).object().length();
						
		System.out.println("Response was: " + id);
//		String mail = r.json(
//				"https://api.mailinator.com/api/email?msgid="
//						+ id
//						+ "&token="
//						+ manager.getProperty("mailinator_api_token")).get("data.parts.body")
//				.toString().replaceAll("\\<.*?>","");
//		System.out.println("Email was: " + mail);
//		return hello;
		
		
//		{"messages":[{"seconds_ago":97328,"id":"1417074158-64482801-kavtest1","to":"kavtest1@mailinator.com","time":1417074158103,
//		"subject":"\u00AB9-18: \u041E\u0444\u0438\u0441\u043D\u044B\u0439 \u043A\u0432\u0430\u0440\u0442\u0430\u043B\u00BB: \u0441\u043C\u0435\u043D\u0430 \u043F\u0430\u0440\u043E\u043B\u044F",
//		"fromfull":"admin@9-18dk.b.adv.ru","been_read":false,"from":"admin@9-18dk.b.adv.ru","ip":"62.117.94.186"}]}

	}
	
	public void getEmailsNumber2() throws Exception {
		GetRequest request = Unirest.get("https://api.mailinator.com/api/inbox?to="
				+ manager.getProperty("mailinator_box_template")
				+ "&token="
				+ manager.getProperty("mailinator_api_token"));
		HttpResponse<JsonNode> sdsd = request.asJson();
		System.out.println("Response was: " + sdsd);
//		  .routeParam("method", "get")
//		  .queryString("name", "Mark")
//		  .asJson();
		
	}

}
