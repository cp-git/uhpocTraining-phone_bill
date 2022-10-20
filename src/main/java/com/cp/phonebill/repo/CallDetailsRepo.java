package com.cp.phonebill.repo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cp.phonebill.entities.CallDetails;
import com.cp.phonebill.helper.DBManager;

public class CallDetailsRepo {
	Statement stmt = null;
	PreparedStatement pstmt = null;
	Connection con = null;
	DBManager dbm = null;
	ResultSet rs = null;
	List<CallDetails> callDetailsList = null;
	CustomerRepo customerRepo = null;

	public HashMap<Long, List<CallDetails>> getAllCallDetails() {

		String getQuery = "SELECT * FROM call_details ORDER BY cust_accno";
		HashMap<Long, List<CallDetails>> callList = new HashMap<>();
		customerRepo = new CustomerRepo();
		try {
			dbm = DBManager.getDBManager();
			con = dbm.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getQuery);

			rs.next();
			do {
				int customerAccNoTemp = rs.getInt("cust_accno");

				callDetailsList = new ArrayList<>();
				int customerAccNo = 0;
				do {
					if (customerAccNoTemp == rs.getInt("cust_accno")) {
						customerAccNo = rs.getInt("cust_accno");
						int callDetailsId = rs.getInt("call_details_id");
						Date callDate = rs.getDate("call_date");
						long callPhoneNo = rs.getLong("call_phno");
						String callInOut = rs.getString("call_in_out");
						int callDuration = rs.getInt("call_duration");

						CallDetails call = new CallDetails(callDetailsId, callDate, callPhoneNo, callInOut,
								callDuration, customerAccNo);

						callDetailsList.add(call);

					} else {

						break;
					}

				} while (rs.next());

				callList.put(customerRepo.getCustomerPhoneNoByAccountNo(customerAccNoTemp, con), callDetailsList);

				if (rs.isAfterLast()) {
					break;
				}

			} while (true);

//			System.out.println(callList);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			dbm.closeConnection(con);
		}
		return callList;

	}

	public int insertCallDetails(CallDetails callDetails) {

		String insertQuery = "INSERT INTO call_details(call_date,call_phno,call_in_out,call_duration,cust_accno) VALUES(?,?,?,?,?)";
		int callDetailsId = 0;
		try {
			dbm = DBManager.getDBManager();
			con = dbm.getConnection();
			pstmt = con.prepareStatement(insertQuery);

			pstmt.setDate(1, callDetails.getCallDate());
			pstmt.setLong(2, callDetails.getCallPhoneNo());
			pstmt.setString(3, callDetails.getCallInOut());
			pstmt.setInt(4, callDetails.getCallDuration());
			pstmt.setInt(5, callDetails.getCustomerAccNo());

			pstmt.execute();
			callDetailsId = getLastCallDetailsId(con);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			dbm.closeConnection(con);
		}

		return callDetailsId;

	}

	public int getLastCallDetailsId(Connection connection) {

		String getQuery = "select max(call_details_id) from call_details";
		int callDetailsId = 0;
		try {
			// dbm.printConSize();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(getQuery);
			while (rs.next()) {
				callDetailsId = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		// dbm.printConSize();
		return callDetailsId;

	}
}
