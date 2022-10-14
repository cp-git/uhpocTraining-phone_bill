package com.cp.phonebill.controller;

import java.sql.Connection;

import com.cp.phonebill.helper.DBManager;

public class MainController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("hey");
		Connection con = null;
		DBManager dbm = DBManager.getDBManager();
		con = dbm.getConnection();
		System.out.println(con);

	}

}
