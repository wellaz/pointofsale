package com.equation.cashierll.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Wellington
 */

public class GetProductNames {
	ResultSet rs;
	Statement stm;

	public GetProductNames(ResultSet rs, Statement stm) {
		this.stm = stm;
		this.rs = rs;

	}

	public ArrayList<String> getProductName() {
		ArrayList<String> array = new ArrayList<>();
		String find = "SELECT product_name FROM commonproducts";
		try {
			rs = stm.executeQuery(find);
			while (rs.next()) {
				array.add(rs.getString(1));
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return array;
	}

	public String getProductName(String barcode_number) {
		String name = null;
		String query = "SELECT product_name FROM commonproducts WHERE barcode_number = '" + barcode_number + "'";
		try {
			rs = stm.executeQuery(query);
			if (rs.next()) {
				name = rs.getString(1);
			} else {
				name = "Product name not found!";
			}

		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return name;
	}
}