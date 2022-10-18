package com.cp.phonebill.controller;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import com.cp.phonebill.entities.Customer;
import com.cp.phonebill.impl.CustomerServiceImpl;
import com.cp.phonebill.services.CustomerService;

public class MainController {
	static Properties props = null;
	static HashMap<Long, Customer> customerCache = null;

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
				System.out.println("Enter customer phone number");
				String customerPhoneNo = "";
				customerPhoneNo = scanner.nextLine();
				if (isValidPhoneNumber(customerPhoneNo)) {
					if (customerCache.containsKey(Long.parseLong(customerPhoneNo))) {
						System.out.println("Enter call details");
						System.out.println("Enter date - DD/MM/YYYY");
						String date = scanner.nextLine();

						while (true) {
							System.out.println("Enter phone number");
							String callPhoneNo = scanner.nextLine();
							if (isValidPhoneNumber(callPhoneNo)) {

								System.out.println("Enter [i]ncoming / [o]utgoing call");
								String callInOut = scanner.nextLine();

								System.out.println("Enter call duration");
								int callDuration = scanner.nextInt();
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
				break;
			case 3:
//				CallDetailsRepo callRepo = new CallDetailsRepo();
//				callRepo.getAllCallDetails();
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
		customerCache = new HashMap<>();
		customerCache = custServ.initializeCustomerCache();
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
