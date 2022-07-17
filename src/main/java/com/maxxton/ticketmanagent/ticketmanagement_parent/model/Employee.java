package com.maxxton.ticketmanagent.ticketmanagement_parent.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "T_Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long emp_id;
	String emp_name;
	String emp_designation;
	String emp_type;

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
