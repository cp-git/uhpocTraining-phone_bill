package com.cp.phonebill.impl;

import java.util.HashMap;
import java.util.List;

import com.cp.phonebill.entities.CallDetails;
import com.cp.phonebill.repo.CallDetailsRepo;
import com.cp.phonebill.services.CallDetailsService;

public class CallDetailsServiceImpl implements CallDetailsService {

	CallDetailsRepo callDetailsRepo = null;

	public HashMap<Long, List<CallDetails>> initializeCallCache() {

		callDetailsRepo = new CallDetailsRepo();
		return callDetailsRepo.getAllCallDetails();

	}

	public int createCallDetails(CallDetails callDetails) {

		callDetailsRepo = new CallDetailsRepo();
		int callDetailsId = callDetailsRepo.insertCallDetails(callDetails);
		return callDetailsId;

	}

	public List<CallDetails> getCallDetailsByCustomerAccNo(int customerAccNo) {
		// TODO Auto-generated method stub

		return null;
	}

}
