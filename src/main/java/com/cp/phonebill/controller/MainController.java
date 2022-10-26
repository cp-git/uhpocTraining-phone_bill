package com.cp.phonebill.controller;

import java.io.FileInputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.cp.phonebill.entities.CallDetails;
import com.cp.phonebill.entities.Customer;
import com.cp.phonebill.exception.CPException;
import com.cp.phonebill.helper.DBManager;
import com.cp.phonebill.helper.MessageBundle;
import com.cp.phonebill.impl.CallDetailsServiceImpl;
import com.cp.phonebill.impl.CustomerServiceImpl;
import com.cp.phonebill.services.CallDetailsService;
import com.cp.phonebill.services.CustomerService;

public class MainController {
	static Properties props = null;
	static HashMap<Long, Customer> customerCache = null;
	static HashMap<Long, List<CallDetails>> callCache = null;
	static MessageBundle mb = null;
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws CPException {
		mb = MessageBundle.getBundle();
		// Loading company property file and initializing cache

		loadCompProperty();
		loadCache();

		int option = 0; // for option selected by user
		while (true) {

			// Main menu
			System.out.println("********* Main Menu ***********");
			System.out.println("1. Create Customer Details.");
			System.out.println("2. Add Call Details");
			System.out.println("3. Print Phone Bill");
			System.out.println("4. Exit");
			System.out.println("Please enter your option 1, 2, 3 or 4");

			// scanner class get inputs from console

			try {
				option = scanner.nextInt();
				scanner.nextLine();
			} catch (Exception ex) {
				option = 0;
			}

			switch (option) {
			// Adding customer details
			case 1:

				createCustomerDetails();
				break;

			// Adding call details of customer
			case 2:

				addCallDetails();
				break;

			// printing phone bill
			case 3:

				printPhoneBill();
				break;

			case 4:

				DBManager dbm = DBManager.getDBManager();
				dbm.cleanPool();
				scanner.close();

				System.out.println(mb.getMessage("terminate_application"));
				System.exit(0);
				break;

			default:
				System.out.println(mb.getMessage("invalid_option"));
				break;
			}

		}

	}

	// for loading company details into property
	private static void loadCompProperty() {
		FileInputStream fs;
		try {
			props = new Properties();
			fs = new FileInputStream("src/main/resources/comp_info.properties");
			props.load(fs);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	// for Loading customer cache and callDetalil cache
	private static void loadCache() {
		CustomerService custServ = new CustomerServiceImpl();
		customerCache = custServ.initializeCustomerCache();

		CallDetailsService callServ = new CallDetailsServiceImpl();
		callCache = callServ.initializeCallCache();

	}

	// for validation of phone number
	private static boolean isValidPhoneNumber(String number) {
		long num = 0L;
		try {

			num = Long.parseLong("0" + number);
		} catch (Exception e) {

			return false;
		}

		return 7000000000L <= num && num <= 9999999999L;
	}

	// for validation of phone number
	private static boolean isValidPinCode(String pinCode) {
		int num = 0;
		try {

			num = Integer.parseInt("0" + pinCode);
		} catch (Exception e) {

			return false;
		}
		return 100000L <= num && num <= 999999L;
	}

	// to print phone bill
	private static void printBill(Customer customer, List<CallDetails> callDetailsList) {

		CallDetails call = callDetailsList.get(0);
		Date startDate = call.getCallDate();
		LocalDate dateObj = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String billEndPeriod = dateObj.format(formatter);
		String billDate = dateObj.plusDays(1).format(formatter);
		String dueDate = dateObj.plusDays(15).format(formatter);

		double totalAmount = calculateTotalAmount(callDetailsList);

		System.out.println("					" + props.getProperty("companyName"));
		System.out.println("					   " + props.getProperty("companyCity"));
		System.out.println("					" + props.getProperty("companyState"));

		System.out.println("Customer Name	  " + customer.getCustomerName() + "			Billing Period		"
				+ startDate + " to " + billEndPeriod);
		System.out.println("Phone no	  " + customer.getCustomerPhoneNo() + "		Billing Date		" + billDate);
		System.out.println("Account No	  " + customer.getCustomerAccNo() + "			Plane Name 		"
				+ props.getProperty("planName"));
		System.out.println("Address 1	  " + customer.getCustomerAddress1() + "		Plane Short text	"
				+ props.getProperty("planShortText"));
		System.out.println(
				"Address 2	  " + customer.getCustomerAddress2() + "		Amount Payable		" + totalAmount);
		System.out.println("City 		  " + customer.getCustomerCity() + "			Due date		" + dueDate);
		System.out.println("State 		  " + customer.getCustomerState());
		System.out.println("Pin 		  " + customer.getCustomerPincode());

		// calculating amount for outgoing calls and total amount
		System.out.print("\nDate\t\tPhone Number\tIncoming/ Outgoing\tCall Duration (in Seconds)\tAmount (in Rs)");

		for (CallDetails callDetail : callDetailsList) {
			System.out.print("\n" + callDetail.getCallDate() + "\t" + callDetail.getCallPhoneNo() + "\t");

			double amount = 0.0d;
			if (callDetail.getCallInOut().equals("o") || callDetail.getCallInOut().equals("O")) {
				System.out.print("OUT");
				amount = (callDetail.getCallDuration() * 0.1);

			} else {
				System.out.print("IN");
			}
			System.out.print("\t\t\t" + callDetail.getCallDuration());
			System.out.print("\t\t\t\t" + amount);
		}

		System.out.println();
		System.out.println("\nTotal Amount	" + totalAmount);
//		System.out.println(dueDate);
	}

	// to calculate total amount for outgoing calls
	public static double calculateTotalAmount(List<CallDetails> callDetailsList) {
		double totalAmount = 0.0d;
		for (CallDetails callDetail : callDetailsList) {

			double amount = 0.0d;

			if (callDetail.getCallInOut().equals("o") || callDetail.getCallInOut().equals("O")) {

				amount = (callDetail.getCallDuration() * 0.1);
				totalAmount = totalAmount + amount;
			}

		}

		return totalAmount;
	}

	// to create customer details
	private static void createCustomerDetails() {
		CustomerService customerServ = new CustomerServiceImpl();

		while (true) {

			String customerName = null;
			String customerPhoneNo = null;
			String customerAddress1 = null;
			String customerAddress2 = null;
			String customerCity = null;
			String customerState = null;
			String customerPincode = null;

			try {
				System.out.println("Enter customer name");
				customerName = scanner.nextLine();

				System.out.println("Enter customer phone no");
				customerPhoneNo = scanner.nextLine();

				if (!isValidPhoneNumber(customerPhoneNo)) {

					throw new CPException("C001", mb.getMessage("invalid_phone_no"));
				}

				if (customerCache.containsKey(Long.parseLong(customerPhoneNo))) {
					System.out.println("Customer details already available");
					System.out.println("Do you want to add another Customer [Y]es or [N]o?");
					String choice = scanner.nextLine();

					if (choice.equals("Y") || choice.equals("y")) {
						continue;
					} else {
						break;
					}
				}

				System.out.println("Enter customer address1");
				customerAddress1 = scanner.nextLine();

				System.out.println("Enter customer address2");
				customerAddress2 = scanner.nextLine();

				System.out.println("Enter customer city");
				customerCity = scanner.nextLine();

				System.out.println("Enter customer state");
				customerState = scanner.nextLine();

				System.out.println("Enter address pincode");
				customerPincode = scanner.nextLine();

				if (!isValidPinCode(customerPincode)) {
					throw new CPException("C003", mb.getMessage("invalid_pincode"));
				}

				Customer customer = new Customer(customerName, Long.parseLong(customerPhoneNo), customerAddress1,
						customerAddress2, customerCity, customerState, Integer.parseInt(customerPincode));

				int customerAccNo = customerServ.createCustomer(customer);
				customer.setCustomerAccNo(customerAccNo);

				customerCache.put(Long.parseLong(customerPhoneNo), customer);

				System.out.println(
						"Customer details added successfully" + "\nDo you want to add another Customer [Y]es or [N]o?");
				String choice = scanner.nextLine();

				if (choice.equals("Y") || choice.equals("y")) {
					continue;
				} else {
					break;
				}

			} catch (CPException ex) {

				System.out.println(ex.toString());
				continue;
			}
		}
	}

	// to add call details for customer
	private static void addCallDetails() {
		String customerPhoneNo = "";
		String date = "";
		String callPhoneNo = "";
		String callInOut = "";
		int callDuration = 0;
		Date callDate = null;

		try {
			while (true) {
				System.out.println("Enter customer phone number");
				customerPhoneNo = scanner.nextLine();

				if (isValidPhoneNumber(customerPhoneNo)) {

					if (customerCache.containsKey(Long.parseLong(customerPhoneNo))) {

						System.out.println("Enter  Call Details");
						System.out.println("Enter date (YYYY-MM-DD)");
						date = scanner.nextLine();

						// System.out.println(date);
						try {

							callDate = Date.valueOf(date);

						} catch (Exception ex) {

							throw new CPException("C002", mb.getMessage("invalid_date"));
//						ex.printStackTrace();

						}

						while (true) {

							System.out.println("Enter phone number");
							callPhoneNo = scanner.nextLine();

							if (!isValidPhoneNumber(customerPhoneNo)) {

								throw new CPException("C001", mb.getMessage("invalid_phone_no"));
							}

							System.out.println("Enter [i]ncoming / [o]utgoing call");
							callInOut = scanner.nextLine();

							if (!(callInOut.equals("i") || callInOut.equals("I") || callInOut.equals("o")
									|| callInOut.equals("O"))) {

								System.out.println(
										"please enter 'i' for incoming or 'o' for outgoing\n Please re-enter details");
								continue;
							}

							System.out.println("Enter call duration");
							callDuration = scanner.nextInt();
							scanner.nextLine();

							// getting details of customer using customer phone number
							Customer customer = customerCache.get(Long.parseLong(customerPhoneNo));

							// inserting all call Details in single object
							CallDetails callDetails = new CallDetails(callDate, Long.parseLong(callPhoneNo), callInOut,
									callDuration, customer.getCustomerAccNo());

							CallDetailsService callServ = new CallDetailsServiceImpl();

							// creating call details entry in DB and returning callDetailsId
							int callDetailsId = callServ.createCallDetails(callDetails);
							callDetails.setCallDetailsId(callDetailsId);

							// Updating call details cache for given entry
							List<CallDetails> callDetailsList = callCache.get(Long.parseLong(customerPhoneNo));

							/*
							 * if there is no entry of given customer phone number then it will create new
							 * list of callDetails and then inserting entry
							 */
							if (callDetailsList == null) {

								callDetailsList = new ArrayList<>();
							}

							callDetailsList.add(callDetails);
							callCache.put(Long.parseLong(customerPhoneNo), callDetailsList);

							System.out.println(
									"Call details added. \nDo you want to add more call details [Y]es or [N]o?");
							String choice = scanner.nextLine();

							if (choice.equals("Y") || choice.equals("y")) {
								continue;
							} else {
								break;
							}

						}
						break;
					} else {

						System.out.println(mb.getMessage("customer_not_exist"));
						break;
					}

				} else {

					System.out.println(mb.getMessage("invalid_phone_no"));
					continue;
				}

			}
		} catch (CPException ex) {
			System.out.println(ex.toString());

		} catch (Exception e) {
			System.out.println("Customer creation failed");
			e.printStackTrace();
		}
	}

	// for module - printing phone bill for particular customer
	private static void printPhoneBill() {

		try {

			while (true) {

				System.out.println("Enter Customer phone number");
				String customerPhoneNo = scanner.nextLine();

				if (!isValidPhoneNumber(customerPhoneNo)) {

					throw new CPException("C001", mb.getMessage("invalid_phone_no"));
				}

				long phoneNo = Long.parseLong(customerPhoneNo);

				if (customerCache.containsKey(phoneNo)) {

					Customer customer = customerCache.get(phoneNo);

					List<CallDetails> callDetailsList = callCache.get(phoneNo);

					if (callDetailsList == null || callDetailsList.isEmpty()) {
						throw new CPException("101", mb.getMessage("call_details_not_exist"));

					}

					printBill(customer, callDetailsList);

					break;

				} else {
					System.out.println("Customer details does not exist to print bill. "
							+ "\nDo you want to proceed for another customer? [Y]es/ [N]o.?");

					String choice = scanner.nextLine();
					if (choice.equals("Y") || choice.equals("y")) {
						continue;
					} else {
						break;
					}
				}
			}

		} catch (CPException ex) {
			System.out.println(ex.toString());
		}
	}

}
