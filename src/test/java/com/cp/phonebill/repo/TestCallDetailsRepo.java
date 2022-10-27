package com.cp.phonebill.repo;

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
import com.cp.phonebill.impl.CallDetailsServiceImpl;
import com.cp.phonebill.services.CallDetailsService;

public class TestCallDetailsRepo {

	static CallDetailsRepo callRepo = null;
	static CallDetailsService callServ = null;
	static HashMap<Long, List<CallDetails>> callCache = null;
	static DBManager dbm = null;
	static List<CallDetails> expList = null;
	Connection con = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Before");
		callRepo = new CallDetailsRepo();
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
	public void testGetAllCallDetails() {
		CallDetails call = new CallDetails(Date.valueOf("2022-10-25"), 9876543210L, "i", 120, 30015);
		CallDetails call2 = new CallDetails(Date.valueOf("2022-10-26"), 9638527410L, "o", 150, 30015);
		expList.add(call);
		expList.add(call2);

		callCache = callRepo.getAllCallDetails();
		if (callCache.isEmpty()) {
			return;
		}

		System.out.println(callCache);
		List<CallDetails> callList = callCache.get(9988776655L);

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
	public void tearDown() throws Exception {
		dbm.closeConnection(con);
	}
}
