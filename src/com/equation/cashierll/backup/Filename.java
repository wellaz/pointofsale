package com.equation.cashierll.backup;

import com.equation.cashierll.helpers.SetDateCreated;

/**
 *
 * @author Wellington
 */

public class Filename {
	public static String setDatabaseFileName() {
		return new SetDateCreated().getDate().replace("-", "_");
	}
}