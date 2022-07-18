package com.maxxton.ticketmanagent.ticketmanagement_parent.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.lang.NonNull;


@Entity
@Table(name = "T_Users", uniqueConstraints = {@UniqueConstraint(columnNames = {"emp_id"})})
public class Users {

	@Id
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee.emp_id")
	private Employee employee;
	String username;
	String password;
	
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
