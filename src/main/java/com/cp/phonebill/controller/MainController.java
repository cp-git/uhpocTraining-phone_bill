package com.cp.phonebill.controller;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

import com.cp.phonebill.entities.Customer;
import com.cp.phonebill.impl.CustomerServiceImpl;
import com.cp.phonebill.services.CustomerService;

public class MainController {
	static Properties props = null;
	static HashMap<Long, Customer> customerCache = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadCompProperty();
		loadCache();

	}

	private static void loadCompProperty() {
		FileInputStream fs;
		try {
			props = new Properties();
			fs = new FileInputStream("src/main/resources/comp_info.properties");
			props.load(fs);
			/*
			 * System.out.println(props.getProperty("dburl"));
			 * System.out.println(props.getProperty("dbname"));
			 * System.out.println(props.getProperty("dbusername"));
			 * System.out.println(props.getProperty("dbpassword"));
			 * System.out.println(props.getProperty("maxcon"));
			 */
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private static void loadCache() {
		CustomerService custServ = new CustomerServiceImpl();
		customerCache = new HashMap<>();
		customerCache = custServ.initializeCustomerCache();
		System.out.println(customerCache);
	}
}
