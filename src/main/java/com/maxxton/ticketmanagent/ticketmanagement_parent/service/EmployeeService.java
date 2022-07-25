package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ConstraintViolationException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Users;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.EmployeeRepo;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.UserRepo;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	UserRepo userRepo;
	
	Logger logger = LoggerFactory.getLogger(UserService.class);

	public Employee createEmployee(Employee emp) {

		emp.setEmp_id(ThreadLocalRandom.current().nextInt(100000, 1000000));
		Employee response = employeeRepo.save(emp);
		
		Users userEntity = new Users();
		userEntity.setEmployee(response);
		userEntity.setUsername(emp.getEmp_name() + emp.getEmp_id() + "#");
		userEntity.setPassword(emp.getEmp_name() + ThreadLocalRandom.current().nextInt(100000, 1000000));

		userRepo.save(userEntity);
		logger.info("Employee created successfully with user credentials...");
		
		return response;
	}

	public List<Employee> findAllEmployees() {

		Iterable<Employee> response = employeeRepo.findAll();
		List<Employee> empList = new ArrayList<>();
		for (Employee em : response) {
			Employee m = new Employee();
			m.setEmp_id(em.getEmp_id());
			m.setEmp_name(em.getEmp_name());
			m.setEmp_designation(em.getEmp_designation());
			empList.add(m);
		}
		
		logger.info("List of all Employees");
		return empList;
	}

	public long deleteEmployeeById(long id) {
		
		try{
			employeeRepo.deleteById(id);
			logger.info("Employee Deleted");
			return id;
		}catch (DataIntegrityViolationException exception) {
			logger.error("Data Integrity Violation");
			throw new ConstraintViolationException("DataIntegrityViolationException", exception);
		}catch(Exception ex){
			logger.error("Employee not found");
			throw new ItemNotFoundException("Data Unavailable", ex);
		}
		
		
	}

	
	public Employee findEmployeeById(Long id) {
		
		
		 Optional<Employee> emp_id = employeeRepo.findById(id);
		 Employee response = emp_id.get();
		 logger.info("Employee found");
		 return response;
		 
		 
	}

	public Employee updateEmployeeById(Employee emp, long id) {
		
		Optional<Employee> empEntity = employeeRepo.findById(id);
		if(empEntity.isPresent()) {
			
			Employee response = employeeRepo.save(emp);
			logger.info("Employee updated successfully");
			return response;
		}
		else
		{
			throw new ItemNotFoundException("Item not found");
		}
		
	}

	public void deleteAllEmployee() {

		try{
			employeeRepo.deleteAll();
			logger.info("All employees deleted...");
		}
		catch(Exception ex){
			logger.warn("Could not find more employees to delete...");
			throw new ItemNotFoundException("Data Unavailable", ex);
		}
		
	}
}