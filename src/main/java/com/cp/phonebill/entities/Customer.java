package com.cp.phonebill.entities;

public class Customer {

	private int customerAccNo;
	private long customerPhoneNo;
	private String customerAddress1;
	private String customerAddress2;
	private String customerCity;
	private String customerState;
	private String customerPincode;

	public Customer() {
		super();
	}

	public Customer(long customerPhoneNo, String customerAddress1, String customerAddress2, String customerCity,
			String customerState, String customerPincode) {
		super();
		this.customerPhoneNo = customerPhoneNo;
		this.customerAddress1 = customerAddress1;
		this.customerAddress2 = customerAddress2;
		this.customerCity = customerCity;
		this.customerState = customerState;
		this.customerPincode = customerPincode;
	}

	public Customer(int customerAccNo, long customerPhoneNo, String customerAddress1, String customerAddress2,
			String customerCity, String customerState, String customerPincode) {
		super();
		this.customerAccNo = customerAccNo;
		this.customerPhoneNo = customerPhoneNo;
		this.customerAddress1 = customerAddress1;
		this.customerAddress2 = customerAddress2;
		this.customerCity = customerCity;
		this.customerState = customerState;
		this.customerPincode = customerPincode;
	}

	public int getCustomerAccNo() {
		return customerAccNo;
	}

	public void setCustomerAccNo(int customerAccNo) {
		this.customerAccNo = customerAccNo;
	}

	public long getCustomerPhoneNo() {
		return customerPhoneNo;
	}

	public void setCustomerPhoneNo(long customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo;
	}

	public String getCustomerAddress1() {
		return customerAddress1;
	}

	public void setCustomerAddress1(String customerAddress1) {
		this.customerAddress1 = customerAddress1;
	}

	public String getCustomerAddress2() {
		return customerAddress2;
	}

	public void setCustomerAddress2(String customerAddress2) {
		this.customerAddress2 = customerAddress2;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerState() {
		return customerState;
	}

	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}

	public String getCustomerPincode() {
		return customerPincode;
	}

	public void setCustomerPincode(String customerPincode) {
		this.customerPincode = customerPincode;
	}

}
