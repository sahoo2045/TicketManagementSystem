package com.maxxton.ticketmanagent.ticketmanagement_parent.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.InvalidIdException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/employee/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee emp) {
		
		Employee response = employeeService.createEmployee(emp);
		return new ResponseEntity<Employee>(response, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/employee/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> findAllEmployees() {

		List<Employee> response = employeeService.findAllEmployees();
		return new ResponseEntity<List<Employee>>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/employee/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> deleteEmployee(@PathVariable long id) {
		
		Long response = employeeService.deleteEmployeeById(id);
		return new ResponseEntity<Long>(response, HttpStatus.ACCEPTED);

	}
	
	@RequestMapping(value = "/employee/findById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> findEmployeeById(@PathVariable long id) {
		
		Employee response = employeeService.findEmployeeById(id);
		return new ResponseEntity<Employee>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/employee/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Employee> updateEmployeeById(@RequestBody Employee emp, @PathVariable long id) {
		if (emp.getEmp_id() != id) {
			throw new InvalidIdException("Invalid Id OR Employee_Id");
		}
		Employee response = employeeService.updateEmployeeById(emp , id);
		return new ResponseEntity<Employee>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/employee/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteAllEmployee() throws Exception {
		
			employeeService.deleteAllEmployee();
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.ACCEPTED);
		
	}
	
	
	
	
	
	
	

}