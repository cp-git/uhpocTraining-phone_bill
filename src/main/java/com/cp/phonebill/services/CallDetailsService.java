package com.cp.phonebill.services;

import java.util.HashMap;
import java.util.List;

import com.cp.phonebill.entities.CallDetails;

public interface CallDetailsService {
	HashMap<Long, List<CallDetails>> initializeCallCache();

	int createCallDetails(CallDetails callDetails);

	List<CallDetails> getCallDetailsByCustomerAccNo(int customerAccNo);

}
