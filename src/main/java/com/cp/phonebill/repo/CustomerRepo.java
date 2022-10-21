package com.cp.phonebill.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cp.phonebill.entities.Customer;
import com.cp.phonebill.helper.DBManager;

public class CustomerRepo {

	DBManager dbm = null;
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public int insertCustomerDetails(Customer customer) {

		String insertQuery = "INSERT INTO customer(cust_PhNo,cust_name, cust_address1, cust_address2, cust_city, cust_state, cust_pincode) VALUES(?,?,?,?,?,?,?)";
		dbm = DBManager.getDBManager();
		con = dbm.getConnection();
		int customerAccNo = 0;
		try {
			pstmt = con.prepareStatement(insertQuery);
			pstmt.setLong(1, customer.getCustomerPhoneNo());
			pstmt.setString(2, customer.getCustomerName());
			pstmt.setString(3, customer.getCustomerAddress1());
			pstmt.setString(4, customer.getCustomerAddress2());
			pstmt.setString(5, customer.getCustomerCity());
			pstmt.setString(6, customer.getCustomerState());
			pstmt.setInt(7, customer.getCustomerPincode());

			pstmt.execute();
			dbm.printConSize();
			customerAccNo = getNewestAccountNumber(con);
			dbm.printConSize();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbm.closeConnection(con);
		}

		return customerAccNo;
	}

	public int getNewestAccountNumber(Connection connection) {
		// TODO Auto-generated method stub
		String getQuery = "select max(cust_accno) from customer";
		int customerAccNo = 0;
		try {
			// dbm.printConSize();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(getQuery);
			while (rs.next()) {
				customerAccNo = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		// dbm.printConSize();
		return customerAccNo;
	}

	public List<Customer> getAllCustomerDetails() {
		// TODO Auto-generated method stub
		String getQuery = "SELECT * FROM customer";
		dbm = DBManager.getDBManager();
		List<Customer> custList = new ArrayList<>();
		try {
			con = dbm.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getQuery);
			while (rs.next()) {
				int customerAccNo = rs.getInt("cust_accno");
				long customerPhoneNo = rs.getLong("cust_phno");
				String customerName = rs.getString("cust_name");
				String customerAddress1 = rs.getString("cust_address1");
				String customerAddress2 = rs.getString("cust_address2");
				String customerCity = rs.getString("cust_city");
				String customerState = rs.getString("cust_state");
				int customerPincode = rs.getInt("cust_pincode");

				Customer cust = new Customer(customerAccNo, customerName, customerPhoneNo, customerAddress1,
						customerAddress2, customerCity, customerState, customerPincode);

				custList.add(cust);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbm.closeConnection(con);
		}
		return custList;
	}

	public long getCustomerPhoneNoByAccountNo(int customerAccNoTemp, Connection connection) {

		if (connection == null) {
			connection = dbm.getConnection();
		}

		String getQuery = "SELECT cust_phno FROM customer WHERE cust_accno = " + customerAccNoTemp + "";
		long customerPhoneNo = 0;
		try {
			// dbm.printConSize();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(getQuery);
			while (rs.next()) {
				customerPhoneNo = rs.getLong(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		// dbm.printConSize();
		return customerPhoneNo;

	}
}
