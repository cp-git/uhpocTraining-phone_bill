package com.cp.phonebill.repo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

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

//	@Test
//	public void testGetAllCallDetails() {
//		CallDetails call = new CallDetails(Date.valueOf("2022 - 10 - 12"), 7418529631L, "o", 50, 30001);
//		CallDetails call2 = new CallDetails(Date.valueOf("2022 - 10 - 12"), 9876543210L, "o", 100, 30001);
//		expList.add(call);
//		expList.add(call2);
//
//		callCache = callRepo.getAllCallDetails();
//
//		List<CallDetails> callList = callCache.get("123");
//
//	}

	@After
	public void tearDown() throws Exception {
		dbm.closeConnection(con);
	}
}
