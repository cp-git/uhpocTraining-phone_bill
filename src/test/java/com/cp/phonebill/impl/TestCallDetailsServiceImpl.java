package com.cp.phonebill.impl;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cp.phonebill.entities.CallDetails;
import com.cp.phonebill.helper.DBManager;
import com.cp.phonebill.services.CallDetailsService;

public class TestCallDetailsServiceImpl {

	static CallDetailsService callServ = null;
	static HashMap<Long, List<CallDetails>> callCache = null;
	static DBManager dbm = null;
	static List<CallDetails> expList = null;
	Connection con = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Before");
		callServ = new CallDetailsServiceImpl();
		callCache = new HashMap<>();
		dbm = DBManager.getDBManager();
		expList = new ArrayList<>();
	}

	@Before
	public void setUp() throws Exception {
		con = dbm.getConnection();
	}

	@Test
	public void testInitializeCallCache() {
		CallDetails call = new CallDetails(Date.valueOf("2022-10-12"), 7418529631L, "o", 50, 30001);
		CallDetails call2 = new CallDetails(Date.valueOf("2022-10-12"), 9876543210L, "o", 100, 30001);
		expList.add(call);
		expList.add(call2);

		callCache = callServ.initializeCallCache();
		System.out.println(callCache);
		List<CallDetails> callList = callCache.get(123L);

		Iterator<CallDetails> it1 = expList.iterator();
		Iterator<CallDetails> it2 = callList.iterator();

		while (it1.hasNext() && it2.hasNext()) {
			CallDetails callDetails = it1.next();
			CallDetails expCallDetails = it2.next();

			assertEquals(callDetails.getCallDate(), expCallDetails.getCallDate());
			assertEquals(callDetails.getCallPhoneNo(), expCallDetails.getCallPhoneNo());
			assertEquals(callDetails.getCallInOut(), expCallDetails.getCallInOut());
			assertEquals(callDetails.getCallDuration(), expCallDetails.getCallDuration());
			assertEquals(callDetails.getCustomerAccNo(), expCallDetails.getCustomerAccNo());
		}

	}

	@After
	public void tearDown() {
		dbm.closeConnection(con);
	}
}
