package com.cp.phonebill.services;

import java.util.HashMap;
import java.util.List;

import com.cp.phonebill.entities.Customer;

public interface CustomerService {

	HashMap<Long, Customer> initializeCustomerCache();

	int createCustomer(Customer customer);

	List<Customer> getAllCustomerDetails();

	Long getCustomerPhoneNoByAccountNo(int customerAccNo);
}
