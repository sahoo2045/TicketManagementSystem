package com.maxxton.ticketmanagent.ticketmanagement_parent.model;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/*
 * @author - Ashutosh Sahoo
 */

@Entity
@Table(name = "T_Ticket")
@JsonInclude(Include.NON_NULL)
public class Ticket {

	@Id
	@Column(name = "ticket_id")
	private long ticket_no;
	@NonNull
	private String ticket_summary;
	@NonNull
	private String ticket_description;
	@ManyToMany
	private Set<Employee> employee;
	@NonNull
	private long ticket_createdBy;
	@NonNull
	private int ticket_riskLevel;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date ticket_creationDate;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date ticket_lastUpdate;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date ticket_endDate;
	@OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE)
    private Collection<Comment> comments;
	@Enumerated(EnumType.STRING)
	@NonNull
	private TicketStatus ticket_status;
	@NonNull
	private String ticket_type;
	private double logWorkHours;
	@NonNull
	private boolean currentAssignee;

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

	public Set<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(Set<Employee> employee) {
		this.employee = employee;
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

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	public TicketStatus getTicket_status() {
		return ticket_status;
	}

	public void setTicket_status(TicketStatus ticket_status) {
		this.ticket_status = ticket_status;
	}

	public String getTicket_type() {
		return ticket_type;
	}

	public void setTicket_type(String ticket_type) {
		this.ticket_type = ticket_type;
	}

	public double getLogWorkHours() {
		return logWorkHours;
	}

	public void setLogWorkHours(double logWorkHours) {
		this.logWorkHours = logWorkHours;
	}

	public boolean isCurrentAssignee() {
		return currentAssignee;
	}

	public void setCurrentAssignee(boolean currentAssignee) {
		this.currentAssignee = currentAssignee;
	}
	
}
