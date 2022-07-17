package com.maxxton.ticketmanagent.ticketmanagement_parent.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/* @author - Ashutosh Sahoo
*/
@Entity
@Table(name = "T_Ticket")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long ticket_no;
	String ticket_summary;
	String ticket_description;
	long ticket_assignedTo;
	long ticket_createdBy;
	int ticket_riskLevel;
	Date ticket_creationDate;
	Date ticket_lastUpdate;
	Date ticket_endDate;
	String ticket_comments;
	String ticket_status;
	String ticket_type;
	
	public long getTicket_no() {
		return ticket_no;
	}
	public void setTicket_no(long ticket_no) {
		this.ticket_no = ticket_no;
	}
	public String getTicket_summary() {
		return ticket_summary;
	}
	public void setTicket_summary(String ticket_summary) {
		this.ticket_summary = ticket_summary;
	}
	public String getTicket_description() {
		return ticket_description;
	}
	public void setTicket_description(String ticket_description) {
		this.ticket_description = ticket_description;
	}
	public long getTicket_assignedTo() {
		return ticket_assignedTo;
	}
	public void setTicket_assignedTo(long ticket_assignedTo) {
		this.ticket_assignedTo = ticket_assignedTo;
	}
	public long getTicket_createdBy() {
		return ticket_createdBy;
	}
	public void setTicket_createdBy(long ticket_createdBy) {
		this.ticket_createdBy = ticket_createdBy;
	}
	public int getTicket_riskLevel() {
		return ticket_riskLevel;
	}
	public void setTicket_riskLevel(int ticket_riskLevel) {
		this.ticket_riskLevel = ticket_riskLevel;
	}
	public Date getTicket_creationDate() {
		return ticket_creationDate;
	}
	public void setTicket_creationDate(Date ticket_creationDate) {
		this.ticket_creationDate = ticket_creationDate;
	}
	public Date getTicket_lastUpdate() {
		return ticket_lastUpdate;
	}
	public void setTicket_lastUpdate(Date ticket_lastUpdate) {
		this.ticket_lastUpdate = ticket_lastUpdate;
	}
	public Date getTicket_endDate() {
		return ticket_endDate;
	}
	public void setTicket_endDate(Date ticket_endDate) {
		this.ticket_endDate = ticket_endDate;
	}
	public String getTicket_comments() {
		return ticket_comments;
	}
	public void setTicket_comments(String ticket_comments) {
		this.ticket_comments = ticket_comments;
	}
	public String getTicket_status() {
		return ticket_status;
	}
	public void setTicket_status(String ticket_status) {
		this.ticket_status = ticket_status;
	}
	public String getTicket_type() {
		return ticket_type;
	}
	public void setTicket_type(String ticket_type) {
		this.ticket_type = ticket_type;
	}
	
	
	
	
}
