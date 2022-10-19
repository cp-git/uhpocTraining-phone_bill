package com.cp.phonebill.controller;

import java.io.FileInputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.cp.phonebill.entities.CallDetails;
import com.cp.phonebill.entities.Customer;
import com.cp.phonebill.impl.CallDetailsServiceImpl;
import com.cp.phonebill.impl.CustomerServiceImpl;
import com.cp.phonebill.repo.CallDetailsRepo;
import com.cp.phonebill.services.CallDetailsService;
import com.cp.phonebill.services.CustomerService;

public class MainController {
	static Properties props = null;
	static HashMap<Long, Customer> customerCache = null;
	static HashMap<Long, List<CallDetails>> callCache = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		loadCompProperty();
		loadCache();

		while (true) {
			System.out.println("============= Main Menu ============");
			System.out.println("1. Create Customer Details.");
			System.out.println("2. Add Call Details");
			System.out.println("3. Print Phone Bill");
			System.out.println("4. Exit");

			Scanner scanner = new Scanner(System.in);
			int option = 0;

			try {
				option = scanner.nextInt();
				scanner.nextLine();
			} catch (Exception ex) {
				// TODO: handle exception
				option = 0;
			}

			switch (option) {
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
							System.out.println("Customer already exist");
							System.out.println("Do you want to add another Customer [Y]es or [N]o?");
							String choice = scanner.nextLine();

							if (choice.equals("Y") || choice.equals("y")) {
								System.out.println("please enter correct input");
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
						System.out.println("Do you want to add another Customer [Y]es or [N]o?");
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

							System.out.println("Enter call details");
							System.out.println("Enter date - YYYY-MM-DD");
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

							System.out.println("customer details not exist");
							break;
						}

					} else {

						System.out.println("Please re-enter details because mobile number is not valid");
						continue;
					}

					Customer customer = customerCache.get(Long.parseLong(customerPhoneNo));

					// System.out.println("cd" + callDate);

					CallDetails callDetails = new CallDetails(callDate, Long.parseLong(callPhoneNo), callInOut,
							callDuration, customer.getCustomerAccNo());

					CallDetailsService callServ = new CallDetailsServiceImpl();
					int callDetailsId = callServ.createCallDetails(callDetails);
					callDetails.setCallDetailsId(callDetailsId);

					List<CallDetails> callDetailsList = callCache.get(Long.parseLong(customerPhoneNo));
					callDetailsList.add(callDetails);
					callCache.put(Long.parseLong(customerPhoneNo), callDetailsList);
					System.out.println(callCache);

					System.out.println("Do you want to add more call details [Y]es or [N]o?");
					String choice = scanner.nextLine();

					if (choice.equals("Y") || choice.equals("y")) {
						continue;
					} else {
						break;
					}

				}
				break;
			case 3:
				CallDetailsRepo callRepo = new CallDetailsRepo();
				System.out.println(callRepo.getAllCallDetails());
				break;
			case 4:
				scanner.close();
				System.exit(0);
				break;

			default:
				System.out.println("Please select valid option from 1 to 4");
				break;
			}
		}

	}

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

	private static void loadCache() {
		CustomerService custServ = new CustomerServiceImpl();
		customerCache = custServ.initializeCustomerCache();

		CallDetailsService callServ = new CallDetailsServiceImpl();
		callCache = callServ.initializeCallCache();

		System.out.println(callCache);
		System.out.println(customerCache);
	}

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
