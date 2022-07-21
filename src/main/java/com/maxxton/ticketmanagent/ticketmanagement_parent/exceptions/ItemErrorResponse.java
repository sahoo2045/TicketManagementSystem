package com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ItemErrorResponse {

	private int status;

	public ItemErrorResponse() {
	}

	private String message;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date timeStamp;

	public ItemErrorResponse(int status, String message, Date timeStamp) {
		this.status = status;
		this.message = message;
		this.timeStamp = timeStamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

}
