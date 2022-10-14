package com.cp.phonebill.impl;

import java.util.HashMap;
import java.util.List;

import com.cp.phonebill.entities.Customer;
import com.cp.phonebill.repo.CustomerRepo;
import com.cp.phonebill.services.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	public HashMap<Long, Customer> initializeCustomerCache() {
		// TODO Auto-generated method stub
		HashMap<Long, Customer> customerCache = new HashMap<>();
		CustomerRepo custRepo = new CustomerRepo();
		List<Customer> custList = custRepo.getAllCustomerDetails();
		for (Customer cust : custList) {
			customerCache.put(cust.getCustomerPhoneNo(), cust);
		}
		return customerCache;
	}

	public int createCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Customer> getAllCustomerDetails() {
		// TODO Auto-generated method stub
		return null;
	}

}
