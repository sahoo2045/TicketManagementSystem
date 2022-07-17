package com.maxxton.ticketmanagent.ticketmanagement_parent.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "T_Users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long emp_id;
	String username;
	String password;
	
	public long getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(long emp_id) {
		this.emp_id = emp_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
