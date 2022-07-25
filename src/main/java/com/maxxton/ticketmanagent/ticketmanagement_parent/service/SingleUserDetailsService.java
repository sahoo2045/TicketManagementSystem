package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Users;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.EmployeeRepo;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.UserRepo;
import com.maxxton.ticketmanagent.ticketmanagement_parent.security.UserDetailsImpl;

@Service
public class SingleUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EmployeeRepo employeeRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if(userRepo.count() == 0) {
			Users defaultUser = new Users();
			defaultUser.setUsername("admin");
			defaultUser.setPassword("admin");
			Employee defaultEmployee = new Employee();
			defaultEmployee.setEmp_name("admin");
			defaultEmployee.setEmp_designation("admin");
			defaultUser.setEmployee(defaultEmployee);
			employeeRepo.save(defaultEmployee);
			userRepo.save(defaultUser);
		}
		
		Users user = userRepo.findbyUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Error 404");
		} else {
			return new UserDetailsImpl(user);
		}

	}

}
