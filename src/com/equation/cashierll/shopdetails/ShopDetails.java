package com.equation.cashierll.shopdetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Wellington
 */

public class ShopDetails {
	ResultSet rs;
	Statement stm;

	public ShopDetails(ResultSet rs, Statement stm) {
		this.rs = rs;
		this.stm = stm;
	}

	// getting the shop name
	public String getShopName() {
		String query = "SELECT branch_name FROM client_name";
		String name = null;
		try {
			rs = stm.executeQuery(query);
			if (rs.first()) {
				name = rs.getString(1);
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return name;
	}

	// getting the contact details
	public String getClientContact() {
		String query = "SELECT contact FROM client_name ";
		String name = null;
		try {
			rs = stm.executeQuery(query);
			if (rs.first()) {
				name = rs.getString(1);
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return name;
	}

	// getting the shop logo as bytes array
	public byte[] getClientLogo() {
		String query = "SELECT logo FROM client_name ";
		byte[] image = null;
		try {
			rs = stm.executeQuery(query);
			if (rs.first()) {
				byte[] data = rs.getBytes(1);
				image = data;
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return image;
	}
}