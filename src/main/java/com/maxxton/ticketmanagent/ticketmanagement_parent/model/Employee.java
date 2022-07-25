package com.maxxton.ticketmanagent.ticketmanagement_parent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/*
 * @author - Ashutosh Sahoo
 */

@Entity
@Table(name = "t_employee")
@JsonInclude(Include.NON_NULL)
public class Employee {

	@Id
	@Column(name = "emp_id")
	private long emp_id;
	@NonNull
	private String emp_name;
	@NonNull
	private String emp_designation;

	public long getEmp_id() {

		return emp_id;
	}

	public void setEmp_id(long emp_id) {

		this.emp_id = emp_id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getEmp_designation() {
		return emp_designation;
	}

	public void setEmp_designation(String emp_designation) {
		this.emp_designation = emp_designation;
	}

	/*
	 * public Set<Ticket> getTicket() { return ticket; }
	 * 
	 * public void setTicket(Set<Ticket> ticket) { this.ticket = ticket; }
	 */

}
