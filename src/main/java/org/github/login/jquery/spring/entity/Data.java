package org.github.login.jquery.spring.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {
	public static HashMap<String,User> users = new HashMap<>();
	
	static{
		for(int i=0;i!=5;i++){
			String role = "admin";
			switch (i) {
			case 2:
				role="owner";
				break;
			case 4:
				role="bidder";
				break;
			default:
				break;
			}
			users.put(String.valueOf(i),new User(i, "random name "+i, "randommail"+i+"@ggg.com"
					, "pass"+i, null, null, null, role));
		}
	}
}
