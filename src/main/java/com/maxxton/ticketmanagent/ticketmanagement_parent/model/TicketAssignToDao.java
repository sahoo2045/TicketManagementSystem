package com.maxxton.ticketmanagent.ticketmanagement_parent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ticketAssignedTo")
@JsonInclude(Include.NON_NULL)
public class TicketAssignToDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "assignedId")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ticketId", referencedColumnName = "ticketId")
	@NotNull
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Ticket ticket;
	@OneToOne
	@JoinColumn(name = "employeeId")
	@NonNull
	private Employee employee;
	@Column(name = "logWorkHours")
	private double logWorkHours;
	@NonNull
	@Column(name = "currentAssignee")
	private boolean currentAssignee;

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
