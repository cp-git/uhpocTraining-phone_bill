package com.cp.phonebill.entities;

import java.sql.Date;

public class CallDetails {

	private int callDetailsId;
	private Date callDate;
	private long callPhoneNo;
	private String callInOut;
	private int callDuration;
	private int customerAccNo;

	public CallDetails() {
		super();
	}

	public CallDetails(Date callDate, long callPhoneNo, String callInOut, int callDuration, int customerAccNo) {
		super();
		this.callDate = callDate;
		this.callPhoneNo = callPhoneNo;
		this.callInOut = callInOut;
		this.callDuration = callDuration;
		this.customerAccNo = customerAccNo;
	}

	public CallDetails(int callDetailsId, Date callDate, long callPhoneNo, String callInOut, int callDuration,
			int customerAccNo) {
		super();
		this.callDetailsId = callDetailsId;
		this.callDate = callDate;
		this.callPhoneNo = callPhoneNo;
		this.callInOut = callInOut;
		this.callDuration = callDuration;
		this.customerAccNo = customerAccNo;
	}

	public int getCallDetailsId() {
		return callDetailsId;
	}

	public void setCallDetailsId(int callDetailsId) {
		this.callDetailsId = callDetailsId;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public long getCallPhoneNo() {
		return callPhoneNo;
	}

	public void setCallPhoneNo(long callPhoneNo) {
		this.callPhoneNo = callPhoneNo;
	}

	public String getCallInOut() {
		return callInOut;
	}

	public void setCallInOut(String callInOut) {
		this.callInOut = callInOut;
	}

	public int getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}

	public int getCustomerAccNo() {
		return customerAccNo;
	}

	public void setCustomerAccNo(int customerAccNo) {
		this.customerAccNo = customerAccNo;
	}

	@Override
	public String toString() {
		return "CallDetails [callDetailsId=" + callDetailsId + ", callDate=" + callDate + ", callPhoneNo=" + callPhoneNo
				+ ", callInOut=" + callInOut + ", callDuration=" + callDuration + ", customerAccNo=" + customerAccNo
				+ "]";
	}

}
