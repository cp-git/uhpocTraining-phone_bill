package phonebill.impl;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.cp.phonebill.entities.Customer;
import com.cp.phonebill.helper.DBManager;
import com.cp.phonebill.impl.CustomerServiceImpl;
import com.cp.phonebill.services.CustomerService;

public class TestCustomerServiceImpl {

	private CustomerService impl = null;
	HashMap<Long, Customer> customerCache = null;
	DBManager dbm = null;
	Connection con = null;
	List<Customer> expList = null;
	List<Customer> custList = null;

	@Before
	public void setUp() throws Exception {
		System.out.println("Before");
		impl = new CustomerServiceImpl();
		customerCache = new HashMap<>();
		dbm = DBManager.getDBManager();
		con = dbm.getConnection();
		expList = new ArrayList<>();
	}

	@Test
	public void testGetAllCustomerDetails() {
		Customer customer = new Customer(10001, "mayur", 9975, "A nagar", "B colony", "pune", "mh", 412307);
		Customer customer2 = new Customer(20001, "akash", 9976, "abc nagar", "abc colony", "pune", "mh", 123456);
		expList.add(customer);
		expList.add(customer2);

		custList = impl.getAllCustomerDetails();
		System.err.println(expList);
		System.err.println(custList);
		assertEquals(expList.size(), custList.size());
		for (int i = 0; i < expList.size(); i++) {
			Customer expCust = expList.get(i);
			Customer cust = custList.get(i);
			assertEquals(expCust.getCustomerAccNo(), cust.getCustomerAccNo());
			assertEquals(expCust.getCustomerName(), cust.getCustomerName());
			assertEquals(expCust.getCustomerPhoneNo(), cust.getCustomerPhoneNo());
			assertEquals(expCust.getCustomerAddress1(), cust.getCustomerAddress1());
			assertEquals(expCust.getCustomerAddress2(), cust.getCustomerAddress2());
			assertEquals(expCust.getCustomerCity(), cust.getCustomerCity());
			assertEquals(expCust.getCustomerState(), cust.getCustomerState());
			assertEquals(expCust.getCustomerPincode(), cust.getCustomerPincode());
		}

	}

}
