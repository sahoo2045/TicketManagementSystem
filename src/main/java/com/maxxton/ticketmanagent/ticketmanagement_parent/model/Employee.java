package com.maxxton.ticketmanagent.ticketmanagement_parent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * @author - Ashutosh Sahoo
 */

@Entity
@Table(name = "T_Employee")
public class Employee {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	@Column(name = "emp_id")
	long emp_id;
	@NonNull
	String emp_name;
	@NonNull
	String emp_designation;

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

}
