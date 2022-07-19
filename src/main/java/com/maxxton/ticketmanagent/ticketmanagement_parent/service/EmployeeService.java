package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public Employee createEmployee(Employee emp) {
		Employee response = employeeRepo.save(emp);
		
		Users userEntity = new Users();
		userEntity.setEmployee(response);
		userEntity.setUsername(emp.getEmp_name() + Math.random());
		userEntity.setPassword(emp.getEmp_name() + emp.getEmp_id() + "#");

		userRepo.save(userEntity);
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

		return empList;
	}

	public long deleteEmployeeById(long id) {
		
		try{
			employeeRepo.deleteById(id);
			return id;
		}catch (DataIntegrityViolationException exception) {
			throw new ConstraintViolationException("DataIntegrityViolationException", exception);
		}catch(Exception ex){
			throw new ItemNotFoundException("Data Unavailable", ex);
		}
		
	}

	
	public Employee findEmployeeById(Long id) {
		
		
		 Optional<Employee> emp_id = employeeRepo.findById(id);
		 Employee response = emp_id.get();
		 return response;
		 
	}

	public Employee updateEmployeeById(Employee emp, long id) {
		
		Optional<Employee> empEntity = employeeRepo.findById(id);
		if(empEntity.isPresent()) {
			
			Employee response = employeeRepo.save(emp);
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
		}
		catch(Exception ex){
			throw new ItemNotFoundException("Data Unavailable", ex);
		}
		
	}
}