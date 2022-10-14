package com.cp.phonebill.entities;

import java.util.Date;

public class CallDetails {

	private int callDetailsId;
	private Date callDate;
	private long callPhoneNo;
	private char callInOut;
	private int callDuration;

	public CallDetails() {
		super();
	}

	public CallDetails(Date callDate, long callPhoneNo, char callInOut, int callDuration) {
		super();
		this.callDate = callDate;
		this.callPhoneNo = callPhoneNo;
		this.callInOut = callInOut;
		this.callDuration = callDuration;
	}

	public CallDetails(int callDetailsId, Date callDate, long callPhoneNo, char callInOut, int callDuration) {
		super();
		this.callDetailsId = callDetailsId;
		this.callDate = callDate;
		this.callPhoneNo = callPhoneNo;
		this.callInOut = callInOut;
		this.callDuration = callDuration;
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

	public char getCallInOut() {
		return callInOut;
	}

	public void setCallInOut(char callInOut) {
		this.callInOut = callInOut;
	}

	public int getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}

	@Override
	public String toString() {
		return "CallDetails [callDetailsId=" + callDetailsId + ", callDate=" + callDate + ", callPhoneNo=" + callPhoneNo
				+ ", callInOut=" + callInOut + ", callDuration=" + callDuration + "]";
	}

}
