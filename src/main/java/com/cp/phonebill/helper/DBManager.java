package com.cp.phonebill.helper;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBManager {

	private static DBManager dbManager = null;
	private List<Connection> conManager = null;
	private Properties props = null;

	/**
	 * Private constructor
	 */
	private DBManager() {

		loadConnProperty();
		initialization();

	}

	/**
	 * initialize the db pool
	 */

	private void initialization() {
		int maxCon = Integer.parseInt(props.getProperty("maxcon"));
		conManager = new ArrayList<Connection>();
		for (int i = 0; i < maxCon; i++) {
			conManager.add(createConnection());
		}
	}

	private Connection createConnection() {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(props.getProperty("dburl") + props.getProperty("dbname"),
					props.getProperty("dbusername"), props.getProperty("dbpassword"));
		} catch (Exception exp) {
			exp.printStackTrace();

		}
		return con;
	}

	/**
	 * Method to reads DB properties from the resources/dbcon.properties file
	 */
	private void loadConnProperty() {
		FileInputStream fs;
		try {
			props = new Properties();
			fs = new FileInputStream("src/main/resources/db.properties");
			props.load(fs);
			/*
			 * System.out.println(props.getProperty("dburl"));
			 * System.out.println(props.getProperty("dbname"));
			 * System.out.println(props.getProperty("dbusername"));
			 * System.out.println(props.getProperty("dbpassword"));
			 * System.out.println(props.getProperty("maxcon"));
			 */
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * To get the DB Manager instance
	 * 
	 * @return DBManager
	 * @throws CPException
	 */
	public static DBManager getDBManager() {
		if (null == dbManager) {
			dbManager = new DBManager();
		}
		return dbManager;
	}

	public Connection getConnection() {
		Connection con = null;
		if (conManager.size() > 0) {
			con = conManager.get(0);
			conManager.remove(0);
			try {
				if (con.isClosed()) {
					con = createConnection();
				}
			} catch (Exception exp) {
				exp.printStackTrace();

			}
		} else {
			// throw new CPException("001", mb.getMessage("001"));
		}
		// printConSize();
		return con;
	}

	public void closeConnection(Connection con) {
		conManager.add(con);
		// printConSize();
	}

	public void cleanPool() {
		int maxCon = conManager.size();
		for (int i = 0; i < maxCon; i++) {
			Connection con = conManager.get(0);
			conManager.remove(0);
			System.out.println(conManager.size());
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		printConSize();
	}

	public void printConSize() {
		System.out.println(" Available Connection ... " + conManager.size());
	}

}
