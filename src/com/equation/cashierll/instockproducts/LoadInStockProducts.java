package com.equation.cashierll.instockproducts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Wellington
 */

public class LoadInStockProducts {
	Statement stm, stmt;
	ResultSet rs, rs1;

	public LoadInStockProducts(ResultSet rs, ResultSet rs1, Statement stm, Statement stmt) {
		this.rs = rs;
		this.stm = stm;
		this.stmt = stmt;
		this.rs1 = rs1;
	}

	public ArrayList<String> inStockItems() {
		ArrayList<String> array = new ArrayList<>();
		String query = "SELECT product_name FROM bulk_stock WHERE remaining > '" + 0 + "'";
		try {
			rs = stm.executeQuery(query);
			rs.last();
			int rows = rs.getRow();
			if (rows > 0) {
				String query1 = "SELECT product_name FROM bulk_stock WHERE remaining > '" + 0 + "'";
				rs1 = stmt.executeQuery(query1);
				while (rs1.next()) {
					String item = rs1.getString(1);
					array.add(item);
				}
			}
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		return array;
	}

	public boolean isItemInStock(String item, ArrayList<String> instockitems) {
		List<String> mnTypes = null;
		ArrayList<String> array = instockitems;
		int size = array.size();
		String[] dat = new String[size];
		for (int i = 0; i < size; i++)
			dat[i] = array.get(i);
		mnTypes = Arrays.asList(dat);
		return mnTypes.stream().anyMatch(t -> item.equals(t));
	}
}