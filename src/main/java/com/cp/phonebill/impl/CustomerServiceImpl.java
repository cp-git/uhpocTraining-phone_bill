package com.cp.phonebill.impl;

import java.util.HashMap;
import java.util.List;

import com.cp.phonebill.entities.Customer;
import com.cp.phonebill.repo.CustomerRepo;
import com.cp.phonebill.services.CustomerService;

public class CustomerServiceImpl implements CustomerService {

	CustomerRepo customerRepo = null;

	public HashMap<Long, Customer> initializeCustomerCache() {
		// TODO Auto-generated method stub
		HashMap<Long, Customer> customerCache = new HashMap<>();
		List<Customer> custList = getAllCustomerDetails();
		for (Customer cust : custList) {
			customerCache.put(cust.getCustomerPhoneNo(), cust);
		}
		return customerCache;
	}

	public int createCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerRepo = new CustomerRepo();
		int customerAccNo = 0;

		customerAccNo = customerRepo.insertCustomerDetails(customer);
		return customerAccNo;
	}

	public List<Customer> getAllCustomerDetails() {
		// TODO Auto-generated method stub
		customerRepo = new CustomerRepo();
		return customerRepo.getAllCustomerDetails();
	}

}
