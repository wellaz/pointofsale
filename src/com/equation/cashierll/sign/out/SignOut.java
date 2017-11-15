package com.equation.cashierll.sign.out;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.equation.cashierll.audio.Click;
import com.equation.cashierll.helpers.SetDateCreated;
import com.equation.cashierll.sign.duration.HeartBeat;
import com.equation.cashierll.sign.in.Monitor;
import com.equation.cashierll.temporarydata.delete.DeleteTMPTableData;

/**
 *
 * @author Wellington
 */

public class SignOut {
	Statement stm;
	ResultSet rs;

	public SignOut(Statement stm, ResultSet rs) {
		this.rs = rs;
		this.stm = stm;
	}

	public void signOff() {
		deleteTemporaryData();
		postSignOffDetails();
		new Click().closeappPlay();
	}

	public void deleteTemporaryData() {
		DeleteTMPTableData delete = new DeleteTMPTableData(rs, stm);
		delete.deleteTemporaryData();
	}

	public String getUsername() {
		return Monitor.users.get(Monitor.users.size() - 1);
	}

	public String getFullname() {
		return Monitor.fullname.get(Monitor.fullname.size() - 1);
	}

	public String getDate() {
		return new SetDateCreated().getDate();
	}

	public String getTime() {
		return new SetDateCreated().getTime();
	}

	private void postSignOffDetails() {
		String query = "INSERT INTO sign_out(username,full_name,date,time,duration)VALUES('" + getUsername() + "','"
				+ getFullname() + "','" + getDate() + "','" + getTime() + "','" + HeartBeat.getDuration() + "')";
		try {
			stm.execute(query);
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
	}
}