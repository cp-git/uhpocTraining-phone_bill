package com.cp.phonebill.repo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.cp.phonebill.entities.CallDetails;
import com.cp.phonebill.helper.DBManager;

public class CallDetailsRepo {
	Statement stmt = null;
	Connection con = null;
	DBManager dbm = null;
	ResultSet rs = null;

	public HashMap<Integer, List<CallDetails>> getAllCallDetails() {

		String getQuery = "SELECT * FROM call_details ORDER BY cust_accno";
		HashMap<Integer, List<CallDetails>> callList = new HashMap<>();
		List<CallDetails> callDetailsList = new ArrayList<>();
		try {
			dbm = DBManager.getDBManager();
			con = dbm.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getQuery);

			rs.next();
			while (rs.isLast()) {
				int customerAccNo = 0;
				int customerAccNoTemp = rs.getInt("cust_accno");
				callDetailsList.clear();
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
						System.out.println(call.toString());
					} else {
						System.out.println();
						break;

					}
					System.out.println("**" + customerAccNoTemp + " **" + customerAccNo);
				} while (rs.next());
				callList.put(customerAccNo, callDetailsList);
				System.out.println(callDetailsList);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbm.closeConnection(con);
		}
		return callList;

	}
}
