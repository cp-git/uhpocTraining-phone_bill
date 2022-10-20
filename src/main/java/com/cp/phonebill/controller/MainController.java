package com.cp.phonebill.controller;

import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.cp.phonebill.entities.CallDetails;
import com.cp.phonebill.entities.Customer;
import com.cp.phonebill.helper.DBManager;
import com.cp.phonebill.impl.CallDetailsServiceImpl;
import com.cp.phonebill.impl.CustomerServiceImpl;
import com.cp.phonebill.services.CallDetailsService;
import com.cp.phonebill.services.CustomerService;

public class MainController {
	static Properties props = null;
	static HashMap<Long, Customer> customerCache = null;
	static HashMap<Long, List<CallDetails>> callCache = null;

	public static void main(String[] args) {

		// Loading company property file and initializing cache
		loadCompProperty();
		loadCache();

		while (true) {

			// Main menu
			System.out.println("********* Main Menu ***********");
			System.out.println("1. Create Customer Details.");
			System.out.println("2. Add Call Details");
			System.out.println("3. Print Phone Bill");
			System.out.println("4. Exit");
			System.out.println("Please enter your option 1, 2, 3 or 4");

			// scanner class get inputs from console
			Scanner scanner = new Scanner(System.in);
			int option = 0; // for option selected by user

			try {
				option = scanner.nextInt();
				scanner.nextLine();
			} catch (Exception ex) {
				option = 0;
			}

			switch (option) {
			// Adding customer details
			case 1:
				CustomerService customerServ = new CustomerServiceImpl();
				while (true) {

					String customerName = null;
					String customerPhoneNo = null;
					String customerAddress1 = null;
					String customerAddress2 = null;
					String customerCity = null;
					String customerState = null;
					int customerPincode = 0;

					System.out.println("Enter customer name");
					customerName = scanner.nextLine();

					System.out.println("Enter customer phone no");
					customerPhoneNo = scanner.nextLine();

					if (isValidPhoneNumber(customerPhoneNo)) {
						// System.out.println("Please re-enter details");
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
						customerPincode = scanner.nextInt();
						scanner.nextLine();

						Customer customer = new Customer(customerName, Long.parseLong(customerPhoneNo),
								customerAddress1, customerAddress2, customerCity, customerState, customerPincode);

						int customerAccNo = customerServ.createCustomer(customer);
						System.out.println(customerAccNo);
						customerCache.put(Long.parseLong(customerPhoneNo), customer);
						System.out.println("Customer details added successfully"
								+ "\nDo you want to add another Customer [Y]es or [N]o?");
						String choice = scanner.nextLine();

						if (choice.equals("Y") || choice.equals("y")) {
							continue;
						} else {
							break;
						}

					} else {
						System.out.println("Please re-enter details because mobile number is not valid");
						continue;
					}

				}

				break;

			// Adding call details of customer
			case 2:

				String customerPhoneNo = "";
				String date = "";
				String callPhoneNo = "";
				String callInOut = "";
				int callDuration = 0;
				Date callDate = null;

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

								System.out.println("Error :  Incorrect date or format");
//								ex.printStackTrace();
								continue;
							}

							while (true) {

								System.out.println("Enter phone number");
								callPhoneNo = scanner.nextLine();

								if (isValidPhoneNumber(callPhoneNo)) {

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

									break;
								} else {

									System.out.println("Please re-enter details because phone number is not valid");
									continue;
								}
							}

						} else {

							System.out.println("Customer details does not exist. ");
							break;
						}

					} else {

						System.out.println("Please re-enter details because mobile number is not valid");
						continue;
					}

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

					System.out.println("Call details added. \nDo you want to add more call details [Y]es or [N]o?");
					String choice = scanner.nextLine();

					if (choice.equals("Y") || choice.equals("y")) {
						continue;
					} else {
						break;
					}

				}
				break;

			// printing phone bill
			case 3:

				break;
			case 4:
				DBManager dbm = DBManager.getDBManager();
				dbm.cleanPool();
				scanner.close();
				System.exit(0);
				break;

			default:
				System.out.println("Please enter option between 1 and 4");
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
			// TODO: handle exception
			return false;
		}

		return 7000000000L <= num && num <= 9999999999L;
	}
}
