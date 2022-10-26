package com.cp.phonebill.repo;

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
import com.cp.phonebill.impl.CustomerServiceImpl;
import com.cp.phonebill.services.CustomerService;

public class TestCustomerRepo {

	private static CustomerRepo custRepo = null;
	private static CustomerService custServ = null;
	static HashMap<Long, Customer> customerCache = null;
	static DBManager dbm = null;
	static Connection con = null;
	static List<Customer> expList = null;
	List<Customer> custList = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Before");
		custRepo = new CustomerRepo();
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
	public void testGetNewestAccountNumber() {

		int LastCustAccNo = custRepo.getNewestAccountNumber(con);

		custList = custServ.getAllCustomerDetails();

		Customer cust = custList.get(custList.size() - 1);

		assertEquals(cust.getCustomerAccNo(), LastCustAccNo);
		//
//		assertEquals(expCust.getCustomerAccNo(), cust.getCustomerAccNo());
//		assertEquals(expCust.getCustomerName(), cust.getCustomerName());
//		assertEquals(expCust.getCustomerPhoneNo(), cust.getCustomerPhoneNo());
//		assertEquals(expCust.getCustomerAddress1(), cust.getCustomerAddress1());
//		assertEquals(expCust.getCustomerAddress2(), cust.getCustomerAddress2());
//		assertEquals(expCust.getCustomerCity(), cust.getCustomerCity());
//		assertEquals(expCust.getCustomerState(), cust.getCustomerState());
//		assertEquals(expCust.getCustomerPincode(), cust.getCustomerPincode());

	}

	@Test
	public void testInitializeCustomerCache() {
		Customer expCust = new Customer(30015, "Mayur", 9988776655L, "add1", "add2", "pune", "mh", 412307);

		HashMap<Long, Customer> custCache = custServ.initializeCustomerCache();
		Customer cust = custCache.get(9988776655L);
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
	public void testGetCustomerPhoneNoByAccountNo() {

		long phoneNo = custRepo.getCustomerPhoneNoByAccountNo(30015, con);
		long phoneNo2 = custRepo.getCustomerPhoneNoByAccountNo(30016, con);

		assertEquals(9988776655L, phoneNo);
		assertEquals(9966338855L, phoneNo2);

	}

	@After
	public void tearDown() {
		dbm.closeConnection(con);
	}

}
