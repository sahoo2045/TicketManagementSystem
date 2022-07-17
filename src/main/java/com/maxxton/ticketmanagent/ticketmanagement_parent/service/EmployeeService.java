package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.EmployeeRepo;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo employeeRepo;

	public Employee createEmployee(Employee emp) {

		Employee response = employeeRepo.save(emp);
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
			m.setEmp_type(em.getEmp_type());
			empList.add(m);
		}

		return empList;
	}

	public long deleteEmployeeById(long id) {
		
		try{
			employeeRepo.deleteById(id);
			return id;
		}
		catch(Exception ex){
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