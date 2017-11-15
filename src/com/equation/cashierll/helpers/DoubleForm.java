package com.equation.cashierll.helpers;

import java.text.DecimalFormat;

/**
 *
 * @author Wellington
 */
public class DoubleForm {

	public double form(double num) {
		DecimalFormat df = new DecimalFormat("0.00");
		double n = Double.parseDouble(df.format(num));
		return n;
	}
}