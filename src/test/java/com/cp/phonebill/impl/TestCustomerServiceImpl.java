package com.cp.phonebill.impl;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cp.phonebill.entities.Customer;
import com.cp.phonebill.helper.DBManager;
import com.cp.phonebill.services.CustomerService;

public class TestCustomerServiceImpl {

	private static CustomerService custServ = null;
	static HashMap<Long, Customer> customerCache = null;
	static DBManager dbm = null;
	static Connection con = null;
	static List<Customer> expList = null;
	List<Customer> custList = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Before");
		custServ = new CustomerServiceImpl();
		customerCache = new HashMap<>();
		dbm = DBManager.getDBManager();
		expList = new ArrayList<>();
	}

	@Before
	public void setUp() throws Exception {
		con = dbm.getConnection();
	}

	@Test
	public void testGetCustomerDetails() {
		Customer expCust = new Customer(10001, "mayur", 12345, "add1", "add2", "pune", "mstate", 123456);

		custList = custServ.getAllCustomerDetails();

		Customer cust = custList.get(0);
		assertEquals(expCust.getCustomerAccNo(), cust.getCustomerAccNo());
		assertEquals(expCust.getCustomerName(), cust.getCustomerName());
		assertEquals(expCust.getCustomerPhoneNo(), cust.getCustomerPhoneNo());
		assertEquals(expCust.getCustomerAddress1(), cust.getCustomerAddress1());
		assertEquals(expCust.getCustomerAddress2(), cust.getCustomerAddress2());
		assertEquals(expCust.getCustomerCity(), cust.getCustomerCity());
		assertEquals(expCust.getCustomerState(), cust.getCustomerState());
		assertEquals(expCust.getCustomerPincode(), cust.getCustomerPincode());

	}

	@Test
	public void testInitializeCustomerCache() {
		Customer expCust = new Customer(10001, "mayur", 12345, "add1", "add2", "pune", "mstate", 123456);

		HashMap<Long, Customer> custCache = custServ.initializeCustomerCache();
		Customer cust = custCache.get(12345L);
		assertEquals(expCust.getCustomerAccNo(), cust.getCustomerAccNo());
		assertEquals(expCust.getCustomerName(), cust.getCustomerName());
		assertEquals(expCust.getCustomerPhoneNo(), cust.getCustomerPhoneNo());
		assertEquals(expCust.getCustomerAddress1(), cust.getCustomerAddress1());
		assertEquals(expCust.getCustomerAddress2(), cust.getCustomerAddress2());
		assertEquals(expCust.getCustomerCity(), cust.getCustomerCity());
		assertEquals(expCust.getCustomerState(), cust.getCustomerState());
		assertEquals(expCust.getCustomerPincode(), cust.getCustomerPincode());

	}

	@After
	public void tearDown() {
		dbm.closeConnection(con);
	}

}
