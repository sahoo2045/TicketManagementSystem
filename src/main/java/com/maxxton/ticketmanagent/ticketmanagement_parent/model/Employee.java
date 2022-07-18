package com.maxxton.ticketmanagent.ticketmanagement_parent.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "T_Employee", uniqueConstraints = { @UniqueConstraint(columnNames = { "emp_id" }) })
public class Employee {

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long emp_id;
	String emp_name;
	String emp_designation;
	String emp_type;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, orphanRemoval = false)
	private List<Users> listUsers = new ArrayList<>();
	
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, orphanRemoval = false)
	private List<Ticket> listTickets = new ArrayList<>();

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

	public String getEmp_type() {
		return emp_type;
	}

	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}

}
